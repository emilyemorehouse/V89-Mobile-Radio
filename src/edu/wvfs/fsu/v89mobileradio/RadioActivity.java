package edu.wvfs.fsu.v89mobileradio;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.MediaController;
public class RadioActivity extends FragmentActivity implements OnPreparedListener, MediaController.MediaPlayerControl {
	
	private MediaController mc;
    private MediaPlayer mp;
    private Handler handler = new Handler();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabFragment.currentTab = 0;
		setContentView(R.layout.activity_main);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.content_fragment, new RadioFragment());
		ft.commit();
		
		mp = new MediaPlayer();
		mp.setOnPreparedListener(this);
	    mc = new MyMediaController(this);
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					mp.setDataSource("http://voice.wvfs.fsu.edu:8000/stream");
					mp.prepare();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}).start();
	}
	
	public void onPrepared(MediaPlayer mediaPlayer) {
	    mc.setMediaPlayer(this);
	    mc.setAnchorView(findViewById(R.id.RadioLayout));
        mediaPlayer.start();
	    handler.post(new Runnable() {
	      public void run() {
	        mc.setEnabled(true);
	        mc.show(0);
	      }
	    });
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		return mp.isPlaying();
	}

	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isPlaying() {
		return mp.isPlaying();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		mp.pause();
	}

	@Override
	public void seekTo(int pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		mp.start();
	}

}
