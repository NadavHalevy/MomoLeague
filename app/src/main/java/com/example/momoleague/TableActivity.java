package com.example.momoleague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableRow;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TableActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    ArrayList<TableRow> table = new ArrayList<>();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase database;
    Button back;
    private ArrayList<ListItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ListView listView = findViewById(R.id.list_view);
        back = findViewById(R.id.backBtn);

        items = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("emails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> childs = dataSnapshot.getChildren();
                for(DataSnapshot child : childs){
                    String uid = child.getKey();
                    String email = child.getValue(String.class);
                    FirebaseDatabase.getInstance().getReference().child("table").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            TableData row = dataSnapshot.getValue(TableData.class);
                            if(row == null)
                                return;
                            ListItem item = new ListItem(row.correct,row.games,email);
                            items.add(item);
                            Collections.sort(items, (o1, o2) -> o2.getPoints() - o1.getPoints());
                            listView.setAdapter(new MyArrayAdapter(getApplicationContext(),R.layout.list_item,items));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
