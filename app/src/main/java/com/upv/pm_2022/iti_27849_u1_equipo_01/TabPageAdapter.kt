package com.upv.pm_2022.iti_27849_u1_equipo_01

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabPageAdapter(activity: FragmentActivity, private val tabCount: Int) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> DashboardFragment()
            1 -> GroupsFragment()
            2 -> StudentsFragment()
            3 -> AssistancePassFragment()
            else -> DashboardFragment()
        }
    }

}