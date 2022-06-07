package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    lateinit var spGroups       : Spinner
    lateinit var adapterGroups  : ArrayAdapter<CharSequence>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        spGroups = view.findViewById(R.id.groupsSpinner)

        adapterGroups = ArrayAdapter.createFromResource(requireContext(), R.array.groups, android.R.layout.simple_spinner_item);
        adapterGroups.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Bind adapter with operator spinner
        spGroups.setAdapter(adapterGroups);

        return view
    }
}