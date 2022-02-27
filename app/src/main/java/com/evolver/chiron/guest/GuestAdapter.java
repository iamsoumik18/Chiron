package com.evolver.chiron.guest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evolver.chiron.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class GuestAdapter extends FirebaseRecyclerAdapter<GuestModel, GuestAdapter.ViewHolder> {

    public GuestAdapter(@NonNull FirebaseRecyclerOptions<GuestModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull GuestModel guestModel) {
        holder.name.setText(guestModel.getHospital());
        holder.bedCnt.setText(guestModel.getBed());
        holder.price.setText(guestModel.getPrice());
    }

    @NonNull
    @Override
    public GuestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_item_design,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView bedCnt;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hospitalName);
            bedCnt = itemView.findViewById(R.id.bedCnt);
            price = itemView.findViewById(R.id.price);
        }
    }

}
