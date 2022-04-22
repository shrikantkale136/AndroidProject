package com.cis.project.views.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.cis.project.R
import kotlinx.android.synthetic.main.fragment_dashboard_menu.*

class DashboardMenu : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dashboardMenu1.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_dashboardMenu_to_todoNavigation)
        }
        dashboardMenu2.setOnClickListener(){
            Navigation.findNavController(view).navigate(R.id.action_dashboardMenu_to_notesNavigation)
        }
    }


}