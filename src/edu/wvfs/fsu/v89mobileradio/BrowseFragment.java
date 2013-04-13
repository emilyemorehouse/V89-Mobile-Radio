package edu.wvfs.fsu.v89mobileradio;

import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
public class BrowseFragment extends android.support.v4.app.Fragment {
	private Button search;
	private EditText query;
	private Spinner searchby;
	private ListView resultList;
	private CustomAdapters.Reg adapter;
	private ArrayList<ListViewCreator> results = new ArrayList<ListViewCreator>();
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
	  final View myFragmentView = inflater.inflate(R.layout.browse_fragment, container, false);
	  search = (Button) myFragmentView.findViewById(R.id.search_button);
	  query = (EditText) myFragmentView.findViewById(R.id.search_query);
	  searchby = (Spinner) myFragmentView.findViewById(R.id.searchby);
	  resultList = (ListView) myFragmentView.findViewById(R.id.results_list);
	  adapter = new CustomAdapters.Reg(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, results);
	  resultList.setAdapter(adapter);
	  search.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			String text = query.getText().toString();
			switch(searchby.getSelectedItemPosition())
			{
			case 0:
				resultList.setAdapter(adapter);
				SwitchHeader(myFragmentView, 0);
				results = SearchByArtist(text);
				break;
			case 1:
				SwitchHeader(myFragmentView, 1);
				results = SearchByAlbum(text);
				break;
			case 2:
				SwitchHeader(myFragmentView, 2);
				results = SearchByTitle(text);
				break;
			default:
				results = new ArrayList<ListViewCreator>();
			}
			
			adapter.clear();
			resultList.invalidate();
			for(ListViewCreator result : results)
				adapter.add(result);
			adapter.notifyDataSetChanged();
		
		}
		  
	  });
	  return myFragmentView;
	 }
	
	private ArrayList<ListViewCreator> SearchByArtist(String text)
	{
		ArrayList<ListViewCreator> artists = new ArrayList<ListViewCreator>();
		for(int i =0; i< MobileRadioApplication.artists.size(); ++i)
		{
			Artist ar = (Artist) MobileRadioApplication.artists.get(i);
			if(ar.name.toLowerCase(Locale.US).contains(text.toLowerCase()))
				artists.add(ar);
				
		}
		return artists;			
	}
	
	private ArrayList<ListViewCreator> SearchByAlbum(String text)
	{
		ArrayList<ListViewCreator> albums = new ArrayList<ListViewCreator>();
		for(int i =0; i< MobileRadioApplication.albums.size(); ++i)
		{
			Album al = (Album) MobileRadioApplication.albums.get(i);
			if(al.name.toLowerCase(Locale.US).contains(text.toLowerCase()))
					albums.add(al);
				
		}
		return albums;
	}
	
	private ArrayList<ListViewCreator> SearchByTitle(String text)
	{
		ArrayList<ListViewCreator> songs = new ArrayList<ListViewCreator>();
		for(int i =0; i< MobileRadioApplication.songs.size(); ++i)
		{
			Song s = (Song) MobileRadioApplication.songs.get(i);
			if(s.name.toLowerCase(Locale.US).contains(text.toLowerCase()))
					songs.add(MobileRadioApplication.songs.get(i));
				
		}
		return songs;
	}
	
	private void SwitchHeader(View item, int type)
	{
		View C = item.findViewById(R.id.list_header);
	    ViewGroup parent = (ViewGroup) C.getParent();
	    int index = parent.indexOfChild(C);
	    parent.removeView(C);
	    //switch here
	    switch(type)
	    {
	    case 0:
	    	C = Artist.createHeaderView(getActivity().getBaseContext());
	    	break;
	    case 1:
		    C = Album.createHeaderView(getActivity().getBaseContext());
	    	break;
	    case 2:
		    C = Song.createHeaderView(getActivity().getBaseContext());
	    	break;
	    }
	    parent.addView(C, index);
	}
}
