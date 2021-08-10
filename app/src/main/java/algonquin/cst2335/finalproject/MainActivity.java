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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This Program is for searching Soccer game news.
 * @author Ayham Alahmed
 * @version 1
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Button search this is for searching the name of the article news.
     */
    Button search;
    /**
     * TextView editName finding the title.
     */
    TextView editName;
    /**
     * Button instructions this is for help.
     */
    Button instructions;
    /**
     * Button author this is for showing the author name and the version number.
     */
    Button author;
    /**
     * ProgressBar progressBar for showing the progress from 0 to 100.
     */
    ProgressBar progressBar;
    /**
     * RecyclerView articles this is for showing the retrieve data.
     */
    RecyclerView articles;
    /**
     * DrawerLayout drawer this is for tool bar
     */
    DrawerLayout drawer;
    /**
     * NavigationView navigation to go to another activity.
     */
    NavigationView navigation;
    /**
     * Intent intent to go to another activity.
     */
    Intent intent;
    /**
     * String title this is for the article title
     */
    String title;
    /**
     * String date this is showing the article date
     */
    String date;
    /**
     * String iconName this is for article image
     */
    String iconName;

    /**
     * This method has help button, navigation bar and article search function.
     * @param savedInstanceState is savedInstanceState.
     */

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
        author= findViewById(R.id.author);
/**
 * This is for help and will show the user how can use this app.
 */

        instructions.setOnClickListener(click ->
        {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("How to use Soccer Games api.")
                    .setMessage("To get any sport articles related to soccer Games. Just type the name " +
                            "of the article in the search area, then click search.")
                    .setPositiveButton("start", (dlg, select) -> {
                    }).show();
        });
/**
 * this is for showing the author name and the version number.
 */
        author.setOnClickListener(click ->
        {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Author: Ayham Alahmed")
                    .setMessage("Version:1")
                    .setPositiveButton("ok", (dlg, select) -> {
                    }).show();
        });
/**
 * this is for showing  navigation bar.
 */
        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem ob) {
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

/**
 * this is for saving the entered title of the article.
 */
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String defaultValue = null;
        prefs.getString("VariableName", defaultValue);
        String name = prefs.getString("Name", "");
        editName.setText(name);
/**
 * this is for AlertDialog box and toast message.
 */
        search.setOnClickListener(clicked -> {

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
                    .setTitle("Soccer Game api")
                    .setNegativeButton("Cancel", (dialog, cl) -> {
                })
                .setPositiveButton("Start Searching", (dialog, cl) -> {
                    Snackbar.make(search, "Downloading the article ", Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk -> {
                            })
                            .show();
                })
                    .create().show();

            /**
             * this is for getting data from server.
             */

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Soccer Game api")
                    .setMessage("We are searching for some sport article news")
                    .setView(new ProgressBar(MainActivity.this))
                    .show();

//            Executor newThread = Executors.newSingleThreadExecutor();
//            newThread.execute(() -> {
//                        try {
//                            String articleName = cityNameEditText.getText().toString();
//                            String serverURL = "http://www.goal.com/en/feeds/news?fmt=rss";
//                            URL url = new URL(serverURL);
//                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                            String text = (new BufferedReader(
//                                    new InputStreamReader(in, StandardCharsets.UTF_8)))
//                                    .lines()
//                                    .collect(Collectors.joining("\n"));
//                            JSONObject theDocument = new JSONObject(text);
//                            JSONObject coord = theDocument.getJSONObject("coord");
//                            JSONArray articleArray = theDocument.getJSONArray("article");
//                            JSONObject position0 = weatherArray.getJSONObject(0);
//
//
//                            String iconName = position0.getString("icon");
//                            int vis = theDocument.getInt("visibility");
//                            String name = theDocument.getString("name");
//
//
//                            JSONObject mainObject = theDocument.getJSONObject("main");
//                              title = mainObject.getDouble("title");
//                              date = mainObject.getDouble("date");
//
//                            Bitmap image = null;
//                            File file = new File(getFilesDir(), iconName + ".png");
//                            if (file.exists()) {
//                                image = BitmapFactory.decodeFile(getFilesDir() + "/" + iconName + ".png");
//                            } else {
//                                URL imgUrl = new URL("http://www.goal.com/en/feeds/news?fmt=rss" + iconName + ".png");
//                                HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
//                                connection.connect();
//                                int responseCode = connection.getResponseCode();
//                                if (responseCode == 200) {
//                                    image = BitmapFactory.decodeStream(connection.getInputStream());
//                                    image.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));
//                                    ImageView imageView = findViewById(R.id.icon);
//                                    imageView.setImageBitmap(image);
//                                }
//                            }
//                            Bitmap finalImage = image;
//                            Bitmap finalImage1 = image;
//                            runOnUiThread(() -> {
//                                TextView textView = findViewById(R.id.title);
//                                textView.setText("The title is " + title);
//                                textView.setVisibility(View.VISIBLE);
//
//                                textView = findViewById(R.id.date);
//                                textView.setText("The date is " + date);
//                                textView.setVisibility(View.VISIBLE);
//
//
//
//                                ImageView imageView = findViewById(R.id.icon);
//                                imageView.setImageBitmap(finalImage);
//                                imageView.setVisibility(View.VISIBLE);
//                                imageView.setImageBitmap(finalImage1);
//
//                            });
//
//                            FileOutputStream fOut = null;
//                            fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
//                            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//                            fOut.flush();
//                            fOut.close();
//
//                        } catch (IOException | JSONException ieo) {
//                            Log.e("Connection error:", ieo.getMessage());
//                        }
//            });
        });
    }

    /**
     * this is for showing the ratting for the app.
     */
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