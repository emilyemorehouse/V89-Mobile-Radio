package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Album implements ListViewCreator{
	public ArrayList<ListViewCreator> songs;
	public int id;
	public String name;
	public int year;
	public Artist artist;
	public Album() { }
	public static Album FromCursor(Cursor c, SQLiteDatabase db, Artist ar)
	{
		
		Album album = new Album();
		album.id = c.getInt(0);
		album.name = c.getString(1);
		album.songs = MobileRadioApplication.getSongsByAlbum(album.id, db, ar, album);
		album.artist = ar;
		return album;
	}
	
	@Override
	public View createView(Context ctx)
	{
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1f);
		final LinearLayout layout = new LinearLayout(ctx);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout inner = new LinearLayout(ctx);
		inner.setOrientation(LinearLayout.HORIZONTAL);
		final CustomLinearLayout list = new CustomLinearLayout(ctx);
		list.setOrientation(LinearLayout.VERTICAL);
		list.setLayoutParams(llp);
		for(ListViewCreator s : songs)
			list.addView(s.createExpandableView(ctx));
		
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
		title.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
		
		TextView al = new TextView(ctx);
		al.setOnClickListener(titleClick);
		al.setText(artist.name);
		al.setPadding(4, 0, 0, 0);
		al.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
				
		title.setTextSize(16);
		al.setTextSize(16);
		
		inner.addView(title);
		inner.addView(al);
		layout.addView(inner);
		layout.addView(list);

		return layout;
	}
	
	@Override
	public View createExpandableView(Context ctx) {
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1f);
		final LinearLayout layout = new LinearLayout(ctx);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout inner = new LinearLayout(ctx);
		inner.setOrientation(LinearLayout.HORIZONTAL);
		inner.setLayoutParams(llp);
		final CustomLinearLayout list = new CustomLinearLayout(ctx);
		list.setOrientation(LinearLayout.VERTICAL);
		list.setLayoutParams(llp);
		for(ListViewCreator s : songs)
			list.addView(s.createExpandableView(ctx));
		final ImageView img = new ImageView(ctx);
		img.setImageResource(R.drawable.dropdown_default);
		OnClickListener titleClick = new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(list.getVisibility() == View.VISIBLE){
					list.setVisibility(View.GONE);
					img.setImageResource(R.drawable.dropdown_default);
				}else{
					list.setVisibility(View.VISIBLE);
					img.setImageResource(R.drawable.dropdown_expanded);
				}
			}
			
		};
		
		img.setOnClickListener(titleClick);
		
		TextView title = new TextView(ctx);
		title.setOnClickListener(titleClick);
		title.setText(name);
		title.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
		title.setPadding(16, 0, 0, 0);
		title.setTextSize(16);
		inner.addView(title);
		inner.addView(img);
		layout.addView(inner);
		layout.addView(list);

		return layout;
	}
	
	public static View createHeaderView(Context ctx)
	{
		LinearLayout layout = new LinearLayout(ctx);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView title = new TextView(ctx);
		title.setText("Name");
		title.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.5f));
		title.setTextSize(16);
		
		TextView al = new TextView(ctx);
		al.setText("Artist");
		al.setPadding(4, 0, 0, 0);
		al.setTextSize(16);
		al.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.5f));
		

		layout.addView(title);
		layout.addView(al);
		layout.setId(R.id.list_header);
		return layout;
	}
}
