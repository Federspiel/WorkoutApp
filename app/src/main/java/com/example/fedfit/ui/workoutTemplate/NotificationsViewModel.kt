package com.example.fedfit.ui.workoutTemplate


import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fedfit.view.EmptyExerciseCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import java.net.URL



class NotificationsViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val _autoCompleteTextList = MutableLiveData<ArrayList<String>>().apply{
        
    }

    private val _workout = MutableLiveData<ArrayList<String>>().apply {
       Thread {
           val url = URL("https://wger.de/api/v2/muscle/")
           val apiResponse = url.readText()
           postValue(parseApiMuscleResponse(apiResponse))
       }.start()
   }

    private fun parseApiMuscleResponse(apiResponse: String): ArrayList<String> {
        val muscleList:ArrayList<String> = ArrayList()
        val gson = Gson()
        val hashMap = gson.fromJson<HashMap<String,Object>>(apiResponse,HashMap::class.java)
        val linkedTreeMapList = hashMap["results"] as ArrayList<LinkedTreeMap<String,String>>

        linkedTreeMapList.forEach{ lmp ->
            lmp["name"]?.let { muscleList.add(it) }
        }

        return muscleList
    }

    val muscleList: LiveData<ArrayList<String>> = _workout




    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }

    fun postWorkoutModel(title: String, eecv: ArrayList<EmptyExerciseCardView>) {
        Thread {
            // a potentially time consuming task
            val tempMap: ArrayList<Map<String, String>> = ArrayList()
            eecv.forEach { eec ->
                val exercise = hashMapOf(
                        "muscle" to eec.muscleList.selectedItem.toString(),
                        "exerciseName" to eec.exerciseTitle.text.toString()
                )
                tempMap.add(exercise)
            }
            val workout = hashMapOf(
                    "title" to title,
                    "user" to auth.uid,
                    "exercises" to tempMap
            )
            db.collection("WorkoutModel")
                    .add(workout)
                    .addOnSuccessListener { documentReference ->
                         Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                     }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
        }.start()
    }

    fun getNewAutoCompleteSuggestions(muscle: Int) {
        Thread {
            val muscleID = apiGetMuscleID(muscle)
            val url = URL("https://wger.de/api/v2/exercise/?muscles=$muscleID&language=2&limit=100")
            val apiResponse = url.readText()
            _autoCompleteTextList.postValue(parseApiExerciseResponse(apiResponse))
        }.start()
    }



    private fun apiGetMuscleID(muscle: Int): Any {
        val url = URL("https://wger.de/api/v2/muscle/")
        val apiResponse = url.readText()
        var id = 1
        val gson = Gson()
        val hashMap = gson.fromJson<HashMap<String,Object>>(apiResponse,HashMap::class.java)
        val linkedTreeMapList = hashMap["results"] as ArrayList<LinkedTreeMap<String,String>>
        linkedTreeMapList[muscle]["image_url_main"]?.let {id = Integer.parseInt(it.substring(it.indexOf("-")+1,it.indexOf(".")))}
        return id;

    }

    private fun parseApiExerciseResponse(apiResponse: String): ArrayList<String> {
        val autoCompList:ArrayList<String> = ArrayList()
        val gson = Gson()
        val hashMap = gson.fromJson<HashMap<String,Object>>(apiResponse,HashMap::class.java)
        val linkedTreeMapList = hashMap["results"] as ArrayList<LinkedTreeMap<String,String>>
        linkedTreeMapList.forEach{ lmp ->
            lmp["name"]?.let { autoCompList.add(it) }
        }
        return autoCompList
    }

    val autoCompleteTextList:LiveData<ArrayList<String>> = _autoCompleteTextList

    val text: LiveData<String> = _text

}