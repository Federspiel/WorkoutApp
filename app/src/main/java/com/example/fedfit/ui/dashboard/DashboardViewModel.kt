package com.example.fedfit.ui.dashboard

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fedfit.`object`.WorkoutSessionDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashboardViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _userWorkoutSessions = MutableLiveData<ArrayList<WorkoutSessionDTO>>().apply {
        val tempSessionDTO:ArrayList<WorkoutSessionDTO> = ArrayList()
        db.collection("WorkoutSession")
                .whereEqualTo("user", auth.uid)
                .get()
                .addOnSuccessListener { documents -> for (document in documents)
                {
                   val hashMap = document.data.toMap()
                   tempSessionDTO.add(WorkoutSessionDTO(
                           getTitleFromDBDocument(hashMap),
                           getDateFromDBDocument(hashMap),
                           getRepsFromDBDocument(hashMap),
                           getSetsFromDBDocument(hashMap),
                           getExerciseFromDBDocument(hashMap),
                           getMuscleFromDBDocument(hashMap)
                   ))
                }
                value = tempSessionDTO
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                }
    }


    val workoutSessions: LiveData<ArrayList<WorkoutSessionDTO>> = _userWorkoutSessions



    private fun getMuscleFromDBDocument(document: Map<String, Any>): ArrayList<String> {
        return document["muscle"] as  ArrayList<String>
    }

    private fun getExerciseFromDBDocument(document: Map<String, Any>): ArrayList<String> {
        return document["exerciseTitle"] as  ArrayList<String>
    }

    private fun getSetsFromDBDocument(document: Map<String, Any>):  ArrayList<String> {
        return document["sets"] as  ArrayList<String>
    }

    private fun getRepsFromDBDocument(document: Map<String, Any>):  ArrayList<String> {
        return document["repetitions"] as  ArrayList<String>
    }

    private fun getDateFromDBDocument(document: Map<String, Any>): String {
        return document["date"] as String
    }

    private fun getTitleFromDBDocument(document: Map<String, Any>): String {
        return document["title"] as String
    }

}