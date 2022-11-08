package com.nhs.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.nhs.R
import com.nhs.contract.LoginContract
import com.nhs.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(),LoginContract.View {

    private lateinit var loginPresenter: LoginPresenter
    private val ACCESS_TOKEN_LOGIN = "ACCESS_TOKEN_LOGIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initPresenter()
        attachListener()
    }

    private fun initPresenter(){
        loginPresenter = LoginPresenter(this)
    }

    private fun attachListener() {
        a_signin_btn_login.setOnClickListener(View.OnClickListener {
            if (isValid()){
                var email = a_signin_edt_username.text.toString()
                var password = a_sign_in_edt_password.text.toString()
                userLogin(email,password)
            }
        })

        a_signin_btn_signup.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        })

        sign_in_forgot_password.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
        })
    }

    fun isValid(): Boolean {
        if (a_signin_edt_username.text.toString().trim().isEmpty()) {
            a_signIn_username.error = getString(R.string.enter_email)
            return false
        } else if (a_sign_in_edt_password.text!!.trim().isEmpty()) {
            a_signIn_username.error = getString(R.string.valid_password)
            return false
        } else {
            return true
        }
    }

    private fun userLogin(name: String, email: String){
        if (isNetworkAvailable()){
            loginPresenter.loadRegisterDetails(name,email)
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

    override fun showInvalidAuthentication() {
        a_signin_edt_username.text?.clear()
        a_sign_in_edt_password.text?.clear()
    }

    override fun showSignupSuccessful() {
        showSnackbar(R.string.signup_successful)
    }

    override fun loadToken(accessToken: String) {
        val sharedPreference =  getSharedPreferences("NHS_ACCESS_TOKEN_PREFERENCE",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString(ACCESS_TOKEN_LOGIN,accessToken)
        editor.apply()
    }

    override fun showHomeActivity(accessToken:String) {
        val intent = Intent(this,HomeActivity::class.java)
        intent.putExtra("TOKEN",accessToken)
        startActivity(intent)
    }

    override fun showProgress() {
        a_base_v_content_loading_login.showProgress()
    }

    override fun hideProgress() {
        a_base_v_content_loading_login.hideProgress()
    }

    override fun onDestroy() {
        loginPresenter.unsubscribe()
        super.onDestroy()
    }
}