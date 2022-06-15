package com.upv.pm_2022.iti_27849_u1_equipo_01.Models

import android.content.ContentValues
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.ASSISTANCES
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS_STUDENTS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.STUDENTS

class Student(var id: Int? = null, var first_name: String? = null, var last_name: String? = null) {

    /**
     * Convert attributes to ContentValues
     * @return ContentValues
     */
    fun toContentValues(): ContentValues {
        val values = ContentValues().apply {
            put(STUDENTS.COLUMN_NAME_FIRSTNAME, first_name)
            put(STUDENTS.COLUMN_NAME_LASTNAME, last_name)
        }

        return values
    }

    /**
     * Assign group to this student
     * @param Group group: Group to assign
     */
    fun assignGroup(group: Group){
        GROUPS_STUDENTS.assignGroup(this.id.toString(), group.id.toString())
    }


    fun getGroupId(): Int{
        return GROUPS_STUDENTS.getStudentGroupId(this.id.toString())
    }

    /**
     * Get all assistances of this student
     * @return MutableList<Assistance> List of assistances
     */
    fun getAssistances(): MutableList<Assistance>{
        return ASSISTANCES.getStudentAssistances(this.id.toString())
    }

    /**
     * Parse instance to string
     * @return String
     */
    override fun toString() : String{
        return "$id - $first_name $last_name"
    }
}