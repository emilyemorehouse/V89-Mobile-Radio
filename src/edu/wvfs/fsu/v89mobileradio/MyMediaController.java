package edu.wvfs.fsu.v89mobileradio;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.MediaController;

public class MyMediaController extends MediaController {
    public MyMediaController(Context context)
    {
    	super(context);
    }
	public MyMediaController(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
    @Override
    public void show(int timeout)
    {
    	super.show(0);
    }
    
    @Override
    public void hide()
    { return; }
    
    public void reallyHide()
    { super.hide(); }
}
