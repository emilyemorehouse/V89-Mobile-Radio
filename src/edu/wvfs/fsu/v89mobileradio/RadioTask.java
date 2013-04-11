/*
 * Task class for v89 Mobile Radio
 * Author: Clinton Powell 
 */
package edu.wvfs.fsu.v89mobileradio;

import edu.wvfs.fsu.v89mobileradio.MobileRadioApplication.ErrorType;
import android.media.MediaPlayer;
import android.os.AsyncTask;

//Radio task runs in the background of the main activity, so as to avoid stalling the initial startup time
public class RadioTask extends AsyncTask<Void, Void, Void> implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{
    
	private MediaPlayer mPlayer;
    
	//interface for the main activity to access the service onprepared listener
	private TaskInterface listener;
    public boolean IsPrepared;
    
    //set up the media player before loading
    protected void onPreExecute() {
    	mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(this);
    }

    
    //constructor with interface to listener
    public RadioTask(TaskInterface listener){
        this.listener=listener;
    }
    
    //background process that prepares the sound data.
    @Override
    protected Void doInBackground (Void... params){
 		try {
			mPlayer.setDataSource("http://voice.wvfs.fsu.edu:8000/stream");
	 		mPlayer.prepare();
		} catch (Exception e) {
			listener.onTaskError(ErrorType.ConnectError);
		}
 	    mPlayer.setOnErrorListener(this);
        if(mPlayer!= null)
        {
         	mPlayer.setVolume(10,10);
        }
        return null;
    }

    //public method to control radio pause
	public void pauseMusic()
	{
		if(mPlayer.isPlaying())
		{
			mPlayer.pause();
		}
	}

    //public method to control radio resume
	public void resumeMusic()
	{
		if(mPlayer.isPlaying()==false)
		{
			mPlayer.start();
		}
	}

    //public method to control radio stop
	public void stopMusic()
	{
		mPlayer.stop();
		mPlayer.release();
		mPlayer = null;
	}
	

    //public method to check whether radio is playing
	public boolean isPlaying()
	{ return mPlayer.isPlaying(); }
	

    //error event to handle and show connection issues/interrupts etc
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		listener.onTaskError(ErrorType.ConnectError);
		return false;
	}

    //when mediaplayer is prepared, calls interface taskprepared method to handle
	//showing of play/pause button, removal of loading icon
	@Override
	public void onPrepared(MediaPlayer mp) {
		listener.onTaskPrepared();
		IsPrepared = true;
	}


	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		listener.onTaskError(ErrorType.ConnectError);
	}
	
}
