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

class studentsAssistancesAdapter(private val context: Context, private val students: MutableList<Student>) : BaseAdapter() {
    private lateinit var tvStudentName: TextView
    private lateinit var tvGroupName: TextView
    private lateinit var tvAssistances: TextView
    private lateinit var tvFails: TextView

    override fun getCount(): Int {
        return students.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.item_student_list, parent, false)

        tvStudentName = convertView.findViewById(R.id.tvStudentName)
        tvGroupName = convertView.findViewById(R.id.tvGroupName)
        tvAssistances = convertView.findViewById(R.id.tvAssistances)
        tvFails = convertView.findViewById(R.id.tvFails)

        tvStudentName.text = students[position].first_name + " " + students[position].last_name
        tvGroupName.text = students[position].getGroupName()
        tvAssistances.text = students[position].getTotalStudentAssistances().toString()
        tvFails.text = students[position].getTotalStudentFauls().toString()

        return convertView
    }
}