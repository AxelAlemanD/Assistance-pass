package com.upv.pm_2022.iti_27849_u1_equipo_01.Helpers

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.upv.pm_2022.iti_27849_u1_equipo_01.Contracts.GroupContract
import com.upv.pm_2022.iti_27849_u1_equipo_01.Models.Group

class GroupDbHelper (context: Context) : SQLiteOpenHelper(context, "assistancePass.db", null, 1) {

    private val SQL_CREATE_ENTRIES = "CREATE TABLE ${GroupContract.FeedGroup.TABLE_NAME} ("+
                                        "${BaseColumns._ID} INTEGER PRIMARY KEY,"+
                                        "${GroupContract.FeedGroup.COLUMN_NAME_NAME} STRING)"
    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${GroupContract.FeedGroup.TABLE_NAME}"
    private val DB_READABLE = this.readableDatabase // Gets the data repository in write mode
    private val DB_WRITABLE = this.readableDatabase // Gets the data repository in read mode


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    /**
     * Insert a new record in the Groups table
     * @param Group group: Group to insert
     * @return Long inserted record id
     */
    fun create(group: Group): Long? {
        return DB_WRITABLE?.insert(GroupContract.FeedGroup.TABLE_NAME, null, group.toContentValues())
    }


    /**
     * Delete a record from the Groups table
     * @param String groupId: Group ID to delete
     * @return Int Total rows deleted
     */
    fun delete(groupId: String): Int {
        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(groupId) // Specify arguments in placeholder order.
        return DB_READABLE.delete(GroupContract.FeedGroup.TABLE_NAME, selection, selectionArgs) // Issue SQL statement.
    }

    /**
     * Update record information
     * @param Group group: Updated group
     * @return Int Total rows updated
     */
    fun update(group: Group): Int {
        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(group.id.toString())

        return DB_WRITABLE.update(
            GroupContract.FeedGroup.TABLE_NAME,
            group.toContentValues(),
            selection,
            selectionArgs)
    }

    /**
     * Get all records from Groups table
     * @return MutableList<Group> List of groups
     */
    fun all(): MutableList<Group> {
        var cursor = DB_READABLE.query(
                        GroupContract.FeedGroup.TABLE_NAME,   // The table to query
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

        var cursor = DB_READABLE.query(
            GroupContract.FeedGroup.TABLE_NAME,
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
               name = getString(getColumnIndexOrThrow(GroupContract.FeedGroup.COLUMN_NAME_NAME))

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
//        var id: Int
//        var name: String
        var group = Group()

        with(cursor) {
            while (moveToNext()) {
                val id : Int = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val name : String = getString(getColumnIndexOrThrow(GroupContract.FeedGroup.COLUMN_NAME_NAME))
                group = Group(id, name)
            }
        }
        cursor.close()
        return group
    }


}