package com.example.simpleweatherapp.presentation.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.data.local.LocalWeather
import kotlinx.android.synthetic.main.item_historical_data.view.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherHistoryAdapter(private val onWeatherClick: OnWeatherClick) :
    ListAdapter<LocalWeather, WeatherHistoryAdapter.WeatherHistoryViewHolder>(DiffUtilCall) {

    object DiffUtilCall : DiffUtil.ItemCallback<LocalWeather>() {
        override fun areItemsTheSame(oldItem: LocalWeather, newItem: LocalWeather): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LocalWeather, newItem: LocalWeather): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class WeatherHistoryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(onWeatherClick: OnWeatherClick, localWeather: LocalWeather) {
            view.date_tv.text = getDate(localWeather.time.toLong(), "dd.MM.yyyy.-hh:mm")
            view.desc_tv.text =
                localWeather.desc + "," + localWeather.temp + view.context.getString(
                    R.string.celsius
                )
            view.setOnClickListener {
                onWeatherClick.onClick(localWeather)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historical_data, parent, false)
        return WeatherHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherHistoryViewHolder, position: Int) {
        holder.bind(onWeatherClick,getItem(position))
    }
}

fun getDate(milliSeconds: Long, dateFormat: String?): String? {
    // Create a DateFormatter object for displaying date in specified format.
    val formatter = SimpleDateFormat(dateFormat)

    // Create a calendar object that will convert the date and time value in milliseconds to date.
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}