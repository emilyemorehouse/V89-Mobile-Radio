package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
}
