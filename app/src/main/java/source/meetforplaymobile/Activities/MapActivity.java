package source.meetforplaymobile.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.List;

import source.meetforplaymobile.Api.ApiResponseInterface;
import source.meetforplaymobile.Api.RetrofitManager;
import source.meetforplaymobile.Models.EventBasicInfo;
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

        Button btnAddEvent = findViewById(R.id.btn_add_event);
        btnAddEvent.setText("Dodaj wydarzenie");
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddEvent();
            }
        });

        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setText("Wyloguj");
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLogin();
            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        RetrofitManager retrofitManager = new RetrofitManager();

        builder = new AlertDialog.Builder(this, R.style.DialogStyle);

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
                        showEventBasicInfo(event);
                        return true;
                    }
                });
            }

            @Override
            public void onError(@NonNull String throwable) {

            }
        });
    }

    public void showEventBasicInfo(EventCoordinates event) {
        final HashMap<Object, Object> parameters = new HashMap<>();
        parameters.put("eventId", event.getEventId());

        RetrofitManager retrofitManager = new RetrofitManager();

        retrofitManager.getData("GetEventBasicInfo", parameters, new ApiResponseInterface() {
            @Override
            public void onSuccess(@NonNull String value) {
                final List<EventBasicInfo> eventBasicInfoList = new Gson().fromJson(
                        value, new TypeToken<List<EventBasicInfo>>() {
                        }.getType());

                EventBasicInfo eventBasicInfo = eventBasicInfoList.get(0);
                if (eventBasicInfo != null) {
                    String message;

                    message = eventBasicInfo.getCategoryName() + " \n";
                    message += eventBasicInfo.getEventDate() + " \n";
                    message += eventBasicInfo.getTakenSpots() + "/"
                            + eventBasicInfo.getMaxPerson() + " \n";
                    message += eventBasicInfo.getObjectName();
                    builder.setMessage(message)
                            .setCancelable(true)
                            .setPositiveButton("Dołącz", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    goJoinEvent(event.getEventId());
                                    finish();
                                }
                            })
                            .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.setTitle(eventBasicInfo.getEventName());
                    dialog.show();
                }
            }

            @Override
            public void onError(@NonNull String throwable) {

            }
        });
    }

    public void goJoinEvent(int eventId) {
        Intent intent = new Intent(this, JoinEventActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("eventId", eventId);
        intent.putExtra("userEmail", userEmail);
        startActivity(intent);
    }

    public void goAddEvent() {
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("userEmail", userEmail);
        startActivity(intent);
    }

    private void backToLogin() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Udało się poprawnie wylogować",
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
