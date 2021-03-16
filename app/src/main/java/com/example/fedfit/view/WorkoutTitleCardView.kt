package com.example.fedfit.view

import android.content.Context
import android.graphics.Color

import android.widget.LinearLayout
import android.widget.TextView
import com.example.fedfit.`object`.WorkoutSessionDTO

class WorkoutTitleCardView{

    private lateinit var lin:LinearLayout
    private var wsDTO:WorkoutSessionDTO
    private var collapsed:Boolean = true
    private var fecViewList:ArrayList<FilledExerciseCardView> = ArrayList()

    constructor(context: Context, wsDTO: WorkoutSessionDTO){
        this.wsDTO = wsDTO
        setupLayout(context)
        buildTitleCard(context)
        buildExerciseCardViews(context)
        addExerciseCardToLayout()
    }

    private fun addExerciseCardToLayout() {
        fecViewList.forEach { view ->
            lin.addView(view.getLayout())
        }
    }

    private fun buildExerciseCardViews(context: Context) {
        var i = 0;
        while(i < wsDTO.getExerciseList().size){
            val title =  wsDTO.getExerciseList()[i].getTitle()
            val muscle =  wsDTO.getExerciseList()[i].getMuscle()
            val rep =  wsDTO.getExerciseRepList()[i]
            val sets =  wsDTO.getExerciseSetList()[i]
            fecViewList.add(
                    FilledExerciseCardView(
                            context,
                            title,
                            muscle,
                            rep,
                            sets
                    )
            )
            i++;
        }

    }

    private fun buildTitleCard(context: Context) {
        var workoutTitle = TextView(context)
        workoutTitle.text = wsDTO.getTitle()
        workoutTitle.textSize = 30F
        lin.addView(workoutTitle)
    }


    private fun setupLayout(context: Context) {
        var layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        val p = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        layout.layoutParams = p
        lin = layout
    }
    
   

    fun getLayout(): LinearLayout {
        return lin
    }
    
    fun isCollapsed(): Boolean { return this.collapsed}
    
    fun setCollapsed(collapsed:Boolean){
        this.collapsed = collapsed
    }

    fun showExercises() {
        this.fecViewList.forEach { fecView -> 
            fecView.show()
        }
    }

    fun hideExercises() {
        this.fecViewList.forEach { fecView ->
            fecView.hide()
        }
    }

}



