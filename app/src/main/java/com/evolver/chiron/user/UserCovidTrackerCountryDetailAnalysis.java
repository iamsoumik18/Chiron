package com.evolver.chiron.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.evolver.chiron.R;
import com.evolver.chiron.databinding.ActivityUserCovidTrackerCountryDetailAnalysisBinding;
import com.evolver.chiron.databinding.ActivityUserSignInBinding;
import com.evolver.chiron.user.CovidTrackerAdapter.CountryAdapter;
import com.evolver.chiron.user.ModelCovidTracker.ModelCountriesAll.CountryInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserCovidTrackerCountryDetailAnalysis extends AppCompatActivity {

    TextView cases,todayCases,deaths,todayDeaths,recovered, active,critical,GlobalStat;
    ActivityUserCovidTrackerCountryDetailAnalysisBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserCovidTrackerCountryDetailAnalysisBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        cases = binding.Casesno;
        todayCases = binding.TodayCasesno;
        deaths = binding.Deathno;
        todayDeaths = binding.TodayDeathNo;
        recovered = binding.RecoveryNo;
        active = binding.Activeno;
        critical = binding.CriticalNo;

        GlobalStat = binding.GlobalStat;

        Intent intent = getIntent();
        String countryname = intent.getStringExtra("CountryName");
        GlobalStat.setText(countryname+"Stat");

        ApiCall(countryname);

    }

    private void ApiCall(String countryname) {

        String URL = "https://corona.lmao.ninja/v2/countries/"+countryname;
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    cases.setText(jsonObject.getString("cases"));
                    todayCases.setText(jsonObject.getString("todayCases"));
                    deaths.setText(jsonObject.getString("deaths"));
                    todayDeaths.setText(jsonObject.getString("todayDeaths"));
                    recovered.setText(jsonObject.getString("recovered"));
                    active.setText(jsonObject.getString("active"));
                    critical.setText(jsonObject.getString("critical"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserCovidTrackerCountryDetailAnalysis.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}