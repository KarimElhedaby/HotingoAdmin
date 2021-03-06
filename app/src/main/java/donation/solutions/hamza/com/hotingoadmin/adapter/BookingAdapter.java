package donation.solutions.hamza.com.hotingoadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.model.RoomOrderModel;


public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.VH> {

    private int rowLayout;
    private Context context;
    private onBookClickListner onBookClickListner;
    private ArrayList<RoomOrderModel> roomOrders;


    public static class VH extends RecyclerView.ViewHolder {
        TextView customerNameTV;
        TextView roomNumberTV;

        public VH(View v) {
            super(v);

            customerNameTV = v.findViewById(R.id.bookingCustomerNameTV);
            roomNumberTV = v.findViewById(R.id.bookingCustomerRoomTV);

        }
    }

    public BookingAdapter(int rowLayout, Context context, ArrayList<RoomOrderModel> roomsOrders, onBookClickListner listner) {

        this.rowLayout = rowLayout;
        this.context = context;
        this.onBookClickListner = listner;
        this.roomOrders = roomsOrders;

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
                onBookClickListner.onBookClickListner(roomOrders.get(position));
            }
        });
        holder.customerNameTV.setText(roomOrders.get(position).getUserRoomorder().getName().toString());
        holder.roomNumberTV.setText(roomOrders.get(position).getRoom().getNumber().toString());
    }

    @Override
    public int getItemCount() {
        return roomOrders.size();
    }

    public interface onBookClickListner {
        void onBookClickListner(RoomOrderModel roomOrderModel);

    }
}

