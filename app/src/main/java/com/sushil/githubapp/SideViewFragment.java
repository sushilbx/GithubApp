package com.sushil.githubapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.sushil.githubapp.databinding.FragmentSideViewBinding;

public class SideViewFragment extends Fragment {
    FragmentSideViewBinding b;
    SessionManager sessionManager;

    public SideViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentSideViewBinding.inflate(inflater, container, false);
        sessionManager = new SessionManager(getContext());



        b.mbLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                sessionManager.logoutUser();

            }
        });

        return b.getRoot();
    }

}