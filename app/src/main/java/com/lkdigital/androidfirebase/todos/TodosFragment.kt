package com.lkdigital.androidfirebase.todos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lkdigital.androidfirebase.R
import com.lkdigital.androidfirebase.adapters.TodosAdapter
import com.lkdigital.androidfirebase.databinding.FragmentTodosBinding
import com.lkdigital.androidfirebase.viewmodels.TodoViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [TodosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodosFragment : Fragment() {

    private lateinit var todosAdapter : TodosAdapter
    //val todos = arrayListOf<Todo>()

    private lateinit var viewModel: TodoViewModel

    private val TAG = "TodosFragment"

    private lateinit var binding: FragmentTodosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

//        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todos, container, false)

        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        //Using dataBinding to bind data directly to views in XML
        binding.todoViewModel = viewModel
        binding.lifecycleOwner = this

        todosAdapter = TodosAdapter(viewModel.todos.value!!)
        binding.todosList.adapter = todosAdapter
        val layoutManager = LinearLayoutManager(activity)
        binding.todosList.layoutManager = layoutManager
        binding.todosList.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        return binding.root
    }

}