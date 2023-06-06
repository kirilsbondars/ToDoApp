// Website from which code samples were taken:
// https://www.geeksforgeeks.org/android-recyclerview-in-kotlin/

package com.example.todoapp
import TaskItem
import android.content.Context
import android.content.Intent
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: List<TaskItem>, private val context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.textView.text = ItemsViewModel.name
        if(ItemsViewModel.completed) {
            holder.textView.paintFlags = STRIKE_THRU_TEXT_FLAG
            holder.completeButton.setImageResource(R.drawable.checked_24)
        } else {
            holder.completeButton.setImageResource(R.drawable.unchecked_24)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val completeButton: ImageButton = itemView.findViewById(R.id.completeButton)

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, EditTask::class.java)
                intent.putExtra("task", mList[bindingAdapterPosition].id.toString())
                context.startActivity(intent)
            }
        }
    }
}

