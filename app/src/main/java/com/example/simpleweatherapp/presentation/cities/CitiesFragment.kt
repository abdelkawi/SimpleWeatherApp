package com.example.simpleweatherapp.presentation.cities


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.databinding.FragmentCitiesBinding
import com.example.simpleweatherapp.presentation.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CitiesFragment : Fragment(R.layout.fragment_cities), OnCityClick {

    private val mCityAdapter: CityAdapter = CityAdapter(this)
    private lateinit var mBinding: FragmentCitiesBinding
    private val mViewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding = FragmentCitiesBinding.bind(view)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        mBinding.addCityBtn.setOnClickListener {
            findNavController().navigate(CitiesFragmentDirections.actionCitiesFragmentToAddCityDialog())
        }
        mViewModel.getSearchList().observe(viewLifecycleOwner) {
            mCityAdapter.submitList(it.map { it.cityName })
            mBinding.citiesRv.adapter = mCityAdapter
        }

    }

    override fun onClick(city: String) {
        findNavController().navigate(
            CitiesFragmentDirections.actionCitiesFragmentToCityDetailsFragment(
                cityName = city, savedData = null
            )
        )
    }

    override fun onInfoClick(city: String) {
        findNavController().navigate(
            CitiesFragmentDirections.actionCitiesFragmentToHistoricalDataFragment(city)
        )
    }
}

interface OnCityClick {
    fun onClick(city: String)
    fun onInfoClick(city: String)
}