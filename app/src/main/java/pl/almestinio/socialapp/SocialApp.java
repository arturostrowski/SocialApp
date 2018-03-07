package pl.almestinio.socialapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

import pl.almestinio.socialapp.http.RestClient;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class SocialApp extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        RestClient restClient = new RestClient();

    }
}
