package com.lkdigital.androidfirebase.todos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.lkdigital.androidfirebase.models.Todo

class TodoViewModel: ViewModel() {
    private val _todos = MutableLiveData<ArrayList<Todo>>()
    val todos: LiveData<ArrayList<Todo>> get() = _todos

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        _todos.value = gettodos()
        _loading.value = true
    }

    private fun gettodos(): ArrayList<Todo> {
        val todos = arrayListOf<Todo>()
        FirebaseFirestore.getInstance().collection("todos")
            .orderBy("created")
            .addSnapshotListener { snapshot, exception ->

                if (exception != null) {
                    Log.e("Exception:", "Could not retrieve todos ${exception.localizedMessage}")
                }

                if (snapshot != null) {
                    todos.clear()

                    for (document in snapshot.documents) {
                        val data = document.data
                        val todo = data?.get("todo") as String
                        val completed = data?.get("completed") as Boolean
                        val created = data?.get("created") as Timestamp

                        val documentId = document.id

                        val newTodo = Todo(todo, completed, created, documentId)
                        todos.add(newTodo)
                    }
                }

                _loading.value = false
            }

        return todos
    }
}