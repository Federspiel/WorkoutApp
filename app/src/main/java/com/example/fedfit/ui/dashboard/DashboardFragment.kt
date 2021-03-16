package com.example.fedfit.ui.dashboard


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fedfit.R
import com.example.fedfit.`object`.WorkoutSessionDTO
import com.example.fedfit.view.WorkoutTitleCardView


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    var wtcViewList:ArrayList<WorkoutTitleCardView> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel.workoutSessions.observe(viewLifecycleOwner, Observer {
            createTitleCards(it)
            addTitleCardListeners()
            showCards(view)
        })
    }

    private fun showCards(view: View) {
        var dashboardView = view.findViewById<View>(R.id.dashboard_body) as LinearLayout
        dashboardView.removeAllViews()
        wtcViewList.forEach { wtcView ->
            dashboardView.addView(wtcView.getLayout())
        }
    }

    private fun addTitleCardListeners() {
        wtcViewList.forEach{ wtcView ->
            wtcView.getLayout().setOnClickListener{
                if(wtcView.isCollapsed()){
                    wtcView.showExercises()
                    wtcView.setCollapsed(false)
                }
                else{
                    wtcView.hideExercises()
                    wtcView.setCollapsed(true)
                }
            }
        }
    }

    private fun createTitleCards(it: ArrayList<WorkoutSessionDTO>?) {
        wtcViewList = ArrayList()
        it?.forEach{ wsDTO ->
            wtcViewList.add(WorkoutTitleCardView(requireContext(),wsDTO))
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


}