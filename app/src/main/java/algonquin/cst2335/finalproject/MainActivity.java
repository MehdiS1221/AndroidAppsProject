package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView editName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        editName = findViewById(R.id.editTextTextPersonName);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String defaultValue = null;
        prefs.getString("VariableName", defaultValue);
        String name = prefs.getString("Name", "");
        editName.setText(name);
        button.setOnClickListener(clicked -> {
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
                    Snackbar.make(button, "Downloading the article ", Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk -> {
                            })
                            .show();
                })
                    .create().show();
        });
    }
}