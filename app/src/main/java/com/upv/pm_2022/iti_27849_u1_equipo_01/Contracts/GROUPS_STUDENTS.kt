package com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts

import android.content.ContentValues
import android.database.Cursor
import android.database.DatabaseUtils
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
        var cursor = MainActivity.db.readableDatabase.rawQuery("SELECT t3._id, t3.first_name, t3.last_name " +
                "FROM groups t1 " +
                "INNER JOIN $TABLE_NAME t2 " +
                "ON t1._id = t2.group_id " +
                "and t1._id = $groupId " +
                "INNER JOIN students T3 on t3._id = t2.student_id", null)

        return STUDENTS.toStudentList(cursor)
    }

    fun getStudentGroupId(studentId: String): Int{
        var groupId: Int = 0

        var cursor = MainActivity.db.readableDatabase.query(
            TABLE_NAME,
            arrayOf("group_id"),
            "$COLUMN_NAME_STUDENT_ID = ?",
            arrayOf(studentId),
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                groupId = getInt(getColumnIndexOrThrow("group_id"))
            }
        }
        cursor.close()
         return groupId
    }

    fun getTotalStudentsInTheGroup(groupId: String): Long {
        return getStudentsByGroup(groupId).size.toLong()
//        return DatabaseUtils.longForQuery(MainActivity.db.readableDatabase,
//                                    "SELECT COUNT(_id) FROM groups_students WHERE group_id = $groupId",
//                                null)
    }
}