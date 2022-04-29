package com.cis.project.views.utility.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cis.project.R

class ToDoAdapter(private val toDoData: ArrayList<ToDoDataClass>): RecyclerView.Adapter<ToDoAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoAdapter.MyViewHolder {
        val viewLayoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_todollayout, parent, false)
        return ToDoAdapter.MyViewHolder(viewLayoutInflater)
    }

    override fun onBindViewHolder(holder: ToDoAdapter.MyViewHolder, position: Int) {
        val toDo : ToDoDataClass = toDoData[position]
        holder.todoItem.text = toDo.todoItem
    }

    override fun getItemCount(): Int {
        return toDoData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoItem: TextView = itemView.findViewById(R.id.todoItem)
    }

}