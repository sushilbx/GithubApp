package com.sushil.githubapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private Button loginBtn;
    private EditText githubEdit;
    SessionManager sessionManager;
    private FirebaseAuth auth;
    private OAuthProvider.Builder provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(MainActivity.this);
        loginBtn = findViewById(R.id.github_login_btn);
        githubEdit = findViewById(R.id.githubId);
        provider = OAuthProvider.newBuilder("github.com");
        auth = FirebaseAuth.getInstance();
        provider.addCustomParameter("login", githubEdit.getText().toString());
        ArrayList<String> scopes = new ArrayList<String>();
        scopes.add("user:email");
        provider.setScopes(scopes);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(githubEdit.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter your github id", Toast.LENGTH_LONG).show();
                } else {
                    signInWithGithubProvider();
                }
            }
        });

    }

    private void signInWithGithubProvider() {
        Task<AuthResult> pendingResultTask = auth.getPendingAuthResult();
        if (pendingResultTask != null) {
            pendingResultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(MainActivity.this, "User exist", Toast.LENGTH_LONG).show();
                }
            });
            pendingResultTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Error : $it", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            auth.startActivityForSignInWithProvider(MainActivity.this, provider.build())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            firebaseUser = auth.getCurrentUser();

                            String accessToken = ((OAuthCredential)authResult.getCredential()).getAccessToken();
                            sessionManager.createLoginSession(accessToken);
                            String userName= firebaseUser.getDisplayName();
                            sessionManager.createName(userName);


                            Log.e("sushil", "githubUserName" + firebaseUser.getDisplayName() + "token: "+accessToken);
                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                           Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.putExtra("githubUserName", firebaseUser.getDisplayName());
                            intent.putExtra("token", accessToken);
                           startActivity(intent);




                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle failure.
                            Toast.makeText(MainActivity.this, "Error : "+e, Toast.LENGTH_LONG).show();
                        }
                    });

        }

    }
}
