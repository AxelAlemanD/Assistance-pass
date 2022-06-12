package com.upv.pm_2022.iti_27849_u1_equipo_01.Models

import android.content.ContentValues
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GroupContract
import java.io.Serializable

class Group(var id: Int? = null, var name: String? = null) : Serializable {

    /**
     * Convierte la información de la instancia a ContentValues
     * @return ContentValues valores de la instancia
     */
    fun toContentValues(): ContentValues {
        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(GroupContract.FeedGroup.COLUMN_NAME_NAME, name)
        }

        return values
    }

    /**
     * Parsea la instancia a String
     * @return String
     */
    override fun toString() : String{
        return "ID: $id \t NAME: $name"
    }
}