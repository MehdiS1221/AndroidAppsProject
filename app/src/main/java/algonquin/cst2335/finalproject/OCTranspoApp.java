package algonquin.cst2335.finalproject;

import android.app.AlertDialog;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OCTranspoApp extends AppCompatActivity{
    RecyclerView busList;
    Button addButton;
    Button removeButton;
    EditText busStopEditView;
    String busStopNumber;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView questionMark;
    ProgressBar pb;
    Intent intent;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.octranspobusrouteapp);

        //progress bar

        pb = findViewById(R.id.progressBar2);



//question alert
        questionMark = findViewById(R.id.imageView2);
        questionMark.setOnClickListener(click ->
        {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("How to use the app.")
                    .setMessage("query the OCTranspo web servers for bus route information. The previously queried bus routes should be displayed in a list where the user can select. The can also add, or remove bus stop numbers which they want to get information about, which gets stored in a database.")
                    .setPositiveButton("Ok", (dlg, select) -> {

                        Snackbar.make(addButton, "try to add a bus stop!", Snackbar.LENGTH_LONG).show();

                    }).show();
        });





        //navigation bar
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
//                if (item.getItemId() == R.id.nav_bus) {
//                    Toast.makeText(OCTranspoApp.this, "Already in app!", Toast.LENGTH_LONG).show();
//
//                    return true;
//                }else{
//                    if (item.getItemId() == R.id.nav_bus){
//                        setContentView(R.layout.octranspobusrouteapp);
//                        return true;}
//                }
//
//                if (item.getItemId() == R.id.nav_soccer) {
//                    setContentView(R.layout.activity_main);
//
//                    return true;
//                }else{
//                    if (item.getItemId() == R.id.nav_soccer) {
//                        setContentView(R.layout.activity_main);
//                        return true;}
//                }
                switch (item.getItemId()) {
                    case R.id.nav_bus :
                        intent = new Intent(OCTranspoApp.this, OCTranspoApp.class);
                        startActivity(intent);
                        Toast.makeText(OCTranspoApp.this, "Already in app!", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_soccer :
                        intent = new Intent(OCTranspoApp.this, MainActivity.class);
                        startActivity(intent);

                        break;
                }

                return false;
            }
        });

        busList = findViewById(R.id.busRouteRecyclerView);
        busList.setAdapter(new recyclerAdapter());
        removeButton = findViewById(R.id.removeButton);
        addButton = findViewById(R.id.addButton);
        busStopEditView = findViewById(R.id.busStopEditText);






        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String defaultValue = null;
        prefs.getString("VariableName", defaultValue);
        String name = prefs.getString("Name", "");
        busStopEditView.setText(name);

        //add bus stop numbers
        removeButton.setOnClickListener(click -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Name", busStopEditView.getText().toString());
            editor.apply();

            busStopNumber = busStopEditView.getText().toString();

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Delete bus stop?")
                    .setMessage("Do you want to delete the bus stop number " + busStopNumber + " ?")
                    .setPositiveButton("Yes", (dlg, select) -> {//here to delete bus stop

                        Snackbar.make(addButton, "You deleted message #" + busStopNumber, Snackbar.LENGTH_LONG)
                                .setAction("undo", clk -> {//undo button

                                }).show();
                    })
                    .setNegativeButton("no", (dlg, select) -> {
                        Toast toast = Toast.makeText(this, "Action cancelled", Toast.LENGTH_LONG);
                        toast.show();

                    })
                    .show();


        });
        addButton.setOnClickListener(click -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Name", busStopEditView.getText().toString());
            editor.apply();


            busStopNumber = busStopEditView.getText().toString();

            Toast toast = Toast.makeText(this, "Added bus stop "+busStopNumber+".", Toast.LENGTH_LONG);
            toast.show();
            new Downloader().execute();


        });



                }

                class Downloader extends AsyncTask<Void, Integer, Integer> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setMax(100);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        pb.setProgress(values[0]);
    }

    @Override
    protected Integer doInBackground(Void... voids) {

        for(int i=0; i<100;i++){
            publishProgress(i);

            try{
                URL url = new URL("https://api.octranspo1.com/v2.0/GetRouteSummaryForStop?appID={appID}&apiKey={apiKey}&stopNo={stopNo}&format={format}");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader

            }catch(MalformedURLException e){


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Toast.makeText(getApplicationContext(), "Download finished!", Toast.LENGTH_LONG).show();
    }
}





    private class busInfo{

    }

    private class myBusRouteViews extends RecyclerView.ViewHolder{
        TextView busRoutes;

        public myBusRouteViews( View itemView) {
            super(itemView);


            //selecting a bus route to see details
            itemView.setOnClickListener(click -> {
                int row = getAdapterPosition();



            });



            busRoutes = itemView.findViewById(R.id.busRouteView);
        }
    }


    private class recyclerAdapter extends RecyclerView.Adapter<myBusRouteViews>{



            @Override
            public myBusRouteViews onCreateViewHolder(ViewGroup parent, int viewType) {
                return new myBusRouteViews(getLayoutInflater().inflate(R.layout.busroute, parent, false));
            }

            @Override
            public void onBindViewHolder(myBusRouteViews holder, int position) {
                holder.busRoutes.setText("");

                myBusRouteViews thisRowLayout = (myBusRouteViews) holder;
                thisRowLayout.busRoutes.setText("");//here is where we will set the text

            }

            @Override
            public int getItemCount() {
                return 0;
            }


    }

}

