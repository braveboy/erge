package com.tupian;

import com.example.autohide.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
	private AudioManager audioManager;
	private boolean SoundEnabled;
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	int [] imid = {		
			R.drawable.p0001,
			R.drawable.p05,
			R.drawable.p06,
	};
	
	int [] sounid = {
			R.raw.s01,
			R.raw.s04,
			R.raw.s05,
	};
	
	int currimid = 0;
	int repeat = 1;
	
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
			
			@Override
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
        	
        	@Override
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
        	
        	@Override
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
        final View proBar = findViewById(R.id.fullscreen_content_controls);
		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, proBar,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = proBar.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							proBar
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							proBar.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});
	
		// Set up the user interaction to manually show or hide the system UI.
		proBar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(1000);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
	
	private void initSounds(int a) {
		// TODO Auto-generated method stub
		mMediaPlayer = MediaPlayer.create(this, a);//≥ı ºªØMediaPlayer
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        	
			@Override
			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				
				//Repeat all
				if(repeat==1) {
					currimid = (currimid + 1)%imid.length;
					iv.setImageResource(imid[currimid]);
					mMediaPlayer.release();
					initSounds(sounid[currimid]);
					seekBar.setMax(mMediaPlayer.getDuration()); 
					btnPlay.setImageResource(R.drawable.pause_1);
					mMediaPlayer.start();
				} 
				//Repeat one
				else if(repeat==2) {
					mMediaPlayer.start();
				}
				//Repeat no
				else if(repeat==0) {
					btnPlay.setImageResource(R.drawable.play_1);
					//do nothing
				}

			}
        });
 
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMediaPlayer.pause();
		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
		handler.removeCallbacks(updateThread);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		invalidateOptionsMenu();
		SoundEnabled = false;
		mMediaPlayer.start();
		btnPlay.setImageResource(R.drawable.pause_1);
		handler.post(updateThread);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    if(repeat==1) {
	    	inflater.inflate(R.menu.play__actions, menu);
	    } else if(repeat==2) {
	    	inflater.inflate(R.menu.play__actions_b, menu);
	    } else if(repeat==0) {
	    	inflater.inflate(R.menu.play__actions_c, menu);
	    }
	    
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		/*MenuItem menuItem = menu.findItem(R.id.action_mute);
		menuItem.setIcon(R.drawable.ic_action_volume_on);	*/
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_mute:
	        	audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
				if (!SoundEnabled) {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    item.setIcon(R.drawable.ic_action_volume_muted);
                } else {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    item.setIcon(R.drawable.ic_action_volume_on);
                }
                SoundEnabled = !SoundEnabled;
	            return true;
	            
	        case R.id.action_repeat:
	        	if(repeat==1) {
	        		repeat = 2;
	        		item.setIcon(R.drawable.repeat_once);
	        	} else if (repeat==2) {
	        		repeat = 0;
	        		item.setIcon(R.drawable.repeat_no);
	        	} else if (repeat==0) {
	        		repeat = 1;
	        		item.setIcon(R.drawable.repeat_all);
	        	}
	        	
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
