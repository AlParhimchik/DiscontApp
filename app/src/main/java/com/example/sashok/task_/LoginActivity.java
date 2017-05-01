package com.example.sashok.task_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sashok.task_.Answer.Answer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText inputLogin;
    private TextInputEditText inputPassword;
    private TextView btnEnter;
    private TextView btnReg;
    private ProgressDialog pDialog;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputLogin = (TextInputEditText) findViewById(R.id.login_edit_text);
        inputPassword = (TextInputEditText) findViewById(R.id.password_edit_text);
        btnEnter = (TextView) findViewById(R.id.enter_button);
        btnReg = (TextView) findViewById(R.id.reg_btn);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Authenticating...");

        session = new SessionManager(getApplicationContext());

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateForm()) {
                    pDialog.show();
                    String login = inputLogin.getText().toString();
                    String password = inputPassword.getText().toString();
                    makeLogin(login, password);

                    //onLoginSuccess("xyeta");

                }

            }
        });
    }

    public void makeLogin(String login, String password) {
        Call<Answer> answerCall = MyApplication.getServiceApi().Authenticate(login, password);
        answerCall.enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, Response<Answer> response) {
                if (response.body().getError() == null) {
                    onLoginSuccess(response.body().getData().getSessionKey());
                } else {
                    onLoginError(response.body().getError().getErrorMessage());

                }
            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void onLoginSuccess(String sKey) {
        session.setSessionKey(sKey);
        pDialog.dismiss();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }

    public void onLoginError(String error) {
        pDialog.dismiss();
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();

    }

    public boolean validateForm() {
        boolean valid = true;
        String login = inputLogin.getText().toString();
        String password = inputPassword.getText().toString();

        if (login.isEmpty()) {
            inputLogin.setError("enter login");
            valid = false;
        } else {
            inputLogin.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            inputPassword.setError("enter more than 4 characters");
            valid = false;
        } else {
            inputPassword.setError(null);
        }
        return valid;
    }
}
