package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group

class GroupDetails : AppCompatActivity(){

    lateinit var group: Group
    lateinit var groupName: TextView
    lateinit var btnAssistancePassGD: Button
    lateinit var registeredStudents: TextView
    lateinit var totalAttendancePasses: TextView
    lateinit var totalStudentsFailedDueToAbsence: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_details)
        setTitle("Ver grupo");

        showBackButton()

        groupName = findViewById(R.id.groupName)
        btnAssistancePassGD = findViewById(R.id.btnAssistancePassGD)
        registeredStudents = findViewById(R.id.registeredStudents)
        totalAttendancePasses = findViewById(R.id.totalAttendancePasses)
        totalStudentsFailedDueToAbsence = findViewById(R.id.totalStudentsFailedDueToAbsence)

        val intent = intent
        val recivedObject = intent.extras

        if (recivedObject != null) {
            group = recivedObject.getSerializable("group") as Group
            groupName.text = group.name
            registeredStudents.text = "0"
            totalAttendancePasses.text = "0"
            totalStudentsFailedDueToAbsence.text = "0"
        }
    }

    private fun showBackButton(){
        // calling the action bar
        var actionBar = getSupportActionBar()

        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}