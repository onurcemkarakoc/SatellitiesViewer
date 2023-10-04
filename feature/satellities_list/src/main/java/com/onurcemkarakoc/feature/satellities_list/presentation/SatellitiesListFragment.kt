package com.onurcemkarakoc.feature.satellities_list.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.onurcemkarakoc.core.common.Constants
import com.onurcemkarakoc.core.common.utils.readJsonAsset
import com.onurcemkarakoc.feature.satellities_list.databinding.FragmentSatellitiesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatellitiesListFragment : Fragment() {

    private val viewModel: SatellitiesListViewModel by viewModels()

    private var binding: FragmentSatellitiesListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSatellitiesListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.textView?.setOnClickListener {
            viewModel.getSatellitiesList(requireContext().readJsonAsset(Constants.SATELLITES_JSON_FILE_NAME))
        }
    }

}