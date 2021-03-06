package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.ASSISTANCES
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GROUPS_STUDENTS
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.STUDENTS

class DbHelper (context: Context) : SQLiteOpenHelper(context, "assistancePass.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(GROUPS.SQL_CREATE_GROUPS_TABLE)
        db.execSQL(STUDENTS.SQL_CREATE_STUDENTS_TABLE)
        db.execSQL(GROUPS_STUDENTS.SQL_CREATE_GROUPS_STUDENTS_TABLE)
        db.execSQL(ASSISTANCES.SQL_CREATE_ASISSTANCES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(GROUPS.SQL_DELETE_GROUPS_TABLE)
        db.execSQL(STUDENTS.SQL_DELETE_STUDENTS_TABLE)
        db.execSQL(GROUPS_STUDENTS.SQL_DELETE_GROUPS_STUDENTS_TABLE)
        db.execSQL(ASSISTANCES.SQL_DELETE_ASISSTANCES_TABLE)
        onCreate(db)
    }
}