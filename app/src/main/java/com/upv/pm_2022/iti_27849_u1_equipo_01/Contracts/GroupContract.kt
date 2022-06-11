package com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts

import android.provider.BaseColumns

/***
 * Container in which the name of the table and its columns are defined
 */
object GroupContract {
    // The BaseColumns interface allows the inner class to inherit a primary key field called _ID
    object FeedGroup : BaseColumns {
        const val TABLE_NAME = "groups"
        const val COLUMN_NAME_NAME = "name"
    }
}