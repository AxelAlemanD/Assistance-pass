package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.upv.pm_2022.iti_27849_u1_equipo_01.Helpers.GroupDbHelper
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group

/**
 * A simple [Fragment] subclass.
 * Use the [GroupsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupsFragment : Fragment() {

    lateinit var createGroupBtn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_groups, container, false)
        val GroupDb = GroupDbHelper(this.requireContext())

        createGroupBtn = view.findViewById(R.id.createGroupBtn)

        createGroupBtn.setOnClickListener{
            showDialogToCreateGroup(GroupDb)
        }

        return view
    }

    private fun showDialogToCreateGroup(GroupDb : GroupDbHelper){
        // Assign values
        val builder = AlertDialog.Builder(context)
        val viewDialog = layoutInflater.inflate(R.layout.add_group, null)
        // Pass view to builder
        builder.setView(viewDialog)
        // Create dialog
        val dialog = builder.create()
        dialog.show()
        // Get dialog data
        val groupName = viewDialog.findViewById<EditText>(R.id.groupName)
        val saveGroupBtn = viewDialog.findViewById<Button>(R.id.saveGroupBtn)

        saveGroupBtn.setOnClickListener {
            createNewGroup(groupName, GroupDb)
        }
    }

    private fun createNewGroup(groupName: EditText, GroupDb: GroupDbHelper){
        if(Validator.validateField(groupName)){
            GroupDb.create(Group(null, groupName.text.toString()))
            Toast.makeText(requireContext(), "Grupo agregado", Toast.LENGTH_SHORT).show()
        }
    }
}