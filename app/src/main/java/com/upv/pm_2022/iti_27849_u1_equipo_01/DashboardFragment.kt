package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.upv.pm_2022.iti_27849_u1_equipo_01.Adapters.studentsAssistancesAdapter
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.ASSISTANCES
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS_STUDENTS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.STUDENTS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    lateinit var btnRandomStudent : RelativeLayout
    lateinit var assistancePassBtn : RelativeLayout
    lateinit var tvTotalAssistancesOfSelectedGroup: TextView
    lateinit var tvTotalGroups: TextView
    lateinit var tvTotalStudents: TextView
    lateinit var tvTotalStudentsOfSelectedGroup: TextView
    lateinit var lvStudents : ListView
    lateinit var adapterStudents : studentsAssistancesAdapter
    lateinit var spGroups : Spinner
    lateinit var adapterGroups : ArrayAdapter<Group>
    companion object{
        var listGroups : MutableList<Group> = mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        assistancePassBtn = view.findViewById(R.id.assistancePassBtn)
        btnRandomStudent = view.findViewById(R.id.btnRandomStudent)
        tvTotalStudents = view.findViewById(R.id.tvTotalStudents)
        tvTotalGroups = view.findViewById(R.id.tvTotalGroups)
        tvTotalStudentsOfSelectedGroup = view.findViewById(R.id.tvTotalStudentsOfSelectedGroup)
        tvTotalAssistancesOfSelectedGroup = view.findViewById(R.id.tvTotalAssistancesOfSelectedGroup)
        spGroups = view.findViewById(R.id.groupsSpinner)
        lvStudents = view.findViewById(R.id.lvStudents)
        listGroups.addAll(GROUPS.all())

        // Load test data into spinner
        adapterGroups = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listGroups)
        spGroups.setAdapter(adapterGroups) // Bind adapter with operator spinner

        // Code extract from: https://stackoverflow.com/a/49376648
        spGroups?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedGroup = adapterGroups.getItem(position)
                loadGroupInfo(selectedGroup!!)
            }
        }

        btnRandomStudent.setOnClickListener{
            val intent = Intent(this.requireContext(), RandomStudent::class.java)
            startActivity(intent)
        }

        assistancePassBtn.setOnClickListener{
            val intent = Intent(this.requireContext(), AssistancePass::class.java)
            startActivity(intent)
        }

        loadStats()

        return view
    }

    /**
     * Load general statistics
     */
    fun loadStats(){
        tvTotalGroups.text = listGroups.size.toString()
        tvTotalStudents.text = STUDENTS.getTotalStudents().toString()
    }

    /**
     * Load information of selected group
     * @param Group selectedGroup
     */
    fun loadGroupInfo(selectedGroup: Group){
        val studentsOfSelectedGroup : MutableList<Student> = selectedGroup?.getStudents()!!

        tvTotalStudentsOfSelectedGroup.text = studentsOfSelectedGroup.size.toString()
        tvTotalAssistancesOfSelectedGroup.text = selectedGroup.getTotalAssistancesPasses().toString()

        // Load students into ListView
        adapterStudents = studentsAssistancesAdapter(requireContext(), studentsOfSelectedGroup)
        lvStudents.adapter = adapterStudents
    }
}