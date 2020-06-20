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
import source.meetforplaymobile.Models.RegisterResult;
import source.meetforplaymobile.Models.User;
import source.meetforplaymobile.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private EditText repeatPasswordInput;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);
        repeatPasswordInput = findViewById(R.id.input_repeat_password);
        registerButton = findViewById(R.id.btn_register);

        Button linkLogin = findViewById(R.id.btn_link_login);

        Button btnAddEvent = findViewById(R.id.btn_add_event);
        btnAddEvent.setVisibility(View.GONE);

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public void register() {
        if (!validate()) {
            return;
        }
        registerButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autoryzacja...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onRegister();
                        progressDialog.dismiss();
                    }
                }, 1000);
    }

    public void onRegister() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        final HashMap<String, String> parameters = new HashMap<>();
        final User user = new User();
        user.setPassword(password);
        user.setEmail(email);

        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        RetrofitManager retrofitManager = new RetrofitManager();

        retrofitManager.getData("RegisterUser", parameters, new ApiResponseInterface() {
            @Override
            public void onSuccess(@NonNull String value) {
                List<RegisterResult> result_list = new Gson().fromJson(
                        value, new TypeToken<List<RegisterResult>>() {
                        }.getType());
                RegisterResult result = result_list.get(0);

                if (result.getStatus() != 0) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Rejestracja powiodła się",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else {
                    onRegisterFailed();
                }
            }

            @Override
            public void onError(@NonNull String throwable) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Nie udało się zarejestrować",
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 100);
                toast.show();
            }
        });
        registerButton.setEnabled(true);
    }

    public void onRegisterFailed() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Ten adres email jest już zajęty",
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
        emailInput.setError("Ten adres email jest już zajęty");
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String repeatPassword = repeatPasswordInput.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Proszę podać poprawny adres email");
            valid = false;
        } else {
            emailInput.setError(null);
        }

        if (password.isEmpty() & password.length() < 6) {
            passwordInput.setError("Hasło musi mieć więcej jak 6 znaków");
            valid = false;
        } else {
            passwordInput.setError(null);
        }

        if (!password.equals(repeatPassword)) {
            passwordInput.setError("Hasła nie są takie same");
            repeatPasswordInput.setError("Hasła nie są takie same");
            valid = false;
        } else {
            passwordInput.setError(null);
        }

        return valid;
    }

    public void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
