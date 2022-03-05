package com.evolver.chiron.user.NetworkCovidTracker;

import com.evolver.chiron.user.ModelCovidTracker.GlobalResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("all")
    Call<GlobalResponse> globalresponse();
}
