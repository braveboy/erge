package com.tupian;


import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView iv;
	Button btnPrev;
	Button btnNext;
	Button btnStart;
	Button btnEnd;
	MediaPlayer mMediaPlayer;
//	private List<String> mMusicList = new ArrayList<String>();
	
	int [] imid = {
			
			R.drawable.p01,
			R.drawable.p02,
			R.drawable.p03,
	};
	int [] sounid = {
			R.raw.s01,
			R.raw.s04,
			R.raw.s05,
	};
	int currimid = 0;
	
	private View.OnClickListener mylistener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if ( v== btnPrev ){
				currimid = (currimid - 1 + imid.length)%imid.length;
				iv.setImageResource(imid[currimid]);
				mMediaPlayer.release();
				initSounds(sounid[currimid]);
				mMediaPlayer.start();
				
			}else if(v == btnNext){
				currimid = (currimid + 1)%imid.length;
				iv.setImageResource(imid[currimid]);
				mMediaPlayer.release();
				initSounds(sounid[currimid]);
				mMediaPlayer.start();
			}
		}

	};

	private View.OnClickListener mysound = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btnStart){//点击了使用MediaPlayer播放声音按钮
				if(!mMediaPlayer.isPlaying()){
					mMediaPlayer.start();
				}
			}
			else if(v == btnEnd){//点击了暂停MediaPlayer声音按钮
				if(mMediaPlayer.isPlaying()){
					mMediaPlayer.pause();//暂停声音
				}
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSounds(sounid[0]);//初始化声音
		setContentView(R.layout.activity_main);
		
		iv = (ImageView)findViewById(R.id.imageView1);
		btnPrev = (Button)findViewById(R.id.button1);
		btnNext = (Button)findViewById(R.id.button2);
		btnStart = (Button)findViewById(R.id.button3);
		btnEnd = (Button)findViewById(R.id.button4);
		
		btnPrev.setOnClickListener(mylistener);
		btnNext.setOnClickListener(mylistener);
		btnStart.setOnClickListener(mysound);
		btnEnd.setOnClickListener(mysound);
	}

	private void initSounds(int a) {
		// TODO Auto-generated method stub
		mMediaPlayer = MediaPlayer.create(this, a);//初始化MediaPlayer
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
