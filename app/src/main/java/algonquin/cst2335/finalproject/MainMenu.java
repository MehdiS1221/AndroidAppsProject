package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.finalproject.hussein.activities.MoviesSplashActivity;

public class MainMenu extends AppCompatActivity {
    Button OCTranspoMenuButton;
    Button electricMenuButton;
    Button soccerMenuButton;
    Button movieMenuButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu);


        OCTranspoMenuButton = findViewById(R.id.OCTranspoMenuButton);
        electricMenuButton = findViewById(R.id.electricCarMenuButton);
        soccerMenuButton = findViewById(R.id.soccerGameMenuButton);
        movieMenuButton = findViewById(R.id.movieAppMenuButton);

        OCTranspoMenuButton.setOnClickListener(click -> {
            Intent intent = new Intent(MainMenu.this, OCTranspoApp.class);
            startActivity(intent);
        });

        soccerMenuButton.setOnClickListener(click -> {
            Intent intent = new Intent(MainMenu.this, MainActivity.class);
            startActivity(intent);
        });
        electricMenuButton.setOnClickListener(click ->{
            Intent intent = new Intent(MainMenu.this, MainActivityCarCharging.class);
            startActivity(intent);




//        movieMenuButton.setOnClickListener(v -> {
//            startActivity(new Intent(this, MoviesSplashActivity.class));
//        });


        });
        movieMenuButton.setOnClickListener(v -> {
            startActivity(new Intent(this, MoviesSplashActivity.class));
        });

        }


}