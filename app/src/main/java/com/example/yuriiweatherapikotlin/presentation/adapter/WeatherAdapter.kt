package com.example.yuriiweatherapikotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.yuriiweatherapikotlin.R
import androidx.recyclerview.widget.RecyclerView
import com.example.yuriiweatherapikotlin.domain.models.WeatherDay


class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    private var weathers: List<WeatherDay> = ArrayList()
    fun setWeathers(weathersVM: List<WeatherDay>) {
        weathers = weathersVM
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weathers[position]
        holder.textViewMax.text = weather.getTemperature().maxTemperature
        holder.textViewMin.text = weather.getTemperature().minTemperature
        holder.textViewAvg.text = weather.getTemperature().avgTemperature
        holder.textViewDate.text = weather.date
        if (weather.getTemperature().isSnow == 1) {
            holder.textViewFall.setText(R.string.snow)
        } else if (weather.getTemperature().isRain == 1) {
            holder.textViewFall.setText(R.string.rain)
        } else {
            holder.textViewFall.setText(R.string.none)
        }
    }

    override fun getItemCount(): Int {
        return weathers.size
    }

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewMax: TextView
        val textViewMin: TextView
        val textViewAvg: TextView
        val textViewDate: TextView
        val textViewFall: TextView

        init {
            textViewAvg = itemView.findViewById(R.id.textViewAvg)
            textViewMin = itemView.findViewById(R.id.textViewMin)
            textViewMax = itemView.findViewById(R.id.textViewMax)
            textViewDate = itemView.findViewById(R.id.textViewDate)
            textViewFall = itemView.findViewById(R.id.textViewFall)
        }
    }
}