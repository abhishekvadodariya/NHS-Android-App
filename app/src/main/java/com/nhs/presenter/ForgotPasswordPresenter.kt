package com.nhs.presenter

import com.nhs.contract.ForgotPasswordContract
import com.nhs.contract.LoginContract
import com.nhs.model.api.JobAPI
import com.nhs.model.api.JobServices
import com.nhs.model.modelClass.ForgotPasswordRes
import com.nhs.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Response

class ForgotPasswordPresenter constructor(view: ForgotPasswordContract.View):BasePresenter<ForgotPasswordContract.View>(view),ForgotPasswordContract.UserAction {

    private var apiclient: JobServices? = null
    init {
        apiclient = JobAPI.JobAPI.buildService(JobServices::class.java)
    }

    override fun loadForgotPasswordDetails(email: String) {
        view.showProgress()
        val forgotPasswordCall = apiclient?.userForgotPassword(email)
        forgotPasswordCall?.enqueue(object : retrofit2.Callback<ForgotPasswordRes>{
            override fun onResponse(call: Call<ForgotPasswordRes>, response: Response<ForgotPasswordRes>) {
                view.hideProgress()
                if (response.isSuccessful){
                    val result = response.body()?.status
                    view.showSignupSuccessful(result!!)
                }
            }

            override fun onFailure(call: Call<ForgotPasswordRes>, t: Throwable) {
                view.hideProgress()
                view.showSomethingWentWrong()
            }

        })
    }

    override fun subscribe() {

    }

}