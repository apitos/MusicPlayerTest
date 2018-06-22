package com.example.android.musicplayertest;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ImageView playButton;
    private SeekBar progressBar, volumeBar;
    private TextView elapsedTimeLable, remainingTimeLable;
    private MediaPlayer mediaPlayer;
    private int totalTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (ImageView) findViewById(R.id.btnPlay);
        elapsedTimeLable = (TextView) findViewById(R.id.songCurrentDurationLabel);
        remainingTimeLable = (TextView) findViewById(R.id.songRemainingDurationLabel);

        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.leo_rojas_der_einsame_hirte);
        //mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.leo_rojas_el_condor_pasa);
        //mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(0);
        mediaPlayer.setVolume(0.5f, 0.5f);

        /* mediaPlayer.start(); */
        totalTime = mediaPlayer.getDuration();
        progressBar = (SeekBar) findViewById(R.id.songProgressBar);
        //Toast.makeText(this, "TotalTime = "+totalTime, Toast.LENGTH_SHORT).show();
        progressBar.setMax(totalTime);

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    progressBar.setProgress(progress);

                    //----------------
                    if (progress == 0) {
                        playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    }
                    if (progress == totalTime) {
                        playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    }
                    //--------------------
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
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    //playButton.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    playButton.setImageResource(R.drawable.ic_pause_white_24dp);
                } else {
                    mediaPlayer.pause();
                    //playButton.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    playButton.setImageResource(R.drawable.ic_play_white_24dp);
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
        }).start();
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
        } else {
            timeLabel += sec;
        }


        return timeLabel;

    }

//    public void getRawFiles() {
//        String sName;
//        String sTitle = "";
//
//        Field[] fields = R.raw.class.getFields();
//// loop for every file in raw folder
//        for (int count = 0; count < fields.length; count++) {
//
//            int rid = fields[count].getInt(fields[count]);
//
//            // Use that if you just need the file name
//            String filename = fields[count].getName();
//
//            String arrInfo[] = filename.split("_");
//            sName = arrInfo[0] + arrInfo[1];
//            for (int count2 = 2; count2 < arrInfo.length; count2++) {
//                sTitle = sTitle + arrInfo[count2];
//
//            }
//
//            //List.add(sName, sTitle);
//
//
//        }
//    }
}

/**
/*
 * Created by User on 1/2/2018.
 /

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");

            setImage(imageUrl, imageName);
        }
    }


    private void setImage(String imageUrl, String imageName){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView name = findViewById(R.id.image_description);
        name.setText(imageName);

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }

}

 */