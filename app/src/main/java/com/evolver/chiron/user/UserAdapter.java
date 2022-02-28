package com.evolver.chiron.user;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull UserModel userModel) {
        holder.name.setText(userModel.getHospital());
        holder.bedCnt.setText(userModel.getBed());
        holder.price.setText(userModel.getPrice());
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserSelectedHospitalActivity.class);
                intent.putExtra("name",userModel.getHospital());
                intent.putExtra("bedCnt",userModel.getBed());
                intent.putExtra("price",userModel.getPrice());
                intent.putExtra("Addres", userModel.getAddres());
                intent.putExtra("Phone", userModel.getPhone());
                intent.putExtra("facilites",userModel.getFacilites());
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
        TextView facilites;
        TextView Addres;
        TextView Phone;


        FrameLayout frameLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.hospitalName);
            bedCnt = itemView.findViewById(R.id.bedCnt);
            price = itemView.findViewById(R.id.price);
            facilites = itemView.findViewById(R.id.facilites);
            Addres = itemView.findViewById(R.id.Addres);
            Phone = itemView.findViewById(R.id.Phone);
            frameLayout = itemView.findViewById(R.id.frameLayout);
        }
    }

}
