package com.example.Bustonks

import android.content.Context
import android.content.SharedPreferences
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_tr_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Adapter_tr(listArray: ArrayList<trans_arr>, context: Context): RecyclerView.Adapter<Adapter_tr.ViewHolder>(){

    var listArrayR = listArray
    var contextR = context
    private lateinit var Fli: SharedPreferences //Объявления файла данных

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name = view.findViewById<EditText>(R.id.name_tr_ed)
        val kol = view.findViewById<TextView>(R.id.counter_txt)

        fun bind1(lis: trans_arr, context: Context){
            name.setText(lis.name_tr)
            kol.text = lis.kolvo.toString()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_tr.ViewHolder {
        val inflater = LayoutInflater.from(contextR)
        return ViewHolder(inflater.inflate(R.layout.item_tr_layout, parent, false))
    }



    override fun onBindViewHolder(holder: Adapter_tr.ViewHolder, position: Int) {
        Fli = contextR.getSharedPreferences("Transp", Context.MODE_PRIVATE)
        val edd = Fli.edit()
        var ii: Int? = 0

        var listItem = listArrayR.get(position)

        //Сохраняем название маршрута
        holder.itemView.name_tr_ed.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {

                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                    listItem.name_tr = holder.itemView.name_tr_ed.text.toString()
                    edd.putString(listItem.ind.toString() + "_" + position.toString() + "_name", listItem.name_tr).apply()

                    return true
                }
                return false
            }
        }
        )


        holder.itemView.plus_but.setOnClickListener(View.OnClickListener {
            listItem.kolvo += 1
            notifyItemChanged(position)

            ii = listItem.ind
            edd.putInt(ii.toString() + "_" + position.toString() + "_kolvo", listItem.kolvo)
            edd.putInt(ii.toString() + "_sum", Fli.getInt(ii.toString() + "_sum", 0) + 1)

            //Запоминаем дату последнего изменения
            edd.putString(ii.toString() + "_date", (SimpleDateFormat("dd.MM.yy H:mm").format(
                Calendar.getInstance().time) + "— " + listItem.name_tr)).apply()
        })
        holder.itemView.min_but.setOnClickListener(View.OnClickListener {
            listItem.kolvo -= 1
            notifyItemChanged(position)

            ii = listItem.ind
            edd.putInt(ii.toString() + "_" + position.toString() + "_kolvo", listItem.kolvo)
            edd.putInt(ii.toString() + "_sum", Fli.getInt(ii.toString() + "_sum", 0) - 1)

            //Запоминаем дату последнего изменения
            edd.putString(ii.toString() + "_date", (SimpleDateFormat("dd.MM.yy H:mm").format(
                Calendar.getInstance().time) + "— " + listItem.name_tr)).apply()
        })

        holder.bind1(listItem, contextR)
    }

    override fun getItemCount(): Int {
        return listArrayR.size
    }

    //Обновить recycler массивом Mass
    fun updater(Mass: List<trans_arr>){
        listArrayR.clear()
        listArrayR.addAll(Mass)
        notifyDataSetChanged()
    }

}