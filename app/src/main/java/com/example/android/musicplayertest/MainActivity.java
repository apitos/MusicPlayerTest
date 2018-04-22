package com.example.android.musicplayertest;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private ImageButton playButton;
    private SeekBar progressBar, volumeBar;
    private TextView elapsedTimeLable, remainingTimeLable;
    private MediaPlayer mediaPlayer;
    private int totalTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (ImageButton) findViewById(R.id.btnPlay);
        elapsedTimeLable = (TextView) findViewById(R.id.songCurrentDurationLabel);
        remainingTimeLable = (TextView) findViewById(R.id.songRemainDurationLabel);

        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.leo_rojas_el_condor_pasa);
        mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(0.5f, 0.5f);

        totalTime = mediaPlayer.getDuration();
        progressBar = (SeekBar) findViewById(R.id.songProgressBar);
        progressBar.setMax(totalTime);

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    //progressBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        volumeBar = (SeekBar) findViewById(R.id.volumeProgressBar);
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumeNumber = progress / 100f;
                mediaPlayer.setVolume(volumeNumber, volumeNumber);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    playButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                } else {
                    mediaPlayer.pause();
                    playButton.setBackgroundResource(R.drawable.ic_play_arrow);
                }
            }
        });


        /**********/
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try {
                        Message message = new Message();

                        message.what = mediaPlayer.getCurrentPosition();

                        handler.sendMessage(message);

                        Thread.sleep(1000);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            int currentPosition = message.what;
            progressBar.setProgress(currentPosition);

            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLable.setText(elapsedTime);

            String remainTime = createTimeLabel(totalTime - currentPosition);
            remainingTimeLable.setText("- " + remainTime);
        }
    };


    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) {
            timeLabel += "0";
            timeLabel += sec;
        }
        return timeLabel;

    }
}

