package com.nhs.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.nhs.R
import com.nhs.contract.ForgotPasswordContract
import com.nhs.presenter.ForgotPasswordPresenter
import com.nhs.presenter.LoginPresenter
import kotlinx.android.synthetic.main.a_forgot_password.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class ForgotPasswordActivity : AppCompatActivity(),ForgotPasswordContract.View {

    private lateinit var forgotPasswordPresenter: ForgotPasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_forgot_password)

        initPresenter()
        attachListener()
    }

    fun initPresenter(){
        forgotPasswordPresenter = ForgotPasswordPresenter(this)
    }

    private fun attachListener() {
        a_forgot_password_btn_send.setOnClickListener(View.OnClickListener {
            if (isValid()){
                val email = a_forgot_password_edt_email.text.toString()
                userLogin(email)
            }
        })
    }

    fun isValid(): Boolean {
        if (a_forgot_password_edt_email.text.toString().trim().isEmpty()) {
            a_forgot_password_username.error = getString(R.string.enter_email)
            return false
        } else {
            return true
        }
    }

    private fun userLogin(email: String){
        if (isNetworkAvailable()){
            forgotPasswordPresenter.loadForgotPasswordDetails(email)
        }else{
            showNetworkNotAvailable()
        }
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


    override fun showSomethingWentWrong() {
        showSnackbar(R.string.somethig_went_wrong)
    }

    override fun showSignupSuccessful(status : String) {
        a_forgot_password_tv_success_message.visibility = View.VISIBLE
    }

    override fun showProgress() {
        a_base_v_content_loading_forgot_password.showProgress()
    }

    override fun hideProgress() {
        a_base_v_content_loading_forgot_password.hideProgress()
    }
}