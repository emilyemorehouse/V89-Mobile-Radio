package edu.wvfs.fsu.v89mobileradio;

import edu.wvfs.fsu.v89mobileradio.MobileRadioApplication.ConnectStatus;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class RadioFragment extends android.support.v4.app.Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View myFragmentView = inflater.inflate(R.layout.radio_fragment,
				container, false);
		final MobileRadioApplication myApp = (MobileRadioApplication) getActivity()
				.getApplication();
		myApp.reconnect = (Button)myFragmentView.findViewById(R.id.reconnect_button);
		myApp.loader = (ProgressBar) myFragmentView
				.findViewById(R.id.preparing_bar);
		myApp.play = (ToggleButton) myFragmentView.findViewById(R.id.playPause);

		if (myApp.rServ != null && myApp.rServ.isPlaying())
			myApp.play.toggle();
		myApp.play.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					myApp.rServ.resumeMusic();
				else
					myApp.rServ.pauseMusic();
			}

		});

		myApp.reconnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TaskInterface fa = (TaskInterface)getActivity();
				myApp.rServ = new RadioTask(fa);
				myApp.rServ.execute();
				myApp.loader.setVisibility(View.VISIBLE);
				myApp.reconnect.setVisibility(View.GONE);
			}

		});

		if (myApp.status == ConnectStatus.Connected) {
			myApp.play.setVisibility(View.VISIBLE);
			myApp.loader.setVisibility(View.GONE);
		} else if (myApp.status == ConnectStatus.Error || myApp.status == ConnectStatus.NoNetwork) {
			myApp.play.setVisibility(View.GONE);
			myApp.loader.setVisibility(View.GONE);
			myApp.reconnect.setVisibility(View.VISIBLE);
		}
		return myFragmentView;
	}
}
