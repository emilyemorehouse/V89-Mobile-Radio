package edu.wvfs.fsu.v89mobileradio;

import android.content.Context;
import android.view.View;

public interface ListViewCreator {
	public View createView(Context ctx);
	public View createExpandableView(Context ctx);
}