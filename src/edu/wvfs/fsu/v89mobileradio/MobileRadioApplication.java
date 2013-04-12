package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
public class MobileRadioApplication extends Application{
	public RadioTask rServ;
	public CheckBox play;
	public ProgressBar loader;
	public Button reconnect;
	public LinearLayout conContent;
	public LinearLayout disconContent;
	public ConnectStatus status = ConnectStatus.NotConnected;
	public static ArrayList<Artist> artists = new ArrayList<Artist>();
	public static ArrayList<Album> albums = new ArrayList<Album>();
	public static ArrayList<ListViewCreator> songs = new ArrayList<ListViewCreator>();
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
	
	public static ArrayList<Album> getAlbumsByArtist(int artistId, SQLiteDatabase db, Artist ar)
	{
		Cursor c = db.rawQuery(String.format("select rowid, * from album where artistid=%d",artistId), null);
		if(!c.moveToFirst()) return albums;
		albums.add(Album.FromCursor(c, db, ar));
		while(c.moveToNext())
			albums.add(Album.FromCursor(c, db, ar));
		c.close();
		return albums;
	}
	
	public static ArrayList<ListViewCreator> getSongsByAlbum(int albumId, SQLiteDatabase db, Artist ar, Album al)
	{
		Cursor c = db.rawQuery(String.format("select rowid,* from song where albumid=%d",albumId), null);
		if(!c.moveToFirst()) return songs;
		songs.add(Song.FromCursor(c,db, ar, al));
		while(c.moveToNext())
			songs.add(Song.FromCursor(c,db, ar, al));
		c.close();
		return songs;
	}
}
