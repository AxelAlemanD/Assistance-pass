package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.upv.pm_2022.iti_27849_u1_equipo_01.Adapters.studentsAssistancesAdapter
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.STUDENTS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student

/**
 * A simple [Fragment] subclass.
 * Use the [StudentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentsFragment : Fragment() {

    lateinit var groupsSpinner : Spinner
    lateinit var adapterGroups : ArrayAdapter<Group>
    lateinit var lvStudents : ListView
    lateinit var adapterStudents : studentsAssistancesAdapter
    lateinit var total_students: TextView
    lateinit var createStudentBtn: Button
    var selectedGroup: Group? = null
    val studentsOfSelectedGroup : MutableList<Student> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_students, container, false)
        groupsSpinner = view.findViewById(R.id.groupsSpinner)
        lvStudents = view.findViewById(R.id.lvStudents)
        total_students = view.findViewById(R.id.total_students)
        createStudentBtn = view.findViewById(R.id.createStudentBtn)


        // Load data into spinner
        adapterGroups = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,DashboardFragment.listGroups)
        groupsSpinner.setAdapter(adapterGroups) // Bind adapter with operator spinner

        // Code extract from: https://stackoverflow.com/a/49376648
        groupsSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                var selectedGroup = adapterGroups.getItem(position)
                selectedGroup = adapterGroups.getItem(position)
                loadStudentList(selectedGroup!!)
            }
        }

        lvStudents.setOnItemClickListener { parent, view, position, id ->
            val selectedStudent = adapterStudents.getItem(position)
            startStudentDetailsActivity(selectedStudent!!)
        }

        createStudentBtn.setOnClickListener{
            showDialogToAddStudent()
        }
        return view
    }

    /**
     * Load student list of selected group
     * @param Group selectedGroup
     */
    fun loadStudentList(selectedGroup: Group){
//        val studentsOfSelectedGroup : MutableList<Student> = selectedGroup?.getStudents()!!
        studentsOfSelectedGroup.clear()
        studentsOfSelectedGroup.addAll(selectedGroup?.getStudents()!!)
        total_students.text = "Total de estudiantes: "+ studentsOfSelectedGroup.size.toString()
        adapterStudents = studentsAssistancesAdapter(requireContext(), studentsOfSelectedGroup)
        lvStudents.adapter = adapterStudents
    }

    /**
     * Start the student details activity and submit the selected student
     * @param Student selected student of ListView
     */
    private fun startStudentDetailsActivity(selectedStudent: Student){
        val intent = Intent(this.requireContext(), StudentDetails::class.java)
        val bundle = Bundle()
        bundle.putSerializable("student", selectedStudent)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    /**
     * Show dialog to create a new student
     */
    private fun showDialogToAddStudent(){
        // Assign values
        val builder = AlertDialog.Builder(requireContext())
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
     * Get input data and create a new student
     * @return Boolean is inserted
     */
    private fun addNewStudentFromGroup(first_name: EditText, last_name: EditText): Boolean{
        if(Validator.validateField(first_name) && Validator.validateField(last_name)){
            var student = STUDENTS.create(Student(null, first_name.text.toString(), last_name.text.toString()))
            student.assignGroup(selectedGroup!!)
            studentsOfSelectedGroup.add(student)
            adapterStudents.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Alumno agregado al grupo "+ selectedGroup!!.name, Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }
}