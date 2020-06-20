package source.meetforplaymobile.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import source.meetforplaymobile.Api.ApiResponseInterface;
import source.meetforplaymobile.Api.RetrofitManager;
import source.meetforplaymobile.Models.EventCoordinates;
import source.meetforplaymobile.R;


public class MapActivity extends FragmentActivity implements
        OnMapReadyCallback {
    AlertDialog.Builder builder;
    private int userId;
    private String userEmail;
    private TextView userEmailText;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        userEmail = intent.getStringExtra("userEmail");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        RetrofitManager retrofitManager = new RetrofitManager();

        builder = new AlertDialog.Builder(this);

        retrofitManager.getData("GetEventsCoordinates", null, new ApiResponseInterface() {
            @Override
            public void onSuccess(@NonNull String value) {

                final List<EventCoordinates> eventCoordinatesList = new Gson().fromJson(
                        value, new TypeToken<List<EventCoordinates>>() {
                        }.getType());


                float zoomLevel = (float) 6.0;
                map = googleMap;
                LatLng poland = new LatLng(51.755482, 19.363078);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poland, zoomLevel));

                for (int i = 0; i < eventCoordinatesList.size(); i++) {
                    double latitude = eventCoordinatesList.get(i).getLatitude();
                    double longitude = eventCoordinatesList.get(i).getLongitude();
                    LatLng point = new LatLng(latitude, longitude);
                    map.addMarker(new MarkerOptions().position(point)).setTag(0);
                }

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        EventCoordinates event = eventCoordinatesList.stream().filter(
                                e -> e.getLatitude() == marker.getPosition().latitude
                                        && e.getLongitude() == marker.getPosition().longitude
                        ).findFirst().orElse(null);
                        assert event != null;

                        builder.setMessage("Do you want to close this application ?")
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        Toast.makeText(getApplicationContext(), "you choose yes action for alertbox",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();
                                        Toast.makeText(getApplicationContext(), "you choose no action for alertbox",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                        AlertDialog dialog = builder.create();

                        dialog.setTitle(event.getEventName());
                        dialog.show();
                        return true;
                    }

                    ;
                });
            }

            @Override
            public void onError(@NonNull String throwable) {

            }
        });
    }


}
