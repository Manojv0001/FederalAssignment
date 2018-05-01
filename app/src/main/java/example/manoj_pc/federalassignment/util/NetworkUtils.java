package example.manoj_pc.federalassignment.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by MANOJ-PC on 4/3/2018.
 */

public class NetworkUtils {
    private static int TYPE_WIFI = 1;
    private static int TYPE_MOBILE = 2;
    private static int TYPE_NOT_CONNECTED = 0;
    private static NetworkInfo networkInfo;
    private static NetworkInfo activeNetwork;


    public static boolean isNetworkAvailable(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtils.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtils.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkUtils.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkUtils.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }



}
