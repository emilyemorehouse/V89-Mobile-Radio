package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

public class MobileRadioApplication extends Application{
	public RadioTask rServ;
	public ToggleButton play;
	public ProgressBar loader;
	public Button reconnect;
	public ConnectStatus status = ConnectStatus.NotConnected;
	public ArrayList<Artist> artists = new ArrayList<Artist>();
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
	
	public static ArrayList<Album> getAlbumsByArtist(int artistId, SQLiteDatabase db)
	{
		ArrayList<Album> albums = new ArrayList<Album>();
		Cursor c = db.rawQuery(String.format("select rowid, * from album where artistid=%d",artistId), null);
		if(!c.moveToFirst()) return albums;
		albums.add(Album.FromCursor(c, db));
		while(c.moveToNext())
			albums.add(Album.FromCursor(c, db));
		return albums;
	}
	
	public static ArrayList<Song> getSongsByAlbum(int albumId, SQLiteDatabase db)
	{
		ArrayList<Song> songs = new ArrayList<Song>();
		Cursor c = db.rawQuery(String.format("select rowid,* from song where albumid=%d",albumId), null);
		if(!c.moveToFirst()) return songs;
		songs.add(Song.FromCursor(c,db));
		while(c.moveToNext())
			songs.add(Song.FromCursor(c,db));
		return songs;
	}
}
