package donation.solutions.hamza.com.hotingoadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.model.RoomModel;


public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.VH> {

    private int rowLayout;
    private Context context;
    private onRoomClickListener onBookClickListner;
    private ArrayList<RoomModel> rooms;


    public static class VH extends RecyclerView.ViewHolder {
        TextView roomNameTV;
        TextView roomNumberTV;

        public VH(View v) {
            super(v);

            roomNameTV = v.findViewById(R.id.roomNameTV);
            roomNumberTV = v.findViewById(R.id.roomNumberTV);

        }
    }

    public RoomAdapter(int rowLayout, Context context, ArrayList<RoomModel> roomModels, onRoomClickListener listner) {

        this.rowLayout = rowLayout;
        this.context = context;
        this.onBookClickListner = listner;
        this.rooms = roomModels;

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
                onBookClickListner.onRoomClick(rooms.get(position));
            }
        });
        holder.roomNameTV.setText(rooms.get(position).getDesc().toString());
        holder.roomNumberTV.setText(rooms.get(position).getNumber().toString());
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public interface onRoomClickListener {
        void onRoomClick(RoomModel room);

    }
}

