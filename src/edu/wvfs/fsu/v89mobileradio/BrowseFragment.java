package edu.wvfs.fsu.v89mobileradio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class BrowseFragment extends android.support.v4.app.Fragment {
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  View myFragmentView = inflater.inflate(R.layout.browse_fragment, container, false);
	  
	  return myFragmentView;
	 }
}
