package donation.solutions.hamza.com.hotingoadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.model.ServiceOrderModel;


public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.VH> {

    private int rowLayout;
    private Context context;
    private onRequestClickListner onRequestClickListner;
    private ArrayList<ServiceOrderModel> serviceOrders;


    public static class VH extends RecyclerView.ViewHolder {

        TextView bookingRequestTV;
        TextView requestCustomerNameTV;

        public VH(View v) {
            super(v);
            bookingRequestTV = v.findViewById(R.id.bookingRequestTV);
            requestCustomerNameTV = v.findViewById(R.id.requestCustomerNameTV);

        }
    }

    public RequestAdapter(int rowLayout, Context context, ArrayList<ServiceOrderModel> serviceOrderModel, onRequestClickListner listner) {

        this.rowLayout = rowLayout;
        this.context = context;
        this.onRequestClickListner = listner;
        this.serviceOrders = serviceOrderModel;

    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRequestClickListner.onRequestClickListner(serviceOrders.get(position));
            }
        });
        if (!serviceOrders.isEmpty()) {
            holder.bookingRequestTV.setText(serviceOrders.get(position).getService().getName().toString());
            holder.requestCustomerNameTV.setText(serviceOrders.get(position).getUserServicesOrded().getName().toString());
        }
    }

    @Override
    public int getItemCount() {
        return serviceOrders.size();
    }

    public interface onRequestClickListner {
        void onRequestClickListner(ServiceOrderModel serviceOrderModel);

    }


}

