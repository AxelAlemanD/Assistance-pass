package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.upv.pm_2022.iti_27849_u1_equipo_01.Adapters.studentsAssistancesAdapter
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_students, container, false)
        groupsSpinner = view.findViewById(R.id.groupsSpinner)
        lvStudents = view.findViewById(R.id.lvStudents)
        total_students = view.findViewById(R.id.total_students)

        // Load data into spinner
        adapterGroups = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,DashboardFragment.listGroups)
        groupsSpinner.setAdapter(adapterGroups) // Bind adapter with operator spinner

        // Code extract from: https://stackoverflow.com/a/49376648
        groupsSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var selectedGroup = adapterGroups.getItem(position)
//                selectedGroup = adapterGroups.getItem(position)!!
                loadStudentList(selectedGroup!!)
            }
        }
        return view
    }

    /**
     * Load student list of selected group
     * @param Group selectedGroup
     */
    fun loadStudentList(selectedGroup: Group){
        val studentsOfSelectedGroup : MutableList<Student> = selectedGroup?.getStudents()!!
        total_students.text = "Total de estudiantes: "+ studentsOfSelectedGroup.size.toString()
        adapterStudents = studentsAssistancesAdapter(requireContext(), studentsOfSelectedGroup)
        lvStudents.adapter = adapterStudents
    }
}