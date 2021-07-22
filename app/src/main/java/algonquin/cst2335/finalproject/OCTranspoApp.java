package algonquin.cst2335.finalproject;

import android.app.AlertDialog;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class OCTranspoApp extends AppCompatActivity {
    RecyclerView busList;
    Button addButton;
    Button removeButton;
    EditText busStopEditView;
    String busStopNumber;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.octranspobusrouteapp);










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


        });



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

