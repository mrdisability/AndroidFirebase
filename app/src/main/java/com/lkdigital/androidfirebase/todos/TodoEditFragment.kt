package com.lkdigital.androidfirebase.todos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lkdigital.androidfirebase.R
import com.lkdigital.androidfirebase.databinding.FragmentTodoDetailBinding
import com.lkdigital.androidfirebase.databinding.FragmentTodoEditBinding
import com.lkdigital.androidfirebase.viewmodels.TodoDetailViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [TodoEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoEditFragment : Fragment() {

    private lateinit var viewModel: TodoDetailViewModel

    private val TAG = "TodoEditFragment"

    private lateinit var binding: FragmentTodoEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_edit, container, false)

        viewModel = ViewModelProvider(this).get(TodoDetailViewModel::class.java)

        //Using dataBinding to bind data directly to views in XML
        binding.todoEditViewModel = viewModel
        binding.lifecycleOwner = this

        val id = arguments?.getString("id")
        viewModel.getTodo(id!!)

        val successObserver = Observer<Boolean> { success ->
            if (success) {
                var bundle = bundleOf("id" to id)
                findNavController().navigate(R.id.todoDetailFragment, bundle)
            }
        }

        viewModel.successfulUpdate.observe(requireActivity(), successObserver)

        binding.updateTodoB.setOnClickListener {
            viewModel.updateTodo(id, binding.updateTodoET.text.toString(), binding.updateCompletedCB.isChecked)
        }

        return binding.root
    }

}