package source.meetforplaymobile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import source.meetforplaymobile.R;


public class MapActivity extends AppCompatActivity {
    private int userId;
    private String userEmail;
    private TextView userEmailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        userEmail = getIntent().getStringExtra("userEmail");
        userId = getIntent().getIntExtra("userId", 0);

        userEmailText = findViewById(R.id.user_email);
        userEmailText.append(userEmail);
    }
}
