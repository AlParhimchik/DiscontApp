package com.example.sashok.task_;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sashok.task_.Answer.Answer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sashok on 30.4.17.
 */

public class RegistrationActivity extends AppCompatActivity {
    private TextInputEditText inputLogin;
    private TextInputEditText inputPassword;
    private TextInputEditText inputConfirmPass;
    private TextInputEditText inputPhone;
    private TextView btnReg;
    private ProgressDialog pDialog;
    private SessionManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        inputPhone = (TextInputEditText) findViewById(R.id.phone_edit_text);
        inputLogin = (TextInputEditText) findViewById(R.id.user_name_edit_text);
        inputPassword = (TextInputEditText) findViewById(R.id.password_edit_text);
        inputConfirmPass = (TextInputEditText) findViewById(R.id.confirm_password_edit_text);
        btnReg = (TextView) findViewById(R.id.registr_button);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Registrating...");

        session = new SessionManager(getApplicationContext());

        inputPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validForm()) {
                    pDialog.show();
                    String login = inputLogin.getText().toString();
                    String password = inputPassword.getText().toString();
                    String phone = inputPhone.getText().toString();
                    Call<Answer> answerCall = MyApplication.getServiceApi().Registration(login, password, phone);
                    answerCall.enqueue(new Callback<Answer>() {
                        @Override
                        public void onResponse(Call<Answer> call, Response<Answer> response) {
                            if (response.body().getError() == null) {
                                onRegistrSuccess(response.body().getData().getSessionKey());
                            } else {
                                onRegistrError(response.body().getError().getErrorMessage());

                            }
                            pDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Answer> call, Throwable t) {
                            pDialog.dismiss();
                            Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    public void onRegistrSuccess(String sKey) {
        session.setSessionKey(sKey);
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(intent);

    }

    public void onRegistrError(String error) {

        Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();

    }

    public boolean validForm() {
        boolean valid = true;
        String login = inputLogin.getText().toString();
        String password = inputPassword.getText().toString();
        String confirm_password = inputConfirmPass.getText().toString();
        String phone = inputPhone.getText().toString();

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
        if (confirm_password.isEmpty() || confirm_password.length() < 4) {
            inputPassword.setError("enter more than 4 characters");
            valid = false;
        } else if (!confirm_password.equals(password)) {
            inputConfirmPass.setError("passwords must matches");
            valid = false;
        } else {
            inputConfirmPass.setError(null);
        }
        if (phone.isEmpty() || phone.length() != 16) {
            valid = false;
            inputPhone.setError("unvalid phone");
        } else {
            inputPhone.setError(null);
        }
        return valid;
    }

}
