package com.example.fedfit.`object`

class WorkoutSessionDTO {


    private var title:String
    private var date:String
    private var exerciseList:ArrayList<ExerciseDTO> = ArrayList()
    private var exerciseRepetitionList:ArrayList<String> = ArrayList()
    private var exerciseSetsList:ArrayList<String> = ArrayList()

    constructor(titleModel:String, date:String,repsList:ArrayList<String>,setsList:ArrayList<String>,exerciseTitleArray: ArrayList<String>,exerciseMuscleArray:ArrayList<String>){
        this.title = titleModel
        this.date = date
        this.exerciseRepetitionList = repsList
        this.exerciseSetsList = setsList
        for(i in exerciseTitleArray.indices){
            this.exerciseList.add(ExerciseDTO(exerciseTitleArray[i],exerciseMuscleArray[i]))
        }
    }



    fun getExerciseList(): ArrayList<ExerciseDTO> {
        return this.exerciseList
    }

    fun getTitle(): String {
        return this.title
    }

    fun getExerciseRepList(): ArrayList<String> {
        return this.exerciseRepetitionList

    }
    fun getExerciseSetList(): ArrayList<String> {
        return this.exerciseSetsList
    }
}