package source.meetforplaymobile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import source.meetforplaymobile.Api.ApiResponseInterface;
import source.meetforplaymobile.Api.RetrofitManager;
import source.meetforplaymobile.Models.EventCoordinates;
import source.meetforplaymobile.Models.User;
import source.meetforplaymobile.R;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private int userId;
    private String userEmail;
    private TextView userEmailText;
    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
         mapFragment.getMapAsync(this);




    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        RetrofitManager retrofitManager = new RetrofitManager();

        retrofitManager.getData("GetEventsCoordinates",null, new ApiResponseInterface() {
            @Override
            public void onSuccess(@NonNull String value) {

                List<EventCoordinates> EventCoordinates_list = new Gson().fromJson(
                        value, new TypeToken<List<EventCoordinates>>() {
                        }.getType());



                float zoomLevel = (float) 6.0;
                map = googleMap;
                LatLng poland= new LatLng(51.755482, 19.363078);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poland, zoomLevel));

                for (int i = 0; i< EventCoordinates_list.size(); i++) {

                    Double latitude = EventCoordinates_list.get(i).getLatitude();
                    Double longitude = EventCoordinates_list.get(i).getLongitude();
                    String title = EventCoordinates_list.get(i).getEventName();
                    LatLng point = new LatLng(latitude, longitude);
                    map.addMarker(new MarkerOptions().position(point).title(title));


                }



            }

            @Override
            public void onError(@NonNull String throwable) {

            }
        });





    }
}
