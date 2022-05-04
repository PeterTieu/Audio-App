package com.tieutech.audioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //View variables
    private Button playButton;
    private SeekBar seekBar;

    //Media Player
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtain views
        playButton = (Button) findViewById(R.id.platButton);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        //Set up MediaPlayer to play the audio
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.flume_insane_snippet);

        //Set what happens when the music completes playing
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                int duration = mediaPlayer.getDuration();
                Toast.makeText(MainActivity.this, "The duration is " + String.valueOf(duration/1000), Toast.LENGTH_SHORT).show();

                playButton.setText("PlAY");
            }
        });

        //Set the maximum value of the SeekBar (in ms)
        seekBar.setMax(mediaPlayer.getDuration());

        //Set what happens when the SeekBar is slid/changed
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean seekBarChanged) {

                //Change the playing position of the playback
                if (seekBarChanged) {
                    mediaPlayer.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    //Listener for "Play Music" button
    public void playMusicClick(View view) {
        if (mediaPlayer.isPlaying()) {
            pauseMusic();
        }
        else {
            playMusic();
        }
    }

    //Method to play the audio
    public void playMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.start();;
            playButton.setText("PAUSE");
        }
    }

    //Method to pause the audio
    public void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            playButton.setText("PLAY");
        }
    }

    //When the app is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.release(); //Release the audio
        }
    }
}