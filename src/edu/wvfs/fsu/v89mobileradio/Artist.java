package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Artist implements ListViewCreator {
	public ArrayList<ListViewCreator> albums;
	public int id;
	public String name;
	public Artist() { }
	
	public static Artist FromCursor(Cursor c, SQLiteDatabase db)
	{
		
		Artist artist = new Artist();
		artist.id = c.getInt(0);
		artist.name = c.getString(1);
		artist.albums = MobileRadioApplication.getAlbumsByArtist(artist.id, db, artist);
		return artist;
	}

	@Override
	public View createView(Context ctx) {
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1f);
		final LinearLayout layout = new LinearLayout(ctx);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout inner = new LinearLayout(ctx);
		inner.setOrientation(LinearLayout.HORIZONTAL);
		final CustomLinearLayout list = new CustomLinearLayout(ctx);
		list.setOrientation(LinearLayout.VERTICAL);
		list.setLayoutParams(llp);
		OnClickListener titleClick = new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(list.getVisibility() == View.VISIBLE)
					list.setVisibility(View.GONE);
				else
					list.setVisibility(View.VISIBLE);
			}
			
		};
		
		TextView title = new TextView(ctx);
		title.setOnClickListener(titleClick);
		title.setText(name);
		title.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
		
		for(ListViewCreator s : albums)
			list.addView(s.createExpandableView(ctx));
		
		inner.addView(title);
		layout.addView(inner);
		layout.addView(list);
		return layout;
	}

	@Override
	public View createExpandableView(Context ctx) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static View createHeaderView(Context ctx)
	{
		LinearLayout layout = new LinearLayout(ctx);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView title = new TextView(ctx);
		title.setText("Name");
		title.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
		layout.addView(title);
		layout.setId(R.id.list_header);
		return layout;
	}
}
