package com.example.androiddata.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.androiddata.data.Monster
import com.example.montersdata.LOG_TAG
import com.example.montersdata.R
import com.example.montersdata.ui.main.MainRecyclerAdapter
import com.example.montersdata.ui.shared.SharedViewModel
import com.example.montersdata.util.PrefsHelper

class MainFragment : Fragment(),
    MainRecyclerAdapter.MonsterItemListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var navController: NavController
    private lateinit var adapter: MainRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        val layoutStyle = PrefsHelper.getItemType(requireContext())
        recyclerView.layoutManager =
            if (layoutStyle == "grid") {
                GridLayoutManager(requireContext(), 2)
            } else {
                LinearLayoutManager(requireContext())
            }

        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host
        )

        swipeLayout = view.findViewById(R.id.swipeLayout)
        swipeLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        viewModel.monsterData.observe(viewLifecycleOwner, {
            adapter = MainRecyclerAdapter(requireContext(), it, this)
            recyclerView.adapter = adapter
            swipeLayout.isRefreshing = false
        })

        viewModel.activityTitle.observe(viewLifecycleOwner, {
            requireActivity().title = it
        })

        return view
    }

    override fun onMonsterItemClick(monster: Monster) {
        Log.i(LOG_TAG, "Selected monster: ${monster.monsterName}")
        viewModel.selectedMonster.value = monster
        navController.navigate(R.id.action_nav_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_view_grid -> {
                PrefsHelper.setItemType(requireContext(), "grid")
                recyclerView.layoutManager =
                    GridLayoutManager(requireContext(), 2)
                recyclerView.adapter = adapter
            }
            R.id.action_view_list -> {
                PrefsHelper.setItemType(requireContext(), "list")
                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
            }
            R.id.action_settings -> {
                navController.navigate(R.id.settingsActivity)
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateActivityTitle()
    }

}
