package com.lkdigital.androidfirebase.todos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class TodoDetailViewModel: ViewModel() {
    private val _todo = MutableLiveData<String>()
    val todo: LiveData<String> get() = _todo

    private val _completed = MutableLiveData<Boolean>()
    val completed: LiveData<Boolean> get() = _completed

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val TAG = "TodoDetailViewModel"

    init {
        _todo.value = ""
        _completed.value = false
        _loading.value = true
    }

    fun getTodo(id: String) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("todos").document(id)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    val todo = document.getString("todo")
                    val completed = document.getBoolean("completed")

                    _todo.value = todo.toString()
                    _completed.value = completed!!

                    _loading.value = false

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
}