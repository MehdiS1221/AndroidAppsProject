package algonquin.cst2335.finalproject.hussein.util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import algonquin.cst2335.finalproject.R;

public class UtilClass {

    public static boolean isConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }

    public static void showNoInternetAlertDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.MyDialogTheme);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("DefaultLocale")
    public static String getShortAmount(String amount) {
        try {
            String result = amount.replace(",", "");
            long amountNumber = Long.parseLong(result);
            if (amountNumber > 1000000000000000000L) {
                result = String.format("%.1f", amountNumber / 1000000000000000000.0d).concat("Qui");
            } else if (amountNumber > 1000000000000000L) {
                result = String.format("%.1f", amountNumber / 1000000000000000.0d).concat("Qua");
            } else if (amountNumber > 1000000000000L) {
                result = String.format("%.1f", amountNumber / 1000000000000.0d).concat("T");
            } else if (amountNumber > 1000000000) {
                result = String.format("%.1f", amountNumber / 1000000000.0).concat("B");
            } else if (amountNumber > 1000000) {
                result = String.format("%.1f", amountNumber / 1000000.0).concat("M");
            } else if (amountNumber > 1000) {
                result = String.format("%.1f", amountNumber / 1000.0).concat("K");
            }
            return result;
        } catch (Exception e) {
            return "0.0";
        }
    }
}
