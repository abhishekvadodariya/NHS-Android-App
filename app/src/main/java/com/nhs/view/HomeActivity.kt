package com.nhs.view

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.nhs.R
import com.nhs.adapter.FormAdapter
import com.nhs.adapter.recycler.SmartRecyclerView
import com.nhs.contract.HomeContract
import com.nhs.model.modelClass.QuestionChoicesReq
import com.nhs.model.repos.Data
import com.nhs.presenter.HomePresenter
import kotlinx.android.synthetic.main.a_home.*

class HomeActivity : AppCompatActivity(),HomeContract.View {

    private lateinit var homePresenter: HomePresenter
    lateinit var formAdapter : FormAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_home)
        initPresenter()
        attachListener()
        val sharedPreference =  getSharedPreferences("NHS_ACCESS_TOKEN_PREFERENCE", Context.MODE_PRIVATE)
        val accessToken = sharedPreference.getString("ACCESS_TOKEN_LOGIN", "")

        val token = intent.getStringExtra("TOKEN")

          val newToken = "Bearer "+ token
        println(token)
        println(newToken)

        formAdapter = FormAdapter(applicationContext)
        a_home_rv_form.setRecyclerViewType(SmartRecyclerView.RecyclerViewType.VERTICAL)
        a_home_rv_form.setHasFixedSize(true)
        a_home_rv_form.setAdapter(formAdapter)
        formAdapter.notifyDataSetChanged()
        if (isNetworkAvailable()) {
            homePresenter.getQuotationList(newToken)
        } else {
            showNetworkNotAvailable()
        }
    }

    private fun initPresenter(){
        homePresenter = HomePresenter(this)
    }
    private fun attachListener() {
        a_home_submit.setOnClickListener(View.OnClickListener {
            /*showProgress()
            Handler().postDelayed({
                hideProgress()
                showSignupSuccessful()
            },3000)*/
            var form_filler_name = "John Doe"
            var form_filler_phone = "12356987"
            var form_filler_email = "ohee@gmail.com"
            var form_filler_address_line_1 = "Teesside University"
            var form_filler_address_line_2 = "Middlesbrough"
            var form_filler_post_code = "TS1 3PB"
            var form_filler_city = "Middlesbrough"
            var patient_name = "John Kein"
            var patient_date_of_birth = "11/11/1997"
            var patient_crn_number = "234234"
            var patient_nhs_number = "NHS132333"
            var form_filler_relationship_with_patient = "Friend"
            var details = "My friend is in pain"
            var discuss_permission = "0"
            var hear_back_permission = "1"
            var question_and_choices = ArrayList<QuestionChoicesReq>()

            homePresenter.postQuotationList(form_filler_name,form_filler_phone,form_filler_email,form_filler_address_line_1,
                form_filler_address_line_2,form_filler_post_code,form_filler_city,patient_name,patient_date_of_birth,patient_crn_number,
                patient_nhs_number,form_filler_relationship_with_patient,details,discuss_permission,hear_back_permission,question_and_choices)

        })
    }

    override fun showQuotationList(data: List<Data>) {
        formAdapter.clearAll()
        formAdapter.addItems(data)
    }

    override fun showProgress() {
        a_base_v_content_loading_home.showProgress()
    }

    override fun hideProgress() {
        a_base_v_content_loading_home.hideProgress()
    }

    override fun showSomethingWentWrong() {
        showSnackbar(R.string.somethig_went_wrong)
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

    fun showSignupSuccessful() {
        showSnackbar(R.string.home_successful)
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
        homePresenter.unsubscribe()
        super.onDestroy()
    }

}