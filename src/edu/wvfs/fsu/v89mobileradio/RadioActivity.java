package edu.wvfs.fsu.v89mobileradio;

import edu.wvfs.fsu.v89mobileradio.MobileRadioApplication.ConnectStatus;
import edu.wvfs.fsu.v89mobileradio.MobileRadioApplication.ErrorType;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;


public class RadioActivity extends FragmentActivity implements TaskInterface {
	private Notification bgNote;
	private NotificationManager nm;
	private MobileRadioApplication myApp;
	private boolean isNetworkAvailable() {
		ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
		if (conMgr.getActiveNetworkInfo() != null
		&& conMgr.getActiveNetworkInfo().isAvailable()
		&& conMgr.getActiveNetworkInfo().isConnected()) {
		return true;

		} else return false;
	}
	
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
		
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		if(!isNetworkAvailable())
		{
			myApp.status = ConnectStatus.NoNetwork;
			Toast.makeText(getBaseContext(), "No network connection", Toast.LENGTH_SHORT).show();
			return;
		}
		if(myApp.rServ == null || !myApp.rServ.IsPrepared){
			myApp.rServ = new RadioTask(this);
			myApp.rServ.execute();
		}
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
		if(myApp.rServ == null || !myApp.rServ.isPlaying()) return;
		bgNote = new Notification();
		bgNote.icon = R.drawable.ic_stat_notify;
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
		myApp.status = ConnectStatus.Connected;
		myApp.play.setVisibility(View.VISIBLE);
		myApp.loader.setVisibility(View.GONE);
	}
	@Override
	public void onTaskError(final ErrorType type) {
		// TODO Auto-generated method stub
		myApp.status = ConnectStatus.Error;
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				myApp.reconnect.setVisibility(View.VISIBLE);
				myApp.loader.setVisibility(View.GONE);
				// TODO Auto-generated method stub
				if(type == ErrorType.ConnectError)
					Toast.makeText(getBaseContext(), "Error connecting to V89", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	

}
