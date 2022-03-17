package com.evolver.chiron.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evolver.chiron.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class UserAdapter extends FirebaseRecyclerAdapter<UserModel, UserAdapter.ViewHolder> {

    Context context;

    public UserAdapter(@NonNull FirebaseRecyclerOptions<UserModel> options, Context context) {
        super(options);
        this.context = context;
    }

    public UserAdapter(FirebaseRecyclerOptions<UserModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull UserModel userModel) {
        holder.name.setText(userModel.getHospital());
        holder.bedCnt.setText(userModel.getBedCount());
        holder.price.setText("Rs "+userModel.getPricePerBed());
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserSelectedHospitalActivity.class);
                intent.putExtra("name",userModel.getHospital());
                intent.putExtra("bedCnt",userModel.getBedCount());
                intent.putExtra("price",userModel.getPricePerBed());
                intent.putExtra("address", userModel.getAddress());
                intent.putExtra("phoneNo", userModel.getPhoneNo());
                intent.putExtra("facilities",userModel.getFacilities());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_design,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView bedCnt;
        TextView price;
        TextView facilities;
        TextView address;
        TextView phone;


        FrameLayout frameLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.hospitalName);
            bedCnt = itemView.findViewById(R.id.bedCnt);
            price = itemView.findViewById(R.id.price);
            facilities = itemView.findViewById(R.id.facilities);
            address = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phoneNo);
            frameLayout = itemView.findViewById(R.id.frameLayout);
        }
    }

}
