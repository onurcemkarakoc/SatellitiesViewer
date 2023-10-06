package com.onurcemkarakoc.feature.satellite_detail.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.onurcemkarakoc.core.data.model.SatelliteDetail
import com.onurcemkarakoc.satellities_detail.SatelliteDetailNavigationArgs
import com.onurcemkarakoc.satellities_detail.databinding.FragmentSatelliteDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatelliteDetailFragment : Fragment() {

    private var binding: FragmentSatelliteDetailBinding? = null

    private val args by navArgs<SatelliteDetailNavigationArgs>()

    private val satelliteId: String by lazy {
        args.satelliteId
    }

    private val satelliteName: String by lazy {
        args.satelliteName
    }

    private val viewModel: SatelliteDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSatelliteDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSatelliteDetail(satelliteId)

        viewModel.satelliteDetail.observe(viewLifecycleOwner){
            it?.let { safeSatelliteDetail->
                setViews(safeSatelliteDetail)
            }
        }
    }

    private fun setViews(safeSatelliteDetail: SatelliteDetail) {
        binding?.apply {
            tvSatelliteName.text = satelliteName
            tvSatelliteCost.text = safeSatelliteDetail.cost_per_launch.toString()
            tvSatelliteFlightDate.text = safeSatelliteDetail.first_flight
            tvSatelliteHeight.text = safeSatelliteDetail.height.toString()
        }
    }
}