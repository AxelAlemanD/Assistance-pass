package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.upv.pm_2022.iti_27849_u1_equipo_01.Adapters.StudentsAdapter
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.ASSISTANCES
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student

class AssistancePass : AppCompatActivity(){

    lateinit var spGroups : Spinner
    lateinit var adapterGroups : ArrayAdapter<Group>
    lateinit var assistanceList : ListView
    lateinit var adapterStudents : StudentsAdapter
    lateinit var saveAssistanceBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.assistance_pass)
        setTitle("Pase de lista");

        spGroups = findViewById(R.id.assistanceGroup)
        assistanceList = findViewById(R.id.assistanceList)
        saveAssistanceBtn = findViewById(R.id.saveAssistanceBtn)

        // Load data into spinner
        adapterGroups = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item,
            DashboardFragment.listGroups
        )
        spGroups.setAdapter(adapterGroups) // Bind adapter with operator spinner

        // Code extract from: https://stackoverflow.com/a/49376648
        spGroups?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var selectedGroup = adapterGroups.getItem(position)
                selectedGroup = adapterGroups.getItem(position)!!
                loadStudentList(selectedGroup!!)
            }
        }

        saveAssistanceBtn.setOnClickListener{
            Toast.makeText(applicationContext, "Guardando asistencias", Toast.LENGTH_SHORT).show()
            for(assistance in adapterStudents.getAssistances()){
                ASSISTANCES.create(assistance)
            }
            Toast.makeText(applicationContext, "Asistencias guardadas", Toast.LENGTH_SHORT).show()
        }

        showBackButton()
    }

    /**
     * Load student list of selected group
     * @param Group selectedGroup
     */
    fun loadStudentList(selectedGroup: Group){
        val studentsOfSelectedGroup : MutableList<Student> = selectedGroup?.getStudents()!!
        adapterStudents = StudentsAdapter(this, studentsOfSelectedGroup)
        assistanceList.adapter = adapterStudents
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