package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.upv.pm_2022.iti_27849_u1_equipo_01.Adapters.SingleStudentAdapter
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Assistance
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student

class StudentDetails : AppCompatActivity() {

    lateinit var student: Student
    lateinit var studentName: TextView
    lateinit var groupName: TextView
    lateinit var assist: TextView
    lateinit var faults: TextView
    lateinit var lvAssistancesThisStudent : ListView
    lateinit var singleStudentAdapter: SingleStudentAdapter
    var assistancesOfSelectedStudent: MutableList<Assistance> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_details)
        setTitle("Ver estudiante");

        showBackButton()

        lvAssistancesThisStudent = findViewById(R.id.lvAssistancesThisStudent)
        studentName = findViewById(R.id.studentName)
        groupName = findViewById(R.id.groupName)
        assist = findViewById(R.id.assist)
        faults = findViewById(R.id.faults)

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
        studentName.text = selectedStudent.first_name + " " + selectedStudent.last_name
        groupName.text = selectedStudent.getGroupName()
        assist.text = "Asistencias: "+selectedStudent.getTotalStudentAssistances()
        faults.text = "Faltas: "+selectedStudent.getTotalStudentFauls()
        assistancesOfSelectedStudent.addAll(selectedStudent?.getAssistances()!!)

        // Load student info into ListView
        singleStudentAdapter = SingleStudentAdapter(applicationContext,assistancesOfSelectedStudent)
        lvAssistancesThisStudent.adapter = singleStudentAdapter
    }

}