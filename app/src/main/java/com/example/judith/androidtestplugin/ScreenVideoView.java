package com.example.judith.androidtestplugin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;

import com.example.judith.androidtestplugin.Customs.CustomVideoView;
import com.example.judith.androidtestplugin.PluginClass.MyPlugin;

public class ScreenVideoView extends AppCompatActivity {

    private Button b;
    // Declare variables
    CustomVideoView videoview;
    /*TextView PlaysText;
    TextView PausesText;
    TextView TimeElapsedText;*/
    MyPlugin plugin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        // Find your VideoView in your video_main.xml layout
        videoview = (CustomVideoView) findViewById(R.id.videoView);
        /*PlaysText = (TextView) findViewById(R.id.textView3);
        PausesText = (TextView) findViewById(R.id.textView4);
        TimeElapsedText = (TextView) findViewById(R.id.textView5);*/

        plugin = new MyPlugin(getApplication(), videoview);
/*
        videoview.setPlayPauseListener(new CustomVideoView.PlayPauseListener() {

            @Override
            public void onPlay() {
                //if (plugin.ElapsedTime() != 0) {
                //    PlaysText.setText("Plays: "+ plugin.CountPlay());
                //    TimeElapsedText.setText("Time elapsed: "+ plugin.ElapsedTime() + "ms");
                //}
                plugin.CountPlay(getApplicationContext());
            }

            @Override
            public void onPause() {
                //PausesText.setText("Pauses: "+ plugin.CountPause());
                plugin.CountPause(getApplicationContext());
            }
        });
*/
        // Start the MediaController
        MediaController mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(videoview);
        videoview.setMediaController(mediacontroller);

        // Get the URL from String VideoURL
        String videoURL ="http://cdn.s1.eu.nice264.com/converted_work6/0082c06e504b0a422bf1_6815f2deeb179c29748af42f8cd5ce95.mp4";
        Uri video = Uri.parse(videoURL);

        videoview.setVideoURI(video);
        videoview.start();

        b = (Button) findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
}
