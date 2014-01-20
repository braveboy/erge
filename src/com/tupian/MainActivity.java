package com.tupian;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends Activity {

	private ImageView iv;
	private ImageButton btnPlay;
	private ImageButton btnPrev;
	private ImageButton btnNext;
	private SeekBar seekBar;
	private MediaPlayer mMediaPlayer;
	
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
		
    Handler handler = new Handler();
    Runnable updateThread = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			seekBar.setProgress(mMediaPlayer.getCurrentPosition());  
			handler.postDelayed(updateThread, 100);  
		}
    	
    };
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
		seekBar = (SeekBar)findViewById(R.id.seekBar);  
		seekBar.setMax(mMediaPlayer.getDuration());  
		iv.setImageResource(imid[i]);
		btnPlay.setImageResource(R.drawable.pause_1);
		seekBar.setOnSeekBarChangeListener(
				new SeekBar.OnSeekBarChangeListener() {
					
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						// TODO Auto-generated method stub
						if (fromUser == true){
							mMediaPlayer.seekTo(progress);
						}
								
					}
				}
				
				);
		
		
		mMediaPlayer.start();
		handler.post(updateThread);
		
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
					btnPlay.setImageResource(R.drawable.play_2);
					btnPlay.setImageResource(R.drawable.pause_1);
				
				
				} else if(mMediaPlayer.isPlaying()){
					mMediaPlayer.pause();//‘›Õ£…˘“Ù
					btnPlay.setImageResource(R.drawable.pause_2);
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
				seekBar.setMax(mMediaPlayer.getDuration()); 
				btnPlay.setImageResource(R.drawable.pause_1);
				mMediaPlayer.start();
        	}
        });
        
        btnNext.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
				currimid = (currimid + 1)%imid.length;
				iv.setImageResource(imid[currimid]);
				mMediaPlayer.release();
				initSounds(sounid[currimid]);
				seekBar.setMax(mMediaPlayer.getDuration()); 
				btnPlay.setImageResource(R.drawable.pause_1);
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
		handler.removeCallbacks(updateThread);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMediaPlayer.start();
		handler.post(updateThread);
	}

}
