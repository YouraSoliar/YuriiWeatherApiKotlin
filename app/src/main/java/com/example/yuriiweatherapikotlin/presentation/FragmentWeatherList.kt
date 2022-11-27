package com.example.yuriiweatherapikotlin.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.yuriiweatherapikotlin.presentation.adapter.WeatherAdapter
import com.example.yuriiweatherapikotlin.databinding.FragmentWeatherListBinding

class FragmentWeatherList : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentWeatherListBinding
    private lateinit var adapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = WeatherAdapter()
        binding.recyclerViewWeather.adapter = adapter

        viewModel.getWeatherDay().observe(activity as LifecycleOwner) {
            adapter.setWeathers(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentWeatherList()
    }
}