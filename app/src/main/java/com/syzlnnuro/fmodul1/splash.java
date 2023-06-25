package com.syzlnnuro.fmodul1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity {
    private static final int SPLASH_DURATION = 2000; // Durasi splash screen dalam milidetik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Tambahkan kode atau tugas yang ingin dilakukan saat splash2 screen tampil
        // Contoh: menampilkan logo atau animasi, memuat data awal, dll.

        // Setelah durasi tertentu, arahkan ke activity berikutnya
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);
                finish(); // Tutup splash2 screen agar tidak dapat dikembalikan dengan tombol back
            }
        }, SPLASH_DURATION);
    }

}