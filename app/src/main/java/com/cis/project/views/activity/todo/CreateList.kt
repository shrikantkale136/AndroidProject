package com.cis.project.views.activity.todo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.cis.project.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_create_list.*
import kotlinx.android.synthetic.main.fragment_create_notes.*

class CreateList : Fragment() {
    private lateinit var db: FirebaseFirestore
    private lateinit var userEmail: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()

        // retrieve user email from session using sharedPreference
        val preferences = this.requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
        preferences.getString("email", "")?.let { Log.d("========> EMAIL", it) }
        userEmail = preferences.getString("email", "").toString()

        createListToDashboard.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_global_dashboardMenu)
        }

        createListToToDo.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_createList_to_toDo)
        }

        saveList.setOnClickListener() {
            val toDoItem = todolistData.text.toString()
            Log.d("Entered to do item : ", toDoItem)
            val todo = hashMapOf(
                "todoItem" to toDoItem
            )
            if (userEmail != null && todo != null) {
                db.collection("USERS").document(userEmail).collection("todo")
                    .add(todo)
                    .addOnSuccessListener {
                        Log.d("Note inserted to Database  ========> ", toDoItem)
                        Toast.makeText(activity,"Successfully added to database", Toast.LENGTH_SHORT)
                        todolistData.setText("");
                    }
                    .addOnFailureListener {
                        Log.d("Error to insert note into db  ========> ", toDoItem)
                    }
            }

        }
    }
}