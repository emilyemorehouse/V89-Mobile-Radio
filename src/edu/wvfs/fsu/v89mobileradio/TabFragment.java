package edu.wvfs.fsu.v89mobileradio;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
	  final View myFragmentView = inflater.inflate(R.layout.tabs_fragment, container, false);
	  ImageButton radiobutton = (ImageButton)myFragmentView.findViewById(R.id.button_radio);
	  radiobutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(currentTab == 0) return;
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.content_fragment, new RadioFragment());
				currentTab = 0;
				ft.commit();
			}
			
		});
	  
	  ImageButton calendarbutton = (ImageButton)myFragmentView.findViewById(R.id.button_calendar);
	  calendarbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(currentTab == 1) return;
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.content_fragment, new CalendarFragment());
				currentTab = 1;
				ft.commit();
			}
			
		});
	  
	  ImageButton browsebutton = (ImageButton)myFragmentView.findViewById(R.id.button_browse);
	  browsebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(currentTab == 2) return;
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.content_fragment, new BrowseFragment());
				currentTab = 2;
				ft.commit();
			}
			
		});
	  
	  ImageButton contactbutton = (ImageButton)myFragmentView.findViewById(R.id.button_contact);
	  contactbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(currentTab == 3) return;
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.content_fragment, new ContactFragment());
				currentTab = 3;
				ft.commit();
			}
			
		});
	  
	  return myFragmentView;
	 }
}
