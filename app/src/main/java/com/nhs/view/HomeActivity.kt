package com.nhs.view

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.nhs.R
import com.nhs.adapter.FormAdapter
import com.nhs.adapter.recycler.SmartRecyclerView
import com.nhs.contract.HomeContract
import com.nhs.model.modelClass.Data
import com.nhs.presenter.HomePresenter
import kotlinx.android.synthetic.main.a_home.*

class HomeActivity : AppCompatActivity(),HomeContract.View {

    private lateinit var homePresenter: HomePresenter
    lateinit var formAdapter : FormAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_home)
        initPresenter()
        attachListener()
        val sharedPreference =  getSharedPreferences("NHS_ACCESS_TOKEN_PREFERENCE", Context.MODE_PRIVATE)
        val accessToken = sharedPreference.getString("ACCESS_TOKEN_LOGIN", "")

        val token = intent.getStringExtra("TOKEN")

          val newToken = "Bearer "+ token
        println(token)
        println(newToken)

        formAdapter = FormAdapter(applicationContext)
        a_home_rv_form.setRecyclerViewType(SmartRecyclerView.RecyclerViewType.VERTICAL)
        a_home_rv_form.setHasFixedSize(true)
        a_home_rv_form.setAdapter(formAdapter)
        formAdapter.notifyDataSetChanged()
        if (isNetworkAvailable()) {
            homePresenter.getQuotationList(newToken)
        } else {
            showNetworkNotAvailable()
        }
    }

    private fun initPresenter(){
        homePresenter = HomePresenter(this)
    }
    private fun attachListener() {
        a_home_submit.setOnClickListener(View.OnClickListener {
            showProgress()
            Handler().postDelayed({
                hideProgress()
                showSignupSuccessful()
            },3000)

        })
    }

    override fun showQuotationList(data: List<Data>) {
        formAdapter.clearAll()
        formAdapter.addItems(data)
    }

    override fun showProgress() {
        a_base_v_content_loading_home.showProgress()
    }

    override fun hideProgress() {
        a_base_v_content_loading_home.hideProgress()
    }

    override fun showSomethingWentWrong() {
        showSnackbar(R.string.somethig_went_wrong)
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun showNetworkNotAvailable() {
        showSnackbar(R.string.internet_not_available)
    }

    fun showSignupSuccessful() {
        showSnackbar(R.string.home_successful)
    }

    fun showSnackbar(@StringRes messageStringResId: Int) {
        var snackbar = Snackbar.make(findViewById(android.R.id.content), messageStringResId, Snackbar.LENGTH_LONG)
        showSnackbar(snackbar)
    }

    private fun showSnackbar(snackbar: Snackbar) {
        val snackBarView = snackbar.view
        val snackbarTv = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackbarTv.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
        snackbar.show()
    }

    override fun onDestroy() {
        homePresenter.unsubscribe()
        super.onDestroy()
    }

}