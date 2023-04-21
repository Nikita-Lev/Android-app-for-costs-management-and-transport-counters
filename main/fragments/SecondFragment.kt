package com.example.Bustonks.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Bustonks.Dates
import com.example.Bustonks.MyAdapter
import com.example.Bustonks.R
import kotlinx.android.synthetic.main.fragment_second.view.*


class SecondFragment : Fragment(), View.OnClickListener {

    private lateinit var prefs: SharedPreferences
    var Spis = ArrayList<Dates>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var View2 = inflater.inflate(R.layout.fragment_second, container, false)
        //Слушатель нажатия кнопки
        var myButton = View2.findViewById(R.id.clearr) as Button
        myButton.setOnClickListener(this)

        prefs = this.activity!!.getSharedPreferences("settings", Context.MODE_PRIVATE)

        if (prefs.contains("count_date")) {
            // Получаем данные из файла
            for (j in (prefs.getInt("count_date", 0) - 1) downTo 0) {
                var dat: String = prefs.getString("date$j", " ").toString() //Получаем j-ю дату
                Spis.add(Dates(dat))
            }
            //Передаём адаптеру
            View2.Spisok.layoutManager = LinearLayoutManager(this.activity)
            View2.Spisok.adapter = MyAdapter(Spis, this.activity!!)


        }
        return View2
    }


    //Очистка файла данных
    override fun onClick(p0: View?) {
        prefs.edit().clear().apply()
        this.activity!!.recreate()
        //sec_frag.Spisok.adapter!!.notifyDataSetChanged()
        }
}