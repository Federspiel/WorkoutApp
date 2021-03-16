package com.example.fedfit.ui.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fedfit.`object`.WorkoutModelDTO
import com.example.fedfit.view.SessionExerciseCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomeViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    private val _workout = MutableLiveData<ArrayList<WorkoutModelDTO>>().apply {
        var arrayList:ArrayList<WorkoutModelDTO> = ArrayList()
        db.collection("WorkoutModel")
                .whereEqualTo("user", auth.uid)
                .get()
                .addOnSuccessListener { documents -> for (document in documents)
                {
                   arrayList.add(parseWorkoutModelDocument(document))
                }
                   value = arrayList
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                }
    }

    val workout: LiveData<ArrayList<WorkoutModelDTO>> = _workout

    private fun parseWorkoutModelDocument(document: QueryDocumentSnapshot): WorkoutModelDTO {

        var hashMap = document.data.toMap()
        var titleData:String = hashMap["title"] as String
        var exerciseArrayData:ArrayList<HashMap<String,String>>
        exerciseArrayData = hashMap["exercises"] as ArrayList<HashMap<String, String>>
        var exerciseTitleList:ArrayList<String> = ArrayList()
        var exerciseMuscleList:ArrayList<String> = ArrayList()
        exerciseArrayData.forEach{
            ex ->
            ex["exerciseName"]?.let { exerciseTitleList.add(it) }
            ex["muscle"]?.let { exerciseMuscleList.add(it) }
        }
        return WorkoutModelDTO(titleData,exerciseTitleList,exerciseMuscleList)
    }


    fun saveWorkoutSessionToDatabase(sessionTitle: String, secvArray: ArrayList<SessionExerciseCardView>) {
        Thread {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val c = Calendar.getInstance()
            val date: String = sdf.format(c.time)
            var title = sessionTitle
            if(title == ""){
                title = "Workout-$date"
            }
            val tempRepetitionArray:ArrayList<String> = ArrayList()
            val tempGymSetsArray:ArrayList<String> = ArrayList()
            val tempExerciseTitle:ArrayList<String> = ArrayList()
            val tempMuscleTitle:ArrayList<String> = ArrayList()
            secvArray.forEach{secv ->
                tempRepetitionArray.add(secv.getRepValue())
                tempGymSetsArray.add(secv.getSetValue())
                tempExerciseTitle.add(secv.getExerciseTitleValue())
                tempMuscleTitle.add(secv.getMuscleCategoryValue())
            }
            // a potentially time consuming task
            val workout = hashMapOf(
                    "title" to title,
                    "user" to auth.uid,
                    "date" to date,
                    "exerciseTitle" to tempExerciseTitle,
                    "muscle" to tempMuscleTitle,
                    "repetitions" to tempRepetitionArray,
                    "sets" to tempGymSetsArray

            )
            db.collection("WorkoutSession")
                    .add(workout)
                    .addOnSuccessListener { documentReference ->
                        Log.d(ContentValues.TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error adding document", e)
                    }
        }.start()
    }
}

