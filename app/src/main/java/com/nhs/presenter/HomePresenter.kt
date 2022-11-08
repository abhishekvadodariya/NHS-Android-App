package com.nhs.presenter

import com.nhs.contract.HomeContract
import com.nhs.model.api.JobAPI
import com.nhs.model.api.JobServices
import com.nhs.model.modelClass.QuestionChoicesReq
import com.nhs.model.repos.Data
import com.nhs.model.repos.QuotationListRes
import com.nhs.model.repos.QuotationSubmitRes
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

    override fun postQuotationList(
        form_filler_name: String,
        form_filler_phone: String,
        form_filler_email: String,
        form_filler_address_line_1: String,
        form_filler_address_line_2: String,
        form_filler_post_code: String,
        form_filler_city: String,
        patient_name: String,
        patient_date_of_birth: String,
        patient_crn_number: String,
        patient_nhs_number: String,
        form_filler_relationship_with_patient: String,
        details: String,
        discuss_permission: String,
        hear_back_permission: String,
        question_and_choices: ArrayList<QuestionChoicesReq>
    ) {
        val submitChoices = apiclient?.postQuotationList(form_filler_name,form_filler_phone,
            form_filler_email,form_filler_address_line_1,
            form_filler_address_line_2,form_filler_post_code,
            form_filler_city,patient_name,
            patient_date_of_birth,patient_crn_number,
            patient_nhs_number,form_filler_relationship_with_patient,
            details,discuss_permission,
            hear_back_permission,question_and_choices)

        submitChoices?.enqueue(object : retrofit2.Callback<QuotationSubmitRes>{
            override fun onResponse(
                call: Call<QuotationSubmitRes>,
                response: Response<QuotationSubmitRes>) {

            }

            override fun onFailure(call: Call<QuotationSubmitRes>, t: Throwable) {

            }

        })
    }


    override fun subscribe() {}
}