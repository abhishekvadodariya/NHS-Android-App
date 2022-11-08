package com.nhs.presenter

import com.nhs.contract.HomeContract
import com.nhs.model.api.JobAPI
import com.nhs.model.api.JobServices
import com.nhs.model.modelClass.Data
import com.nhs.model.modelClass.QuotationListRes
import com.nhs.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Response

class HomePresenter constructor(view: HomeContract.View):BasePresenter<HomeContract.View>(view),HomeContract.UserAction {

    private var apiclient: JobServices? = null
    private var listData = ArrayList<Data>()
    init {
        apiclient = JobAPI.JobAPI.buildService(JobServices::class.java)
    }

    override fun getQuotationList(token: String) {
        view.showProgress()
        val getListCall = apiclient?.getQuotationList(token)
        getListCall?.enqueue(object : retrofit2.Callback<QuotationListRes>{
            override fun onResponse(call: Call<QuotationListRes>, response: Response<QuotationListRes>) {
                view.hideProgress()
                println(response.toString())
                listData = response.body()!!.data.toMutableList() as ArrayList<Data>
                view.showQuotationList(listData)
            }

            override fun onFailure(call: Call<QuotationListRes>, t: Throwable) {
                view.hideProgress()
                view.showSomethingWentWrong()
                println(t)
            }

        })
    }



    override fun subscribe() {}
}