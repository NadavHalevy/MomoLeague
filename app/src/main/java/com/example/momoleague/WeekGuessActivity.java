package com.example.momoleague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WeekGuessActivity extends AppCompatActivity {

    private Button back;
    private TextView[] lines = new TextView[8];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_guess);
        int[] linesID = {R.id.line1,R.id.line2,R.id.line3,R.id.line4,R.id.line5,R.id.line6,R.id.line7,R.id.line8};
        for(int i = 0 ; i < lines.length ; i++){
            lines[i] = findViewById(linesID[i]);
            lines[i].setOnClickListener(lineClick);
            final int index = i;
            FirebaseDatabase.getInstance().getReference().child("matches").child(Integer.toString(i+1)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lines[index].setText(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            FirebaseDatabase.getInstance().getReference().child("guesses").child(MyApp.getUid()).child(Integer.toString(i+1)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        lines[index].setClickable(false);
                        lines[index].setTextColor(Color.GREEN);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        back = findViewById(R.id.backBtn);

        back.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private View.OnClickListener lineClick = v->{
        TextView textView = (TextView)v;
        Dialog dialog = MyApp.getGuessDialog(this,guess -> {
            for(int i = 0 ; i < 8 ; i ++){
                if(lines[i].equals(textView)){
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                            .child("guesses").child(MyApp.getUid()).child(Integer.toString(i+1));
                    ref.setValue(guess.name());
                    break;
                }
            }
        });
        dialog.show();
        textView.setClickable(false);
        textView.setTextColor(Color.GREEN);
    };
}
