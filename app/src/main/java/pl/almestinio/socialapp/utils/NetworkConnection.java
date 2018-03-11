package pl.almestinio.socialapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mesti193 on 3/10/2018.
 */

public class NetworkConnection {

    private static ConnectivityManager connectivityManager;
    private static NetworkInfo activeNetwork;

    public static boolean isNetworkConnection(Context context){

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connectivityManager.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
            return true;
        }

        return false;
    }

}
