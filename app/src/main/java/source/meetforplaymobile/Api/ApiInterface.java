package source.meetforplaymobile.Api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/")
    Call<Object> getData(@Body Procedure procedure);
}