package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group

/**
 * A simple [Fragment] subclass.
 * Use the [GroupsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GroupsFragment : Fragment() {

    lateinit var createGroupBtn: Button
    lateinit var lvGroups: ListView
    lateinit var adapterGroups: ArrayAdapter<Group>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_groups, container, false)
        lvGroups = view.findViewById(R.id.lvGroups)
        createGroupBtn = view.findViewById(R.id.createGroupBtn)
        adapterGroups = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, DashboardFragment.listGroups)
        lvGroups.adapter = adapterGroups

        createGroupBtn.setOnClickListener{
            showDialogToCreateGroup()
        }

        lvGroups.setOnItemClickListener { parent, view, position, id ->
            val selectedGroup = adapterGroups.getItem(position)
            startGroupDetailsActivity(selectedGroup!!)
        }
        return view
    }

    /**
     * Show dialog to create a new group
     */
    private fun showDialogToCreateGroup(){
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
            if(createNewGroup(groupName))
                dialog.hide()
        }
    }

    /**
     * Get input data and create a new group
     * @return Boolean is inserted
     */
    private fun createNewGroup(groupName: EditText): Boolean{
        if(Validator.validateField(groupName)){
            var group = GROUPS.create(Group(null, groupName.text.toString()))
            DashboardFragment.listGroups.add(group)
            adapterGroups.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Grupo agregado", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    /**
     * Start the group details activity and submit the selected group
     * @param Group selected group of ListView
     */
    private fun startGroupDetailsActivity(selectedGroup: Group){
        val intent = Intent(this.requireContext(), GroupDetails::class.java)
        val bundle = Bundle()
        bundle.putSerializable("group", selectedGroup)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}