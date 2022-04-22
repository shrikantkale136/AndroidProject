package com.cis.project.views.activity.notes

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cis.project.R
import com.cis.project.views.utility.recyclerView.NotesAdapter
import com.cis.project.views.utility.recyclerView.NotesDataClass
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_notes.*

class Notes : Fragment() {
    private lateinit var db: FirebaseFirestore
    private lateinit var userEmail: String
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var notesList: ArrayList<NotesDataClass>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()

        // retrieve user email from session using sharedPreference
        val preferences = this.requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
        preferences.getString("email", "")?.let { Log.d("========> EMAIL", it) }
        userEmail = preferences.getString("email", "").toString()

        notesToDashboard.setOnClickListener() {
            Navigation.findNavController(view).navigate(R.id.action_global_dashboardMenu)
        }

        notesToCreateNotes.setOnClickListener() {
            Navigation.findNavController(view).navigate(R.id.action_notes_to_createNotes)
        }

        // Recycler view code
        notesRecyclerView.layoutManager = LinearLayoutManager(context)
        val notesArray = resources.getStringArray(R.array.Note)
        notesList = arrayListOf()
        for (i in notesArray.indices) {
            notesList.add(NotesDataClass(notesArray[i]))
            Log.d("Arrayitem ", notesArray[i])
        }

        notesAdapter = NotesAdapter(notesList)
        notesRecyclerView.adapter = notesAdapter
        //eventChangeListener()
        readData()
    }

    private fun eventChangeListener() {
        notesList = arrayListOf()

        if (userEmail != null) {
            db.collection("USERS").document(userEmail).collection("notes")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.e("DB Error", error.message.toString())
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            notesList.add(
                                dc.document.toObject(NotesDataClass::class.java)
                            )
                        }
                    }

                    notesAdapter.notifyDataSetChanged()
                }
        }
    }

    private fun readData() {
        var notesList = arrayListOf<String>()
        db.collection("USERS").document(userEmail).collection("notes")
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { document ->
                    Log.d(
                        "store db ==> ",
                        "Read document with ID ${document.id} - ${document["Note"]}"
                    )
//                    notesList.add(document["Note"])
                }
                Log.d("NotesList", notesList.toString())
            }
            .addOnFailureListener { exception ->
                Log.w("store error ==>", "Error getting documents $exception")
            }
    }

    private fun updateData() {
        //to update
       // db.collection("USERS").document(userEmail).update("notes", notes)
    }
}