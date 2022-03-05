package com.evolver.chiron.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.evolver.chiron.user.CovidTrackerAdapter.CountryAdapter;
import com.evolver.chiron.user.ModelCovidTracker.ModelCountriesAll.CountryInfo;
import com.evolver.chiron.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserCovidCountryTracker extends AppCompatActivity {

    RecyclerView recyclerView;
    List<CountryInfo> countryInfos;
    CountryAdapter countryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_covid_country_tracker);

        recyclerView = findViewById(R.id.recView);

        countryInfos = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apicall();

    }

    private void apicall() {

        String URL = "https://corona.lmao.ninja/v2/countries";
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String Cname = jsonObject.getString("country");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("countryInfo");
                        String flag = jsonObject1.getString("flag");

                        CountryInfo countryInfo = new CountryInfo(flag, Cname);
                        countryInfos.add(countryInfo);

                    }

                    countryAdapter = new CountryAdapter(UserCovidCountryTracker.this, countryInfos);
                    recyclerView.setAdapter(countryAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserCovidCountryTracker.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}