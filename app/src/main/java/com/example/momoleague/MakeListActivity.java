package com.example.momoleague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MakeListActivity extends AppCompatActivity {

    Button backBtn;

    TextView[] lines = new TextView[8];

    boolean delete = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_list);
        int[] linesID = {R.id.line1,R.id.line2,R.id.line3,R.id.line4,R.id.line5,R.id.line6,R.id.line7,R.id.line8};
        for(int i = 0 ; i < lines.length ; i++){
            lines[i] = findViewById(linesID[i]);
            final int index = i;
            FirebaseDatabase.getInstance().getReference().child("matches").child(Integer.toString(i+1)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lines[index].setText(String.valueOf(dataSnapshot.getValue()));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        for(TextView v : lines)
            v.setOnClickListener(lineClick);

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MakeListActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private View.OnClickListener lineClick = v->{
        TextView textView = (TextView)v;
        Dialog dialog = MyApp.getInputDialog(this,input -> {
            textView.setText(input);
            for(int i = 0 ; i < lines.length ; i++) {
                if(lines[i].getId() == textView.getId()) {
                    FirebaseDatabase.getInstance().getReference().child("matches")
                            .child(Integer.toString(i+1)).setValue(textView.getText().toString());
                    break;
                }
            }
        });
        dialog.show();
        if(delete) {
            FirebaseDatabase.getInstance().getReference().child("guesses").setValue("");
            FirebaseDatabase.getInstance().getReference().child("correct_guesses").setValue("");
            delete = false;
        }
    };
}
