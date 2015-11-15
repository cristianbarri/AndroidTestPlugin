package com.example.judith.androidtestplugin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;

import com.example.judith.androidtestplugin.Customs.CustomMediaPlayer;
import com.example.judith.androidtestplugin.PluginClass.MyPlugin;

public class ScreenMediaPlayer extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl {

    private Button b;
    private CustomMediaPlayer mediaPlayer;
    private SurfaceHolder vidHolder;
    private SurfaceView vidSurface;
    String videoURL = "http://cdn.s1.eu.nice264.com/converted_work6/0082c06e504b0a422bf1_6815f2deeb179c29748af42f8cd5ce95.mp4";

    /*TextView PlaysText;
    TextView PausesText;
    TextView TimeElapsedText;*/

    MediaController mediacontroller;

    /*String video_event; //START | FRAME | FINISH
    Boolean first = true;*/

    MyPlugin plugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        //setup ui elements
        vidSurface = (SurfaceView) findViewById(R.id.surfaceView);
        vidHolder = vidSurface.getHolder();
        vidHolder.addCallback(this);

        /*PlaysText = (TextView) findViewById(R.id.textView3);
        PausesText = (TextView) findViewById(R.id.textView4);
        TimeElapsedText = (TextView) findViewById(R.id.textView5);*/

        b = (Button) findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                finish();
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*
         * the MediaController will hide after 3 seconds - tap the screen to
         * make it appear again
         */
        mediacontroller.show();
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want back to menu?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                mediaPlayer.pause();
                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //prepare for playback
        try {
            mediaPlayer = new CustomMediaPlayer();
            mediaPlayer.setDisplay(vidHolder);
            mediaPlayer.setDataSource(videoURL);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediacontroller = new MediaController(this);

            plugin = new MyPlugin(getApplication(), mediaPlayer);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        mediacontroller.setMediaPlayer(this);
        mediacontroller.setAnchorView(findViewById(R.id.surfaceView));

        mediacontroller.show();
    }

    @Override
    public void start() {
        mediaPlayer.start();
        //plugin.CountPlay(getApplicationContext());
        /*if (plugin.ElapsedTime() != 0) {
            PlaysText.setText("Plays: "+ plugin.CountPlay());
            TimeElapsedText.setText("Time elapsed: "+ plugin.ElapsedTime() + "ms");
        }*/
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
        //PausesText.setText("Pauses: " + plugin.CountPause());
       //plugin.CountPause(getApplicationContext());
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
