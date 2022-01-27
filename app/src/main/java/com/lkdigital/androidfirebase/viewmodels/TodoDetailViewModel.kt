package com.lkdigital.androidfirebase.viewmodels

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

    private val _successfulDelete = MutableLiveData<Boolean>()
    val successfulDelete: LiveData<Boolean> get() = _successfulDelete

    private val _successfulUpdate = MutableLiveData<Boolean>()
    val successfulUpdate: LiveData<Boolean> get() = _successfulUpdate

    private val TAG = "TodoDetailViewModel"

    init {
        _todo.value = ""
        _completed.value = false
        _loading.value = true

        _successfulDelete.value = false

        _successfulUpdate.value = false
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

    fun deleteTodo(id: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("todos").document(id)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")

                _successfulDelete.value = true
            }
            .addOnFailureListener {
                    e -> Log.w(TAG, "Error deleting document", e)

                _successfulDelete.value = false
            }
    }

    fun updateTodo(id: String, todo: String, completed: Boolean) {
        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("todos").document(id)

        ref
            .update("todo", todo, "completed", completed)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated!")
                _successfulUpdate.value = true
            }
            .addOnFailureListener {
                    e -> Log.w(TAG, "Error updating document", e)
                _successfulUpdate.value = false
            }
    }
}