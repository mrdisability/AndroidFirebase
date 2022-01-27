package com.lkdigital.androidfirebase.todos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lkdigital.androidfirebase.R
import com.lkdigital.androidfirebase.databinding.FragmentTodoCreateBinding
import com.lkdigital.androidfirebase.databinding.FragmentTodoDetailBinding
import com.lkdigital.androidfirebase.viewmodels.TodoCreateViewModel
import com.lkdigital.androidfirebase.viewmodels.TodoDetailViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [TodoCreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoCreateFragment : Fragment() {

    private lateinit var viewModel: TodoCreateViewModel

    private val TAG = "TodoCreateFragment"

    private lateinit var binding: FragmentTodoCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_create, container, false)

        viewModel = ViewModelProvider(this).get(TodoCreateViewModel::class.java)

        binding.createTodoB.setOnClickListener {
            viewModel.addTodo(binding.todoET.text.toString())
        }

        val successObserver = Observer<Boolean> { success ->
            if (success) {
                findNavController().navigate(R.id.todosFragment)
            }
        }

        viewModel.success.observe(requireActivity(), successObserver)

        return binding.root
    }
}