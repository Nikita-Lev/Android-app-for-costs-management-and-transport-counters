package com.example.Bustonks

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ElemAdapter(private val listArr: ArrayList<Lister>, private val context: Context) :
    RecyclerView.Adapter<ElemAdapter.ViewHolder1>() {

    class ViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
        val summ_id = view.findViewById<TextView>(R.id.summ)
        val time_id = view.findViewById<TextView>(R.id.time)

        fun bind(listI: Lister, context: Context) {
            summ_id.setTextColor(Color.parseColor(listI.colo))
            summ_id.text = listI.summ_i
            time_id.text = listI.time_i

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {
        val inflater = LayoutInflater.from(context)
        return ViewHolder1(inflater.inflate(R.layout.item_layout, parent, false))
    }

    override fun onBindViewHolder(hold: ViewHolder1, position: Int) {
        var listIte = listArr.get(position)
        hold.bind(listIte, context)
    }

    override fun getItemCount(): Int {
        return listArr.size
    }


}
