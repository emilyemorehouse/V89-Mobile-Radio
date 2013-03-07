package edu.wvfs.fsu.v89mobileradio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
public class TabFragment extends android.support.v4.app.Fragment {
	public static int currentTab = 0;
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	  Bundle savedInstanceState) {
	  final FragmentActivity act = this.getActivity();
	  final View myFragmentView = inflater.inflate(R.layout.tabs_fragment, container, false);
	  ImageButton radiobutton = (ImageButton)myFragmentView.findViewById(R.id.button_radio);
	  radiobutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(currentTab == 0) return;
				Intent radio = new Intent();
				radio.setClass(act, RadioActivity.class);
				radio.putExtra("tab", 0);
				startActivity(radio);
			}
			
		});
	  
	  ImageButton calendarbutton = (ImageButton)myFragmentView.findViewById(R.id.button_calendar);
	  calendarbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(currentTab == 1) return;
				Intent calendar = new Intent();
				calendar.setClass(act, CalendarActivity.class);
				calendar.putExtra("tab", 1);
				startActivity(calendar);
			}
			
		});
	  
	  return myFragmentView;
	 }
}
