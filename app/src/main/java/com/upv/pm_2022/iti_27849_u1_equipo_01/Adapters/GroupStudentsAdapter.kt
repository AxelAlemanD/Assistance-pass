package com.upv.pm_2022.iti_27849_u1_equipo_01.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Assistance
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student
import com.upv.pm_2022.iti_27849_u1_equipo_01.R
import java.text.SimpleDateFormat
import java.util.*

class GroupStudentsAdapter(private val context: Context, private val groups: MutableList<Group>) : BaseAdapter() {
    private lateinit var lvGroupName: TextView
    private lateinit var lvNumberStudents: TextView
    private lateinit var assistanceBtn: Button
    private lateinit var deleteBtn: Button

    override fun getCount(): Int {
        return groups.size
    }
    override fun getItem(position: Int): Group {
        return groups[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.item_group, parent, false)
        lvGroupName = convertView.findViewById(R.id.lvGroupName)
        lvNumberStudents = convertView.findViewById(R.id.lvNumberStudents)
        assistanceBtn = convertView.findViewById(R.id.assistanceBtn)
        deleteBtn = convertView.findViewById(R.id.deleteBtn)

        lvGroupName.text = groups[position].name
        lvNumberStudents.text = groups[position].getTotalStudentsInThisGroup().toString()


        return convertView
    }
}