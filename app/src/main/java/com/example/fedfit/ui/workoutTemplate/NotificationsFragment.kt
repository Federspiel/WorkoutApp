package com.example.fedfit.ui.workoutTemplate

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fedfit.R
import com.example.fedfit.view.EmptyExerciseCardView


class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var eecvList: ArrayList<EmptyExerciseCardView>
    private lateinit var mList: ArrayList<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eecvList = ArrayList()
        notificationsViewModel.muscleList.observe(viewLifecycleOwner, Observer {
            mList = it
            updateSpinners(it)
        })
        notificationsViewModel.autoCompleteTextList.observe(viewLifecycleOwner, Observer {
            updateAutoComplete(it)
        })
        addBasicButtonListeners(view)
    }

    private fun updateAutoComplete(it: ArrayList<String>) {
        eecvList.forEach { view ->
            var autoCompleteAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, it)
            view.exerciseTitle.setAdapter(autoCompleteAdapter)
        }
    }

    private fun updateSpinners(it: ArrayList<String>) {
        eecvList.forEach { view ->
            val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, it)
            view.muscleList.adapter = spinnerArrayAdapter
        }
    }

    private fun  addBasicButtonListeners(view:View){
        var btnCreateNew = view.findViewById<View>(R.id.btn_create_new_workout)
        var btnSaveNew = view.findViewById<View>(R.id.btn_save_new_workout)
        var btnDiscardNew = view.findViewById<View>(R.id.btn_discard_new_workout)
        var benCreateExercise = view.findViewById<View>(R.id.btn_add_exercise_to_workout)
        var workoutTitle = view.findViewById<View>(R.id.email_password_reset_input) as EditText
        var linLay:LinearLayout = view.findViewById<View>(R.id.newExerciseBody) as LinearLayout
        btnCreateNew.setOnClickListener{
            btnCreateNew.visibility =View.GONE
            btnSaveNew.visibility =View.VISIBLE
            btnDiscardNew.visibility = View.VISIBLE
            benCreateExercise.visibility = View.VISIBLE
            workoutTitle.visibility = View.VISIBLE
        }
        btnDiscardNew.setOnClickListener{
            btnCreateNew.visibility =View.VISIBLE
            btnSaveNew.visibility =View.GONE
            btnDiscardNew.visibility = View.GONE
            benCreateExercise.visibility = View.GONE
            workoutTitle.visibility = View.GONE
            linLay.removeAllViews()
        }
        benCreateExercise.setOnClickListener{
            var eecv =EmptyExerciseCardView(requireContext(),mList)
            eecvList.add(eecv)
            linLay.addView(eecv.getLayout())
            addListenerToSpinner(eecv)
        }
        btnSaveNew.setOnClickListener{
            notificationsViewModel.postWorkoutModel(workoutTitle.text.toString(),eecvList)
            var toast = Toast.makeText(requireContext(),"Saved to database",Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
    }

    private fun addListenerToSpinner(eecv: EmptyExerciseCardView) {
        eecv.muscleList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                notificationsViewModel.getNewAutoCompleteSuggestions(eecv.muscleList.selectedItemPosition)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

}