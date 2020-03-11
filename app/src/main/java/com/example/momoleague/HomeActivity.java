 package com.example.momoleague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

 public class HomeActivity extends AppCompatActivity {

    Button btnLogOut, tableBtn, lastGameBtn, weekGuessBtn, listGamesBtn, makeListBtn;
    FirebaseAuth mFirebaseAuth;
    ArrayList<TableRow> table = new ArrayList<>();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tableBtn = findViewById(R.id.tableBut);
        lastGameBtn = findViewById(R.id.lastGame);
        weekGuessBtn = findViewById(R.id.weekGuess);
        listGamesBtn = findViewById(R.id.listGames);
        makeListBtn = findViewById(R.id.makeList);
        btnLogOut = findViewById(R.id.logOut);

        tableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TableActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lastGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LastGameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        weekGuessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WeekGuessActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listGamesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListGamesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        makeListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MakeListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intToMain);
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("table");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    TableRow row = ds.getValue(TableRow.class);
                    if(row == null)
                        continue;
                    table.add(row);
                }
                Collections.sort(table);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
