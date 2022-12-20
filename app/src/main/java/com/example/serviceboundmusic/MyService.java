package com.example.serviceboundmusic;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {

    private MyPlayer myPlayer;
    private IBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServiceDemo", "Called onCreate()");

        myPlayer = new MyPlayer(this);
        binder = new MyBinder(); // Memperluas Binder

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ServiceDemo", "Called onBind()");
        myPlayer.play();
        // kembalikan objek pengikat untuk ActivityMain
        return binder;

    }
    // Mengakhiri Layanan
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ServiceDemo", "Called onBind()");
        myPlayer.stop();
        return super.onUnbind(intent);
    }

    // Mengembangkan metode untuk melakukan tugas,
    // Disini saya demo cara rewinding lagunya
    public void fastForward(){

        myPlayer.fastForward(60000); // mundur ke detik ke-120
    }
    public void fastStart(){

        myPlayer.fastStart();
    }

    public class MyBinder extends Binder {

        // Metode ini mengembalikan objek MyService
        public MyService getService() {

            return MyService.this;
        }
    }

}
// Bangun objek pribadi untuk memutar musik
class MyPlayer {
    // Objek ini membantu memainkan lagu
    private MediaPlayer mediaPlayer;

    public MyPlayer(Context context) {
        // Muat musik ke mediaPlayer
        mediaPlayer = MediaPlayer.create(context, R.raw.iwantitthatway);
        // Atur pemutaran berulang terus menerus
        mediaPlayer.setLooping(true);
    }

    public void fastForward(int pos){
        //MediaPlayer.seekTo(pos);
        mediaPlayer.pause();

    }
    public void fastStart(){
        mediaPlayer.start();
    }

    // Bermusik
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    // Berhenti bermain musik
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
