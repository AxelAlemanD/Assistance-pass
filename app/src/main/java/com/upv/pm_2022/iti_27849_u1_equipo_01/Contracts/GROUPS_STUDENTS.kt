package com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import com.upv.pm_2022.iti_27849_u1_equipo_01.MainActivity
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student

object GROUPS_STUDENTS {
    const val TABLE_NAME = "groups_students"
    const val COLUMN_NAME_STUDENT_ID = "student_id"
    const val COLUMN_NAME_GROUP_ID = "group_id"

    val SQL_CREATE_GROUPS_STUDENTS_TABLE = "CREATE TABLE $TABLE_NAME ("+
            "_id INTEGER PRIMARY KEY,"+
            "$COLUMN_NAME_STUDENT_ID  REFERENCES ${STUDENTS.TABLE_NAME}(_id),"+
            "$COLUMN_NAME_GROUP_ID  REFERENCES ${GROUPS.TABLE_NAME}(_id))"

    val SQL_DELETE_GROUPS_STUDENTS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    /**
     * Assign group to student
     * @param String studentId: student to assign
     * @param String groupId: group to assign
     */
    fun assignGroup(studentId: String, groupId: String) {
        val values = ContentValues().apply {
            put(COLUMN_NAME_STUDENT_ID, studentId)
            put(COLUMN_NAME_GROUP_ID, groupId)
        }
        MainActivity.db.writableDatabase?.insert(TABLE_NAME, null, values)
    }

    /**
     * Get all students of a group
     * @param String groupId: group to get
     * @return MutableList<Student> List of students
     */
    fun getStudentsByGroup(groupId: String): MutableList<Student> {
        var cursor = MainActivity.db.readableDatabase.rawQuery("SELECT t3.id, t3.first_name, t3.last_name " +
                "FROM groups t1 " +
                "INNER JOIN groups_students t2 " +
                "ON t1.id = t2.group_id " +
                "and t1.id = $groupId " +
                "INNER JOIN students T3 on t3.id = t2.student_id", null)

        return STUDENTS.toStudentList(cursor)
    }
}