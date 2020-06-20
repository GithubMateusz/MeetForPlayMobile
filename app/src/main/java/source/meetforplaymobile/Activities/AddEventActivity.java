package source.meetforplaymobile.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import source.meetforplaymobile.Api.ApiResponseInterface;
import source.meetforplaymobile.Api.RetrofitManager;
import source.meetforplaymobile.Models.AddEvent;
import source.meetforplaymobile.Models.EventCategory;
import source.meetforplaymobile.Models.Status;
import source.meetforplaymobile.R;

public class AddEventActivity extends FragmentActivity implements OnMapReadyCallback {
    private int userId;
    private String userEmail;
    LatLng MarkerPosition;
    GoogleMap map;
    EditText EtDateTime;
    EditText EeventName;
    EditText EobjectName;
    EditText EminPersonToStart;
    EditText EmaxPersonToStart;
    private Spinner EventCategorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        userEmail = intent.getStringExtra("userEmail");
        setContentView(R.layout.activity_add_event);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapAddEvent);

        mapFragment.getMapAsync(this);

        EeventName = findViewById(R.id.eventName);
        EobjectName = findViewById(R.id.objectName);
        EminPersonToStart = findViewById(R.id.minPersonToStart);
        EmaxPersonToStart = findViewById(R.id.maxPersonToStart);
        EventCategorySpinner = findViewById(R.id.EventCategorySpinner);

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

        RetrofitManager retrofitManager = new RetrofitManager();
        retrofitManager.getData("GetEventCategory", null, new ApiResponseInterface() {
            @Override
            public void onSuccess(@NonNull String value) {

                List<EventCategory> EventCategory_list = new Gson().fromJson(
                        value, new TypeToken<List<EventCategory>>() {
                        }.getType());

                ArrayAdapter<EventCategory> CategoryAdapter = new ArrayAdapter<EventCategory>(
                        AddEventActivity.this, android.R.layout.simple_spinner_item, EventCategory_list);
                CategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                EventCategorySpinner.setAdapter(CategoryAdapter);

            }

            @Override
            public void onError(@NonNull String throwable) {

            }
        });


        EtDateTime = findViewById(R.id.date_time_input);
        EtDateTime.setInputType(InputType.TYPE_NULL);
        EtDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(EtDateTime);
            }
        });
    }      // onCreate

    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(AddEventActivity.this, R.style.TimePickerTheme, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };


        DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this, R.style.TimePickerTheme, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 86400000);
        datePickerDialog.show();
    }


/// koniec wyboru dat


    public void CreateEvent(View v) {

        EventCategory ec = (EventCategory) EventCategorySpinner.getSelectedItem();
        String CategoryName = ec.getName();
        String eventDate = EtDateTime.getText().toString();


        String eventName = EeventName.getText().toString();
        String objectName = EobjectName.getText().toString();
        String minPersonToStart = EminPersonToStart.getText().toString();
        String maxPersonToStart = EmaxPersonToStart.getText().toString();
        String latittude = String.valueOf(MarkerPosition.latitude);
        String longitude = String.valueOf(MarkerPosition.longitude);

        final HashMap<Object, Object> parameters = new HashMap<>();
        final AddEvent event = new AddEvent();

        event.setEvetnName(eventName);
        event.setObjectName(objectName);
        event.setMinPersonToStart(minPersonToStart);
        event.setMaxPersonToStart(maxPersonToStart);
        event.setUserId(userId);
        event.setLatittude(latittude);
        event.setLongitude(longitude);
        event.setEventDate(eventDate);
        event.setEventCategoryName(CategoryName);


        parameters.put("evetnName", event.getEvetnName());
        parameters.put("latittude", event.getLatittude());
        parameters.put("longitude", event.getLongitude());
        parameters.put("eventDate", event.getEventDate());
        parameters.put("objectName", event.getObjectName());
        parameters.put("eventCategoryName", event.getEventCategoryName());
        parameters.put("minPersonToStart", event.getMinPersonToStart());
        parameters.put("maxPersonToStart", event.getMaxPersonToStart());
        parameters.put("userId", event.getUserId());


        RetrofitManager retrofitManager = new RetrofitManager();
        retrofitManager.getData("CreateEvent", parameters, new ApiResponseInterface() {
            @Override
            public void onSuccess(@NonNull String value) {
                List<Status> statusList = new Gson().fromJson(
                        value, new TypeToken<List<Status>>() {
                        }.getType());

                if (statusList.get(0).getStatus() == 100) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Utworzono nowe wydarzenie",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 100);
                    toast.show();
                    backToMap();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Coś poszło nie tak, spróbuj ponownie",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 100);
                    toast.show();
                }
            }

            @Override
            public void onError(@NonNull String throwable) {

            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        float zoomLevel = (float) 6.0;
        map = googleMap;
        LatLng Poland = new LatLng(52.199594, 19.438958);


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Poland, zoomLevel));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : " + latLng.latitude);


                // usuniecie poprzedniego markera
                map.clear();
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                map.addMarker(markerOptions);


                LatLng position = markerOptions.getPosition();

                MarkerPosition = position;


            }
        });
    }

    public void backToMap() {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("userEmail", userEmail);
        startActivity(intent);
    }

    private void  backToLogin() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Udało się poprawnie wylogować",
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
