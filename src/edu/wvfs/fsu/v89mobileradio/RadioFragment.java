package edu.wvfs.fsu.v89mobileradio;

import java.util.Timer;
import java.util.TimerTask;

import edu.wvfs.fsu.v89mobileradio.MobileRadioApplication.ConnectStatus;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class RadioFragment extends android.support.v4.app.Fragment {
	Timer timer;
	TimerTask timerTask;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View myFragmentView = inflater.inflate(R.layout.radio_fragment,
				container, false);
		final MobileRadioApplication myApp = (MobileRadioApplication) getActivity()
				.getApplication();
		myApp.reconnect = (Button) myFragmentView.findViewById(R.id.reconnect_button);
		myApp.loader = (ProgressBar) myFragmentView
				.findViewById(R.id.preparing_bar);
		myApp.play = (CheckBox) myFragmentView.findViewById(R.id.playPause);
		myApp.conContent = (LinearLayout) myFragmentView.findViewById(R.id.connected_content);
		myApp.disconContent = (LinearLayout) myFragmentView.findViewById(R.id.disconnected_content);
		MobileRadioApplication.nowPlayingTitle = (TextView) myFragmentView.findViewById(R.id.nowPlayingTitle);
		MobileRadioApplication.nowPlayingDesc = (TextView) myFragmentView.findViewById(R.id.nowPlayingDesc);
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
		
		final Activity rfa = getActivity();
		timer = new Timer();
		timerTask = new TimerTask(){

			@Override
			public void run() {
				new AsyncTask<Void, Void, Void>(){

					@Override
					protected Void doInBackground(Void... params) {
							final ScheduleItem np = MobileRadioApplication.getNowPlaying();
							rfa.runOnUiThread(new Runnable(){
								@Override
								public void run() {
									MobileRadioApplication.nowPlayingTitle.setText(np.Title);
									MobileRadioApplication.nowPlayingDesc.setText(np.Description);
								}
							});
						return null;
					}

				}.execute();
			}
			
		};
		timer.scheduleAtFixedRate(timerTask, 0, 60500);

		myApp.reconnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TaskInterface fa = (TaskInterface)getActivity();
				myApp.rServ = new RadioTask(fa);
				myApp.rServ.execute();
				myApp.reconnect.setVisibility(View.GONE);
				myApp.loader.setVisibility(View.VISIBLE);
			}

		});

		if (myApp.status == ConnectStatus.Connected) {
			myApp.disconContent.setVisibility(View.GONE);
			myApp.conContent.setVisibility(View.VISIBLE);
			ScheduleItem np = MobileRadioApplication.getNowPlaying();
			MobileRadioApplication.nowPlayingDesc.setText(np.Description);
			MobileRadioApplication.nowPlayingTitle.setText(np.Title);
		} else if (myApp.status == ConnectStatus.Error || myApp.status == ConnectStatus.NoNetwork) {
			myApp.conContent.setVisibility(View.GONE);
			myApp.loader.setVisibility(View.GONE);
			myApp.disconContent.setVisibility(View.VISIBLE);
			myApp.reconnect.setVisibility(View.VISIBLE);
		}
		return myFragmentView;
	}
	@Override
	public void onPause()
	{
		timer.cancel();
		timer.purge();
		super.onPause();
	}
}
