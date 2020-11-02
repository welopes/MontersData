package com.example.montersdata.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.montersdata.R
import com.example.montersdata.databinding.FragmentDetailBinding
import com.example.montersdata.ui.shared.SharedViewModel

class DetailFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host
        )

        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val binding = FragmentDetailBinding.inflate(
            inflater, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

}