package com.upv.pm_2022.iti_27849_u1_equipo_01.Models

import android.content.ContentValues
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.ASSISTANCES
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS_STUDENTS
import java.io.Serializable

class Assistance(var id: Int? = null, var date: String, var is_late: Int, var group_id: Int, var student_id: Int){

    /**
     * Convert attributes to ContentValues
     * @return ContentValues
     */
    fun toContentValues(): ContentValues {
        val values = ContentValues().apply {
            put(ASSISTANCES.COLUMN_NAME_DATE, date)
            put(ASSISTANCES.COLUMN_NAME_IS_LATE, is_late)
            put(ASSISTANCES.COLUMN_NAME_GROUP_ID, group_id)
            put(ASSISTANCES.COLUMN_NAME_STUDENT_ID, student_id)
        }
        return values
    }

    override fun toString(): String{
        return "student_id: $student_id \t is_late: $is_late \t group_id: $group_id"
    }
}