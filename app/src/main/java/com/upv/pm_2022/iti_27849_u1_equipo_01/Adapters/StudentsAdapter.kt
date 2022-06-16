package com.upv.pm_2022.iti_27849_u1_equipo_01.Adapters

import android.content.Context
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

class studentsAdapter(private val context: Context, private val students: MutableList<Student>) : BaseAdapter() {
    private lateinit var fullname: TextView
    private lateinit var assistanceStudentSw: Switch
    private var asistances : MutableList<Assistance> = mutableListOf()
    val simpleDate = SimpleDateFormat("dd/M/yyyy")
    private val currentDate : String = simpleDate.format(Calendar.getInstance().time)

    override fun getCount(): Int {
        return students.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    fun getAssistances(): MutableList<Assistance>{
        return asistances
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.item_assistance, parent, false)
        fullname = convertView.findViewById(R.id.fullname)
        assistanceStudentSw = convertView.findViewById(R.id.assistanceStudentSw)
        fullname.text = " " + students[position].first_name + " " + students[position].last_name

        System.out.println(students[position])

        asistances.add(Assistance(null,
                                    currentDate,
                                    if(assistanceStudentSw.isChecked) 1 else 0,
                                    students[position].getGroupId(),
                                    students[position].id!!
                                )
                        )

        assistanceStudentSw.setOnCheckedChangeListener({ _ , isChecked ->
            asistances[position].is_late = if(isChecked) 1 else 0
        })

        return convertView
    }
}