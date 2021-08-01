package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Button search;
    TextView editName;
    Button instructions;
    ProgressBar progressBar;
    RecyclerView articles;
    DrawerLayout drawer;
    NavigationView navigation;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.search);
        editName = findViewById(R.id.newsSearch);
        progressBar= findViewById(R.id.progressBar);
        articles= findViewById(R.id.articles);
        drawer= findViewById(R.id.drawer);
        navigation= findViewById(R.id.navigation);
        instructions = findViewById(R.id.instructions);

        instructions.setOnClickListener(click ->
        {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("How to use Soccer Games api.")
                    .setMessage("To get any sport articles related to soccer Games. Just type the name " +
                            "of the article in the search area, then click search.")
                    .setPositiveButton("start", (dlg, select) -> {
                    }).show();
        });

        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem ob) {
//                if (ob.getItemId() == R.id.nav_soccer) {
//                    Toast.makeText(MainActivity.this, "You are using this app", Toast.LENGTH_LONG).show();
//                    return true;
//                } else{
//                    if (ob.getItemId() == R.id.nav_bus) {
//                    setContentView(R.layout.octranspobusrouteapp);
//                        return true;
//                    }
//                }

//                if (ob.getItemId() == R.id.nav_bus) {
//                    setContentView(R.layout.octranspobusrouteapp);
//                   // Toast.makeText(MainActivity.this,"Now you are in new app" ,Toast.LENGTH_LONG).show();
//                    return true;
//                }else {
//                    if (ob.getItemId() == R.id.nav_soccer) {
//                    setContentView(R.layout.activity_main);
//                    return true;
//                    }
//                }
            switch (ob.getItemId()) {
                case R.id.nav_soccer :
                     intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "You are using this app", Toast.LENGTH_LONG).show();
                break;
                case R.id.nav_bus :
                    intent = new Intent(MainActivity.this, OCTranspoApp.class);
                    startActivity(intent);

                break;
            }

                return false;
            }
        });

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String defaultValue = null;
        prefs.getString("VariableName", defaultValue);
        String name = prefs.getString("Name", "");
        editName.setText(name);
        search.setOnClickListener(clicked -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Name", editName.getText().toString());
            editor.apply();
            Context context = getApplicationContext();
            CharSequence text = "Soccer Game!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Welcome To sport News")
                    .setTitle("Soccer Game News")
                    .setNegativeButton("Cancel", (dialog, cl) -> {
                })
                .setPositiveButton("Start Searching", (dialog, cl) -> {
                    Snackbar.make(search, "Downloading the article ", Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk -> {
                            })
                            .show();
                })
                    .create().show();
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
            AlertDialog dg = new AlertDialog.Builder(this)
                    .setTitle("Welcome To Ayham's Sport Games Api please put your rate")
                    .setPositiveButton("Rate", (dlg, select) -> {
                        final AlertDialog.Builder pop = new AlertDialog.Builder(this);
                        final RatingBar rating = new RatingBar(this);
                        rating.setMax(5);
                        pop.setIcon(android.R.drawable.btn_star_big_on);
                        pop.setTitle("Add Rating: ");
                        pop.setView(rating);
                        pop.setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dg, int which) {
                                        dg.dismiss();
                                    }
                                })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dg, int id) {
                                                dg.cancel();
                                            }
                                        });
                        pop.create();
                        pop.show();
                    }).show();
    }
}