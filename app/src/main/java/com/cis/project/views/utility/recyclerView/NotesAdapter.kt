package com.cis.project.views.utility.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cis.project.R

class NotesAdapter(private val notesData: ArrayList<NotesDataClass>) : RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.MyViewHolder {
        val viewLayoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_layout, parent, false)
        return NotesAdapter.MyViewHolder(viewLayoutInflater)
    }


    override fun onBindViewHolder(holder: NotesAdapter.MyViewHolder, position: Int) {
        val notes: NotesDataClass = notesData[position]
        holder.noteView.text = notes.Note
    }

    override fun getItemCount(): Int {
        return notesData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteView:TextView = itemView.findViewById(R.id.noteView)
    }
}
