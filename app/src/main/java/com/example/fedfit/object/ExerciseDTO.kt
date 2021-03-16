package com.example.fedfit.`object`

class ExerciseDTO(private var title: String, private var muscle: String) {

    fun getTitle(): String {
        return this.title
    }
    fun getMuscle(): String {
        return this.muscle
    }

}