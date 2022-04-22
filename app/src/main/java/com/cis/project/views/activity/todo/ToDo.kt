package com.cis.project.views.activity.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.cis.project.R
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_to_do.*

class ToDo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoToDashboard.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_global_dashboardMenu)
        }

        todoToCreateList.setOnClickListener() {
            Navigation.findNavController(view).navigate(R.id.action_toDo_to_createList)
        }
    }
}