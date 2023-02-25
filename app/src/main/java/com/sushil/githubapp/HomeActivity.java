package com.sushil.githubapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.sushil.githubapp.Api.RetrofitClient;
import com.sushil.githubapp.databinding.ActivityHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding b;
    DrawerLayout drawerLayout;
    SessionManager sessionManager;
    String accessToken;
    TextView username;
    String userName;
    MaterialButton mbLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);
        sessionManager = new SessionManager(HomeActivity.this);
        accessToken = sessionManager.getLoginSession();
        userName = sessionManager.getName();
        username = findViewById(R.id.username);
        mbLogout = findViewById(R.id.mbLogout);
        drawerLayout =findViewById(R.id.drawerLayout);

        username.setText(userName);
        b.mtHomeNotification.setNavigationOnClickListener(new View.OnClickListener() {
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
        Log.e("sushil", accessToken);
        b.rlLoading.setVisibility(View.VISIBLE);
        Call<RepoModel> call = RetrofitClient.getInstance().getApi().repo("Bearer " + accessToken);
        call.enqueue(new Callback<RepoModel>() {
            @Override
            public void onResponse(Call<RepoModel> call, Response<RepoModel> response) {
                b.rlLoading.setVisibility(View.GONE);
                Log.e("other", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {

                    RepoAdapter repoAdapter = new RepoAdapter(response.body().items, HomeActivity.this);
                    b.rvRepo.setAdapter(repoAdapter);

                }
            }

            @Override
            public void onFailure(Call<RepoModel> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                b.rlLoading.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }
}