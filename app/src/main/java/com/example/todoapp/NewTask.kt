package com.example.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.databinding.ActivityNewTaskBinding
import TaskItem
import android.os.Build
import androidx.annotation.RequiresApi

class NewTask : AppCompatActivity() {
    private lateinit var binding: ActivityNewTaskBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener{
            val db = DBHelper(this)
            val name = binding.name.text.toString()
            val desc = binding.desc.text.toString()
            val task: TaskItem = TaskItem(name=name, description = desc)
            db.addTask(task)
            finish()
        }

    }
}