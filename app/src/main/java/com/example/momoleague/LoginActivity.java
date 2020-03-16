package com.example.momoleague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    public EditText emailId, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.emailId);
        password = findViewById(R.id.passsword);
        btnSignIn = findViewById(R.id.button);
        tvSignUp = findViewById(R.id.textView);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null){
                    MyApp.setUid(mFirebaseUser.getUid());
                    FirebaseDatabase.getInstance().getReference().child("admins").child(MyApp.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean isAdmin = false;
                            if(dataSnapshot.exists()){
                                Boolean b = (Boolean)dataSnapshot.getValue();
                                if(b != null)
                                    isAdmin = b;
                            }
                            MyApp.setIsAdmin(isAdmin);
                            FirebaseDatabase.getInstance().getReference().child("emails").child(MyApp.getUid()).setValue(mFirebaseUser.getEmail());
                            Toast.makeText(LoginActivity.this,"You are logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this,"Please Login", Toast.LENGTH_SHORT).show();
                }

            }
        };


        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter email id");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
                            }else {
                                FirebaseDatabase.getInstance().getReference().child("admins").child(MyApp.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        boolean isAdmin = false;
                                        if(dataSnapshot.exists()){
                                            Boolean b = (Boolean)dataSnapshot.getValue();
                                            if(b != null)
                                                isAdmin = b;
                                        }
                                        MyApp.setIsAdmin(isAdmin);
                                        FirebaseDatabase.getInstance().getReference().child("emails").child(MyApp.getUid()).setValue(email);
                                        Toast.makeText(LoginActivity.this,"You are logged in", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    });
                } else
                    Toast.makeText(LoginActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                Intent intSignUp = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intSignUp);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener((mAuthStateListener));
    }
}
