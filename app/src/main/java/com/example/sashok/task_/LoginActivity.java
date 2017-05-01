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
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        //Observable<Answer> answerCall = MyApplication.getServiceApi().Authenticate(login, password);
        Observable<Answer> myAnswer = Observable.create(new Observable.OnSubscribe<Answer>() {
            @Override
            public void call(Subscriber<? super Answer> subscriber) {
                subscriber.onNext(fromJsonToAnswer());
            }
        });
        myAnswer.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Answer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                onLoginError("Error..no internet connection maybe");
            }

            @Override
            public void onNext(Answer answer) {
                if (answer.getError() == null) {
                    onLoginSuccess(answer.getData().getSessionKey());
                } else {
                    onLoginError(answer.getError().getErrorMessage());

                }

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

    public String loadJSONFromAsset(String file_name) {
        String json = null;
        try {

            InputStream is = getAssets().open(file_name);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public Answer fromJsonToAnswer() {
        String json = loadJSONFromAsset("login_answer.json");
        Gson gson = new Gson();
        return gson.fromJson(json, Answer.class);
    }
}
