package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    Button search;
    TextView editName;
    Button instructions;
    ProgressBar progressBar;
    RecyclerView articles;
    DrawerLayout drawer;
    NavigationView navigation;
    Intent intent;
    private TextView articalNameEditText = null;
    String articleName;
    String title;
    String date;
    String iconName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.search);
        editName = findViewById(R.id.newsSearch);
        progressBar = findViewById(R.id.progressBar);
        articles = findViewById(R.id.articles);
        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        instructions = findViewById(R.id.instructions);
        articalNameEditText = findViewById(R.id.newsSearch);



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
            public boolean onNavigationItemSelected(MenuItem ob) {
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
                    case R.id.nav_soccer:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "You are using this app", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_bus:
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





//            articleName =  articalNameEditText.getText().toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Name", editName.getText().toString());
            editor.apply();
            Context context = getApplicationContext();
            CharSequence text = "Soccer Game!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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


//            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
//                    .setTitle("Sport News")
//                    .setMessage("We are calling people in " + articleName + "to look for their sport team news .")
//                    .setView(new ProgressBar(MainActivity.this))
//                    .show();
//
//        Executor newThread = Executors.newSingleThreadExecutor();
//        newThread.execute(() -> {
//            try {
//                String serverURL = "https://api.openweathermap.org/data/2.5/weather?q="
//                        + URLEncoder.encode(articleName, "UTF-8")
//                        + "&appid=7e943c97096a9784391a981c4d878b22&units=metric&mode=xml";
//
//                URL url = new URL(serverURL);
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//
//                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//                factory.setNamespaceAware(false);
//                XmlPullParser xpp = factory.newPullParser();
//                xpp.setInput(in, "UTF-8");
//
//                while (xpp.next() != XmlPullParser.END_DOCUMENT) {
//                    switch (xpp.getEventType()) {
//                        case XmlPullParser.START_TAG:
//                            if (xpp.getName().equals("taitl")) {
//                                title = xpp.getAttributeValue(null, "taitl");  //this gets the current temperature
//                                date = xpp.getAttributeValue(null, "date"); //this gets the min temperature
//
//                            } else if (xpp.getName().equals("sport")) {
//                                iconName = xpp.getAttributeValue(null, "icon"); //this gets the icon name
//                            }
//                            break;
//
//                        case XmlPullParser.END_TAG:
//                            break;
//                        case XmlPullParser.TEXT:
//                            break;
//                    }
//
//                }
//                Bitmap image = null;
//                File file = new File(getFilesDir(), iconName + ".png");
//                if (file.exists()) {
//                    image = BitmapFactory.decodeFile(getFilesDir() + "/" + iconName + ".png");
//                } else {
//                    URL imgUrl = new URL("https://openweathermap.org/img/w/" + iconName + ".png");
//                    HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
//                    connection.connect();
//                    int responseCode = connection.getResponseCode();
//                    if (responseCode == 200) {
//                        image = BitmapFactory.decodeStream(connection.getInputStream());
//                        image.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));
//                        ImageView imageView = findViewById(R.id.icon);
//                        imageView.setImageBitmap(image);
//                    }
//                }
//
//                Bitmap finalImage = image;
//                Bitmap finalImage1 = image;
//                runOnUiThread(() -> {
////                    TextView textView = findViewById(R.id.temp);
////                    textView.setText("The current Temperature is " + title);
////                    textView.setVisibility(View.VISIBLE);
////
////                    textView = findViewById(R.id.minTemp);
////                    textView.setText("The Minimum Temperature is " + date);
////                    textView.setVisibility(View.VISIBLE);
//
//
//                    ImageView imageView = findViewById(R.id.icon);
//                    imageView.setImageBitmap(finalImage);
//                    imageView.setVisibility(View.VISIBLE);
//                    imageView.setImageBitmap(finalImage1);
//                    dialog.hide();
//                });
//
//                FileOutputStream fOut = null;
//                fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
//                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//                fOut.flush();
//                fOut.close();
//
//            } catch (IOException | XmlPullParserException ieo) {
//                Log.e("Connection error:", ieo.getMessage());
//            }
//        });
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