package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.ASSISTANCES
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS_STUDENTS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.STUDENTS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Assistance
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student
import java.util.ArrayList

class GroupDetails : AppCompatActivity(){

    lateinit var tableDynamic: TableDynamic
    private val dates: MutableList<String> = mutableListOf()
    lateinit var tableLayout: TableLayout
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
    var listAssistances: MutableList<Assistance> = mutableListOf()
    private val rows = ArrayList<Array<String>>()
    private val header = arrayOf("Número de Lista","Nombre","Apellido","Asistencias","Faltas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_details)
        setTitle("Ver grupo");

        showBackButton()

        val intent = intent
        val recivedObject = intent.extras

        groupName = findViewById(R.id.groupName)
        btnAssistancePassGD = findViewById(R.id.btnAssistancePassGD)
        registeredStudents = findViewById(R.id.registeredStudents)
        totalAttendancePasses = findViewById(R.id.totalAttendancePasses)
        totalStudentsFailedDueToAbsence = findViewById(R.id.totalStudentsFailedDueToAbsence)
        addStudentBtn = findViewById(R.id.addStudentBtn)
        tableLayout = findViewById(R.id.table)

        if (recivedObject != null) {
            group = recivedObject.getSerializable("group") as Group
            groupName.text = group.name
            registeredStudents.text = "0"
            totalAttendancePasses.text = "0"
            totalStudentsFailedDueToAbsence.text = "0"
        }

        listStudents.addAll(group.getStudents())
        listAssistances.addAll(group.getAssistances())
        Log.e("adwad",listAssistances.toString())
        adapterStudents = ArrayAdapter(this.applicationContext, android.R.layout.simple_list_item_1, listStudents)
        //lvGroupStudents.adapter = adapterStudents
        Log.d("Alumnos",listStudents.toString())
        tableDynamic = TableDynamic(tableLayout!!, applicationContext)
        if (listStudents.isNotEmpty()){
            if(listAssistances.isNotEmpty()){
                tableDynamic.addAssistance(listAssistances)
                for (date in listAssistances) {
                    dates.add(date.date)
                    totalAttendancePasses.text = dates.distinct().size.toString()
                }
            }
            tableDynamic!!.addHeader(header,idNumbers)
            tableDynamic!!.addData(info)
            registeredStudents.text = listStudents.size.toString()
        }
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
        Log.e("1","Ow0")
        // Assign values
        val builder = AlertDialog.Builder(this)
        val viewDialog = layoutInflater.inflate(R.layout.add_student_from_group, null)
        // Pass view to builder
        Log.e("2","Ow0")
        builder.setView(viewDialog)
        // Create dialog
        val dialog = builder.create()
        Log.e("3","Ow0")
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
            //listStudents.add(student)
            adapterStudents.notifyDataSetChanged()
            Toast.makeText(this.applicationContext, "Alumno agregado", Toast.LENGTH_SHORT).show()
            if (listStudents.isEmpty()){
                listStudents.addAll(group.getStudents())
                tableDynamic!!.addHeader(header,idNumbers)
                tableDynamic!!.addData(info)
            } else {
                listStudents.clear()
                tableDynamic.clear()
                rows.clear()
                listStudents.addAll(group.getStudents())
                tableDynamic!!.addHeader(header,idNumbers)
                tableDynamic!!.addData(info)
                registeredStudents.text = listStudents.size.toString()
            }
            return true
        }
        return false
    }

    private val info: ArrayList<Array<String>>
        private get() {
            for (i in 0..listStudents.size-1){
                rows.add(arrayOf((i+1).toString(),listStudents[i].first_name.toString(),listStudents[i].last_name.toString()))
            }
            return rows
        }

    private val idNumbers: MutableList<String>
        private get(){
            val ids:MutableList<String> = mutableListOf()
            for (i in listStudents) {
                ids.add(i.id.toString())
            }
            return ids
        }



}