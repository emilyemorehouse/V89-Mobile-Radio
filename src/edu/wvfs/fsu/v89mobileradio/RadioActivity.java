package edu.wvfs.fsu.v89mobileradio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import edu.wvfs.fsu.v89mobileradio.MobileRadioApplication.ConnectStatus;
import edu.wvfs.fsu.v89mobileradio.MobileRadioApplication.ErrorType;
import android.support.v4.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
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
		if(myApp.schedInit == false)
		{
			MobileRadioApplication.InitSchedule();
			myApp.schedInit = true;
		}
		if(myApp.dbInit == false)
		{
			InitDatabase();
			myApp.dbInit = true;
		}
		
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
		Intent intent = new Intent(this, RadioActivity.class);
        PendingIntent launchIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
        .setContentIntent(launchIntent)
        .setTicker("V89 Mobile Radio - Streaming")
        .setSmallIcon(R.drawable.ic_stat_notify)
        .setContentTitle("V89 Mobile Radio")
        .setContentText("Streaming")
        .setOngoing(true);
		bgNote = builder.build();
		nm.notify(1,bgNote);
	}
	
	protected void onResume()
	{
		super.onResume();
		nm.cancel(1);
	}
	
	@Override
	public void onTaskPrepared() {
		myApp.status = ConnectStatus.Connected;
		myApp.disconContent.setVisibility(View.GONE);
		myApp.conContent.setVisibility(View.VISIBLE);
		ScheduleItem np = MobileRadioApplication.getNowPlaying();
		MobileRadioApplication.nowPlayingDesc.setText(np.Description);
		MobileRadioApplication.nowPlayingTitle.setText(np.Title);
	}
	@Override
	public void onTaskError(final ErrorType type) {
		myApp.status = ConnectStatus.Error;
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				myApp.disconContent.setVisibility(View.VISIBLE);
				myApp.reconnect.setVisibility(View.VISIBLE);
				myApp.loader.setVisibility(View.GONE);
				if(type == ErrorType.ConnectError)
					Toast.makeText(getBaseContext(), "Error connecting to V89", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void InitDatabase()
	{
		
    	// Path to the just created empty db
    	String outFileName = getFilesDir().getPath()+"/test.db";
		File f = new File(outFileName);
		if(!f.exists())
		{
			try{
		    	InputStream myInput = getAssets().open("test.db");
		    	OutputStream myOutput = new FileOutputStream(outFileName);
	
		    	byte[] buffer = new byte[1024];
		    	int length;
		    	while ((length = myInput.read(buffer))>0){
		    		myOutput.write(buffer, 0, length);
		    	}
		 
		    	//Close the streams
		    	myOutput.flush();
		    	myOutput.close();
		    	myInput.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
	    	
		}
		
		SQLiteDatabase db = SQLiteDatabase.openDatabase(outFileName, null, 0);
		Cursor c = db.rawQuery("select rowid,name from artist", null);
		if(!c.moveToFirst()) return;
		MobileRadioApplication.artists.add(Artist.FromCursor(c,db));
		while(c.moveToNext())
			MobileRadioApplication.artists.add(Artist.FromCursor(c,db));
		
		c.close();
		db.close();
		
	}
}
	
