package com.nhs.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.nhs.R
import com.nhs.contract.RegisterContract
import com.nhs.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(),RegisterContract.View {

    private lateinit var registerPresenter: RegisterPresenter
    private val ACCESS_TOKEN = "ACCESS_TOKEN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initPresenter()
        attachListener()
    }

    private fun initPresenter(){
        registerPresenter = RegisterPresenter(this)
    }

    private fun attachListener(){
            a_signup_btn_signup.setOnClickListener(View.OnClickListener {
                if (isValid()){
                var name = a_signup_edt_name.text.toString()
                var email = a_signup_edt_email.text.toString()
                var password = a_signup_edt_password.text.toString()
                var confirmPassword = a_signup_edt_confirm_password.text.toString()
                userRegistration(name,email,password,confirmPassword)
                }
            })
        alreadyHaveAccount.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        })

    }

    private fun isValid():Boolean{
        if (a_signup_edt_name.text.toString().trim().isEmpty()) {
            fullName.error = getString(R.string.enter_name)
            return false
        } else if (a_signup_edt_email.text!!.trim().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(a_signup_edt_email.text!!).matches()) {
            email.error = getString(R.string.enter_email)
            return false
        } else if(a_signup_edt_password.text!!.trim().isEmpty() && a_signup_edt_password.text!!.count()< 6){
            password.error = getString(R.string.enter_valid_password)
            return false
        } else if(a_signup_edt_confirm_password.text!!.trim().isEmpty() && !a_signup_edt_confirm_password.text!!.equals(a_signup_edt_password.text)){
            confirm_password.error = getString(R.string.confirm_password)
            return false
        }else{
            return true
        }
    }

    private fun userRegistration(name: String, email: String, password: String, confirmPassword: String){
        if (isNetworkAvailable()){
            registerPresenter.loadRegisterDetails(name,email,password,confirmPassword)
        }else{
            showNetworkNotAvailable()
        }
    }

    override fun showHomeActivity(accessToken:String) {
        val intent = Intent(this,HomeActivity::class.java)
        intent.putExtra("TOKEN",accessToken)
        startActivity(intent)
    }

    override fun showSomethingWentWrong() {
        showSnackbar(R.string.somethig_went_wrong)
    }

    override fun showSignupSuccessful() {
        showSnackbar(R.string.somethig_went_wrong)
    }

    override fun showInvalidAuthentication() {
        a_signup_edt_name.text?.clear()
        a_signup_edt_email.text?.clear()
        a_signup_edt_password.text?.clear()
        a_signup_edt_confirm_password.text?.clear()
    }

    override fun loadToken(accessToken: String) {
        val sharedPreference =  getSharedPreferences("NHS_ACCESS_TOKEN_PREFERENCE",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString(ACCESS_TOKEN,accessToken)
        editor.apply()
    }

    override fun showProgress() {
        a_base_v_content_loading.showProgress()
    }

    override fun hideProgress() {
        a_base_v_content_loading.hideProgress()
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


    override fun onDestroy() {
        registerPresenter.unsubscribe()
        super.onDestroy()
    }

}