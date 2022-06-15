package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [AssistancePassFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AssistancePassFragment : Fragment() {
    lateinit var addStudentBtn: Button
    lateinit var listStudent: ListView

    val language = arrayOf<String>("C","C++","Java",".Net","Kotlin","Ruby","Rails","Python","Java Script","Php","Ajax","Perl","Hadoop")

    /*
        Variables del archivo fragment_assistance_pass.xml
     */
    lateinit var assistanceGroup: Spinner
    lateinit var assistanceList: ListView

    /*
        Variables del archivo item_assistance.xml
     */
    lateinit var nameComplete: TextView
    lateinit var assistanceStudentSw: Switch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_assistance_pass, container, false)

        //assistanceList=findViewById(R.layout.fragment_assistance_pass)

        //val myListAdapter = StudentAdapter(this,language)
        //assistanceList.adapter = myListAdapter

        return view
    }

    /**
     * Show dialog to create a new student
     */
    /*private fun showDialogToAddStudent(StudentDb : StudentDbHelper) {
        // Assign values
        val builder = AlertDialog.Builder(context)
        val viewDialog = layoutInflater.inflate(R.layout.add_student, null)
        // Pass view to builder
        builder.setView(viewDialog)
        // Create dialog
        val dialog = builder.create()
        dialog.show()
        // Get dialog data
        val nameStudent = viewDialog.findViewById<EditText>(R.id.nameStudent)
        val lastnameStudent = viewDialog.findViewById<EditText>(R.id.lastnameStudent)
        val saveStudentBtn = viewDialog.findViewById<Button>(R.id.saveStudentBtn)

        saveStudentBtn.setOnClickListener {
            if(createNewStudent(nameStudent, lastnameStudent, StudentDb))
                dialog.hide()
        }
    }*/
}
