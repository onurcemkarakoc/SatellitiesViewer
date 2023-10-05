package com.onurcemkarakoc.feature.satellities_list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.onurcemkarakoc.core.common.utils.addDivider
import com.onurcemkarakoc.core.common.utils.toDetail
import com.onurcemkarakoc.feature.satellities_list.databinding.FragmentSatellitiesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatellitiesListFragment : Fragment() {

    private val viewModel: SatellitiesListViewModel by viewModels()

    private var binding: FragmentSatellitiesListBinding? = null

    private val adapter by lazy {
        SatellitiesAdapter { satelliteId ->
            findNavController().toDetail(satelliteId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSatellitiesListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewModel.getSatelliteList()

            rvSatellities.adapter = adapter
            rvSatellities.addDivider()

            viewModel.satellitiesList.observe(viewLifecycleOwner) {
                adapter.submitList(it)
                pbSatellities.isVisible = false
                rvSatellities.isVisible = true
            }
        }

    }

}