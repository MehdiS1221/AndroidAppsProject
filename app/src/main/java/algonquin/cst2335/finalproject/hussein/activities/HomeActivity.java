package algonquin.cst2335.finalproject.hussein.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import algonquin.cst2335.finalproject.hussein.fragments.HomeFragment;
import algonquin.cst2335.finalproject.hussein.fragments.SavedFragment;

import java.util.Locale;

import algonquin.cst2335.finalproject.R;

import static androidx.core.view.GravityCompat.START;

public class HomeActivity extends AppCompatActivity {

    NavigationView navigation;
    DrawerLayout drawerlayout;
    ImageView ivMenu;
    TextView tvTitle;
    int selectedFrag = 1;// home fragment
    String[] langs = {"American English", "Canadian English"};
    String[] locales = {"en-rUS", "en-rCA"};
    private Fragment fragment;
    private boolean clickedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_movies);
        navigation = findViewById(R.id.navigation_view_home);
        drawerlayout = findViewById(R.id.drawer_layout);
        ivMenu = findViewById(R.id.iv_menu);
        tvTitle = findViewById(R.id.tv_action_bar_title);

        selectedFrag = 1;
        fragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view_home, fragment)
                .addToBackStack("home")
                .commit();

        ivMenu.setOnClickListener(v -> {
            if (!drawerlayout.isDrawerOpen(START)) {
                drawerlayout.openDrawer(START);
            }
        });


        navigation.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    if (selectedFrag != 1) {
                        drawerlayout.closeDrawer(START);
                        if (isAddedInBackStack("home") && getBackStackEntryCount() > 1) {
                            popBackStack();
                            selectedFrag = 1;
                            tvTitle.setText("Home");
                        }
                    }
                    break;


                case R.id.nav_saved:
                    if (selectedFrag != 2) {
                        drawerlayout.closeDrawer(START);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container_view_home, new SavedFragment())
                                .addToBackStack("saved")
                                .commit();
                        selectedFrag = 2;
                        tvTitle.setText("Saved");
                    }
                    break;

                case R.id.nav_change_lang:
                    drawerlayout.closeDrawer(START);
                    showChangeLanguage();
                    break;

                case R.id.nav_help:
                    drawerlayout.closeDrawer(START);
                    showHelpDialog();
                    break;
            }
            return true;
        });


    }


    private void showChangeLanguage() {
        int localeIndex = getLocaleIndex();
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Choose Language...");
        builder.setSingleChoiceItems(langs, localeIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setLocale(locales[which]);
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private int getLocaleIndex() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        String current = sharedPreferences.getString("Lang", "");
        int index = -1;
        for (int i = 0; i < locales.length; i++) {
            if (locales[i].equals(current)) {
                index = i;
                break;
            }
        }
        return index;
    }


    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Lang", language);
        editor.apply();
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.layout_help_movies, null, false);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ImageView ivClose = view.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    int getBackStackEntryCount() {
        return getSupportFragmentManager().getBackStackEntryCount();
    }

    private void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }

    boolean isAddedInBackStack(String tag) {
        boolean found = false;
        int count = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < count; i++) {
            String name = getSupportFragmentManager().getBackStackEntryAt(i).getName();
            if (name.toLowerCase().equals(tag.toLowerCase())) {
                found = true;
                break;
            }
        }
        return found;
    }

    @Override
    public void onBackPressed() {
        Log.e("YAM", "--------------------------------- OnBackPressed! -----------------------------");
        final int backStackEntryCount = getBackStackEntryCount();
        if (backStackEntryCount > 1) {
            popBackStack();
        } else {
            if (drawerlayout.isDrawerOpen(START)) {
                drawerlayout.closeDrawer(START);
            } else {
                if (clickedOnce) {
                    finishAffinity();
                } else {
                    clickedOnce = true;
                    Toast.makeText(this, "Press Back again to exit!!", Toast.LENGTH_SHORT).show();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickedOnce = false;
                    }

                }, 1500);

            }
        }
    }
}