package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.upv.pm_2022.iti_27849_u1_equipo_01.Adapters.SingleStudentAdapter
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Assistance
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student

class StudentDetails : AppCompatActivity() {

    lateinit var student: Student
    lateinit var lvAssistancesThisStudent : ListView
    lateinit var singleStudentAdapter: SingleStudentAdapter
    var assistancesOfSelectedStudent: MutableList<Assistance> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_details)
        setTitle("Ver estudiante");

        showBackButton()

        lvAssistancesThisStudent = findViewById(R.id.lvAssistancesThisStudent)

        val intent = intent
        val recivedObject = intent.extras

        if (recivedObject != null) {
            student = recivedObject.getSerializable("student") as Student
            loadStudentInfo(student)
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

    /**
     * Load information of selected student
     * @param Student selectedStudent
     */
    fun loadStudentInfo(selectedStudent: Student){
        assistancesOfSelectedStudent.addAll(selectedStudent?.getAssistances()!!)

        // Load student info into ListView
        singleStudentAdapter = SingleStudentAdapter(applicationContext,assistancesOfSelectedStudent)
        lvAssistancesThisStudent.adapter = singleStudentAdapter
    }

}