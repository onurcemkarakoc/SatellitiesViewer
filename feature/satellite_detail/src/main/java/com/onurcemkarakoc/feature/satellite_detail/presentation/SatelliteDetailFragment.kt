package com.onurcemkarakoc.feature.satellite_detail.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.onurcemkarakoc.core.data.model.PositionItem
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

    private var size = 0
    private var count = 0
    private var positionItem: PositionItem? = null

    lateinit var mainHandler: Handler

    private val updateTextTask = object : Runnable {
        override fun run() {
            count--
            if (count == 0) {
                count = size
            }
            setPositionsView()
            mainHandler.postDelayed(this, 3000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSatelliteDetailBinding.inflate(inflater, container, false)
        mainHandler = Handler(Looper.getMainLooper())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSatelliteDetail(satelliteId)

        viewModel.satelliteDetail.observe(viewLifecycleOwner) {
            setDetailViews(it)
            viewModel.getSatellitePositionItem(satelliteId)
        }

        viewModel.satellitePositionItem.observe(viewLifecycleOwner) {
            size = it.positions.size
            count = size
            positionItem = it
            setPositionsView()
        }
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateTextTask)
    }

    private fun setPositionsView() {
        binding?.apply {
            positionItem?.let {
                val position = it.positions[count - 1].toString()
                tvSatelliteLastPosition.text = position
            }
        }
    }

    private fun setDetailViews(safeSatelliteDetail: SatelliteDetail) {
        binding?.apply {
            tvSatelliteName.text = satelliteName
            tvSatelliteCost.text = safeSatelliteDetail.cost_per_launch.toString()
            tvSatelliteFlightDate.text = safeSatelliteDetail.first_flight
            tvSatelliteHeight.text = safeSatelliteDetail.height.toString()
        }
    }
}