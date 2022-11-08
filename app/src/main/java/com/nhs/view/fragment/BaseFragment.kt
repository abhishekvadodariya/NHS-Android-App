package com.nhs.view.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.nhs.R
import com.nhs.view.activity.BaseActivity

abstract class BaseFragment : Fragment() {


    private lateinit var vContentLoading: ContentLoadingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getSuperView(inflater, container)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun getSuperView(inflater: LayoutInflater, container: ViewGroup?): View {
        val superView = inflater.inflate(R.layout.f_base, container, false)
        val vsContent = superView.findViewById(R.id.f_base_vs_content) as ViewStub
        vContentLoading = superView.findViewById(R.id.f_base_v_content_loading) as ContentLoadingView
        vsContent.layoutResource = layoutResId()
        vsContent.inflate()

        return superView
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            BaseActivity.currentFragment = this
        }
    }

    /*fun getMainActivity(): MainActivity {
        return activity as MainActivity
    }*/

    override fun onStart() {
        super.onStart()
        BaseActivity.currentFragment = this
    }

    fun showSomethingWentWrong() {
        showSnackbar(R.string.somethig_went_wrong)
    }

    fun showProgress() {
        vContentLoading.showProgress()
    }

    fun hideProgress() {
        vContentLoading.hideProgress()
    }

    fun showToast(@StringRes messageStringResId: Int) {
        Toast.makeText(context, messageStringResId, Toast.LENGTH_SHORT).show()
    }

    fun showToast(message: CharSequence) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackbar(@StringRes messageStringResId: Int) {
        var snackbar = Snackbar.make(requireView(), messageStringResId, Snackbar.LENGTH_LONG)
        showSnackbar(snackbar)
    }

    fun showSnackbar(message: CharSequence) {
        var snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        showSnackbar(snackbar)
    }

    private fun showSnackbar(snackbar: Snackbar) {
        val snackBarView = snackbar.view
        val snackbarTv = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackbarTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snackbar.show()
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun showNetworkNotAvailable() {
        showSnackbar(R.string.internet_not_available)
    }


    abstract fun layoutResId(): Int
}