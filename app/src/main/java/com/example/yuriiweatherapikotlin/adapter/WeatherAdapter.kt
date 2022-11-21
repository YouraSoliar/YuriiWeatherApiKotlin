package com.example.yuriiweatherapikotlin.adapter

import android.R
import android.icu.number.Scale.none
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.yuriiweatherapikotlin.models.WeatherDay


class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    private var weathers: List<WeatherDay> = ArrayList()
    fun setWeathers(weathersVM: List<WeatherDay>) {
        weathers = weathersVM
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: WeatherViewHolder, position: Int) {
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

    internal class WeatherViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewMax: TextView
        private val textViewMin: TextView
        private val textViewAvg: TextView
        private val textViewDate: TextView
        private val textViewFall: TextView

        init {
            textViewAvg = itemView.findViewById(R.id.textViewAvg)
            textViewMin = itemView.findViewById(R.id.textViewMin)
            textViewMax = itemView.findViewById(R.id.textViewMax)
            textViewDate = itemView.findViewById(R.id.textViewDate)
            textViewFall = itemView.findViewById(R.id.textViewFall)
        }
    }
}