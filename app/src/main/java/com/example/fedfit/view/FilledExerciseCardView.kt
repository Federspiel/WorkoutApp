package com.example.fedfit.view

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView


class FilledExerciseCardView {
    private lateinit var lin:LinearLayout
    private lateinit var titleText:TextView
    private lateinit var muscleCategory:TextView
    private lateinit var gymSetsValue:TextView
    private lateinit var repValue:TextView

    constructor(context: Context ,Title: String, Muscle: String ,SetValue:String,RepValue:String){
        setupLinearLayout(context)
        addFieldsToFilledCard(context)
        addFieldValues(Title,Muscle,SetValue,RepValue)
        hide()
    }

    private fun setupLinearLayout(context: Context) {
        var layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        val p = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        layout.layoutParams = p
        lin=layout
    }

    private fun addFieldValues(title: String, muscle: String, setValue: String, repValue: String) {
        this.titleText.text = title
        this.titleText.textSize = 24F
        this.muscleCategory.text = muscle
        val setsText = "Sets: $setValue"
        this.gymSetsValue.text = setsText
        val repsText = "Reps: $repValue"
        this.repValue.text = repsText
    }

    private fun addFieldsToFilledCard(context: Context){
        titleText = TextView(context)
        muscleCategory = TextView(context)
        gymSetsValue = TextView(context)
        repValue = TextView(context)
        lin.addView(titleText)
        lin.addView(muscleCategory)
        lin.addView(gymSetsValue)
        lin.addView(repValue)
    }

    fun hide() {
        lin.visibility = View.GONE

    }

    fun show() {
        lin.visibility = View.VISIBLE

    }
    fun getLayout(): LinearLayout {
        return this.lin
    }
}