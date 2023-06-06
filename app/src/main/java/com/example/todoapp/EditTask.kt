package com.example.todoapp

import TaskItem
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.todoapp.databinding.ActivityEditTaskBinding
import com.example.todoapp.databinding.ActivityNewTaskBinding

class EditTask : AppCompatActivity() {
    private lateinit var binding: ActivityEditTaskBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)
        val id = intent.getStringExtra("task")
        val task = db.getTask(id!!)

        val editable = Editable.Factory.getInstance()
        binding.name.text = editable.newEditable(task!!.name)
        binding.desc.text = editable.newEditable(task.description)
        binding.completed.isChecked = task.completed
        binding.creationDate.text = "Created: " + task.creationDate

        binding.deleteButton.setOnClickListener{
            db.deleteTask(id)
            finish()
        }

        binding.saveButton.setOnClickListener{
            val name = binding.name.text.toString()
            val desc = binding.desc.text.toString()
            val completed = binding.completed.isChecked
            val task: TaskItem = TaskItem(
                id=id,
                name=name,
                description=desc,
                completed=completed
            )
            db.updateTask(task)
            finish()
        }
    }
}