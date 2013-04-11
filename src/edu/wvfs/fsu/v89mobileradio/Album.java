package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Album {
	public ArrayList<Song> songs;
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
}
