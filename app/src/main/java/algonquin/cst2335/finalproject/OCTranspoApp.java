package algonquin.cst2335.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
    String routeNo = "";
    String routeHeadings = "";
    static String appkey = "ab27db5b435b8c8819ffb8095328e775";
    static String appID = "223eb5c3";
    static String busstopnumberstatic;
    static int staticRowAdapter;
    MyDatabaseHelper mydb;
    Element routeHeadingEle;
    Element routeNumEle;
    Element directionIDEle;
    String stopNumberForUrl;
    Element latitudeEle;
    Element longitudeEle;
    Element gpsSpeedEle;
    Element startTimeEle;
    Element adjustedEle;
    int rowAdapter;
    ArrayList<Integer> directionID;
    ArrayList<String> routeN;
    ArrayList<String> destin;
    myBusRouteViews myBusRouteViewss;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.octranspobusrouteapp);

        //database helper

        //progress bar
        pb = findViewById(R.id.progressBar2);


        //data
        directionID = new ArrayList<Integer>();
        routeN = new ArrayList<>();
        destin = new ArrayList<>();
        mydb = new MyDatabaseHelper(OCTranspoApp.this);


        //customadap


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
                if (item.getItemId() == R.id.nav_bus) {
                    Toast.makeText(OCTranspoApp.this, "Already in app!", Toast.LENGTH_LONG).show();

                    return true;
                }
                if (item.getItemId() == R.id.nav_soccer) {
                    setContentView(R.layout.activity_main);

                    return true;
                }
                return false;
            }
        });

        busList = findViewById(R.id.busRouteRecyclerView);
        busList.setAdapter(new recyclerAdapter());
        busList.setLayoutManager(new LinearLayoutManager(OCTranspoApp.this));

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
                    .setTitle("Clear bus stop list?")
                    .setMessage("Do you want to delete the bus stop number " + busStopNumber + " ?")
                    .setPositiveButton("Yes", (dlg, select) -> {//here to delete bus stop

                        mydb.deleteData();

                        storeDataInArrays();


                        busList.setAdapter(new recyclerAdapter());
                        busList.setLayoutManager(new LinearLayoutManager(OCTranspoApp.this));






                        recyclerAdapter recyclerAdapters = new recyclerAdapter();
                        recyclerAdapters.notifyDataSetChanged();
                        busList.setVisibility(View.INVISIBLE);
//                        directionID.clear();
//                        routeN.clear();
//                        destin.clear();
//                        recyclerAdapter recyclerAdapter = new recyclerAdapter();
//                        recyclerAdapter.notifyDataSetChanged();


//                        myBusRouteViewss = new myBusRouteViews(busList);
//                        myBusRouteViewss.notifyDataSetChanged();






                        Snackbar.make(addButton, "You deleted bus stop " + busStopNumber + " from the list", Snackbar.LENGTH_LONG)
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

            busList.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Name", busStopEditView.getText().toString());
            editor.apply();


            busStopNumber = busStopEditView.getText().toString();
            setBusStopNumber();
            Toast toast = Toast.makeText(this, "Added bus stop "+busStopNumber+".", Toast.LENGTH_LONG);
            toast.show();
            new Downloader().execute();


        });



                }

                class Downloader extends AsyncTask<Void, Integer, Integer> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setVisibility(View.VISIBLE);
        pb.setMax(100);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        pb.setProgress(values[0]);
    }

    @Override
    protected Integer doInBackground(Void... voids) {
//        mydb = new MyDatabaseHelper(OCTranspoApp.this);

        SQLiteDatabase db = mydb.getWritableDatabase();





        for(int i=0; i<50;i++) {
            publishProgress(i);
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();

            try{
                URL url = new URL("https://api.octranspo1.com/v2.0/GetRouteSummaryForStop?appID="+appID+"&apiKey="+appkey+"&stopNo="+busStopNumber+"&format={format}");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(inputStream);

                //second url
//                URL url2 = new URL("https://api.octranspo1.com/v2.0/GetNextTripsForStop?appID="+appID+"&apiKey="+appkey+"&stopNo="+busStopNumber+"&routeNo="+routeNumEle.getTextContent()+"&format={format}");
//                HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
//                InputStream inputStream2 = httpURLConnection2.getInputStream();
//                DocumentBuilder builder2 = factory2.newDocumentBuilder();
//                Document doc2 = builder2.parse(inputStream2);




                NodeList routeList = doc.getElementsByTagName("RouteNo");
                for(int i =0;i<routeList.getLength();i++){
                    Node p = routeList.item(i);
                    if(p.getNodeType()==Node.ELEMENT_NODE) {
                        routeNumEle = (Element) p;



                    }
                    NodeList routeHeading = doc.getElementsByTagName("RouteHeading");
                    Node D = routeHeading.item(i);
                    if(D.getNodeType()==Node.ELEMENT_NODE){
                        routeHeadingEle = (Element) D;



                    }


//                    NodeList latitude = doc2.getElementsByTagName("Latitude");
//                    Node E = latitude.item(i);
//                    if(E.getNodeType()==Node.ELEMENT_NODE){
//                        latitudeEle = (Element) E;
//
//
//
//                    }
//                    NodeList longitude = doc2.getElementsByTagName("Longitude");
//                    Node F = longitude.item(i);
//                    if(F.getNodeType()==Node.ELEMENT_NODE){
//                        longitudeEle = (Element) F;
//
//
//
//                    }
//                    NodeList gpsSpeed = doc2.getElementsByTagName("GPSSpeed");
//                    Node G = gpsSpeed.item(i);
//                    if(G.getNodeType()==Node.ELEMENT_NODE){
//                        gpsSpeedEle = (Element) G;
//
//
//
//                    }
//                    NodeList tripStartTime = doc2.getElementsByTagName("TripStartTime");
//                    Node H = tripStartTime.item(i);
//                    if(H.getNodeType()==Node.ELEMENT_NODE){
//                        startTimeEle = (Element) H;
//
//
//
//                    }
//                    NodeList adjustedSched = doc2.getElementsByTagName("AdjustedScheduleTime");
//                    Node I = adjustedSched.item(i);
//                    if(I.getNodeType()==Node.ELEMENT_NODE){
//                        adjustedEle = (Element) I;
//
//
//
//                    }

//,latitudeEle.getTextContent(),longitudeEle.getTextContent(),gpsSpeedEle.getTextContent(),startTimeEle.getTextContent(),adjustedEle.getTextContent()

                    mydb.addBusInfo(i,routeNumEle.getTextContent(),routeHeadingEle.getTextContent());

                }

//                NodeList routeHeading = doc.getElementsByTagName("RouteHeading");
//                for(int i =0;i<routeHeading.getLength();i++){
//                    Node p = routeHeading.item(i);
//                    if(p.getNodeType()==Node.ELEMENT_NODE) {
//                        Element Route = (Element) p;
//                        routeHeadings = routeHeadings + Route.getTextContent()+", ";
//                        mydb.addBusDestination(Route.getTextContent());
//                    }
//                }




            }catch(MalformedURLException e){


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        for(int i=50; i<100;i++) {
            publishProgress(i);
            pb.setVisibility(View.INVISIBLE);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Toast.makeText(getApplicationContext(), "Download finished!", Toast.LENGTH_LONG).show();

        storeDataInArrays();
        busList.setAdapter(new recyclerAdapter());
        busList.setLayoutManager(new LinearLayoutManager(OCTranspoApp.this));
                }
}







    private class myBusRouteViews extends RecyclerView.ViewHolder{
        TextView busRoutesView;
        TextView destinationIDView;
        TextView busRouteNumView;


        public myBusRouteViews( View itemView) {
            super(itemView);

            busRoutesView = itemView.findViewById(R.id.busRouteView);
            destinationIDView = itemView.findViewById(R.id.destinationIDView);
            busRouteNumView = itemView.findViewById(R.id.routeNo);




            //selecting a bus route to see details
            itemView.setOnClickListener(click -> {
                rowAdapter = getAdapterPosition();
                setRowAdapter();
                TopFragment topFragment = new TopFragment();
                FragmentManager manager = getSupportFragmentManager();

                manager.beginTransaction().add(R.id.drawerLayout, topFragment).commit();

            });



        }
    }


    private class recyclerAdapter extends RecyclerView.Adapter<myBusRouteViews>{





            @Override
            public myBusRouteViews onCreateViewHolder(ViewGroup parent, int viewType) {

                return new myBusRouteViews(getLayoutInflater().inflate(R.layout.busroute, parent, false));
            }

            @Override
            public void onBindViewHolder(myBusRouteViews holder, int position) {
                holder.busRoutesView.setText(String.valueOf(destin.get(position)));
                holder.busRouteNumView.setText(String.valueOf(routeN.get(position)));
                holder.destinationIDView.setText(String.valueOf(directionID.get(position)));





            }

            @Override
            public int getItemCount() {
                return destin.size();
            }


    }
    void storeDataInArrays(){
        Cursor cursor = mydb.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                directionID.add(cursor.getInt(0));
                routeN.add(cursor.getString(1));
                destin.add(cursor.getString(2));




            }
        }
    }

    public int getRowAdapter(){


        return staticRowAdapter;
    }
    public String getKey(){
        String key = this.appkey;
        return key;
    }
    public String getApi(){
        String api = appID;
        return api;
    }
    public String getBusStopNumber(){

        return busstopnumberstatic;
    }
    public void setBusStopNumber(){
        busstopnumberstatic = this.busStopNumber;
    }
    public void setRowAdapter(){
        staticRowAdapter = this.rowAdapter;

    }


}

