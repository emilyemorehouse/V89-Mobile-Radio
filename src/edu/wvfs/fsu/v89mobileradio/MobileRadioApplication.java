package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;
import java.util.TimerTask;

import edu.wvfs.fsu.v89mobileradio.ScheduleItem.FlagType;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
	public boolean schedInit = false;
	public static ScheduleItem nowPlaying;
	public static Calendar lastPoll;
	public static TextView nowPlayingTitle;
	public static TextView nowPlayingDesc;
	public static ArrayList<ListViewCreator> artists = new ArrayList<ListViewCreator>();
	public static ArrayList<ListViewCreator> albums = new ArrayList<ListViewCreator>();
	public static ArrayList<ListViewCreator> songs = new ArrayList<ListViewCreator>();
	public static ArrayList<ScheduleItem> schedule = new ArrayList<ScheduleItem>();
	
	public static void InitSchedule()
	{
		schedule = new ArrayList<ScheduleItem>();
		schedule.add(new ScheduleItem("SUNDAY MORNING BLUES","BLUES, R & B and GOSPEL",0,0,13,15,0,60,FlagType.None));
        schedule.add(new ScheduleItem("LEGACIES","SINGER/SONGWRITER",0,0,16,17,0,60,FlagType.None));
        schedule.add(new ScheduleItem( "WORLD MUSIC SHOW","FROM AROUND THE GLOBE", 0,0,18,1,0,60,FlagType.None));
        schedule.add(new ScheduleItem("SUNDAY EVENING JAZZ","NEW and TRADITIONAL JAZZ\nLIVE the 1st Sunday of the Month", 0,0,20,21,0,60,FlagType.None));
        schedule.add(new ScheduleItem("SOLAMENTE LATINO","SALSA, FLAMEANCO, MARIACHI, and MORE!", 0,0,22,1,0,60,FlagType.None));
        schedule.add(new ScheduleItem("ARTIST FEATURE","ARTIST/BAND/LABEL RETROSPECTIVE", 1,1,0,1,0,60,FlagType.None));
        schedule.add(new ScheduleItem("STARING AT THE SUN","EXPERIMENTAL MUSIC", 1,1,18,0,0,60,FlagType.None));
        schedule.add(new ScheduleItem( "YOUR VOICE","NEWS CALL-IN SHOW", 1,1,18,18,0,60,FlagType.None));
        schedule.add(new ScheduleItem("TOMAHAWK TALK","SPORTS CALL-IN SHOW",  1,1,20,1,0,60,FlagType.None));
        schedule.add(new ScheduleItem("NEW RELEASE", "NEW RELEASE PROGRAM", 1,2,22,23,0,60,FlagType.None));
        schedule.add(new ScheduleItem("HOOTENANNY","LOCAL MUSIC / INTERVIEWS / LIVE STUDIO PERFORMANCES", 2,3,22,1,0,60,FlagType.None));
        schedule.add(new ScheduleItem("45 SPEED","INDIE LABEL 7 INCHES",  3,3,0,1,0,60,FlagType.None));
        schedule.add(new ScheduleItem("BETTER OFF DEAD","PUNK, PURE PUNK", 3,3,2,3,0,60,FlagType.None));
        schedule.add(new ScheduleItem("SONIC SAFARI","AUDITORY SCHMORGASBORG", 3,3,19,20,0,60,FlagType.None));
        schedule.add(new ScheduleItem("ORTHOPHONIA","Music from the world of scratchy sounds",  3,3,21,21,0,60,FlagType.FirstWednesday));
        schedule.add(new ScheduleItem("VOX POULI", "NEWS MAGAZINE", 3,3,21,21,0,60,FlagType.None));
        schedule.add(new ScheduleItem("THE VOICE BOX","LITERATURE/SPOKEN WORD", 3,3,21,21,0,60,FlagType.ThirdWednesday));
        schedule.add(new ScheduleItem("SONIC SAFARI HONKY TALK","HOUR OF OLD SCHOOL COUNTRY, ROCKABILLY and MORE!",3,3,21,21,0,60,FlagType.FourthWednesday)); 
        schedule.add(new ScheduleItem("MICKEE FAUST CABARET", "The Fifth of Faust Comedy Hour POLITICAL/SOCIAL SATIRE", 3,3,21,21,0,60,FlagType.FifthWednesday));
        schedule.add(new ScheduleItem("METAL MADNESS","METAL AND HARDCORE", 3,4,22,23,0,60,FlagType.None));
        
        schedule.add(new ScheduleItem("CLUB CONVERGE","DANCE MUSIC AND MORE!",4,5,22,1,0,60,FlagType.None));
        schedule.add(new ScheduleItem("TOP 10 AT 10","V89'S WEEKLY TOP 10",5,5,22,22,0,60,FlagType.None));
        schedule.add(new ScheduleItem("FRIDAY NITE ALL-REQUEST","YOU CALL THE SHOTS",5,6,23,1,0,60,FlagType.None));
        schedule.add(new ScheduleItem("TIME MACHINE","10 YEARS OR OLDER",6,6,10,13,0,60,FlagType.None));
        schedule.add(new ScheduleItem("VIBES HOUSE REGGAE JAM","MUSIC OF THE ISLANDS",6,6,14,16,0,60,FlagType.None));
        schedule.add(new ScheduleItem("UNDAGROUND RAILROAD","NEW AND OLD SCHOOL HIP - HOP",6,6,17,19,0,60,FlagType.None));
        schedule.add(new ScheduleItem("SATURDAY NIGHT FISH FRY","CLASSIC and OBSCURE SOUL and R & B from the 60s, 70s, and 80s",6,6,20,21,0,60,FlagType.None));
        schedule.add(new ScheduleItem("SATURDAY NIGHT PARTY","ALL REQUEST",6,0,22,1,0,60,FlagType.None));
        for(int i=1; i< 6; ++i)
        {
            schedule.add(new ScheduleItem("CAFFEINE-A-GO-GO","MUSIC TO GET YOU GOING!",i,i,6,9,0,60,FlagType.None));
            schedule.add(new ScheduleItem("12-O'CLOCK TAKEOVER","LISTENER SHOWCASE",i,i,12,12,0,60,FlagType.None));
            for(int j = 6; j<18; ++j)
            {
                if(j%2 == 1)
                    schedule.add(new ScheduleItem("NEWS & SPORTS","WORLD/STATE/LOCAL",i,i,j,j,50,60,FlagType.None));
                else
                    schedule.add(new ScheduleItem("CONCERT UPDATE","ALL YOU NEED TO KNOW",i,i,j,j,50,60,FlagType.None));
                schedule.add(new ScheduleItem("WEATHER UPDATE","LOCAL FORECAST",i,i,j,j, 40,50,FlagType.None));
            }
                
        }
        
		Collections.sort(schedule);
	}
	
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
	
	public static ArrayList<ScheduleItem> getScheduleByDay(int day)
	{
		ArrayList<ScheduleItem> list = new ArrayList<ScheduleItem>();
		for(ScheduleItem item : schedule)
			if(item.Day == day) list.add(item);
		
		return list;
	}
	
	public static ScheduleItem getNowPlaying()
	{
		Calendar t = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		lastPoll = t;
		nowPlaying = new ScheduleItem("Regular Programming", "", 0, 0, 0, 0, 0, 0, FlagType.None);
		for(ScheduleItem item : schedule)
			if(item.IsPlaying()) nowPlaying = item;

		return nowPlaying;
	}
	
	public static void RequestSong(Song s, Context ctx)
	{
		Toast.makeText(ctx, "Song requested: " + s.name +" by "+s.artist.name, Toast.LENGTH_SHORT).show();
	}
}
