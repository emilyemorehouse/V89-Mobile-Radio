package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Artist {
	public ArrayList<Album> albums;
	public int id;
	public String name;
	public Artist() { }
	
	public static Artist FromCursor(Cursor c, SQLiteDatabase db)
	{
		
		Artist artist = new Artist();
		artist.id = c.getInt(0);
		artist.name = c.getString(1);
		artist.albums = MobileRadioApplication.getAlbumsByArtist(artist.id, db);
		return artist;
	}
}
