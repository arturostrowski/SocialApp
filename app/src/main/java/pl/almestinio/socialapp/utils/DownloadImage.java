package pl.almestinio.socialapp.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by mesti193 on 3/8/2018.
 */

public class DownloadImage {


    public static void downloadFromUrl(String uRl, Context context) {
        try{
            File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/SocialApp/");

            if (!direct.exists()) {
                direct.mkdirs();
            }

            DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            Uri downloadUri = Uri.parse(uRl);
            DownloadManager.Request request = new DownloadManager.Request(
                    downloadUri);

            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI
                            | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false).setTitle("Demo")
                    .setDescription("Something useful. No, really.")
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "SocialApp/postphoto.jpg");

            mgr.enqueue(request);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
