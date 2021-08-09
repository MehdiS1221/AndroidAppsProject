package algonquin.cst2335.finalproject;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivityCarCharging extends AppCompatActivity {

    String location = null;
    String latitude = null;
    String longitude = null;
    String telephone = null;
    private NodeList LocationTitle;
    private NodeList Latitude;
    private NodeList Longitude;
    private NodeList ContactTelephone1;
    RecyclerView recyclerView;
    ArrayList<String> names = new ArrayList<>();

    private Context context;
    private class recyclerAdapter extends RecyclerView.Adapter<myLocationsViews>{
        @Override
        public myLocationsViews onCreateViewHolder(ViewGroup parent, int viewType) {

            return new myLocationsViews(getLayoutInflater().inflate(R.layout.locationview, parent, false));
        }
        @Override
        public void onBindViewHolder(myLocationsViews holder, int position) {
            holder.tv.setText(String.valueOf(names.get(position)));
        }
        @Override
        public int getItemCount() {

            return names.size();
        }
    }

    public class myLocationsViews extends RecyclerView.ViewHolder{
        TextView tv;
        private Context context;
        public myLocationsViews( View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.LocationDisplay);

            itemView.setOnClickListener(click -> {
                        Fragment newFragment = new Fragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_location,newFragment);
        transaction.addToBackStack(null);

        transaction.commit();

            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main_car);
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new recyclerAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivityCarCharging.this));
        EditText text_search = findViewById(R.id.text_search);
        EditText text_search2 = findViewById(R.id.text_search2);
        Button searchButton = findViewById(R.id.searchButton);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        prefs.getString("VariableName", null);
        String name = prefs.getString("Name", "");
        text_search.setText(name);
        String name2 = prefs.getString("Name2", null);
        text_search2.setText(name2);

        Button btn = findViewById(R.id.instructions);
        btn.setOnClickListener(click ->
        {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Help")
                    .setMessage("Enter the Latitude and Longitude in order to find nearby electric car charging locations.")
                    .setPositiveButton("start", (dlg, select) -> {
                    }).show();
        });



        searchButton.setOnClickListener((click)-> {
            Toast toast = Toast.makeText(MainActivityCarCharging.this, "Searching for nearby stations ", Toast.LENGTH_LONG);
            toast.show();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Name", text_search.getText().toString());
            editor.putString("Name2", text_search2.getText().toString());
            editor.apply();


            Executor newThread = Executors.newSingleThreadExecutor();
        newThread.execute( () -> {
            try {


                String searchLatitude = text_search.getText().toString();
                String searchLongitude = text_search2.getText().toString();
                String stringURL = "https://api.openchargemap.io/v3/poi/?output=xml&countrycode=CA&latitude="
                        + searchLatitude + "&longitude=" + searchLongitude +
                        "&maxresults=10&key=74d0a1f9a1887951e6ade5ae5bfecb22";


                URL url = new URL(stringURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(in);

                LocationTitle = doc.getElementsByTagName("LocationTitle");
                Latitude = doc.getElementsByTagName("Latitude");
                Longitude = doc.getElementsByTagName("Longitude");
                ContactTelephone1 = doc.getElementsByTagName("ContactTelephone1");



                for (int i = 0; i < LocationTitle.getLength(); i++) {
                    Node locationNode = LocationTitle.item(i);
                    Node latitudeNode = Latitude.item(i);
                    Node longitudeNode = Longitude.item(i);
                    Node ContactTelephone1Node = ContactTelephone1.item(i);
                    if (locationNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element locationElement = (Element) locationNode;
                        location = locationElement.getTextContent();
                        names.add(location);
                    }
//                    if (latitudeNode.getNodeType() == Node.ELEMENT_NODE) {
//                        Element latitudeElement = (Element) latitudeNode;
//                        latitude = latitudeElement.getTextContent();
//                        names.add(latitude);
//                    }
//                    if (longitudeNode.getNodeType() == Node.ELEMENT_NODE) {
//                        Element longitudeElement = (Element) longitudeNode;
//                        longitude = longitudeElement.getTextContent();
//                        names.add(longitude);
//                    }
//                    if (ContactTelephone1Node.getNodeType() == Node.ELEMENT_NODE) {
//                        Element telephoneElement = (Element) ContactTelephone1Node;
//                        telephone = telephoneElement.getTextContent();
//                        names.add(telephone);
//                    }


                }
            }

        catch (IOException | ParserConfigurationException | SAXException e){
                Log.e("Connection Error:", e.getMessage());
        }
        } );
        });


    }

}
