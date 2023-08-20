package com.o2.glucomate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.o2.glucomate.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val homeViewModel: HomeViewModel by viewModels()
    private val adapter = GlucoseLevelListAdapter()
    lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // Note that this happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                homeViewModel.glucoseEntries.collect { entries ->
                    adapter.submitList(entries)
                    rv.smoothScrollToPosition(0)
                }
            }
        }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.setOnItemLongClickListener { adapter, view, position ->
            adapter.getItem(position)?.let { glucoseEntry ->


                context?.let { ctx ->
                    MaterialAlertDialogBuilder(ctx)
                        .setTitle("${glucoseEntry.level.roundToInt()} mg/dL - ${glucoseEntry.state.getLabel()}")
                        .setMessage("Do you want to delete this record?")
                        .setNegativeButton("Cancel") { _, _ ->
                            // Respond to negative button press
                        }
                        .setPositiveButton("Yes, Delete!") { _, _ ->
                            // Respond to positive button press
                            homeViewModel.deleteGlucoseEntry(glucoseEntry)
                        }
                        .show()
                }
            }




            true
        }
        rv = view?.findViewById(R.id.rv_glucose_entry_list)!!
        val layoutManager = LinearLayoutManager(context)
        rv.layoutManager = layoutManager
        rv.adapter = adapter
    }
}