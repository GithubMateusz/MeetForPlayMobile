package source.meetforplaymobile.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import source.meetforplaymobile.Api.ApiResponseInterface;
import source.meetforplaymobile.Api.RetrofitManager;
import source.meetforplaymobile.Models.User;
import source.meetforplaymobile.R;

public class LoginActivity extends AppCompatActivity {
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewResult = findViewById(R.id.text_view_result);

        HashMap parameters = new HashMap();
        parameters.put("email", "test@test.pl");
        parameters.put("password", "testowe");

        RetrofitManager retrofitManager = new RetrofitManager();
        retrofitManager.getData("LoginUser", parameters, new ApiResponseInterface() {
            @Override
            public void onSuccess(@NonNull String value) {
                List<User> user_list = new Gson().fromJson(
                        value, new TypeToken<List<User>>(){}.getType());
                textViewResult.append(user_list.get(0).getEmail());
            }

            @Override
            public void onError(@NonNull String throwable) {
                // here you access the throwable and check what to do
            }
        });
    }
}

