package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
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
			  tv.setTextSize(20);
			  
			  
			  TextView tv2 = new TextView(ctx);
			  tv2.setText(String.valueOf(item.Day) + " " +String.valueOf(item.Hour) +":"+ String.valueOf(item.Minute));
			  tv2.setTextSize(10);
			  
			  TextView tv3 = new TextView(ctx);
			  tv3.setText(item.Description);
			  tv3.setTextSize(10);
			  
			  view.addView(tv);
			  view.addView(tv2);
			  view.addView(tv3);
		      return view;
		}
	}
}
