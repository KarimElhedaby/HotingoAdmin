package donation.solutions.hamza.com.hotingoadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.model.ServicesResponce;


public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.VH> {

    private int rowLayout;
    private Context context;
    private onServiceClickListner onServiceClickListner;
    private ArrayList<ServicesResponce> services;


    public static class VH extends RecyclerView.ViewHolder {

        ImageView service_IV;
        TextView serviceTitleTV;
        TextView serviceDescriptionTV;


        public VH(View v) {
            super(v);

            service_IV = v.findViewById(R.id.service_IV);
            serviceTitleTV = v.findViewById(R.id.serviceTitleTV);
            serviceDescriptionTV = v.findViewById(R.id.serviceDescriptionTV);

        }
    }

    public ServicesAdapter(ArrayList<ServicesResponce> services, int rowLayout, Context context, onServiceClickListner listner) {

        this.services = services;
        this.rowLayout = rowLayout;
        this.context = context;
        this.onServiceClickListner = listner;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new VH(view);

    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {


        if (services.get(position).getImg() != null) {
            Glide.with(context).load(services.get(position).getImg()).into(holder.service_IV);
        }

        holder.serviceTitleTV.setText(services.get(position).getName());
        holder.serviceDescriptionTV.setText(services.get(position).getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onServiceClickListner.onServiceClickListner(services.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public interface onServiceClickListner {
        void onServiceClickListner(String id);

    }


}

