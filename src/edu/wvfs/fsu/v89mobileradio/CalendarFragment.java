package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
public class CalendarFragment extends android.support.v4.app.Fragment {
	public String[] weekDays = {
			"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"
	};
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
		  final View myFragmentView = inflater.inflate(R.layout.calendar_fragment, container, false);
		  final ArrayList<ListView> lvlist = new ArrayList<ListView>();
		  lvlist.add((ListView)myFragmentView.findViewById(R.id.calendarlist0));
          lvlist.add((ListView)myFragmentView.findViewById(R.id.calendarlist1));
          lvlist.add((ListView)myFragmentView.findViewById(R.id.calendarlist2));
          lvlist.add((ListView)myFragmentView.findViewById(R.id.calendarlist3));
          lvlist.add((ListView)myFragmentView.findViewById(R.id.calendarlist4));
          lvlist.add((ListView)myFragmentView.findViewById(R.id.calendarlist5));
          lvlist.add((ListView)myFragmentView.findViewById(R.id.calendarlist6));
          
		  final ArrayList<TextView> tvlist = new ArrayList<TextView>();
          tvlist.add((TextView)myFragmentView.findViewById(R.id.calendartext0));
          tvlist.add((TextView)myFragmentView.findViewById(R.id.calendartext1));
          tvlist.add((TextView)myFragmentView.findViewById(R.id.calendartext2));
          tvlist.add((TextView)myFragmentView.findViewById(R.id.calendartext3));
          tvlist.add((TextView)myFragmentView.findViewById(R.id.calendartext4));
          tvlist.add((TextView)myFragmentView.findViewById(R.id.calendartext5));
          tvlist.add((TextView)myFragmentView.findViewById(R.id.calendartext6));
          
          final ArrayList<ImageView> ivlist = new ArrayList<ImageView>();
          ivlist.add((ImageView)myFragmentView.findViewById(R.id.calendarbutton0));
          ivlist.add((ImageView)myFragmentView.findViewById(R.id.calendarbutton1));
          ivlist.add((ImageView)myFragmentView.findViewById(R.id.calendarbutton2));
          ivlist.add((ImageView)myFragmentView.findViewById(R.id.calendarbutton3));
          ivlist.add((ImageView)myFragmentView.findViewById(R.id.calendarbutton4));
          ivlist.add((ImageView)myFragmentView.findViewById(R.id.calendarbutton5));
          ivlist.add((ImageView)myFragmentView.findViewById(R.id.calendarbutton6));
          
	  		OnClickListener titleClick = new OnClickListener(){
	
	  			@Override
	  			public void onClick(View v) {
	  				TextView tv = (TextView)v;
	  				int id = Integer.parseInt(tv.getTag().toString());
	  				ListView tag = lvlist.get(id);
	  				ImageView button = ivlist.get(id);
	  				if(tag.getVisibility() == View.VISIBLE){
	  					tag.setVisibility(View.GONE);
	  					button.setImageResource(R.drawable.dropdown_default);
	  					//tv.set(R.drawable.dropdown_default);
	  				}else{
	  					tag.setVisibility(View.VISIBLE);
	  					button.setImageResource(R.drawable.dropdown_expanded);
	  					//tv.setImageResource(R.drawable.dropdown_expanded);
	  				}
	  			}
	  			
	  		};
		  for(int i = 0; i< 7; ++i)
		  {
			  ListView lv = lvlist.get(i);
			  lv.setAdapter(new CustomAdapters.Schedule(
					  getActivity().getBaseContext(),
					  android.R.layout.simple_list_item_1,
					  MobileRadioApplication.getScheduleByDay(i)
			  ));
			  lv.setVisibility(View.GONE);
			  
			  TextView tv = tvlist.get(i);
			  tv.setTag(i);
			  tv.setTextSize(20);
			  tv.setOnClickListener(titleClick);
			  tv.setText(weekDays[i]);
		  }
		  return myFragmentView;
	 }
}
