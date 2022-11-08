package com.nhs.contract

import com.nhs.model.modelClass.QuestionChoicesReq
import com.nhs.model.repos.Data
import com.nhs.mvp.BaseUserAction
import com.nhs.mvp.BaseView

interface HomeContract {
    interface View:BaseView{
        fun showQuotationList(data: List<Data>)
        fun showSomethingWentWrong()
    }

    interface UserAction:BaseUserAction{
        fun getQuotationList(token:String)
        fun postQuotationList(form_filler_name:String,form_filler_phone:String,
                              form_filler_email:String,form_filler_address_line_1:String,
                              form_filler_address_line_2:String,form_filler_post_code:String,
                              form_filler_city:String,patient_name:String,
                              patient_date_of_birth:String,patient_crn_number:String,
                              patient_nhs_number:String, form_filler_relationship_with_patient:String,
                              details:String,discuss_permission:String,
                              hear_back_permission:String,question_and_choices:ArrayList<QuestionChoicesReq>)
    }
}