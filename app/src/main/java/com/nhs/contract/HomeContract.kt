package com.nhs.contract

import com.nhs.model.modelClass.Data
import com.nhs.mvp.BaseUserAction
import com.nhs.mvp.BaseView

interface HomeContract {
    interface View:BaseView{
        fun showQuotationList(data: List<Data>)
        fun showSomethingWentWrong()
    }

    interface UserAction:BaseUserAction{
        fun getQuotationList(token:String)
        //fun postQuotationList()
    }
}