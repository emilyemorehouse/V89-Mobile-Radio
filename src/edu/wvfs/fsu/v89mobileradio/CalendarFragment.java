package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
public class CalendarFragment extends android.support.v4.app.Fragment {
	
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
		

		
		ScrollView scroller = new ScrollView(getActivity());
		scroller.setBackgroundColor(Color.BLACK);
		
		//ListView test = new ListView(getActivity());
		LinearLayout test = new LinearLayout(getActivity());
		test.setBackgroundColor(Color.BLACK);
		test.setOrientation(LinearLayout.VERTICAL);
		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);


		//List<TextView> textList = new ArrayList<TextView>(33);
		
		for (int i = 0; i < 33; i++)
		{
			TextView text = new TextView(getActivity());
			text.setBackgroundColor(Color.BLACK);
			text.setText(ScheduleData.ProgramTitle[i] + '\n' + ScheduleData.ProgramDescription[i]+ '\n');
			text.setTextColor(Color.WHITE);		
			
			//scroller.addView(text);
			test.addView(text);
		}
		
		scroller.addView(test);
		
		
	  return scroller;
	 }
}
