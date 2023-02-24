package com.sushil.githubapp.Api;


import com.sushil.githubapp.RepoModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Api {
    @GET("repositories?q=java")
    Call<RepoModel> repo(
            @Header("Authorization") String auth
           // @Header("Accept") String Accept,
           // @Header("X-GitHub-Api-Version") String X-GitHub-Api-Version
    );


}


