package edu.wvfs.fsu.v89mobileradio;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class CustomLinearLayout extends LinearLayout {

    public CustomLinearLayout(Context context) {
        super(context);
    }
    
    @Override
    protected void onAttachedToWindow(){
    	setVisibility(View.GONE);
    }
}