package com.nhs.contract

import com.nhs.mvp.BaseUserAction
import com.nhs.mvp.BaseView

interface ForgotPasswordContract {
    interface View:BaseView{
        fun showSomethingWentWrong()
        fun showSignupSuccessful(status : String)
    }

    interface UserAction:BaseUserAction{
        fun loadForgotPasswordDetails(email:String)
    }
}