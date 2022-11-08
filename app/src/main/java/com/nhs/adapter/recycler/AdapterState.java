package com.nhs.adapter.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.nhs.R;


public class AdapterState extends LinearLayout {
    private ImageView ivIcon;
    private TextView tvMessage;

    public AdapterState(Context context) {
        super(context);
        init();
    }

    public AdapterState(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AdapterState(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.v_adapter_state, this, true);
        ivIcon = (ImageView) findViewById(R.id.v_adapter_state_iv_icon);
        tvMessage = (TextView) findViewById(R.id.v_adapter_state_tv_message);
    }

    public void setIcon(@DrawableRes int drawableId) {
        ivIcon.setImageResource(drawableId);
    }

    public void setMessage(String message) {
        tvMessage.setText(message);
    }

    public void setMessage(@StringRes int stringResId) {
        String message = getContext().getString(stringResId);
        setMessage(message);
    }
}
