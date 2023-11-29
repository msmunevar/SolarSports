package com.example.solarsports;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityVideo extends AppCompatActivity {

    Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoView videoView = findViewById(R.id.videoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro); // Replace "nombre_del_video" with the actual resource ID
        videoView.setVideoURI(videoUri);
        videoView.setZOrderOnTop(true); // el VideoView debería estar en la parte superior de la pila de visualización

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start(); // Inicia la reproducción
            }
        });

        // Agregar un Listener para detectar el final del video
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // El video ha terminado, inicia la nueva actividad
                Intent intent = new Intent(ActivityVideo.this, MainActivity.class);
                startActivity(intent);
                finish(); // Opcional: cierra esta actividad si no la necesitas más
            }
        });

    }
}