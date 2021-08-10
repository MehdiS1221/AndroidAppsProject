package algonquin.cst2335.finalproject.hussein.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import algonquin.cst2335.finalproject.R;

public class MoviesSplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_movies);

        setLocale();
    }

    public void next() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MoviesSplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2400);
    }

    @Override
    protected void onResume() {
        super.onResume();
        next();
    }

    private void setLocale() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        String lang = sharedPreferences.getString("Lang", "");
        if (!TextUtils.isEmpty(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.setLocale(locale);
            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        }
    }
}