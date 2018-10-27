package donation.solutions.hamza.com.hotingoadmin.service;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //    public static ApiEndpointInterface apiServiceInterface;
    public static final String BASE_URL = "https://hotingoapp.herokuapp.com/";

    private static Retrofit retrofit = null;
    private static AuthInterceptor mainAuthInterceptor;

    public static Retrofit getClient(AuthInterceptor authInterceptor) {

        OkHttpClient.Builder clientBuilder;
        OkHttpClient client;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(interceptor);


        if (retrofit == null || mainAuthInterceptor.getAccessToken() == null) {
            mainAuthInterceptor = authInterceptor;
            clientBuilder.addInterceptor(mainAuthInterceptor);
            client = clientBuilder.build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
//            apiServiceInterface = retrofit.create(ApiEndpointInterface.class);
        }
        return retrofit;
    }


}
