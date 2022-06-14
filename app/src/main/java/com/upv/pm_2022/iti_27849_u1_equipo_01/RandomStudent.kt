package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student

class RandomStudent : AppCompatActivity(){

    lateinit var spGroups : Spinner
    lateinit var adapterGroups : ArrayAdapter<Group>
    lateinit var studentSelected: TextView
    lateinit var getRandomBtn: Button
    var studentsOfSelectedGroup : MutableList<Student> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.random_student)
        setTitle("Elegir aleatorio");

        showBackButton()

        getRandomBtn = findViewById(R.id.getRandomBtn)
        spGroups = findViewById(R.id.spGroups)
        studentSelected = findViewById(R.id.studentSelected)

        // Load test data into spinner
        adapterGroups = ArrayAdapter(this.applicationContext, android.R.layout.simple_spinner_item,DashboardFragment.listGroups)
        spGroups.setAdapter(adapterGroups) // Bind adapter with operator spinner

        // Code extract from: https://stackoverflow.com/a/49376648
        spGroups?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedGroup = adapterGroups.getItem(position)
                studentsOfSelectedGroup = selectedGroup?.getStudents()!!
            }
        }

        getRandomBtn.setOnClickListener{
            getRandomStudent()
        }
    }

    /**
     * Get a random student from the selected group
     */
    private fun getRandomStudent(){
        if(!studentsOfSelectedGroup.isEmpty()) {
            var random = studentsOfSelectedGroup.random()
            studentSelected.text = random.toString()
        } else{
            Toast.makeText(applicationContext, "El grupo seleccionado no contiene alumnos", Toast.LENGTH_LONG).show()
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