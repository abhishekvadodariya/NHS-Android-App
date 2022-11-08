package com.nhs.adapter.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.nhs.R;


public class RecyclerStateView extends FrameLayout {
    AdapterState asvEmpty;

    public RecyclerStateView(Context context) {
        super(context);
        init();
    }

    public RecyclerStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.v_recycler_states, this, true);
        asvEmpty = (AdapterState) findViewById(R.id.v_recycler_states_asv_empty);
        this.setVisibility(GONE);
    }

    public void setEmptyValues(@DrawableRes int drawableResId, @StringRes int messageResId) {
        asvEmpty.setIcon(drawableResId);
        asvEmpty.setMessage(messageResId);
    }

    public void setEmptyValues(@DrawableRes int drawableResId, String message) {
        asvEmpty.setIcon(drawableResId);
        asvEmpty.setMessage(message);
    }

    public void setState(States state) {
        switch (state) {
            case EMPTY: {
                setVisibility(VISIBLE);
                asvEmpty.setVisibility(VISIBLE);
                break;
            }
            case NORMAL: {
                setVisibility(GONE);
                asvEmpty.setVisibility(GONE);
                break;
            }
        }
    }

    public enum States {
        EMPTY,
        NORMAL
    }
}
