package com.tupian;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SongList extends ListActivity {
	String[] list = {
			"0",
			"1",
			"2",
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song_list);
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list));
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		Intent i = new Intent("com.tupian.PlayScreen");
		i.putExtra("list", list[position]);
		startActivity(i);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.song_list, menu);
		return true;
	}

}
