package com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts

import android.database.Cursor
import android.provider.BaseColumns
import com.upv.pm_2022.iti_27849_u1_equipo_01.MainActivity
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group

object GROUPS {
    const val TABLE_NAME = "groups"
    const val COLUMN_NAME_NAME = "name"

    val SQL_CREATE_GROUPS_TABLE = "CREATE TABLE $TABLE_NAME ("+
                                        "_id INTEGER PRIMARY KEY,"+
                                        "$COLUMN_NAME_NAME STRING)"

    val SQL_DELETE_GROUPS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    /**
     * Insert a new record in the Groups table
     * @param Group group: Group to insert
     * @return Group inserted Group
     */
    fun create(group: Group): Group {
        var id = MainActivity.db.writableDatabase?.insert(TABLE_NAME, null, group.toContentValues())
        return find(id.toString())
    }

    /**
     * Delete a record from the Groups table
     * @param String groupId: Group ID to delete
     * @return Int Total rows deleted
     */
    fun delete(groupId: String): Int {
        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(groupId) // Specify arguments in placeholder order.
        return MainActivity.db.readableDatabase.delete(TABLE_NAME, selection, selectionArgs) // Issue SQL statement.
    }

    /**
     * Update record information
     * @param Group group: Updated group
     * @return Int Total rows updated
     */
    fun update(group: Group): Int {
        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(group.id.toString())

        return MainActivity.db.writableDatabase.update(
            TABLE_NAME,
            group.toContentValues(),
            selection,
            selectionArgs)
    }

    /**
     * Get all records from Groups table
     * @return MutableList<Group> List of groups
     */
    fun all(): MutableList<Group> {
        var cursor = MainActivity.db.readableDatabase.query(
            TABLE_NAME,   // The table to query
            null,                   // Get all columns
            null,                   // don't columns for the WHERE clause
            null,                   // don't values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null                    // don't sort
        )
        return toGroupList(cursor)
    }

    /**
     * Get a record by id
     * @param String groupId: Group ID to get
     * @return Group Obtained group
     */
    fun find(groupId: String): Group {
        // Filter results WHERE "_id" = id"
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(groupId)

        var cursor = MainActivity.db.readableDatabase.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        return toGroup(cursor)
    }

    /**
     * Convert Cursor to Group list
     * @param cursor
     * @return MutableList<Group> groups
     */
    fun toGroupList(cursor: Cursor) : MutableList<Group> {
        val groups: MutableList<Group> = mutableListOf()
        var id: Int
        var name: String

        with(cursor) {
            while (moveToNext()) {
                id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                name = getString(getColumnIndexOrThrow(COLUMN_NAME_NAME))

                groups.add(Group(id, name))
            }
        }

        cursor.close()
        return groups
    }

    /**
     * Convert Cursor to Group
     * @param cursor
     * @return Group
     */
    fun toGroup(cursor: Cursor) : Group {
        var group = Group()

        with(cursor) {
            while (moveToNext()) {
                val id : Int = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val name : String = getString(getColumnIndexOrThrow(COLUMN_NAME_NAME))
                group = Group(id, name)
            }
        }
        cursor.close()
        return group
    }
}