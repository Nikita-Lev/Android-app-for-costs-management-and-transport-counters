package com.example.Bustonks.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Bustonks.Adapter_tr
import com.example.Bustonks.R
import com.example.Bustonks.trans_arr
import kotlinx.android.synthetic.main.buss_fragment_1.view.*


class Buss : Fragment(){

    var Ind: Int = 0 //Индикатор выбранного вида транспорта
    var Adapt: Adapter_tr? = null
    private lateinit var Flile: SharedPreferences //Объявления файла данных

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var Vev = inflater.inflate(R.layout.buss_fragment_1, container, false)

        Flile = this.activity!!.getSharedPreferences("Transp", Context.MODE_PRIVATE)

        var list  = ArrayList<trans_arr>()

        list.addAll(filler(Ind))
        Vev.kind_tr.text = ("Автобусы: " + Flile.getInt(Ind.toString() + "_sum", 0).toString())

        //Установка последнего изменения
        Vev.dat_tr.text = ("Last " + Flile.getString(Ind.toString() + "_date", "Empty"))


        Vev.addTr.setOnClickListener {
            list.add( trans_arr(Ind, "", 0))
            Adapt?.notifyItemInserted(list.size-1)


            //Добавление нового элемента в файл
            val edd = Flile.edit()
            edd.putInt(Ind.toString() + "_size", list.size-1)
            edd.putString(Ind.toString() + "_" + (list.size - 1).toString() + "_name", "")
            edd.putInt(Ind.toString() + "_" + (list.size - 1).toString() + "_kolvo", 0).apply()

        }

        //Слушатели нажатий кнопок транспорта
        Vev.bus_but.setOnClickListener {
            Ind = 0
            Vev.kind_tr.text = ("Автобусы: " + Flile.getInt(Ind.toString() + "_sum", 0).toString())
            Vev.dat_tr.text = ("Last " + Flile.getString(Ind.toString() + "_date", "Empty"))
            Adapt?.updater(filler(Ind))
        }
        Vev.tram_but.setOnClickListener {
            Ind = 1
            Vev.kind_tr.text = ("Трамваи: " + Flile.getInt(Ind.toString() + "_sum", 0).toString())
            Vev.dat_tr.text = ("Last " + Flile.getString(Ind.toString() + "_date", "Empty"))
            Adapt?.updater(filler(Ind))
        }
        Vev.auto_but.setOnClickListener {
            Ind = 2
            Vev.kind_tr.text = ("Авто: " + Flile.getInt(Ind.toString() + "_sum", 0).toString())
            Vev.dat_tr.text = ("Last " + Flile.getString(Ind.toString() + "_date", "Empty"))
            Adapt?.updater(filler(Ind))
        }


        Vev.arr_group_tr.layoutManager = LinearLayoutManager(this.activity)
        Adapt = Adapter_tr(list, this.activity!!)
        Vev.arr_group_tr.adapter = Adapt

        //Добавление нового элемента

        return Vev
    }

    fun filler(IND: Int): ArrayList<trans_arr> {
        var Massik = ArrayList<trans_arr>()
        if (Flile.contains(IND.toString() + "_size")) {
            for (i in 0..Flile.getInt(IND.toString() + "_size", 0)) {
                var name = Flile.getString(IND.toString() + "_" + i.toString() + "_name", "") //Получаем название транспорта
                var kol_vo = Flile.getInt(IND.toString() + "_" + i.toString() + "_kolvo", 0) //Получаем кол-во поездок

                Massik.add(trans_arr(IND, name.toString(), kol_vo))
            }
        }
        return Massik
    }


}