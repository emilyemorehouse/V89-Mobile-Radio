package edu.wvfs.fsu.v89mobileradio;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.ToggleButton;


public class RadioActivity extends FragmentActivity implements TaskPrepared {
	private Notification bgNote;
	private NotificationManager nm;
	private ToggleButton play;
	private MobileRadioApplication myApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabFragment.currentTab = 0;
		setContentView(R.layout.activity_main);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.content_fragment, new RadioFragment());
		myApp = (MobileRadioApplication)getApplication();
		ft.commit();
		if(myApp.rServ == null || !myApp.rServ.IsPrepared){
			myApp.rServ = new RadioTask(this);
			myApp.rServ.execute();
		} else {
			onTaskPrepared();
		}
			
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	protected void onPause()
	{
		super.onPause();
		bgNote = new Notification();
		bgNote.icon = android.R.drawable.stat_notify_sync;
		Intent intent = new Intent(this, RadioActivity.class);
        PendingIntent launchIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        bgNote.contentIntent = launchIntent;
		bgNote.contentView = new RemoteViews(this.getPackageName(), R.layout.notification);
		bgNote.tickerText = "V89 Mobile Playing";
		nm.notify(1,bgNote);
	}
	
	protected void onResume()
	{
		super.onResume();
		nm.cancel(1);
	}
	
	@Override
	public void onTaskPrepared() {
		// TODO Auto-generated method stub
		if(play == null)
		{
			play = (ToggleButton)findViewById(R.id.playPause);
			play.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if(isChecked) 
						myApp.rServ.resumeMusic();
					else
						myApp.rServ.pauseMusic();
				}
				
			});
		}
		ProgressBar loader = (ProgressBar)findViewById(R.id.preparing_bar);
		play.setVisibility(View.VISIBLE);
		loader.setVisibility(View.GONE);
	}

}
