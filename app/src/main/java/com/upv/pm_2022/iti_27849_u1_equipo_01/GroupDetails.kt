package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.STUDENTS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student

class GroupDetails : AppCompatActivity(){

    lateinit var group: Group
    lateinit var groupName: TextView
    lateinit var btnAssistancePassGD: Button
    lateinit var registeredStudents: TextView
    lateinit var totalAttendancePasses: TextView
    lateinit var totalStudentsFailedDueToAbsence: TextView
    lateinit var addStudentBtn: Button
    lateinit var lvGroupStudents: ListView
    lateinit var adapterStudents: ArrayAdapter<Student>
    var listStudents: MutableList<Student> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_details)
        setTitle("Ver grupo");

        showBackButton()

        val intent = intent
        val recivedObject = intent.extras

        if (recivedObject != null) {
            group = recivedObject.getSerializable("group") as Group
            groupName.text = group.name
            registeredStudents.text = "0"
            totalAttendancePasses.text = "0"
            totalStudentsFailedDueToAbsence.text = "0"
        }

        groupName = findViewById(R.id.groupName)
        btnAssistancePassGD = findViewById(R.id.btnAssistancePassGD)
        registeredStudents = findViewById(R.id.registeredStudents)
        totalAttendancePasses = findViewById(R.id.totalAttendancePasses)
        totalStudentsFailedDueToAbsence = findViewById(R.id.totalStudentsFailedDueToAbsence)
        lvGroupStudents = findViewById(R.id.lvGroupStudents)
        addStudentBtn = findViewById(R.id.addStudentBtn)
        listStudents.addAll(group.getStudents())
        adapterStudents = ArrayAdapter(this.applicationContext, android.R.layout.simple_list_item_1, listStudents)
        lvGroupStudents.adapter = adapterStudents

        addStudentBtn.setOnClickListener{
            showDialogToAddStudent()
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
     * Show dialog to create a new group
     */
    private fun showDialogToAddStudent(){
        // Assign values
        val builder = AlertDialog.Builder(this.applicationContext)
        val viewDialog = layoutInflater.inflate(R.layout.add_student_from_group, null)
        // Pass view to builder
        builder.setView(viewDialog)
        // Create dialog
        val dialog = builder.create()
        dialog.show()
        // Get dialog data
        val first_name = viewDialog.findViewById<EditText>(R.id.first_name)
        val last_name = viewDialog.findViewById<EditText>(R.id.last_name)
        val saveStudentBtn = viewDialog.findViewById<Button>(R.id.saveStudentBtn)

        saveStudentBtn.setOnClickListener {
            if(addNewStudentFromGroup(first_name, last_name))
                dialog.hide()
        }
    }

    /**
     * Get input data and create a new group
     * @return Boolean is inserted
     */
    private fun addNewStudentFromGroup(first_name: EditText, last_name: EditText): Boolean{
        if(Validator.validateField(first_name) && Validator.validateField(last_name)){
            var student = STUDENTS.create(Student(null, first_name.text.toString(), last_name.text.toString()))
            student.assignGroup(group)
            listStudents.add(student)
            adapterStudents.notifyDataSetChanged()
            Toast.makeText(this.applicationContext, "Alumno agregado", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

}