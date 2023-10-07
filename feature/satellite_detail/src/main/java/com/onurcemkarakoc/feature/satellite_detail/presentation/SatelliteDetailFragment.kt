package com.onurcemkarakoc.feature.satellite_detail.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.onurcemkarakoc.core.common.base.BaseFragment
import com.onurcemkarakoc.core.common.utils.makeHtml
import com.onurcemkarakoc.core.data.model.PositionItem
import com.onurcemkarakoc.core.data.model.SatelliteDetail
import com.onurcemkarakoc.satellities_detail.R
import com.onurcemkarakoc.satellities_detail.SatelliteDetailNavigationArgs
import com.onurcemkarakoc.satellities_detail.databinding.FragmentSatelliteDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatelliteDetailFragment : BaseFragment<FragmentSatelliteDetailBinding>(FragmentSatelliteDetailBinding::inflate) {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainHandler = Handler(Looper.getMainLooper())
    }

    override fun onDataBound() {
        handleState(viewModel)
        listenEvents()
        viewModel.getSatelliteDetail(satelliteId)
    }

    override fun onError(message: String?) {
        message?.let {
            binding.apply {
                tvError.text = it
                tvError.isVisible = true
                clSatelliteDetail.isVisible = false
            }
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


    private fun listenEvents() {
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
    private fun setPositionsView() {
        binding.apply {
            positionItem?.let {
                val position = it.positions[count - 1].toString()
                tvSatelliteLastPosition.text = root.context.getString(R.string.last_position,position).makeHtml()
            }
        }
    }

    private fun setDetailViews(safeSatelliteDetail: SatelliteDetail) {
        binding.apply {
            clSatelliteDetail.isVisible = true
            tvError.isVisible = false

            tvSatelliteName.text = satelliteName
            tvSatelliteCost.text = tvSatelliteCost.context.getString(R.string.cost,safeSatelliteDetail.cost_per_launch.toString()).makeHtml()
            tvSatelliteFlightDate.text = safeSatelliteDetail.first_flight
            tvSatelliteHeight.text = root.context.getString(R.string.height_mass,safeSatelliteDetail.height.toString()).makeHtml()
        }
    }
}