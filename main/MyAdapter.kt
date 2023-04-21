package com.example.Bustonks


import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(private val listArray: ArrayList<Dates>, private val context: Context) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(vieww: View) : RecyclerView.ViewHolder(vieww) {
        val group_id = vieww.findViewById<TextView>(R.id.tv_name)

        val rec_elem: RecyclerView = vieww.findViewById(R.id.rv_member)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.group_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var listItem = listArray.get(position) //Текущий элемент массива дат
        holder.group_id.text = listItem.info_i       //Передача даты в TextView

        holder.rec_elem.layoutManager = LinearLayoutManager(context)
        holder.rec_elem.adapter = ElemAdapter(elements(listItem.info_i), context)
    }

    override fun getItemCount(): Int {
        return listArray.size
    }

    //Достаёт элементы даты из файла
    fun elements(dat: String): ArrayList<Lister> {
        var arrElem = ArrayList<Lister>()
        var prefs: SharedPreferences =
            context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        //Пробегаем по элементам даты j
        if (prefs.contains("count_date")) {
            for (k in (prefs.getInt("count_elem$dat", 0) - 1) downTo 0) {
                //Присвоение цвета
                var coll: String = "#FF000000" // Белый
                var per = (prefs.getFloat(
                    "value_$dat$k",
                    0.0f
                )).toString()  //Значение элемента k элемента даты j
                if (per.toFloat() > 0) {
                    coll = "#8BC34A" // Зелёный
                    per = "+$per" //Добавляет символ + к числу
                }
                if (per.toFloat() < 0)
                    coll = "#F44336" // Красный

                //Добавляем элемент в массив
                arrElem.add(Lister(per, prefs.getString("time_$dat$k", " ").toString(), coll))

            }
        }
        return arrElem
    }
}