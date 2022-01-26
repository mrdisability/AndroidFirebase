package com.lkdigital.androidfirebase.models

import com.google.firebase.Timestamp

class Todo constructor(val todo: String?, val completed: Boolean?,
                        val created: Timestamp?,
                        val documentId: String)

//todo, completed, created, id