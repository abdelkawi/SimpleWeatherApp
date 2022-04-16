package com.example.simpleweatherapp.presentation.details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.data.Result
import com.example.simpleweatherapp.data.response.CityResponse
import com.example.simpleweatherapp.databinding.CityDetailsFragmentBinding
import com.example.simpleweatherapp.domain.toLocalWeather
import com.example.simpleweatherapp.presentation.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class CityDetailsFragment : Fragment(R.layout.city_details_fragment) {
    private val mViewModel: WeatherViewModel by viewModels()
    private val mArgs: CityDetailsFragmentArgs by navArgs()
    private lateinit var mBinding: CityDetailsFragmentBinding
    private lateinit var mCityResponse: CityResponse
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = CityDetailsFragmentBinding.bind(view)


        if (mArgs.savedData == null)
            getData()
        else mCityResponse = mArgs.savedData ?: CityResponse()


        mBinding.retryBtn.setOnClickListener {
            getData()
        }
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.cities)

    }


    private fun getData() {
        lifecycleScope.launch {
            mBinding.dataContainer.visibility = View.GONE
            mBinding.loadingPb.visibility = View.VISIBLE
            mBinding.retryBtn.visibility = View.GONE
            mBinding.errorTv.visibility = View.GONE
            when (val res = mViewModel.getCityDetails(mArgs.cityName ?: "")) {
                is Result.Error -> {
                    mBinding.loadingPb.visibility = View.GONE
                    mBinding.retryBtn.visibility = View.VISIBLE
                    mBinding.errorTv.visibility = View.VISIBLE
                    mBinding.errorTv.text = res.error
                }
                is Result.Success -> {
                    mCityResponse = res.data
                    populateData()
                    mViewModel.insertWeather(res.data.toLocalWeather())//save data to local
                }
            }
        }
    }

    private fun populateData() {
        mBinding.loadingPb.visibility = View.GONE
        mBinding.dataContainer.visibility = View.VISIBLE
        mBinding.cityNameTv.text = mCityResponse.name ?: ""
        mBinding.temperatureTv.text =
            String.format(
                Locale.US,
                "%.2f",
                mCityResponse.main?.temp?.minus(273.15f)
            ) + getString(
                R.string.celsius
            )
        mBinding.descriptionTv.text = mCityResponse.weather?.get(0)?.description ?: ""
        mBinding.humidityTv.text = (mCityResponse.main?.humidity ?: 0).toString() + "%"
        mBinding.windspeedTv.text = (mCityResponse.wind?.speed ?: 0).toString() + "Km/h"

        mBinding.detailsImv.load(
            "http://openweathermap.org/img/w/${mCityResponse.weather?.get(0)?.icon}.png"

        ) {
        }
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
}