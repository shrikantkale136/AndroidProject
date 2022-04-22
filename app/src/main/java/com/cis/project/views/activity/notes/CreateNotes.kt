package com.cis.project.views.activity.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.cis.project.R
import kotlinx.android.synthetic.main.fragment_create_notes.*

class CreateNotes : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotesToDashboard.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_global_dashboardMenu)
        }

        createNotesToNotes.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_createNotes_to_notes)
        }
    }

}