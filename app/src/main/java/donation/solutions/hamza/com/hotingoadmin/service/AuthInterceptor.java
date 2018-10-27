package donation.solutions.hamza.com.hotingoadmin.service;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final String accessToken;

    public AuthInterceptor(@Nullable String accessToken) {
        if (accessToken == null) {
            this.accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaG9uZSI6IjAxMjIyODgwODU0IiwiZW1haWwiOiJzc0Bhcy5jb20iLCJ1c2VySWQiOiI1YmE4YzY4MGNiNDM4YjAwMTVkZTUyNzQiLCJ0eXBlIjoiTk9STUFMIiwiaWF0IjoxNTM3Nzg3NTIwLCJleHAiOjE1NTM1NTU1MjB9.c0jmP00fHHF_k4LXDm7K0B1a3OTInDg9Uf-Es45j7LQ";

        } else {
            this.accessToken = accessToken;
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .header("Authorization", "bearer " + this.accessToken)
                .method(original.method(), original.body())
                .build();

        Response response = chain.proceed(request);

        return response;
    }

    public String getAccessToken() {
        return accessToken;
    }
}