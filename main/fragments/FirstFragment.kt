package com.example.Bustonks.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.Bustonks.R
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import java.text.SimpleDateFormat
import java.util.*


class FirstFragment : Fragment(){
    private lateinit var prefs: SharedPreferences
    private lateinit var vrasp: SharedPreferences
    private var sum: Float = 0.00f
    var indid : Boolean = false
    var refr: Float = 0.0f
    var kolelem: Int = 0
    var koldat: Int = 0 // Количество дат
    var dd: String = " "
    var Perv: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Значение "в распоряжении"
        vrasp = this.activity!!.getSharedPreferences("Total", Context.MODE_PRIVATE)

        var vv = inflater.inflate(R.layout.fragment_first, container, false)

        vv.valu.setSelection(1)
        prefs = this.activity!!.getSharedPreferences("settings", Context.MODE_PRIVATE)
        // Передача текста в TextView после нажатия Enter
        vv.valu.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                Perv = vv.valu.text.toString()

                //Установка цвета вводимого текста
                if (Perv != "")
                    vv.valu.setTextColor(Color.parseColor(Inst_color(Perv)))

                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    //Получение значение
                    refr = Perv.toFloat()

                    //Текущая дата и время
                    var dat =
                        SimpleDateFormat("dd MM yyyy H:mm").format(Calendar.getInstance().time)
                    val time = dat.substring(11) //Время H:mm
                    dat = month(dat)  //Текущая дата dd MM yy

                    //Сохраняем значения
                    val ed = prefs.edit()

                    koldat = prefs.getInt("count_date", 0)  //Получаем количество дат
                    dd = prefs.getString("date" + (koldat - 1).toString(), " ")
                        .toString()   // Получаем последнюю дату
                    //Если дата новая, то увеличиваем количество и добавляем дату
                    if (dat != dd) {
                        ed.putString("date$koldat", dat)
                        dd = dat
                        koldat += 1
                        ed.putInt("count_date", koldat)
                        //Для обновления activity при первой операции в текущий дент
                        indid = true

                    }
                    kolelem = prefs.getInt(
                        "count_elem$dat",
                        0
                    )       //Получаем количество элементов даты dat
                    ed.putFloat("value_$dat$kolelem", refr)           //Добавляем новый элемент даты
                    ed.putString(
                        "time_$dat$kolelem",
                        time
                    )           //Добавляем время нового элемента даты
                    kolelem += 1
                    ed.putInt("count_elem$dat", kolelem).apply()


                    //Обновление edit Text
                    vv.valu.setText("-")
                    vv.valu.setSelection(1)

                    //Изменение суммы
                    sum += refr
                    sum = round(sum)
                    vv.txt.text = "В распоряжении: " + sum.toString() + "₽"

                    //Обновление Истории операций
                    fragmentSendDataListener!!.onRefresh()

                    //Обновляем activity, если дата новая
                    if(indid == true){
                        indid = false
                        activity!!.recreate()
                    }
                    return true
                }
                return false
            }
        }
        )
        return vv
    }

    //Обновление Истории операций через Activity
    internal interface OnFragmentSendDataListener {
        fun onRefresh()
    }

    private var fragmentSendDataListener: OnFragmentSendDataListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentSendDataListener = try {
            context as OnFragmentSendDataListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString()
                        + " должен реализовывать интерфейс OnFragmentInteractionListener"
            )
        }
    }

    //Определение цвета в зависимости от знака числа
    private fun Inst_color(nam: String): String{
        if(nam[0] == '-')
            return "#F44336" //Красный
        return "#8BC34A" //Зелёный

    }

    private fun round(number: Float): Float {
        return (Math.round(number * 100.0) / 100.0).toFloat()
    }

    private fun month(mon: String): String{
        val nam: Array<String> = arrayOf(
            "янв",
            "фев",
            "мар",
            "апр",
            "мая",
            "июня",
            "июля",
            "авг",
            "сент",
            "окт",
            "ноя",
            "дек"
        )
        return """${mon.substringBefore(' ')} ${
            nam[(mon.substring(3).substringBefore(' ')).toInt() - 1]
        } ${mon.substring(6).substringBefore(' ')} ${"г."}"""
    }


    override fun onPause() {
        super.onPause()
        // Запоминаем данные
        val editor = vrasp.edit()
        sum = round(sum)
        editor.putFloat("Value", sum).apply()
    }

    override fun onResume() {
        super.onResume()

        if (vrasp.contains("Value")) {
            // Получаем сумму в распоряжении из файла
            sum = vrasp.getFloat("Value", 0.00f)
            // Выводим на экран данные из настроек
            txt.text = "В распоряжении: " + sum.toString() + "₽"
        }
    }
}