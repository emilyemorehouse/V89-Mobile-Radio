package edu.wvfs.fsu.v89mobileradio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Song {
	public int id;
	public String name;
	public int tracknumber;
	public int playcount;
	public Artist artist;
	public Album album;
	public Song() { }
	
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
}
