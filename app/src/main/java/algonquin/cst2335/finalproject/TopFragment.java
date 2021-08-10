package algonquin.cst2335.finalproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This handles the fragment and its associated data
 */
public class TopFragment extends Fragment {

    Button returnbb;
    String routeNo;


    Element latitudeEle;
    Element longitudeEle;
    Element gpsSpeedEle;
    Element startTimeEle;
    Element adjustedEle;
    String appID;
    String appkey;
    String busStopNumber;
    View v;


    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_top, container, false);
        returnbb = v.findViewById(R.id.returnButton);


        returnbb.setOnClickListener(click -> {
            TopFragment topFragment = this;

            getFragmentManager().beginTransaction().remove(topFragment).commit();

        });
        OCTranspoApp ocTranspoApp = new OCTranspoApp();

        int row = ocTranspoApp.getRowAdapter();

        appID = ocTranspoApp.getApi();
        appkey = ocTranspoApp.getKey();
        busStopNumber = ocTranspoApp.getBusStopNumber();


        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(getContext());
        Cursor res = myDatabaseHelper.getReadableDatabase().rawQuery("SELECT routeNo FROM mybusinfo WHERE directionId = " + row, null);

        if (res.moveToFirst() && res.getCount() >= 1) {
            do {
                routeNo = res.getString(0);


            } while (res.moveToNext());


        }


//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        new Downloader2().execute();


        return v;
    }

    /**
     * This executes the code to retrieve the data from the url
     */
    class Downloader2 extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


        }

        @Override
        protected Integer doInBackground(Void... voids) {
            DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();

            try {

                URL url2 = new URL("https://api.octranspo1.com/v2.0/GetNextTripsForStop?appID=" + appID + "&apiKey=" + appkey + "&stopNo=" + busStopNumber + "&routeNo=" + routeNo + "&format={format}");
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
                InputStream inputStream2 = httpURLConnection2.getInputStream();
                DocumentBuilder builder2 = factory2.newDocumentBuilder();
                Document doc2 = builder2.parse(inputStream2);

                NodeList latitude = doc2.getElementsByTagName("Latitude");


                    Node E = latitude.item(0);
                    if (E.getNodeType() == Node.ELEMENT_NODE) {
                        latitudeEle = (Element) E;


                    }
                    NodeList longitude = doc2.getElementsByTagName("Longitude");
                    Node F = longitude.item(0);
                    if (F.getNodeType() == Node.ELEMENT_NODE) {
                        longitudeEle = (Element) F;


                    }
                    NodeList gpsSpeed = doc2.getElementsByTagName("GPSSpeed");
                    Node G = gpsSpeed.item(0);
                    if (G.getNodeType() == Node.ELEMENT_NODE) {
                        gpsSpeedEle = (Element) G;


                    }
                    NodeList tripStartTime = doc2.getElementsByTagName("TripStartTime");
                    Node H = tripStartTime.item(0);
                    if (H.getNodeType() == Node.ELEMENT_NODE) {
                        startTimeEle = (Element) H;


                    }
                    NodeList adjustedSched = doc2.getElementsByTagName("AdjustedScheduleTime");
                    Node I = adjustedSched.item(0);
                    if (I.getNodeType() == Node.ELEMENT_NODE) {
                        adjustedEle = (Element) I;


                    }




            } catch (MalformedURLException e) {


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            TextView longView = v.findViewById(R.id.textView7);
            TextView latView = v.findViewById(R.id.textView3);
            TextView gpsView = v.findViewById(R.id.textView4);
            TextView startView = v.findViewById(R.id.textView5);
            TextView adjView = v.findViewById(R.id.textView6);
            longView.setText(longitudeEle.getTextContent());
            latView.setText(latitudeEle.getTextContent());
            gpsView.setText(gpsSpeedEle.getTextContent());
            startView.setText(startTimeEle.getTextContent());
            adjView.setText(adjustedEle.getTextContent());


        }
    }
}


