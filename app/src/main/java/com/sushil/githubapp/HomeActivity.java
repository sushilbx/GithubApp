package com.sushil.githubapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.OAuthProvider;
import com.google.gson.Gson;
import com.sushil.githubapp.Api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    MaterialToolbar mtHomeNotification;
    RecyclerView rvRepo;
    String name;
    DrawerLayout drawerLayout;
    SessionManager sessionManager;
    OAuthProvider.Builder provider;
  //  OAuthCredential firebaseUser;
    TextView username;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = new SessionManager(HomeActivity.this);
        //firebaseUser = sessionManager.getLoginSession();
        Intent intent = getIntent();
        name = intent.getStringExtra("githubUserName");
        token = intent.getStringExtra("token");
        username = findViewById(R.id.username);
        rvRepo = findViewById(R.id.rvRepo);
        mtHomeNotification = findViewById(R.id.mtHomeNotification);
        drawerLayout = findViewById(R.id.drawerLayout);
        username.setText(name);
        mtHomeNotification.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!drawerLayout.isOpen()) {
                    drawerLayout.openDrawer(GravityCompat.START);
                } else {
                    drawerLayout.closeDrawers();
                }
            }
        });
        trendingRepo();

    }

    private void trendingRepo() {
      //  Log.e("", authResult.getCredential().toString());
        Call<RepoModel> call = RetrofitClient.getInstance().getApi().repo("Bearer "+token);
        call.enqueue(new Callback<RepoModel>() {
            @Override
            public void onResponse(Call<RepoModel> call, Response<RepoModel> response) {
                Log.e("other", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {

                    RepoAdapter repoAdapter = new RepoAdapter(response.body().items, HomeActivity.this);
                    rvRepo.setAdapter(repoAdapter);

                }
            }

            @Override
            public void onFailure(Call<RepoModel> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}