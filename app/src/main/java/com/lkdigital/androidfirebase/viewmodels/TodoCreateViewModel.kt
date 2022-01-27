package com.lkdigital.androidfirebase.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firestore.v1.DocumentTransform

class TodoCreateViewModel: ViewModel() {
    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> get() = _success

    private val TAG = "TodoCreateViewModel"

    init {
        _success.value = false
    }

    fun addTodo(todo: String) {
        val db = Firebase.firestore

        val data = hashMapOf(
            "todo" to todo,
            "completed" to false,
            "created" to FieldValue.serverTimestamp()
        )

        db.collection("todos")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")

                _success.value = true
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)

                _success.value = false
            }
    }
}