package com.o2.glucomate.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.o2.glucomate.R
import com.o2.glucomate.databinding.ItemGlucoseEntryBinding
import com.o2.glucomate.db.tables.GlucoseEntry
import kotlin.math.roundToInt

class GlucoseLevelListAdapter: BaseQuickAdapter<GlucoseEntry, GlucoseLevelListAdapter.VH>() {

    class VH(
        parent: ViewGroup,
        val binding: ItemGlucoseEntryBinding = ItemGlucoseEntryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: GlucoseEntry?) {
        holder.binding.root.findViewById<TextView>(R.id.tv_level).text = item?.level?.roundToInt().toString()
        holder.binding.root.findViewById<TextView>(R.id.tv_state).text = item?.state?.getLabel()
        holder.binding.root.findViewById<TextView>(R.id.tv_time).text = item?.createdAt.toString()
    }
}