// Website from which code samples were taken:
// https://www.javatpoint.com/kotlin-android-sqlite-tutorial
// https://www.geeksforgeeks.org/android-sqlite-database-in-kotlin/

package com.example.todoapp
import TaskItem
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY," +
                NAME + " TEXT," +
                DESCRIPTION + " TEXT," +
                CREATION_DATE + " TEXT," +
                DELETION_DATE + " TEXT," +
                COMPLETED + " BOOL" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE " + TABLE_NAME)
        onCreate(db)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTask(task: TaskItem) {
        val values = ContentValues()
        val db = this.writableDatabase

        values.put(NAME, task.name)
        values.put(DESCRIPTION, task.description)
        values.put(CREATION_DATE, LocalDate.now().toString())
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun setCompletedTask(id: String) {
        val values = ContentValues()
        val db = this.writableDatabase

        values.put(COMPLETED, true)
        db.update(TABLE_NAME, values, "id=$id", null)
        db.close()
    }

    fun updateTask(task: TaskItem) {
        val values = ContentValues()
        val db = this.writableDatabase

        values.put(NAME, task.name)
        values.put(DESCRIPTION, task.description)
        values.put(COMPLETED, task.completed)
        db.update(TABLE_NAME, values, "id=${task.id.toString()}", null)
        db.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteTask(id: String) {
        val values = ContentValues()
        val db = this.writableDatabase

        values.put(DELETION_DATE, LocalDate.now().toString())
        db.update(TABLE_NAME, values, "id=$id", null)
        db.close()
    }

    fun getTaskList(): ArrayList<TaskItem>? {
        val taskList: ArrayList<TaskItem> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $DELETION_DATE IS NULL"
        val db = this.readableDatabase
        var cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex(ID))
                val name = cursor.getString(cursor.getColumnIndex(NAME))
                val description = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                val creationDate = cursor.getString(cursor.getColumnIndex(CREATION_DATE))
                val deletionDate = cursor.getString(cursor.getColumnIndex(DELETION_DATE))
                val completed = cursor.getInt(cursor.getColumnIndex(COMPLETED))

                val task = TaskItem(
                    id = id,
                    name = name,
                    description = description,
                    creationDate = creationDate,
                    deletionDate = deletionDate,
                    completed = completed == 1
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        return taskList
    }

    fun getTask(id: String): TaskItem? {
        var task: TaskItem = TaskItem(name="", description = "")

        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $id"
        val db = this.readableDatabase
        var cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex(ID))
                val name = cursor.getString(cursor.getColumnIndex(NAME))
                val description = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                val creationDate = cursor.getString(cursor.getColumnIndex(CREATION_DATE))
                val deletionDate = cursor.getString(cursor.getColumnIndex(DELETION_DATE))
                val completed = cursor.getInt(cursor.getColumnIndex(COMPLETED))

                task = TaskItem(
                    id = id,
                    name = name,
                    description = description,
                    creationDate = creationDate,
                    deletionDate = deletionDate,
                    completed = completed == 1
                )
            } while (cursor.moveToNext())
        }
        return task
    }

    companion object{
        private val DATABASE_NAME = "DB"
        private val DATABASE_VERSION = 2
        val TABLE_NAME = "Tasks"
        val ID = "id"
        val NAME = "name"
        val DESCRIPTION = "description"
        val CREATION_DATE = "creation_date"
        val DELETION_DATE = "deletion_date"
        val COMPLETED = "completed"
    }
}