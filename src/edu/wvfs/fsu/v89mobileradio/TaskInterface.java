package edu.wvfs.fsu.v89mobileradio;
import edu.wvfs.fsu.v89mobileradio.MobileRadioApplication.ErrorType;

public interface TaskInterface {
	void onTaskPrepared();
	void onTaskError(ErrorType type);
}
