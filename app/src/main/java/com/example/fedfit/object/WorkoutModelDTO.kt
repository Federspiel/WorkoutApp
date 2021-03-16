package com.example.fedfit.`object`

class WorkoutModelDTO {
    private var title:String
    private var exerciseList:ArrayList<ExerciseDTO> = ArrayList()

    constructor(titleModel:String, exerciseTitleArray: ArrayList<String>, exerciseMuscleArray:ArrayList<String>){
        this.title=titleModel
        for(i in exerciseTitleArray.indices){
            exerciseList.add(ExerciseDTO(exerciseTitleArray[i],exerciseMuscleArray[i]))
        }
    }

    fun getTitle(): String {
        return this.title
    }

    fun getExerciseList():ArrayList<ExerciseDTO>{
        return this.exerciseList
    }
}