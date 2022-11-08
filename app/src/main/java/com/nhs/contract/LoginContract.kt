package com.nhs.contract

import com.nhs.mvp.BaseUserAction
import com.nhs.mvp.BaseView

interface LoginContract {
    interface View:BaseView{
        fun showSomethingWentWrong()
        fun showInvalidAuthentication()
        fun showSignupSuccessful()
        fun loadToken(accessToken:String)
        fun showHomeActivity(accessToken:String)
    }

    interface UserAction:BaseUserAction{
        fun loadRegisterDetails(email:String, password:String)
    }
}