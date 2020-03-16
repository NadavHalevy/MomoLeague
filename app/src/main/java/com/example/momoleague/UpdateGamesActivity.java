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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UpdateGamesActivity extends AppCompatActivity {

    Button backBtn;
    private TextView[] lines = new TextView[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_guess);

        backBtn = findViewById(R.id.backBtn);
        Button doneButton = findViewById(R.id.done_button);
        doneButton.setVisibility(View.VISIBLE);

        doneButton.setOnClickListener(v->{
            FirebaseDatabase.getInstance().getReference().child("correct_guesses").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<MyApp.Guess> correctGuesses = dataSnapshot.getValue(new GenericTypeIndicator<List< MyApp.Guess >>(){});
                    if(correctGuesses == null)
                        return;
                    FirebaseDatabase.getInstance().getReference().child("guesses").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> childs = dataSnapshot.getChildren();
                            for(DataSnapshot child : childs){
                                String uid = child.getKey();
                                List<MyApp.Guess> userGuesses = child.getValue(new GenericTypeIndicator<List<MyApp.Guess>>() {});
                                if(userGuesses == null || uid == null)
                                    return;
                                DatabaseReference tableRef = FirebaseDatabase.getInstance().getReference().child("table").child(uid);
                                int games = 0, points = 0;
                                for(int i = 0 ; i < userGuesses.size() ; i++){
                                    if(userGuesses.get(i) == null || correctGuesses.get(i) == null)
                                        continue;
                                    games++;
                                    if(userGuesses.get(i) == correctGuesses.get(i))
                                        points++;
                                }
                                final int totalGames = games, totalPoints = points;
                                tableRef.child("games").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists())
                                            dataSnapshot.getRef().setValue(dataSnapshot.getValue(Integer.class)+totalGames);
                                        else
                                            dataSnapshot.getRef().setValue(totalGames);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                tableRef.child("correct").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists())
                                            dataSnapshot.getRef().setValue(dataSnapshot.getValue(Integer.class)+totalPoints);
                                        else
                                            dataSnapshot.getRef().setValue(totalPoints);
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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            finish();
        });

        int[] linesID = {R.id.line1,R.id.line2,R.id.line3,R.id.line4,R.id.line5,R.id.line6,R.id.line7,R.id.line8};
        for(int i = 0 ; i < lines.length ; i++) {
            lines[i] = findViewById(linesID[i]);
            lines[i].setOnClickListener(lineClick);
            final int index = i;
            FirebaseDatabase.getInstance().getReference().child("matches").child(Integer.toString(i + 1)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lines[index].setText(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateGamesActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private View.OnClickListener lineClick = v->{
        TextView textView = (TextView)v;
        Dialog dialog = MyApp.getGuessDialog(this, guess -> {
            for(int i = 0 ; i < 8 ; i ++){
                if(lines[i].equals(textView)){
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                            .child("correct_guesses").child(Integer.toString(i+1));
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
