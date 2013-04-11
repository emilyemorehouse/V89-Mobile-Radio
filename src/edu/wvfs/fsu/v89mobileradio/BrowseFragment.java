package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
public class BrowseFragment extends android.support.v4.app.Fragment {
	private Button search;
	private EditText query;
	private Spinner searchby;
	private ListView resultList;
	private ArrayAdapter<Song> adapter;
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
	  View myFragmentView = inflater.inflate(R.layout.browse_fragment, container, false);
	  search = (Button) myFragmentView.findViewById(R.id.search_button);
	  query = (EditText) myFragmentView.findViewById(R.id.search_query);
	  searchby = (Spinner) myFragmentView.findViewById(R.id.searchby);
	  resultList = (ListView) myFragmentView.findViewById(R.id.results_list);
	  adapter = new ArrayAdapter<Song>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, MobileRadioApplication.songs);
	  resultList.setAdapter(adapter);
	  search.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			String text = query.getText().toString();
			ArrayList<Song> results = new ArrayList<Song>();
			if(text.length() == 0) return;
			switch(searchby.getSelectedItemPosition())
			{
			case 0:
				results = SearchByArtist(text);
				break;
			case 1:
				results = SearchByAlbum(text);
				break;
			case 2:
				results = SearchByTitle(text);
				break;
			}
			
		}
		  
	  });
	  return myFragmentView;
	 }
	
	private ArrayList<Song> SearchByArtist(String text)
	{
		ArrayList<Song> songs = new ArrayList<Song>();
		for(int i =0; i< MobileRadioApplication.artists.size(); ++i)
		{
			Artist ar = MobileRadioApplication.artists.get(i);
			if(ar.name.toLowerCase().contains(text.toLowerCase()))
				for(int j = 0; j < ar.albums.size(); ++j)
					songs.addAll(ar.albums.get(j).songs);
				
		}
		adapter.clear();
		for(Song song : songs)
			adapter.add(song);
		return songs;			
	}
	
	private ArrayList<Song> SearchByAlbum(String text)
	{
		ArrayList<Song> songs = new ArrayList<Song>();
		for(int i =0; i< MobileRadioApplication.albums.size(); ++i)
		{
			Album al = MobileRadioApplication.albums.get(i);
			if(al.name.toLowerCase().contains(text.toLowerCase()))
					songs.addAll(al.songs);
				
		}
		adapter.clear();
		for(Song song : songs)
			adapter.add(song);
		return songs;
	}
	
	private ArrayList<Song> SearchByTitle(String text)
	{
		ArrayList<Song> songs = new ArrayList<Song>();
		for(int i =0; i< MobileRadioApplication.songs.size(); ++i)
		{
			if(MobileRadioApplication.songs.get(i).name.toLowerCase().contains(text.toLowerCase()))
					songs.add(MobileRadioApplication.songs.get(i));
				
		}
		adapter.clear();
		for(Song song : songs)
			adapter.add(song);
		return songs;
	}
}
