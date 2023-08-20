package com.o2.glucomate

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.o2.glucomate.databinding.ActivityMainBinding
import com.o2.glucomate.db.tables.GlucoseEntry
import com.o2.glucomate.db.tables.State
import com.o2.glucomate.misc.RulerView
import com.o2.glucomate.ui.home.HomeViewModel
import com.o2.glucomate.ui.main.SectionsPagerAdapter
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener {
            // Handle add button click
            showCustomViewDialog(BottomSheet(LayoutMode.WRAP_CONTENT))
        }
    }

    private fun showCustomViewDialog(dialogBehavior: DialogBehavior = ModalDialog) {
        val dialog = MaterialDialog(this, dialogBehavior).show {
            title(null, "Add new record")
            customView(R.layout.layout_add_glucose_entry, scrollable = true, horizontalPadding = true)
            cornerRadius(36f)
            cancelOnTouchOutside(false)
            positiveButton(null, "Save") { dialog ->
                // Pull the password out of the custom view when the positive button is pressed
                val view = dialog.getCustomView()
                val state = when (view.findViewById<ChipGroup>(R.id.chip_group).checkedChipId) {
                    R.id.chip_fasting -> State.FASTING
                    R.id.chip_before_meal -> State.BEFORE_MEAL
                    R.id.chip_after_meal -> State.AFTER_MEAL
                    R.id.chip_before_bed -> State.BEFORE_BED_TIME
                    else -> State.BEFORE_BED_TIME
                }

                val levelRuler = view.findViewById<RulerView>(R.id.ruler_level)
                val entry = GlucoseEntry(createdAt = Date(), state = state, level = levelRuler.getValue())
                homeViewModel.addGlucoseEntry(entry)
            }
            negativeButton(android.R.string.cancel)
            lifecycleOwner(this@MainActivity)
        }

        val tvLevel = dialog.view.findViewById<TextView>(R.id.tv_level)
        val levelRuler = dialog.view.findViewById<RulerView>(R.id.ruler_level)
        levelRuler.setValueListener { value ->
            tvLevel.text = value.roundToInt().toString()
        }
        tvLevel.text = levelRuler.getValue().roundToInt().toString()
    }
}