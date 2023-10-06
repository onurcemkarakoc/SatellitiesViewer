package com.onurcemkarakoc.core.common.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.onurcemkarakoc.core.common.LoadingDialog
import com.onurcemkarakoc.core.common.utils.Status

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<B : ViewBinding>(private val inflate: Inflate<B>) : Fragment() {

    abstract fun onDataBound()
    abstract fun onError(message: String?)

    private var _binding: ViewBinding? = null
    protected val binding: B
        get() = _binding as B

    private lateinit var loadingDialog: Dialog
    private val isActive
        get() = isAdded

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onDataBound()
        // sendScreenEvent
    }

    fun handleState(viewModel: BaseViewModel) {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            state?.let {
                when (it.status) {
                    Status.LOADING -> setLoadingIndicator(
                        true
                    )

                    Status.SUCCESS -> setLoadingIndicator(
                        false
                    )

                    Status.ERROR -> {
                        onError(it.message)
                        setLoadingIndicator(false)
                    }
                }
                viewModel.state.value = null
            }
        }
    }


    private fun setLoadingIndicator(
        loading: Boolean,
    ) {
        if (::loadingDialog.isInitialized) {
            loadingDialog.apply {
                if (loading) {
                    if (!isShowing && isActive) show()
                } else {
                    if (isShowing) dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        if (loadingDialog.isShowing) loadingDialog.dismiss()
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}