package com.example.fedfit.view

import android.R
import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.*


class EmptyExerciseCardView {
    private lateinit var lin:LinearLayout
    lateinit var exerciseTitle:AutoCompleteTextView
    lateinit var muscleList:Spinner


    constructor(context: Context, muscleArray:ArrayList<String>){
        setupLinearLayout(context)
        setupInputFields(context,muscleArray)
        addToLayout()

    }

    private fun addToLayout() {
        lin.addView(muscleList)
        lin.addView(exerciseTitle)
    }

    private fun setupInputFields(context: Context, muscleArray:ArrayList<String>) {
        val title = AutoCompleteTextView(context)
        val muscle = Spinner(context)
        title.imeOptions = (EditorInfo.IME_ACTION_DONE)
        title.inputType = (InputType.TYPE_CLASS_TEXT)
        exerciseTitle = title
        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, muscleArray)
        muscle.adapter = spinnerArrayAdapter
        muscleList = muscle
    }

    private fun setupLinearLayout(context: Context){
        var layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        val p = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        layout.layoutParams = p
        lin=layout
    }

    fun getLayout(): LinearLayout {
        return lin
    }

    }