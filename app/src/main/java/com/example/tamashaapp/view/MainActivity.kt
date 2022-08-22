package com.example.tamashaapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tamashaapp.R
import com.example.tamashaapp.common.Constants
import com.example.tamashaapp.databinding.ActivityMainBinding
import com.example.tamashaapp.model.Employees
import com.example.tamashaapp.viewmodel.AppViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notifyAll

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: AppViewModel by viewModels()
    private lateinit var view: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)
        observe()

        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = true
            viewModel.loadListData()
            binding.refreshLayout.isRefreshing = false
        }

    }

    private fun observe() {
        viewModel.dataList().observe(this, Observer { employees ->
            if(employees.loading) {
                binding.progress.visibility = View.VISIBLE
            }
            if(employees.message.isNotBlank()) {
                binding.progress.visibility = View.GONE
                if(employees.status != Constants.FAILURE) {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    binding.recyclerView.adapter = EmployeesAdapter(employees.data, applicationContext)
                    binding.recyclerView.setHasFixedSize(true)
                    binding.errorText.visibility = View.GONE
                } else {
                    binding.errorText.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                Snackbar.make(view, employees.message, Snackbar.LENGTH_SHORT).show()
            }

        })
    }
}