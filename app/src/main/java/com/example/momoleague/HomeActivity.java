 package com.example.momoleague;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

 public class HomeActivity extends AppCompatActivity {

    Button btnLogOut, tableBtn, weekGuessBtn, listGamesBtn, makeListBtn, updateGuesses;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        WebView webView = findViewById(R.id.home_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new SSLTolerentWebViewClient());
        webView.loadUrl("https://www.sport5.co.il");
        tableBtn = findViewById(R.id.tableBut);
        weekGuessBtn = findViewById(R.id.weekGuess);
        listGamesBtn = findViewById(R.id.listGames);
        makeListBtn = findViewById(R.id.makeList);
        btnLogOut = findViewById(R.id.logOut);
        updateGuesses = findViewById(R.id.updateGuesses);

        LinearLayout adminButtons = findViewById(R.id.admin_buttons);
        adminButtons.setVisibility(MyApp.getIsAdmin() ? View.VISIBLE : View.INVISIBLE);

        tableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TableActivity.class);
                startActivity(intent);
                finish();
            }
        });

        updateGuesses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UpdateGamesActivity.class);
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
                Intent intent = new Intent(HomeActivity.this, UpdateGamesActivity.class);
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

    }

     class SSLTolerentWebViewClient extends WebViewClient {
         @Override
         public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
             if (error.toString().equals("piglet"))
                 handler.cancel();
             else
                 handler.proceed(); // Ignore SSL certificate errors
         }
     }
}
