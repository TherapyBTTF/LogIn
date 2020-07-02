package com.salah.login;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.google.android.material.textfield.TextInputLayout;
import com.salah.login.myrequest.MyRequest;

public class Connexion extends AppCompatActivity {

    private TextInputLayout til_pseudo, til_password;
    private RequestQueue queue;
    private MyRequest request;
    private Button btn_send_login;
    private ProgressBar pb_loader_login;
    private Handler handler;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);
        handler = new Handler();
        sessionManager = new SessionManager(this);

        til_pseudo = (TextInputLayout) findViewById(R.id.til_pseudo_log);
        til_password = (TextInputLayout) findViewById(R.id.til_password_log);
        btn_send_login = (Button) findViewById(R.id.btn_send_login);
        pb_loader_login = (ProgressBar) findViewById(R.id.pb_loader_login);

        btn_send_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pseudo = til_pseudo.getEditText().getText().toString().trim();
                final String password = til_password.getEditText().getText().toString().trim();
                pb_loader_login.setVisibility(View.VISIBLE);
                if (pseudo.length() > 0 && password.length() > 0) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            request.login(pseudo, password, new MyRequest.LoginCallback() {
                                @Override
                                public void onSuccess(String id, String pseudo) {
                                    pb_loader_login.setVisibility(View.GONE);
                                    // sessionManager.insertUser(id, pseudo);
                                    Toast.makeText(Connexion.this, "Connexion effectuée !", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(String message) {
                                    pb_loader_login.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }, 1000);
                } else {
                    Toast.makeText(Connexion.this, "Veuillez remplir tous les champs !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}