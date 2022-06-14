package com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts

import android.database.Cursor
import android.database.DatabaseUtils
import android.provider.BaseColumns
import com.upv.pm_2022.iti_27849_u1_equipo_01.MainActivity
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Student

object STUDENTS {
    const val TABLE_NAME = "students"
    const val COLUMN_NAME_FIRSTNAME = "first_name"
    const val COLUMN_NAME_LASTNAME = "last_name"

    val SQL_CREATE_STUDENTS_TABLE = "CREATE TABLE $TABLE_NAME ("+
            "_id INTEGER PRIMARY KEY,"+
            "$COLUMN_NAME_FIRSTNAME STRING,"+
            "$COLUMN_NAME_LASTNAME STRING)"

    val SQL_DELETE_STUDENTS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    /**
     * Insert a new record in the Students table
     * @param Student student: Student to insert
     * @return Student inserted Student
     */
    fun create(student: Student): Student {
        val id = MainActivity.db.writableDatabase?.insert(TABLE_NAME, null, student.toContentValues())
        return find(id.toString())
    }

    /**
     * Delete a record from the Students table
     * @param String studentId: Student ID to delete
     * @return Int Total rows deleted
     */
    fun delete(studentId: String): Int {
        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(studentId) // Specify arguments in placeholder order.
        return MainActivity.db.readableDatabase.delete(TABLE_NAME, selection, selectionArgs) // Issue SQL statement.
    }

    /**
     * Update record information
     * @param Student student: Updated student
     * @return Int Total rows updated
     */
    fun update(student: Student): Int {
        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(student.id.toString())

        return MainActivity.db.writableDatabase.update(
            TABLE_NAME,
            student.toContentValues(),
            selection,
            selectionArgs)
    }

    /**
     * Get all records from Students table
     * @return MutableList<Student> List of students
     */
    fun all(): MutableList<Student> {
        var cursor = MainActivity.db.readableDatabase.query(
            TABLE_NAME,   // The table to query
            null,                   // Get all columns
            null,                   // don't columns for the WHERE clause
            null,                   // don't values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null                    // don't sort
        )
        return toStudentList(cursor)
    }

    /**
     * Get a record by id
     * @param String studentId: Student ID to get
     * @return Student Obtained student
     */
    fun find(studentId: String): Student {
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(studentId)

        var cursor = MainActivity.db.readableDatabase.query(
            TABLE_NAME,   // The table to query
            null,                   // Get all columns
            selection,              // The columns for the WHERE clause
            selectionArgs,           // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null                    // don't sort
        )
        return toStudent(cursor)
    }

    /**
     * Get total row from Students table
     * @param Long total students
     */
    fun getTotalStudents(): Long{
        return DatabaseUtils.queryNumEntries(MainActivity.db.readableDatabase, TABLE_NAME);
    }

    /**
     * Convert Cursor to Student list
     * @param cursor
     * @return MutableList<Student> students
     */
    fun toStudentList(cursor: Cursor) : MutableList<Student> {
        val students: MutableList<Student> = mutableListOf()
        var id: Int
        var first_name: String
        var last_name: String

        with(cursor) {
            while (moveToNext()) {
                id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                first_name = getString(getColumnIndexOrThrow(COLUMN_NAME_FIRSTNAME))
                last_name = getString(getColumnIndexOrThrow(COLUMN_NAME_LASTNAME))
                students.add(Student(id, first_name, last_name))
            }
        }

        cursor.close()
        return students
    }

    /**
     * Convert Cursor to Student
     * @param cursor
     * @return Student
     */
    fun toStudent(cursor: Cursor) : Student {
        var id: Int
        var first_name: String
        var last_name: String
        var student = Student()

        with(cursor) {
            while (moveToNext()) {
                id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                first_name = getString(getColumnIndexOrThrow(COLUMN_NAME_FIRSTNAME))
                last_name = getString(getColumnIndexOrThrow(COLUMN_NAME_LASTNAME))
                student = Student(id, first_name, last_name)
            }
        }
        cursor.close()
        return student
    }
}