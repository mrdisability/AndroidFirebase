package com.lkdigital.androidfirebase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lkdigital.androidfirebase.R
import com.lkdigital.androidfirebase.models.Todo

class TodosAdapter(val todos: ArrayList<Todo>) : RecyclerView.Adapter<TodosAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return todos.count()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val todoTV = itemView.findViewById<TextView>(R.id.listTodo)

        //CardViews
        val completedCV = itemView.findViewById<CardView>(R.id.completedCardView)
        val incompleteCV = itemView.findViewById<CardView>(R.id.incompleteCardView)

        fun bindTodos(todo: Todo) {
            todoTV?.text = todo.todo

            if (todo.completed == true) {
                completedCV.visibility = View.VISIBLE
            }else {
                incompleteCV.visibility = View.VISIBLE
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTodos(todos[position])

        holder.itemView.setOnClickListener { view ->

            val id = todos[position].documentId
            val todo = todos[position].todo

            var bundle = bundleOf("id" to id)
            view.findNavController().navigate(R.id.todoDetailFragment, bundle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.todo_list_view, parent, false)
        return ViewHolder(view)
    }
}