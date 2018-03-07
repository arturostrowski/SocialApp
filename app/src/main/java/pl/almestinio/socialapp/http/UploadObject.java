package pl.almestinio.socialapp.http;

/**
 * Created by mesti193 on 1/24/2018.
 */

public class UploadObject {
    private String success;
    public UploadObject(String success) {
        this.success = success;
    }
    public String getSuccess() {
        return success;
    }
}