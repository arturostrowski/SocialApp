package pl.almestinio.socialapp.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class RestClient {

    static RequestsService client;

    String API_BASE_URL = "https://almestinio.pl/";

    public RestClient(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response;
            }
        });

        Retrofit builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        client = builder.create(RequestsService.class);

    }

    static public RequestsService getClient(){
        return client;
    }

}
