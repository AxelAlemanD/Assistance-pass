package com.upv.pm_2022.iti_27849_u1_equipo_01.Models

import android.content.ContentValues
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.ASSISTANCES
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS_STUDENTS
import java.io.Serializable

class Group(var id: Int? = null, var name: String? = null) : Serializable {

    /**
     * Convert attributes to ContentValues
     * @return ContentValues
     */
    fun toContentValues(): ContentValues {
        val values = ContentValues().apply {
            put(GROUPS.COLUMN_NAME_NAME, name)
        }
        return values
    }

    /**
     * Get all students of this group
     * @return MutableList<Student> List of students
     */
    fun getStudents(): MutableList<Student> {
        return GROUPS_STUDENTS.getStudentsByGroup(this.id.toString())
    }

    /**
     * Get total students of this group
     * @return Long Total students
     */
    fun getTotalStudentsInThisGroup(): Long{
        return GROUPS_STUDENTS.getTotalStudentsInTheGroup(this.id.toString())
    }

    /**
     * Get all assistances of this group
     * @return MutableList<Assistance> List of assistances
     */
    fun getAssistances(): MutableList<Assistance>{
        return ASSISTANCES.getGroupAssistances(this.id.toString())
    }

    /**
     * Get total attendance passes of this group
     * @return Int Total as
     */
    fun getTotalAssistancesPasses(): Int{
        return ASSISTANCES.getTotalGroupAssistances(this.id.toString()).toInt()
    }

    /**
     * Parse instance to string
     * @return String
     */
    override fun toString() : String{
        return "$name"
    }
}