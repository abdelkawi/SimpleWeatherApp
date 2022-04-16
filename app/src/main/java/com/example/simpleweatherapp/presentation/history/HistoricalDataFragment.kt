package com.example.simpleweatherapp.presentation.history

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.data.local.LocalWeather
import com.example.simpleweatherapp.databinding.HistoricalDataFragmentBinding
import com.example.simpleweatherapp.domain.toCityResponse
import com.example.simpleweatherapp.presentation.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoricalDataFragment : Fragment(R.layout.historical_data_fragment), OnWeatherClick {
    private val mViewModel: WeatherViewModel by viewModels()
    private val mArgs: HistoricalDataFragmentArgs by navArgs()
    private lateinit var mBinding: HistoricalDataFragmentBinding
    private val mWeatherHistoryAdapter = WeatherHistoryAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = HistoricalDataFragmentBinding.bind(view)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lifecycleScope.launch {
            val list = mViewModel.getWeatherHistory(mArgs.cityName)
            mWeatherHistoryAdapter.submitList(list)
            mBinding.historicalDataRv.adapter = mWeatherHistoryAdapter
        }
        mBinding.toolbar.title = mArgs.cityName + " " + getString(R.string.historical_data)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }

    override fun onClick(localWeather: LocalWeather) {
        findNavController().navigate(
            HistoricalDataFragmentDirections.actionHistoricalDataFragmentToCityDetailsFragment(
                savedData = localWeather.toCityResponse(),
                cityName = null
            )
        )
    }
}

interface OnWeatherClick {
    fun onClick(localWeather: LocalWeather)
}