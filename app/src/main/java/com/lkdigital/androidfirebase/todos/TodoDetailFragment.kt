package com.lkdigital.androidfirebase.todos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
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

        return binding.root
    }

//    fun showAlert(message: String) {
//        val builder = AlertDialog.Builder(requireActivity())
//        builder.setMessage(message)
//        builder.show()
//    }

}