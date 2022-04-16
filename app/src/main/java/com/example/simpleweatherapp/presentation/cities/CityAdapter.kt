package com.example.simpleweatherapp.presentation.cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleweatherapp.R

class CityAdapter(private val onCityClick: OnCityClick) :
    ListAdapter<String, CityAdapter.CityViewHolder>(DiffCallBack) {

    object DiffCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    class CityViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(onCityClick: OnCityClick, city: String) {
            view.findViewById<TextView>(R.id.city_name_tv).text = city
            view.setOnClickListener {
                onCityClick.onClick(city)
            }
            view.findViewById<ImageView>(R.id.info_imv).setOnClickListener {
                onCityClick.onInfoClick(city)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(onCityClick, getItem(position))

    }
}
