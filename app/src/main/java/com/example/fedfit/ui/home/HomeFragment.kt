package com.example.fedfit.ui.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fedfit.`object`.WorkoutModelDTO
import com.example.fedfit.R
import com.example.fedfit.view.SessionExerciseCardView

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private  var secvArray:ArrayList<SessionExerciseCardView> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var workoutSessions:ArrayList<WorkoutModelDTO> = ArrayList()
        homeViewModel.workout.observe(viewLifecycleOwner, Observer {
            fillSpinner(view, it)
            spinnerFix(view,it)
        })
        addBasicButtonListeners(view)
    }

    private fun addBasicButtonListeners(view: View){
        var btnCreateWorkoutSessionEntry = view.findViewById<View>(R.id.btn_create_new_workout_session_entry)
        var btnSaveWorkoutSession = view.findViewById<View>(R.id.btn_workout_session_entry_save)
        var btnDiscardWorkoutSession = view.findViewById<View>(R.id.btn_workout_session_entry_discard)
        var spinner = view.findViewById<View>(R.id.spinner_select_workout_session) as Spinner
        var linDynamicExerciseView = view.findViewById<View>(R.id.home_dynamic_content_body) as LinearLayout
        var editSessionTitle = view.findViewById<View>(R.id.email_password_reset_input) as EditText

        btnCreateWorkoutSessionEntry.setOnClickListener {
            btnCreateWorkoutSessionEntry.visibility = View.GONE
            btnSaveWorkoutSession.visibility = View.VISIBLE
            btnDiscardWorkoutSession.visibility = View.VISIBLE
            spinner.visibility = View.VISIBLE
            linDynamicExerciseView.visibility = View.VISIBLE
            editSessionTitle.visibility = View.VISIBLE
        }
        btnDiscardWorkoutSession.setOnClickListener {
            btnCreateWorkoutSessionEntry.visibility = View.VISIBLE
            btnSaveWorkoutSession.visibility = View.GONE
            btnDiscardWorkoutSession.visibility = View.GONE
            spinner.visibility = View.GONE
            linDynamicExerciseView.visibility = View.GONE
            editSessionTitle.visibility = View.GONE
            editSessionTitle.text.clear()
            linDynamicExerciseView.removeAllViews()
            secvArray = ArrayList()
        }
        btnSaveWorkoutSession.setOnClickListener{
            homeViewModel.saveWorkoutSessionToDatabase(editSessionTitle.text.toString(),secvArray)
            var toast = Toast.makeText(requireContext(),"Saved to database",Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
    }


    private fun spinnerFix(view: View, arrayList: ArrayList<WorkoutModelDTO>){
        val spnLocale = view.findViewById<View>(R.id.spinner_select_workout_session) as Spinner
        val dynamicDiv = view.findViewById<View>(R.id.home_dynamic_content_body) as LinearLayout
        spnLocale.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                dynamicDiv.removeAllViews()
                secvArray = ArrayList()
                var value = spnLocale.selectedItem.toString()
                arrayList.forEach{
                    wmDTO ->
                    if(wmDTO.getTitle() == value){
                        wmDTO.getExerciseList().forEach{exDTO ->
                            var tempSSEV = SessionExerciseCardView(requireContext(),exDTO)
                            secvArray.add(tempSSEV)
                            dynamicDiv.addView(secvArray.last().getLayout())
                        }

                    }
                }

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        }
    }




    private fun fillSpinner(view: View, test: ArrayList<WorkoutModelDTO>) {
        if(test.size >= 0){
            var spinner = view.findViewById<View>(R.id.spinner_select_workout_session) as Spinner
            var titleList: ArrayList<String> = ArrayList()
            test.forEach { wmDTO -> titleList.add(wmDTO.getTitle()) }
            val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, titleList)
            spinner.adapter = spinnerArrayAdapter
        }
        else{
            return
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}