package com.vaibhav.newsup;

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

public class register extends AppCompatActivity {

    private EditText user,pass;
    private TextView login_redirect;
    private Button register;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login_redirect = findViewById(R.id.login_redirect);
        register = findViewById(R.id.register);

        register.setEnabled(false);

        mauth = FirebaseAuth.getInstance();

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
                    register.setEnabled(true);
                else
                    register.setEnabled(false);

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
                    register.setEnabled(true);
                else
                    register.setEnabled(false);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u,p;
                u = user.getText().toString();
                p = pass.getText().toString();
                final ProgressDialog progressDialog = new ProgressDialog(register.this);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                mauth.createUserWithEmailAndPassword(u,p)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(com.vaibhav.newsup.register.this,"Success",Toast.LENGTH_SHORT).show();
                                    Intent ii = new Intent(com.vaibhav.newsup.register.this,MainActivity.class);
                                    progressDialog.cancel();
                                    ii.putExtra("id",mauth.getCurrentUser().getEmail());
                                    startActivity(ii);
                                    finish();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(register.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        });
            }
        });

        login_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(com.vaibhav.newsup.register.this,login.class);
                startActivity(o);
                finish();
            }
        });
    }
}
