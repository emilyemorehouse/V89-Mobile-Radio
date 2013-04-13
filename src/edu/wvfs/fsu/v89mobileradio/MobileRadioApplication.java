package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
public class MobileRadioApplication extends Application{
	public RadioTask rServ;
	public CheckBox play;
	public ProgressBar loader;
	public Button reconnect;
	public LinearLayout conContent;
	public LinearLayout disconContent;
	public ConnectStatus status = ConnectStatus.NotConnected;
	public boolean dbInit = false;
	public static ArrayList<ListViewCreator> artists = new ArrayList<ListViewCreator>();
	public static ArrayList<ListViewCreator> albums = new ArrayList<ListViewCreator>();
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
	
	public static ArrayList<ListViewCreator> getAlbumsByArtist(int artistId, SQLiteDatabase db, Artist ar)
	{
		ArrayList<ListViewCreator> albumlist = new ArrayList<ListViewCreator>();
		Cursor c = db.rawQuery(String.format("select rowid, * from album where artistid=%d",artistId), null);
		if(!c.moveToFirst()) return albumlist;
		albumlist.add(Album.FromCursor(c, db, ar));
		while(c.moveToNext())
			albumlist.add(Album.FromCursor(c, db, ar));
		c.close();
		albums.addAll(albumlist);
		return albumlist;
	}
	
	public static ArrayList<ListViewCreator> getSongsByAlbum(int albumId, SQLiteDatabase db, Artist ar, Album al)
	{
		ArrayList<ListViewCreator> songlist = new ArrayList<ListViewCreator>();
		Cursor c = db.rawQuery(String.format("select rowid,* from song where albumid=%d",albumId), null);
		if(!c.moveToFirst()) return songlist;
		songlist.add(Song.FromCursor(c,db, ar, al));
		while(c.moveToNext())
			songlist.add(Song.FromCursor(c,db, ar, al));
		c.close();
		songs.addAll(songlist);
		return songlist;
	}
	
	public static void RequestSong(Song s, Context ctx)
	{
		Toast.makeText(ctx, "Song requested: " + s.name +" by "+s.artist.name, Toast.LENGTH_SHORT).show();
	}
}
