package source.meetforplaymobile.Api;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitManager {

    public void getData(
            String procedureName, HashMap parameters, final ApiResponseInterface apiResponseInterface) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sql.mateusz-gierczak.pl")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiInterface service = retrofit.create(ApiInterface.class);
        Procedure procedure = new Procedure(procedureName, parameters);

        Call<Object> callback = service.getData(procedure);
        callback.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> callback, Response<Object> response) {
                if (response.isSuccessful()) {
                    apiResponseInterface.onSuccess(new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Object> callback, Throwable t) {
                apiResponseInterface.onSuccess(t.getMessage());
            }
        });
    }
}
