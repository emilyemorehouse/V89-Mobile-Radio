package edu.wvfs.fsu.v89mobileradio;

import android.app.Application;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

public class MobileRadioApplication extends Application{
	public RadioTask rServ;
	public ToggleButton play;
	public ProgressBar loader;
	public Button reconnect;
	public ConnectStatus status = ConnectStatus.NotConnected;
	public enum ErrorType {
		ConnectError,
		InterruptError
	}
	
	public enum ConnectStatus {
		NotConnected,
		Connected,
		Error,
		NoNetwork
	}
}
