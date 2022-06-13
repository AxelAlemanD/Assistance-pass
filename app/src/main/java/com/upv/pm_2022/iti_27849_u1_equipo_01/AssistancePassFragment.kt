package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

/**
 * A simple [Fragment] subclass.
 * Use the [AssistancePassFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AssistancePassFragment : Fragment() {
    lateinit var addStudentBtn: Button
    lateinit var listStudent: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_assistance_pass, container, false)

        return view
    }

    /**
     * Show dialog to create a new student
     */
    private fun showDialogToAddStudent(StudentDb : StudentDbHelper) {
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
    }
}