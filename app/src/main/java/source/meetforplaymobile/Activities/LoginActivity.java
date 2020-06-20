package source.meetforplaymobile.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        Button linkRegister = findViewById(R.id.btn_link_register);
        Button btnAddEvent = findViewById(R.id.btn_add_event);
        btnAddEvent.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegister();
            }
        });
    }

    public void login()
    {
        if (!validate())
        {
            return;
        }
        loginButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autoryzacja...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLogin();
                        progressDialog.dismiss();
                    }
                }, 1000);
    }

    public void onLogin(){
        final String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        final HashMap<String, String> parameters = new HashMap<>();
        final User user = new User();
        user.setPassword(password);
        user.setEmail(email);

        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());

        RetrofitManager retrofitManager = new RetrofitManager();

        retrofitManager.getData("LoginUser", parameters, new ApiResponseInterface() {
            @Override
            public void onSuccess(@NonNull String value) {
                List<User> user_list = new Gson().fromJson(
                        value, new TypeToken<List<User>>() {
                        }.getType());
                user.setId(user_list.get(0).getId());


                if(user.getId() != -100)
                {
                    Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                    intent.putExtra("userId", user.getId());
                    intent.putExtra("userEmail", user.getEmail());
                    startActivity(intent);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Udało się zalogować",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 100);
                    toast.show();
                }
                else
                {
                    onLoginFailed();
                }
            }

            @Override
            public void onError(@NonNull String throwable) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Nie udało się zalogować",
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 100);
                toast.show();
            }
        });
        loginButton.setEnabled(true);
    }

    public void goRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onLoginFailed()
    {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Email i/lub hasło jest niepoprawny",
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
        emailInput.setError("Email i/lub hasło jest niepoprawny");
        passwordInput.setError("Email i/lub hasło jest niepoprawny");
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Proszę podać poprawny adres email");
            valid = false;
        } else {
            emailInput.setError(null);
        }

        if (password.isEmpty()) {
            passwordInput.setError("Nie poprawne hasło");
            valid = false;
        } else {
            passwordInput.setError(null);
        }

        return valid;
    }
}

