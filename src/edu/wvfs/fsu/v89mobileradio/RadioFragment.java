package edu.wvfs.fsu.v89mobileradio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
public class RadioFragment extends android.support.v4.app.Fragment {
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  View myFragmentView = inflater.inflate(R.layout.radio_fragment, container, false);
	  final MobileRadioApplication myApp = (MobileRadioApplication)getActivity().getApplication();
	  myApp.loader = (ProgressBar)myFragmentView.findViewById(R.id.preparing_bar);
	  myApp.play = (ToggleButton)myFragmentView.findViewById(R.id.playPause);
	  if(myApp.rServ != null && myApp.rServ.isPlaying())
		  myApp.play.toggle();
	  myApp.play.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked) 
					myApp.rServ.resumeMusic();
				else
					myApp.rServ.pauseMusic();
			}
			
		});
	  if(myApp.rServ != null && myApp.rServ.IsPrepared)
	  {
		  
			myApp.play.setVisibility(View.VISIBLE);
			myApp.loader.setVisibility(View.GONE);
	  }
	  return myFragmentView;
	 }
}
