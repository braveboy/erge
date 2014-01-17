package com.tupian;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView iv;
	ImageButton btnPlay;
	ImageButton btnPrev;
	ImageButton btnNext;
	MediaPlayer mMediaPlayer;
	
	int [] imid = {
			
			R.drawable.p04,
			R.drawable.p05,
			R.drawable.p06,
	};
	int [] sounid = {
			R.raw.s01,
			R.raw.s04,
			R.raw.s05,
	};
	int currimid = 0;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int i = getIntent().getIntExtra("int", 0);
		initSounds(sounid[i]);
		currimid = i;

		setContentView(R.layout.activity_main);
		
		iv = (ImageView)findViewById(R.id.imageView1);
		btnPlay = (ImageButton)findViewById(R.id.play);
		btnPrev = (ImageButton)findViewById(R.id.prev);
		btnNext = (ImageButton)findViewById(R.id.next);
		
		iv.setImageResource(imid[i]);
		mMediaPlayer.start();
		
		
		btnPlay.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			
				if(btnPlay.isPressed()){
					
					if(!mMediaPlayer.isPlaying()) {
						btnPlay.setImageResource(R.drawable.play_2);
					} else if(mMediaPlayer.isPlaying()){
						btnPlay.setImageResource(R.drawable.pause_2);
					}
				}
				
				if(!mMediaPlayer.isPlaying()) {
					mMediaPlayer.start();
					btnPlay.setImageResource(R.drawable.pause_1);
				
				
				} else if(mMediaPlayer.isPlaying()){
					mMediaPlayer.pause();//‘›Õ£…˘“Ù
					btnPlay.setImageResource(R.drawable.play_1);
				}
			}
			
		});
		
        btnPrev.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
				currimid = (currimid - 1 + imid.length)%imid.length;
				iv.setImageResource(imid[currimid]);
				mMediaPlayer.release();
				initSounds(sounid[currimid]);
				mMediaPlayer.start();
        	}
        });
        
        btnNext.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
				currimid = (currimid + 1)%imid.length;
				iv.setImageResource(imid[currimid]);
				mMediaPlayer.release();
				initSounds(sounid[currimid]);
				mMediaPlayer.start();
        	}
        });
	}

	private void initSounds(int a) {
		// TODO Auto-generated method stub
		mMediaPlayer = MediaPlayer.create(this, a);//≥ı ºªØMediaPlayer
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMediaPlayer.pause();	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMediaPlayer.start();
	}

}
