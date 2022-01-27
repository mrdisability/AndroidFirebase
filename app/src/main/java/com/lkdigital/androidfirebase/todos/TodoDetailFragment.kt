package com.lkdigital.androidfirebase.todos

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.lkdigital.androidfirebase.R
import com.lkdigital.androidfirebase.databinding.FragmentTodoDetailBinding
import com.lkdigital.androidfirebase.viewmodels.TodoDetailViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [TodoDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoDetailFragment : Fragment() {

    private lateinit var viewModel: TodoDetailViewModel

    private val TAG = "TodoDetailFragment"

    private lateinit var binding: FragmentTodoDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //(activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_detail, container, false)

        viewModel = ViewModelProvider(this).get(TodoDetailViewModel::class.java)

        //Using dataBinding to bind data directly to views in XML
        binding.todoDetailViewModel = viewModel
        binding.lifecycleOwner = this

        val id = arguments?.getString("id")
        viewModel.getTodo(id!!)

        binding.goToEditB.setOnClickListener {
            var bundle = bundleOf("id" to id)
            findNavController().navigate(R.id.todoEditFragment, bundle)
        }

        binding.deleteB.setOnClickListener {
            showDeleteAlert(id)
        }

        val successObserver = Observer<Boolean> { success ->
            if (success) {
                findNavController().navigate(R.id.todosFragment)
            }
        }

        viewModel.successfulDelete.observe(requireActivity(), successObserver)

        return binding.root
    }

    fun showDeleteAlert(id: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Delete Alert")
        builder.setMessage("Delete this todo?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(activity,
                android.R.string.yes, Toast.LENGTH_SHORT).show()

            viewModel.deleteTodo(id)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(activity,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }

        builder.show()
    }

}

//    fun showAlert(message: String) {
//        val builder = AlertDialog.Builder(requireActivity())
//        builder.setMessage(message)
//        builder.show()
//    }