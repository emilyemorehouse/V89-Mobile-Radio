package edu.wvfs.fsu.v89mobileradio;

import java.io.IOException;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class RadioTask extends AsyncTask<Void, Void, Void> implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener{
    private MediaPlayer mPlayer;
    private TaskPrepared listener;
    public boolean IsPrepared;
    
    protected void onPreExecute() {
    	mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(this);
    }
    
    public RadioTask(TaskPrepared listener){
        this.listener=listener;
    }
    
    @Override
    protected Void doInBackground (Void... params){
    	try {
 		   mPlayer.setDataSource("http://voice.wvfs.fsu.edu:8000/stream");
 		   mPlayer.prepare();
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
        mPlayer.setOnErrorListener(this);

        if(mPlayer!= null)
        {
         	mPlayer.setVolume(10,10);
        }
        return null;
    }

	public void pauseMusic()
	{
		if(mPlayer.isPlaying())
		{
			mPlayer.pause();
		}
	}

	public void resumeMusic()
	{
		if(mPlayer.isPlaying()==false)
		{
			mPlayer.start();
		}
	}

	public void stopMusic()
	{
		mPlayer.stop();
		mPlayer.release();
		mPlayer = null;
	}
	public boolean isPlaying()
	{ return mPlayer.isPlaying(); }
	
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		listener.onTaskPrepared();
		IsPrepared = true;
	}
	
}
