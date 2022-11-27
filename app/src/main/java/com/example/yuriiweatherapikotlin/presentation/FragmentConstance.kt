package com.example.yuriiweatherapikotlin.presentation

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.yuriiweatherapikotlin.R
import com.example.yuriiweatherapikotlin.databinding.FragmentConstanceBinding
import com.example.yuriiweatherapikotlin.domain.models.City

class FragmentConstance : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentConstanceBinding
    lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConstanceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewConst1.setOnClickListener {
            viewModel.city.value = City(binding.textViewConst1.text.toString())
            openFragment()
        }
        binding.textViewConst2.setOnClickListener {
            viewModel.city.value = City(binding.textViewConst2.text.toString())
            openFragment()
        }
        binding.textViewConst3.setOnClickListener {
            viewModel.city.value = City(binding.textViewConst3.text.toString())
            openFragment()
        }
        binding.textViewConst4.setOnClickListener {
            viewModel.city.value = City(binding.textViewConst4.text.toString())
            openFragment()
        }

        binding.textViewConst1.setOnLongClickListener {
            showDialog(binding.textViewConst1)
            true
        }
        binding.textViewConst2.setOnLongClickListener {
            showDialog(binding.textViewConst2)
            true
        }
        binding.textViewConst3.setOnLongClickListener {
            showDialog(binding.textViewConst3)
            true
        }
        binding.textViewConst4.setOnLongClickListener {
            showDialog(binding.textViewConst4)
            true
        }

        preferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val hold1 = preferences.getString("hold1", getString(R.string.text_view_hold))
        val hold2 = preferences.getString("hold2", getString(R.string.text_view_hold))
        val hold3 = preferences.getString("hold3", getString(R.string.text_view_hold))
        val hold4 = preferences.getString("hold4", getString(R.string.text_view_hold))
        val holds = mutableListOf(hold1, hold2, hold3, hold4)
        val texts = mutableListOf(
            binding.textViewConst1,
            binding.textViewConst2,
            binding.textViewConst3,
            binding.textViewConst4)

        for (i in 0..3) {
            if (holds[i] == getString(R.string.text_view_hold)) {
                texts[i].setText((R.string.text_view_hold))
            } else {
                texts[i].text = holds[i]
            }
        }
    }

    private fun openFragment() {
        val fragmentWeatherList = FragmentWeatherList()
        activity?.
        supportFragmentManager?.beginTransaction()?.
        replace(R.id.place_holder, fragmentWeatherList)?.
        addToBackStack(null)?.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentConstance()
    }

    private fun showDialog(textView: TextView){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle(getText(R.string.dialog_pin_your_city))

        val input = EditText(context)

        input.hint = getString(R.string.alert_enter_city)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton(R.string.dialog_set, DialogInterface.OnClickListener { dialog, which ->
            if (input.text.toString() == "") {
                textView.text = getText(R.string.text_view_hold)
            } else {
                textView.text = input.text.toString()
            }
        })
        builder.setNegativeButton(R.string.dialog_cancel, DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel() })

        builder.show()
    }


    override fun onPause() {
        super.onPause()
        val editor: SharedPreferences.Editor = preferences.edit()
        val texts = mutableListOf(
            binding.textViewConst1,
            binding.textViewConst2,
            binding.textViewConst3,
            binding.textViewConst4)

        for (i in 0..3) {
            if (texts[i].text.toString() == getString(R.string.text_view_hold)) {
                editor.putString("hold${i+1}", getString(R.string.text_view_hold))
            } else {
                editor.putString("hold${i+1}", texts[i].text.toString())
            }
        }
        editor.apply()
    }
}