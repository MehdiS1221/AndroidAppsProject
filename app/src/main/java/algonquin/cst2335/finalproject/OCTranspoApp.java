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
    Button add;
    Button removeButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.octranspobusrouteapp);

        busList = findViewById(R.id.busRouteRecyclerView);
        busList.setAdapter(new recyclerAdapter());
        Button returnButton = findViewById(R.id.returnButton);
        removeButton = findViewById(R.id.removeButton);
        add = findViewById(R.id.addButton);

//        returnButton.setOnClickListener(click -> {
//            setContentView(R.layout.octranspobusrouteapp);
//        });

        //add bus stop numbers
        add.setOnClickListener(click ->{
            EditText editText = findViewById(R.id.busStopEditText);
            String busStopNumber = editText.getText().toString();

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Delete bus stop?")
                    .setMessage("Do you want to delete the bus stop number "+ busStopNumber + " ?")
                    .setPositiveButton("Yes", (dlg, select) ->{//here to delete bus stop

                        Snackbar.make(add, "You deleted message #" + busStopNumber, Snackbar.LENGTH_LONG)
                                .setAction("undo", clk ->{//undo button

                                }).show();
                    })
                    .setNegativeButton("no", (dlg, select) ->{
                        Toast toast = Toast.makeText(this, "Action cancelled", Toast.LENGTH_LONG);
                        toast.show();

                    })
                    .show();



                }
        );

//
//                    removeButton.setOnClickListener(clicked -> {
//                        String busStopNumber = findViewById(R.id.busStopEditText).toString();
//                        Context context = getApplicationContext();
//                        CharSequence text = "Soccer Game!";
//                        int duration = Toast.LENGTH_LONG;
//
//                        Toast toast = Toast.makeText(context, text, duration);
//                        toast.show();
//                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                        builder.setMessage("Welcome To sport News")
//                                .setTitle("Soccer Game News")
//                                .setNegativeButton("Cancel", (dialog, cl) -> {
//                                })
//                                .setPositiveButton("Start Downloding", (dialog, cl) -> {
//                                    Snackbar.make(removeButton, "Downloading the article ", Snackbar.LENGTH_LONG)
//                                            .setAction("Undo", clk -> {
//                                            })
//                                            .show();
//                                })
//                                .create().show();
//                    });

//        removeButton.setOnClickListener(clicked -> {
//
//
//
//            CharSequence text = "Soccer Game!";
//            int duration = Toast.LENGTH_LONG;
//            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
//            toast.show();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("Welcome To sport News")
//                    .setTitle("Soccer Game News")
//                    .setNegativeButton("Cancel", (dialog, cl) -> {
//                    })
//                    .setPositiveButton("Start Downloding", (dialog, cl) -> {
//                        Snackbar.make(removeButton, "Downloading the article ", Snackbar.LENGTH_LONG)
//                                .setAction("Undo", clk -> {
//                                })
//                                .show();
//                    })
//                    .create().show();
//        });

//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(OCTranspoApp.this);
//                    builder.setMessage("Do you want to delete the bus stop number "+ busStopNumber + " ?")
//                            .setTitle("Title").setPositiveButton("Yes", (dlg, clic) -> {
//
////                    removedMessage = messages.get(row);
////                    messages.remove(row);
////                    adt.notifyItemRemoved(row);
//
//
////                    db.delete(MyOpenHelper.TABLE_NAME, "_id=?", new String[] { Long.toString( removedMessage.getId() )});
//
//
////
////                    messages.remove(row);//which message do you delete?
////                    //update list view:
////                    adt.notifyItemRemoved(row);
//                        Snackbar.make(add, "You deleted bus stop" + busStopNumber, Snackbar.LENGTH_LONG).show();
//                    })
//                            .setNegativeButton("No", (dlg, clic) -> {
//                                Toast toast = Toast.makeText(getApplicationContext(), "Cancelled deletion of bus stop number!", Toast.LENGTH_LONG);
//                                toast.show();
//                            })
//                            .create()
//                            .show();

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
//                holder.busRoutes.setText("");

                myBusRouteViews thisRowLayout = (myBusRouteViews) holder;
                thisRowLayout.busRoutes.setText("");//here is where we will set the text

            }

            @Override
            public int getItemCount() {
                return 0;
            }


    }

}

