package com.example.simpleweatherapp.presentation.addcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.data.Result
import com.example.simpleweatherapp.databinding.DialogAddCityBinding
import com.example.simpleweatherapp.presentation.WeatherViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCityDialog : BottomSheetDialogFragment() {
    private lateinit var mBinding: DialogAddCityBinding
    private val mViewModel: WeatherViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = DialogAddCityBinding.bind(view)
        mBinding.saveBtn.setOnClickListener {
            lifecycleScope.launch {
                mBinding.saveBtn.isEnabled = false
                if (mViewModel.checkCityName(mBinding.cityNameEt.text.toString())) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.already_added),
                        Toast.LENGTH_LONG
                    ).show()
                    mBinding.saveBtn.isEnabled = true

                } else {
                    val res =
                        mViewModel.getCityDetails(mBinding.cityNameEt.text.toString().lowercase())
                    when (res) {
                        is Result.Error -> {
                            mBinding.saveBtn.isEnabled = true
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.data_not_found),
                                Toast.LENGTH_LONG
                            ).show()

                        }
                        is Result.Success -> {
                            mViewModel.saveSearch(res.data.name ?: "")
                            dismiss()
                        }
                    }
                }

            }
        }
    }
}



