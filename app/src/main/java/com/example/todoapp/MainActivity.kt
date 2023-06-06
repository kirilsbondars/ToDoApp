// Website from which code samples were taken:
// https://github.com/codeWithCal/TodoListTutorialProject/blob/BottomSheetModalTutorial/app/src/main/java/com/example/todolisttutorial/MainActivity.kt

package com.example.todoapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newTaskButton.setOnClickListener{
            val intent = Intent(this, NewTask::class.java)
            startActivity(intent)
        }

        getAllTasks()
    }

    override fun onResume() {
        super.onResume()
        getAllTasks()
    }

    fun getAllTasks() {
        val db = DBHelper(this)
        val tasks = db.getTaskList()

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = CustomAdapter(tasks!!, this)
        recyclerview.adapter = adapter
    }
}