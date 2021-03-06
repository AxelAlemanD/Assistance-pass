package com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts

import android.database.Cursor
import android.database.DatabaseUtils
import android.provider.BaseColumns
import com.upv.pm_2022.iti_27849_u1_equipo_01.MainActivity
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Assistance


object ASSISTANCES {
    const val TABLE_NAME = "assistances"
    const val COLUMN_NAME_DATE = "date"
    const val COLUMN_NAME_IS_LATE = "is_late"
    const val COLUMN_NAME_GROUP_ID = "group_id"
    const val COLUMN_NAME_STUDENT_ID = "student_id"

    val SQL_CREATE_ASISSTANCES_TABLE = "CREATE TABLE $TABLE_NAME ("+
            "_id INTEGER PRIMARY KEY,"+
            "$COLUMN_NAME_DATE INT,"+
            "$COLUMN_NAME_IS_LATE INT,"+
            "$COLUMN_NAME_STUDENT_ID  REFERENCES ${STUDENTS.TABLE_NAME}(_id),"+
            "$COLUMN_NAME_GROUP_ID  REFERENCES ${GROUPS.TABLE_NAME}(_id))"

    val SQL_DELETE_ASISSTANCES_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    /**
     * Insert a new record in the Assistances table
     * @param Assistance assistance: Assistance to insert
     */
   fun create(assistance: Assistance){
       MainActivity.db.writableDatabase?.insert(TABLE_NAME, null, assistance.toContentValues())
   }

    /**
     * Get all the attendances of a student
     * @param String studentId: student to get
     * @return MutableList<Assistance> List of assistances
     */
    fun getStudentAssistances(studentId: String): MutableList<Assistance>{
        var cursor = MainActivity.db.readableDatabase.rawQuery(
            "SELECT assistances._id, assistances.date, assistances.is_late, assistances.group_id, assistances.student_id " +
                "FROM students " +
                "INNER JOIN assistances " +
                "ON students._id = assistances.student_id " +
                "and students._id = $studentId", null)

        return toAssistanceList(cursor)
    }

    /**
     * Get all the attendances of a group
     * @param String studentId: group to get
     * @return MutableList<Assistance> List of assistances
     */
    fun getGroupAssistances(groupId: String): MutableList<Assistance>{
        var cursor = MainActivity.db.readableDatabase.rawQuery(
            "SELECT * FROM assistances WHERE group_id = $groupId",
            null)
//        var cursor = MainActivity.db.readableDatabase.rawQuery(
//            "SELECT assistances._id, assistances.date, assistances.is_late, assistances.group_id, assistances.student_id " +
//                "FROM groups " +
//                "INNER JOIN assistances " +
//                "ON groups._id = assistances.group_id " +
//                "and groups._id = $groupId", null)
        return toAssistanceList(cursor)
    }


    /**
     * Get all the attendances of a group
     * @param String studentId: group to get
     * @return MutableList<Assistance> List of assistances
     */
    fun getTotalGroupAssistances(groupId: String): Long{
        val assistPasses = DatabaseUtils.longForQuery(MainActivity.db.readableDatabase, "SELECT COUNT(*) FROM assistances WHERE group_id = $groupId", null)
        val totalStudents = GROUPS_STUDENTS.getTotalStudentsInTheGroup(groupId)
        var totalAssistances: Long = 0
        System.out.println("assistPasses $assistPasses")
        System.out.println("totalStudents $totalStudents")
        try{
            totalAssistances = assistPasses / totalStudents
        } catch (e: ArithmeticException){}
        return totalAssistances
    }


    fun getTotalStudentAssistances(studentId: String): Long{
        val totalAssistances = DatabaseUtils.longForQuery(MainActivity.db.readableDatabase, "SELECT COUNT(*) FROM assistances WHERE student_id = $studentId AND is_late = 1", null)
        return totalAssistances
    }

    fun getTotalStudentFauls(studentId: String): Long{
        val totalFauls = DatabaseUtils.longForQuery(MainActivity.db.readableDatabase, "SELECT COUNT(*) FROM assistances WHERE student_id = $studentId AND is_late = 0", null)
        return totalFauls
    }

    /**
     * Convert Cursor to Assistance list
     * @param cursor
     * @return MutableList<Assistance> assistances
     */
    fun toAssistanceList(cursor: Cursor) : MutableList<Assistance> {
        val assistances: MutableList<Assistance> = mutableListOf()
        var id: Int
        var date: String
        var is_late: Int
        var group_id: Int
        var student_id: Int

        with(cursor) {
            while (moveToNext()) {
                id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                date = getString(getColumnIndexOrThrow(COLUMN_NAME_DATE))
                is_late = getInt(getColumnIndexOrThrow(COLUMN_NAME_IS_LATE))
                group_id = getInt(getColumnIndexOrThrow(COLUMN_NAME_GROUP_ID))
                student_id = getInt(getColumnIndexOrThrow(COLUMN_NAME_STUDENT_ID))
                assistances.add(Assistance(id, date, is_late, group_id, student_id))
            }
        }

        cursor.close()
        return assistances
    }
}