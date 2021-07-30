package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    ImageView instructions;
    Button welcome;
    ProgressBar progressBar;
    RecyclerView articles;
    DrawerLayout drawer;
    NavigationView navigation;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.search);
        editName = findViewById(R.id.newsSearch);
        instructions= findViewById(R.id.instructions);
        progressBar= findViewById(R.id.progressBar);
        articles= findViewById(R.id.articles);
        drawer= findViewById(R.id.drawer);
        navigation= findViewById(R.id.navigation);
        welcome = findViewById(R.id.rate);

        welcome.setOnClickListener(click ->
        {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Wellcome To Ayham's Sport Games Api please give your rate")
                    .setPositiveButton("Rate", (dlg, select) -> {

                        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
                        final RatingBar rating = new RatingBar(this);
                        rating.setMax(5);

                        popDialog.setIcon(android.R.drawable.btn_star_big_on);
                        popDialog.setTitle("Add Rating: ");
                        popDialog.setView(rating);
                        popDialog.setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                     dialog.dismiss();
                                    }
                                })

                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                        popDialog.create();
                        popDialog.show();

                    }).show();
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

        instructions.setOnClickListener(click ->
        {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("How to use Soccer Games api.")
                    .setMessage("To get sport articles related to soccer Games. Just type the name " +
                            "of the article in the search area, then click search.")
                    .setPositiveButton("start", (dlg, select) -> {
                  }).show();
        });


    }
}