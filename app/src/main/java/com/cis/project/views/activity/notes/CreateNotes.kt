package com.cis.project.views.activity.notes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cis.project.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_create_notes.*


class CreateNotes : Fragment() {
    private lateinit var db: FirebaseFirestore
    private lateinit var userEmail: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()

        // retrieve user email from session using sharedPreference
        val preferences = this.requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
        preferences.getString("email", "")?.let { Log.d("========> EMAIL", it) }
        userEmail = preferences.getString("email", "").toString()

        createNotesToDashboard.setOnClickListener() {
            Navigation.findNavController(view).navigate(R.id.action_global_dashboardMenu)
        }

        createNotesToNotes.setOnClickListener() {
            Navigation.findNavController(view).navigate(R.id.action_createNotes_to_notes)
        }

        saveNote.setOnClickListener() {
            val note = notesData.text.toString()
            Log.d("Entered Note : ", note)
            val notes = hashMapOf(
                "Note" to note
            )
            if (userEmail != null && note != null) {
                db.collection("USERS").document(userEmail).collection("notes")
                    .add(notes)
                    .addOnSuccessListener {
                        Log.d("Note inserted to Database  ========> ", note)
                        Toast.makeText(activity,"Successfully added to database",Toast.LENGTH_SHORT)
                        notesData.setText("");
                    }
                    .addOnFailureListener {
                        Log.d("Error to insert note into db  ========> ", note)
                    }
            }

        }

        readData()
    }

    private fun readData() {
        //retrieve data from firebase db
        /*userDocument.collection("notes")
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("Database value ========> ", "Notes ${document.query}")
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(
                        "Network Error",
                        "Error writing document",
                        e
                    )
                }*/
    }


}