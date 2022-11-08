package com.nhs.presenter

import com.nhs.contract.LoginContract
import com.nhs.model.api.JobAPI
import com.nhs.model.api.JobServices
import com.nhs.model.repos.RegisterRes
import com.nhs.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Response

class LoginPresenter constructor(view: LoginContract.View):BasePresenter<LoginContract.View>(view),LoginContract.UserAction {

    private var apiclient: JobServices? = null
    init {
        apiclient = JobAPI.JobAPI.buildService(JobServices::class.java)
    }

    override fun loadRegisterDetails(email: String, password: String) {
        view.showProgress()
        val loginApiCall = apiclient?.userLogin(email,password)
        loginApiCall?.enqueue(object : retrofit2.Callback<RegisterRes>{
            override fun onResponse(call: Call<RegisterRes>, response: Response<RegisterRes>) {
                view.hideProgress()
                val result = response.body()?.accessToken
                if (!result.equals(null)){
                    view.loadToken(result!!)
                    view.showHomeActivity(result)
                    view.showSignupSuccessful()
                }
            }

            override fun onFailure(call: Call<RegisterRes>, t: Throwable) {
                view.hideProgress()
                view.showInvalidAuthentication()
                view.showSomethingWentWrong()
            }
        })
    }

    override fun subscribe() {
    }
}