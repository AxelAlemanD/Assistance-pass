package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    lateinit var spGroups       : Spinner
    lateinit var adapterGroups  : ArrayAdapter<CharSequence>
    lateinit var listView       : ListView
    var listStudents     : MutableList<String> = mutableListOf()
    lateinit var adapterStudents  : ArrayAdapter<CharSequence>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        spGroups = view.findViewById(R.id.groupsSpinner)
        listView = view.findViewById(R.id.listView)


        // Load test data into spinner
        adapterGroups = ArrayAdapter.createFromResource(requireContext(), R.array.groups, android.R.layout.simple_spinner_item);
        adapterGroups.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spGroups.setAdapter(adapterGroups); // Bind adapter with operator spinner


        // Load test data into ListView
        listStudents.add("Axel Issai Alemán Delgado")
        listStudents.add("José Carlos Avalos Ruiz")
        listStudents.add("José Manuel Rodriguez Garcia")
        listStudents.add("Juan de Dios Nava Gallardo")
        listStudents.add("Orlando Samuel Martinez Dorantes")

        adapterStudents = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
            listStudents as List<CharSequence>
        )
        listView.adapter = adapterStudents




        return view
    }
}