package com.cis.project.views.activity.todo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cis.project.R
import com.cis.project.views.utility.recyclerView.NotesDataClass
import com.cis.project.views.utility.recyclerView.ToDoAdapter
import com.cis.project.views.utility.recyclerView.ToDoDataClass
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_to_do.*

class ToDo : Fragment() {
    private lateinit var db: FirebaseFirestore
    private lateinit var userEmail: String

    private lateinit var toDoList: ArrayList<ToDoDataClass>
    private lateinit var todoAdapter: ToDoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // retrieve user email from session using sharedPreference
        val preferences = this.requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
        preferences.getString("email", "")?.let { Log.d("========> EMAIL", it) }
        userEmail = preferences.getString("email", "").toString()

        todoToDashboard.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_global_dashboardMenu)
        }

        todoToCreateList.setOnClickListener() {
            Navigation.findNavController(view).navigate(R.id.action_toDo_to_createList)
        }

        //readList()

        ToDoRecyclerView.layoutManager = LinearLayoutManager(context)
        ToDoRecyclerView.setHasFixedSize(true)
        toDoList = arrayListOf()
        todoAdapter = ToDoAdapter(toDoList)
        ToDoRecyclerView.adapter = todoAdapter
        readDataOnView()
    }

    private fun readDataOnView() {
        db = FirebaseFirestore.getInstance()
        db.collection("USERS").document(userEmail).collection("todo").
            addSnapshotListener(object: EventListener<QuerySnapshot> {
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                    return
                }
                for (dc:DocumentChange in value?. documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        toDoList.add(dc.document.toObject(ToDoDataClass::class.java))
                    }
                }
                todoAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun readList() {
        val toDoArrayList =  ArrayList<ToDoDataClass>()
        db.collection("USERS").document(userEmail).collection("todo")
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { document ->
                    Log.d(
                        "store db ==> ",
                        "Read document with ID ${document.id} - ${document["todoItem"]}"
                    )
                    //notesList.add(document["Note"])

                   // document.toObject(ToDoDataClass)
                    if(document["Note"] != null) {
                        //oDoArrayList.add("${document["todoItem"]}")

                    }
                }
                //textViewdata.text = toDoArrayList.toString()
                Log.d("ArrayList => ", toDoArrayList.size.toString())
            }
            .addOnFailureListener { exception ->
                Log.w("store error ==>", "Error getting documents $exception")
            }

        for (i in toDoArrayList) {
            println(i)
        }
    }

}