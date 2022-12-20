package com.example.serviceboundmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.serviceboundmusic.MyService.MyBinder;

public class MainActivity extends AppCompatActivity {

    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btOn = (Button) findViewById(R.id.btOn);
        final Button btOff = (Button) findViewById(R.id.btOff);
        final Button btFast = (Button) findViewById(R.id.btTua);

        // Inisialisasi ServiceConnection
        connection = new ServiceConnection() {

            // Metode ini dipanggil oleh sistem saat koneksi ke layanan gagal
            @Override
            public void onServiceDisconnected(ComponentName name) {

                isBound = false;
            }

            // Metode ini dipanggil oleh sistem saat koneksi ke layanan berhasil
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyBinder binder = (MyBinder) service;
                myService = binder.getService(); // Mendapatkan objek MyService
                isBound = true;
            }
        };

        // Inisialisasi intent
        final Intent intent =
                new Intent(MainActivity.this, MyService.class);

        btOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mulai layanan menggunakan bind
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
                // Argumen ketiga memberi tahu bahwa Layanan akan diinisialisasi secara otomatis
                Toast.makeText(MainActivity.this, "Service mulai", Toast.LENGTH_SHORT).show();
            }
        });

        btOff.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Jika Layanan aktif
                if(isBound){
                    // Mematikan Service
                    unbindService(connection);
                    isBound = false;
                }
                Toast.makeText(MainActivity.this,
                        "Service berhenti", Toast.LENGTH_SHORT).show();
            }
        });

        btFast.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Jika layanan bekerja
                if(isBound){
                    // Lagu mundur
                    myService.fastForward();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Service belum aktif", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBound){
                    // Lagu mundur
                    myService.fastStart();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Service belum aktif", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}