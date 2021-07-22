package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        OCTranspoMenuButton.setOnClickListener(click ->{
            Intent intent = new Intent(MainMenu.this, OCTranspoApp.class);
            startActivity(intent);


        });

        soccerMenuButton.setOnClickListener(click -> {
            Intent intent = new Intent(MainMenu.this, MainActivity.class);
            startActivity(intent);
        });


    }


}