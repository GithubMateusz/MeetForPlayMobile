package source.meetforplaymobile.Activities;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import source.meetforplaymobile.Api.ApiResponseInterface;
import source.meetforplaymobile.Api.RetrofitManager;
import source.meetforplaymobile.Models.EventAllInfo;
import source.meetforplaymobile.Models.Status;
import source.meetforplaymobile.R;

public class JoinEventActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    private int userId;
    private int eventId;
    private String userEmail;

    private TextView textEventName;
    private TextView textEventCategoryName;
    private TextView textEventDate;
    private TextView textEventPerson;

    private Button btnSubmit;

    private HashMap<Object, Object> parameters;
    private RetrofitManager retrofitManager = new RetrofitManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        eventId = intent.getIntExtra("eventId", 0);
        userEmail = intent.getStringExtra("userEmail");

        textEventName = findViewById(R.id.event_name);
        textEventCategoryName = findViewById(R.id.event_category);
        textEventDate = findViewById(R.id.event_date);
        textEventPerson = findViewById(R.id.event_person);

        btnSubmit = findViewById(R.id.btn_attaching_an);


        parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("eventId", eventId);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_event);

        mapFragment.getMapAsync(this);

        Button btnAddEvent = findViewById(R.id.btn_add_event);
        btnAddEvent.setText("Powrót");
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMap();
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
    public void onMapReady(GoogleMap googleMap) {
        float zoomLevel = (float) 6.0;
        map = googleMap;
        LatLng Poland = new LatLng(52.199594, 19.438958);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Poland, zoomLevel));
        getEventAllInfo();
    }

    private void getEventAllInfo() {
        retrofitManager.getData("GetEventAllInfo", parameters, new ApiResponseInterface() {
            @Override
            public void onSuccess(@NonNull String value) {
                List<EventAllInfo> eventAllInfoList = new Gson().fromJson(
                        value, new TypeToken<List<EventAllInfo>>() {
                        }.getType());
                EventAllInfo eventAllInfo = eventAllInfoList.get(0);

                setLatLong(eventAllInfo);

                setEventInfoActivity(eventAllInfo);

            }

            @Override
            public void onError(@NonNull String throwable) {

            }
        });
    }

    private void setLatLong(EventAllInfo eventAllInfo) {
        double latitude = eventAllInfo.getLatitude();
        double longitude = eventAllInfo.getLongitude();
        LatLng point = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(point));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 10));
    }

    private void setEventInfoActivity(EventAllInfo eventAllInfo) {
        textEventName.setText(eventAllInfo.getEventName());
        textEventCategoryName.setText(eventAllInfo.getEventCategoryName());
        textEventDate.setText(eventAllInfo.getEventDate());

        String person = eventAllInfo.getTakenSpots() + "/" + eventAllInfo.getMaxPerson();
        textEventPerson.setText(person);

        if (eventAllInfo.getTakesPart() == 0) {
            btnSubmit.setText("Dołącz");
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    joinToEvent();
                }
            });
        } else if (eventAllInfo.getTakesPart() == 1) {
            btnSubmit.setText("Zrezygnuj");
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leaveTheEvent();
                }
            });
        } else {
            btnSubmit.setVisibility(View.GONE);
        }
    }

    private void joinToEvent() {
        retrofitManager.getData("AddUserToEvent", parameters, new ApiResponseInterface() {
            @Override
            public void onSuccess(@NonNull String value) {
                List<Status> statusList = new Gson().fromJson(
                        value, new TypeToken<List<Status>>() {
                        }.getType());
                String text;
                if (statusList.get(0).getStatus() == 100) {
                    text = "Udało się dołaczyć do wydarzenia";
                } else {
                    text = "Coś poszło nie tak spróbuj ponownie";
                }
                refresh(text);
            }

            @Override
            public void onError(@NonNull String throwable) {

            }
        });
    }

    private void leaveTheEvent() {
        retrofitManager.getData("RemoveUserFromEvent", parameters, new ApiResponseInterface() {
            @Override
            public void onSuccess(@NonNull String value) {
                List<Status> statusList = new Gson().fromJson(
                        value, new TypeToken<List<Status>>() {
                        }.getType());
                String text;
                if (statusList.get(0).getStatus() == 100) {
                    text = "Udało się zrezygnować z udziału w wydarzeniu";
                } else {
                    text = "Coś poszło nie tak spróbuj ponownie";
                }
                refresh(text);
            }

            @Override
            public void onError(@NonNull String throwable) {

            }
        });
    }

    private void refresh(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
        startActivity(getIntent());
    }

    private void backToMap() {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("userEmail", userEmail);
        startActivity(intent);
    }

    private void backToLogin() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Udało się poprawnie wylogować",
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
