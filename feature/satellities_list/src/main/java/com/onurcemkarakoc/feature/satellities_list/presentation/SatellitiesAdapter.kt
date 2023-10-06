package com.onurcemkarakoc.feature.satellities_list.presentation

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onurcemkarakoc.core.data.model.Satellite
import com.onurcemkarakoc.feature.satellities_list.R
import com.onurcemkarakoc.feature.satellities_list.databinding.ItemSatelliteListLayoutBinding

class SatellitiesAdapter(private val onItemClicked: (satelliteId: String, satelliteNAme: String) -> Unit) :
    ListAdapter<Satellite, SatellitiesAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<Satellite>() {

        override fun areItemsTheSame(oldItem: Satellite, newItem: Satellite) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Satellite, newItem: Satellite): Boolean =
            oldItem == newItem

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemSatelliteListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClicked)
    }

    fun clearList() {
        submitList(null)
    }

    class ViewHolder(private val itemSatelliteListLayoutBinding: ItemSatelliteListLayoutBinding) :
        RecyclerView.ViewHolder(itemSatelliteListLayoutBinding.root) {
        fun bind(item: Satellite, onItemClicked: (satelliteId: String, satelliteNAme: String) -> Unit) {
            itemSatelliteListLayoutBinding.apply {
                root.setOnClickListener {
                    onItemClicked(item.id.toString(),item.name)
                }
                tvSatelliteName.text = item.name

                val stateString: String
                val textColor: Int
                val icon: Drawable?
                if (item.active) {
                    stateString = tvSatelliteState.context.getString(R.string.active)
                    textColor = ContextCompat.getColor(root.context, android.R.color.black)
                    icon = ContextCompat.getDrawable(root.context,R.drawable.circle_shape)?.apply {
                        setTint(ContextCompat.getColor(root.context, R.color.active_state_color))
                    }
                } else {
                    stateString = tvSatelliteState.context.getString(R.string.passive)
                    textColor = ContextCompat.getColor(root.context, android.R.color.darker_gray)
                    icon = ContextCompat.getDrawable(root.context,R.drawable.circle_shape)?.apply {
                        setTint(ContextCompat.getColor(root.context, R.color.passive_state_color))
                    }
                }
                ivStateIcon.setImageDrawable(icon)
                tvSatelliteState.setTextColor(textColor)
                tvSatelliteName.setTextColor(textColor)

                tvSatelliteState.text = stateString
            }
        }
    }

}