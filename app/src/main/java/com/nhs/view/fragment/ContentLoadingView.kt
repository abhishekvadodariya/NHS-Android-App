package com.nhs.view.fragment

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.nhs.R
import kotlinx.android.synthetic.main.v_content_loading.view.*

class ContentLoadingView : FrameLayout {

    constructor(context: Context) : super(context, null) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        layoutTransition = LayoutTransition()
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.v_content_loading, this, true)
        this.visibility = View.GONE
    }

    fun showProgress() {
        this.visibility = View.VISIBLE
        v_content_loading_ll_normal_progress.visibility = View.VISIBLE
    }

    fun hideProgress() {
        this.visibility = View.GONE
        v_content_loading_ll_normal_progress.visibility = View.GONE
    }
}