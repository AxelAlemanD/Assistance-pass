package com.upv.pm_2022.iti_27849_u1_equipo_01.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Switch
import android.widget.TextView
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Assistance
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student
import com.upv.pm_2022.iti_27849_u1_equipo_01.R
import java.text.SimpleDateFormat
import java.util.*

class SingleStudentAdapter(private val context: Context, private val assitances: MutableList<Assistance>) : BaseAdapter() {
    private lateinit var assistanceDate: TextView
    private lateinit var assistanceStatus: TextView

    override fun getCount(): Int {
        return assitances.size
    }
    override fun getItem(position: Int): Assistance {
        return assitances[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_student, parent, false)

        assistanceDate = convertView.findViewById(R.id.assistanceDate)
        assistanceStatus = convertView.findViewById(R.id.assistanceStatus)

        assistanceDate.text = assitances[position].date

        if(assitances[position].is_late == 1){
            assistanceStatus.text = "Asistio"
            assistanceStatus.setTextColor(Color.parseColor("#00897B"))
        }else{
            assistanceStatus.text = "Falto"
            assistanceStatus.setTextColor(Color.parseColor("#E53935"))
        }


        return convertView
    }
}