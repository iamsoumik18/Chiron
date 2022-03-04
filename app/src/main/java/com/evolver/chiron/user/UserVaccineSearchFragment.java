package com.evolver.chiron.user;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.evolver.chiron.R;
import com.evolver.chiron.databinding.FragmentUserVaccineSearchBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class UserVaccineSearchFragment extends Fragment {

    FragmentUserVaccineSearchBinding binding;

    List<UserVaccineModel> centerList;
    UserVaccineAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public UserVaccineSearchFragment() {

    }

    public static UserVaccineSearchFragment newInstance(String param1, String param2) {
        UserVaccineSearchFragment fragment = new UserVaccineSearchFragment();
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
        binding = FragmentUserVaccineSearchBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        centerList = new ArrayList<>();

        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pincode = binding.pincode.getText().toString();
                closeKeyboard();
                if(pincode.length()==6){
                    centerList.clear();
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dpd = new DatePickerDialog(getActivity(), (datePicker, year1, monthOfYear, dayOfMonth) -> {
                        String dateStr = ""+dayOfMonth+"-"+(monthOfYear+1)+"-"+year1;
                        getAppointment(pincode,dateStr);
                    },year,month,day);
                    dpd.show();
                }
            }
        });
    }

    private void getAppointment(String pincode, String date){
        String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode="+pincode+"&date="+date;
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray centerArray = response.getJSONArray("centers");
                if(centerArray.length()==0){
                    Toast.makeText(getContext(),"No center available",Toast.LENGTH_SHORT).show();
                }
                for(int i=0; i<centerArray.length(); i++){
                    JSONObject centerObj = centerArray.getJSONObject(i);
                    String centerName = centerObj.getString("name");
                    String centerAddress = centerObj.getString("address");
                    String centerFromTime = centerObj.getString("from");
                    String centerToTime = centerObj.getString("to");
                    String feeType = centerObj.getString("fee_type");

                    JSONObject sessionObj = centerObj.getJSONArray("sessions").getJSONObject(0);
                    String ageLimit = String.valueOf(sessionObj.getInt("min_age_limit"));
                    String vaccineName = sessionObj.getString("vaccine");
                    String availableCapacity = String.valueOf(sessionObj.getInt("available_capacity"));

                    UserVaccineModel center = new UserVaccineModel(
                            centerName,
                            centerAddress,
                            centerFromTime,
                            centerToTime,
                            feeType,
                            ageLimit,
                            vaccineName,
                            availableCapacity
                    );

                    centerList.add(center);
                }
                adapter = new UserVaccineAdapter(centerList);
                binding.centerRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.centerRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        queue.add(jsonObjectRequest);
    }

    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

}