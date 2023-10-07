package com.onurcemkarakoc.feature.satellities_list.presentation

import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.onurcemkarakoc.core.common.base.BaseFragment
import com.onurcemkarakoc.core.common.utils.addDivider
import com.onurcemkarakoc.core.common.utils.toDetail
import com.onurcemkarakoc.feature.satellities_list.databinding.FragmentSatellitiesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatellitiesListFragment :
    BaseFragment<FragmentSatellitiesListBinding>(FragmentSatellitiesListBinding::inflate) {

    private val viewModel: SatellitiesListViewModel by viewModels()

    private val adapter by lazy {
        SatellitiesAdapter { satelliteId, satelliteName ->
            findNavController().toDetail(satelliteId, satelliteName)
        }
    }

    override fun onDataBound() {
        handleState(viewModel)
        listenEvents()
        binding.apply {

            rvSatellities.adapter = adapter
            rvSatellities.addDivider()

            etInput.doOnTextChanged { text, _, _, _ ->
                val isEmpty = text.isNullOrEmpty()
                ivRight.isVisible = isEmpty.not()
                viewModel.queryFlow.value = text?.toString().orEmpty()
            }
            ivRight.setOnClickListener {
                etInput.setText("")
                rvSatellities.layoutManager?.scrollToPosition(0)
            }
        }
    }

    override fun onError(message: String?) {
        message?.let {
            binding.apply {
                tvError.text = it
                tvError.isVisible = true
                rvSatellities.isVisible = false
                mcvSearch.isVisible = false
            }
        }
    }

    private fun listenEvents() {
        viewModel.satellitiesList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.apply {
                rvSatellities.isVisible = true
                mcvSearch.isVisible = true
                tvError.isVisible = false
            }
        }
    }
}