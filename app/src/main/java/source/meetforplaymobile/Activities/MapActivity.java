package source.meetforplaymobile.Activities;

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
       // userEmail = getIntent().getStringExtra("userEmail");
        //userId = getIntent().getIntExtra("userId", 0);
        //userEmailText = findViewById(R.id.user_email);
        //userEmailText.append(userEmail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
         mapFragment.getMapAsync(this);




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        float zoomLevel = (float) 6.0;

        map = googleMap;

        LatLng poland= new LatLng(51.755482, 19.363078);
       // map.addMarker(new MarkerOptions().position(poland).title("Poland"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poland, zoomLevel));



    }
}
