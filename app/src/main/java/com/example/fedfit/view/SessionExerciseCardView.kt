package com.example.fedfit.view

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.fedfit.`object`.ExerciseDTO


class SessionExerciseCardView {
    private var lin: LinearLayout
    private lateinit var titleText: TextView
    private lateinit var muscleCategory: TextView
    private lateinit var repetition:EditText
    private lateinit var gymSets:EditText

    constructor(context: Context, exercise:ExerciseDTO){
        lin = setupLinearLayout(context)
        setupTextFields(context,exercise)
    }

    private fun setupTextFields(context: Context,exercise:ExerciseDTO){
        titleText = TextView(context)
        titleText.text = exercise.getTitle()
        muscleCategory = TextView(context)
        muscleCategory.text = exercise.getMuscle()
        repetition= EditText(context)
        repetition.hint = "Enter Repetitions"
        repetition.imeOptions = (EditorInfo.IME_ACTION_DONE)
        repetition.inputType = (InputType.TYPE_CLASS_NUMBER)
        gymSets = EditText(context)
        gymSets.hint = "Enter Sets"
        gymSets.imeOptions = (EditorInfo.IME_ACTION_DONE)
        gymSets.inputType = (InputType.TYPE_CLASS_NUMBER)
        lin.addView(titleText)
        lin.addView(muscleCategory)
        lin.addView(repetition)
        lin.addView(gymSets)
    }


    private fun setupLinearLayout(context: Context): LinearLayout {
        var layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        val p = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        layout.layoutParams = p
        return layout
    }

    fun getLayout () : LinearLayout{
        return this.lin
    }

    fun getRepValue() : String {
        return this.repetition.text.toString()
    }

    fun getExerciseTitleValue():String{
        return this.titleText.text.toString()
    }

    fun getMuscleCategoryValue():String{
        return this.muscleCategory.text.toString()
    }


    fun getSetValue() : String {
        return this.gymSets.text.toString()
    }
}