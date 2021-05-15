package com.vaibhav.newsup.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vaibhav.newsup.MainActivity;
import com.vaibhav.newsup.R;

public class login extends AppCompatActivity {

    private EditText user,pass;
    private TextView register_redirect;
    private Button login;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        register_redirect = findViewById(R.id.register_redirect);
        login = findViewById(R.id.login);

        mauth = FirebaseAuth.getInstance();
        final FirebaseUser user1 = mauth.getCurrentUser();
        if(user1!=null){

            Intent i = new Intent(com.vaibhav.newsup.screens.login.this, MainActivity.class);
            i.putExtra("id",user1.getEmail());
            startActivity(i);
            finish();

        }

        login.setEnabled(false);

        user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!user.getText().toString().equals(null))
                    login.setEnabled(true);
                else
                    login.setEnabled(false);

            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!pass.getText().toString().equals(null))
                    login.setEnabled(true);
                else
                    login.setEnabled(false);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u,p;
                u = user.getText().toString();
                p = pass.getText().toString();
                final ProgressDialog progressDialog = new ProgressDialog(login.this);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                mauth.signInWithEmailAndPassword(u,p)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(com.vaibhav.newsup.screens.login.this,"Success",Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                    Intent ii = new Intent(com.vaibhav.newsup.screens.login.this,MainActivity.class);
                                    ii.putExtra("id",mauth.getCurrentUser().getEmail());
                                    startActivity(ii);
                                    finish();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        });
            }
        });

        register_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(com.vaibhav.newsup.screens.login.this, register.class);
                startActivity(o);
                finish();
            }
        });
    }
}
