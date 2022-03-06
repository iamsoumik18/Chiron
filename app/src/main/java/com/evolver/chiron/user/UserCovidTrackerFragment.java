package com.evolver.chiron.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.evolver.chiron.user.ModelCovidTracker.GlobalResponse;
import com.evolver.chiron.user.NetworkCovidTracker.ApiClientPrivate;
import com.evolver.chiron.user.NetworkCovidTracker.ApiInterface;
import com.evolver.chiron.databinding.FragmentUserCovidTrackerBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCovidTrackerFragment extends Fragment {
    TextView cases,todayCases,deaths,todayDeaths,recovered, active,critical,affectedCountries;
    Button CountryTraker;


    FragmentUserCovidTrackerBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public UserCovidTrackerFragment() {

    }

    public static UserCovidTrackerFragment newInstance(String param1, String param2) {
        UserCovidTrackerFragment fragment = new UserCovidTrackerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserCovidTrackerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cases = binding.Casesno;
        todayCases = binding.TodayCasesno;
        deaths = binding.Deathno;
        todayDeaths = binding.TodayDeathNo;
        recovered = binding.RecoveryNo;
        active = binding.Activeno;
        critical = binding.CriticalNo;
        affectedCountries = binding.AffectedCountryNo;
        CountryTraker = binding.countryTraker;

        apiCall();
        CountryTraker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), UserCovidCountryTracker.class);
                startActivity(i);
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void apiCall() {
        ApiInterface apiInterface = null;
        apiInterface = ApiClientPrivate.getApiClient().create(ApiInterface.class);

        Call<GlobalResponse> call = apiInterface.globalresponse();

        call.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {
                if(response.isSuccessful()){
                    String stCase = String.valueOf(response.body().getCases());
                    String sttodayCase = String.valueOf(response.body().getTodayCases());
                    String stdeath = String.valueOf(response.body().getDeaths());
                    String sttodaydeath = String.valueOf(response.body().getTodayDeaths());
                    String strecovered= String.valueOf(response.body().getRecovered());
                    String stActive = String.valueOf(response.body().getActive());
                    String stCritical = String.valueOf(response.body().getCritical());
                    String AffectedCountry = String.valueOf(response.body().getAffectedCountries());

                    cases.setText(stCase);
                    todayCases.setText(sttodayCase);
                    deaths.setText(stdeath);
                    todayDeaths.setText(sttodaydeath);
                    recovered.setText(strecovered);
                    active.setText(stActive);
                    critical.setText(stCritical);
                    affectedCountries.setText(AffectedCountry);

                }
            }

            @Override
            public void onFailure(Call<GlobalResponse> call, Throwable t) {
                Toast.makeText(getContext(),"There is something wrong. Please try again later",Toast.LENGTH_SHORT).show();
            }
        });
        binding.progressBar.setVisibility(View.INVISIBLE);
    }
}