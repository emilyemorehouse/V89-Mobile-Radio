package edu.wvfs.fsu.v89mobileradio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Song implements ListViewCreator {
	public int id;
	public String name;
	public int tracknumber;
	public int playcount;
	public Artist artist;
	public Album album;
	public Song() { }
	
	private OnClickListener och = new OnClickListener(){
		public void onClick(View v) {
			MobileRadioApplication.RequestSong(Song.this, v.getContext());
		}
	};
	
	public static Song FromCursor(Cursor c, SQLiteDatabase db, Artist ar, Album al)
	{
		
		Song song = new Song();
		song.id = c.getInt(0);
		song.name = c.getString(1);
		song.tracknumber = c.getInt(2);
		song.playcount = c.getInt(3);
		song.album = al;
		song.artist = ar;
		return song;
	}
	
	@Override
	public View createView(Context ctx)
	{
		LinearLayout layout = new LinearLayout(ctx);
		layout.setPadding(4, 4, 4, 4);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		
		
		TextView title = new TextView(ctx);
		title.setText(name);
		title.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.4f));
		title.setOnClickListener(och);
		TextView al = new TextView(ctx);
		al.setText(album.name);
		al.setPadding(4, 0, 0, 0);
		al.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.3f));
		al.setOnClickListener(och);
		TextView ar = new TextView(ctx);
		ar.setText(artist.name);
		ar.setPadding(4, 0, 0, 0);
		ar.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.3f));
		ar.setOnClickListener(och);
		title.setTextSize(20);
		al.setTextSize(20);
		ar.setTextSize(20);
		

		layout.addView(title);
		layout.addView(al);
		layout.addView(ar);
		
		return layout;
	}

	public View createExpandableView(Context ctx)
	{
		LinearLayout layout = new LinearLayout(ctx);
		layout.setPadding(4, 4, 4, 4);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		TextView title = new TextView(ctx);
		title.setText(name);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.4f);
		llp.setMargins(24, 0, 0, 0);
		title.setLayoutParams(llp);
		title.setTextSize(16);
		title.setOnClickListener(och);
		layout.addView(title);
	
		return layout;
	}
	
	public static View createHeaderView(Context ctx)
	{
		LinearLayout layout = new LinearLayout(ctx);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView title = new TextView(ctx);
		title.setText("Title");
		title.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.4f));
		title.setTextSize(30);
		
		TextView al = new TextView(ctx);
		al.setText("Album");
		al.setPadding(4, 0, 0, 0);
		al.setTextSize(30);
		al.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.3f));
		
		TextView ar = new TextView(ctx);
		ar.setText("Artist");
		ar.setPadding(4, 0, 0, 0);
		ar.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.3f));
		ar.setTextSize(30);

		layout.addView(title);
		layout.addView(al);
		layout.addView(ar);
		layout.setId(R.id.list_header);
		return layout;
	}
}
