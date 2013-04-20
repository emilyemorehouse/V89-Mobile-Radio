package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
public class CustomAdapters {
	public static class Reg  extends ArrayAdapter<ListViewCreator> {
			private ArrayList<ListViewCreator> items;
	
		    public Reg(Context context, int textViewResourceId, ArrayList<ListViewCreator> i) {
		            super(context, textViewResourceId, i);
		            items = i;
		    }
		    
		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		    		ListViewCreator o = items.get(position);
		            return o.createView(getContext());
		    }
	}
	public static class Ex  extends ArrayAdapter<ListViewCreator> {
		private ArrayList<ListViewCreator> items;
		
		public Ex(Context context, int textViewResourceId, ArrayList<ListViewCreator> i) {
		        super(context, textViewResourceId, i);
		        items = i;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
				ListViewCreator o = items.get(position);
		        return o.createExpandableView(getContext());
		}
	}
	
	public static class Schedule extends ArrayAdapter<ScheduleItem>{
		private ArrayList<ScheduleItem> items;
		
		public Schedule(Context context, int textViewResourceId, ArrayList<ScheduleItem> i) {
	        super(context, textViewResourceId, i);
	        items = i;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			  ScheduleItem item = items.get(position);
			  Context ctx = parent.getContext();
			  LinearLayout view = new LinearLayout(ctx);
			  view.setOrientation(LinearLayout.VERTICAL);

			  TextView tv = new TextView(ctx);
			  tv.setText(item.Title);
			  tv.setTextSize(18);
			  if(item.IsPlaying())
				  tv.setTextColor(Color.parseColor("#7dede9"));
			  
			  String hourDisplay;
			  String amPm;
			  if(item.Hour%12 == 0) hourDisplay = "12";
			  else hourDisplay = String.valueOf(item.Hour%12);
			  String minuteDisplay = String.valueOf(item.Minute);
			  if(minuteDisplay.length() == 1)
				  minuteDisplay = "0"+minuteDisplay;
			  
			  if(Math.floor(item.Hour/12) == 1) amPm = "pm";
			  else amPm = "am";
			  
			  hourDisplay += ":" + minuteDisplay + amPm;
			  
			  TextView tv2 = new TextView(ctx);
			  tv2.setText(hourDisplay);
			  tv2.setTextSize(16);
			  
			  TextView tv3 = new TextView(ctx);
			  tv3.setText(item.Description);
			  tv3.setTextSize(14);
			  
			  view.addView(tv);
			  view.addView(tv2);
			  view.addView(tv3);
		      return view;
		}
	}
}
