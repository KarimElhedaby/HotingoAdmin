package donation.solutions.hamza.com.hotingoadmin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.model.RoomModel;


public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.VH> {

    private int rowLayout;
    private Context context;
    private onRoomClickListener onBookClickListner;

    private ArrayList<RoomModel> rooms;
    private static final int ITEM_DELETE = 1;
    private static final int ITEM_EDIT = 2;

    public static class VH extends RecyclerView.ViewHolder {
        TextView roomNameTV;
        TextView roomNumberTV;
        ImageView ivOverFlow;
        RoomModel room;

        public VH(View v) {
            super(v);

            roomNameTV = v.findViewById(R.id.roomNameTV);
            roomNumberTV = v.findViewById(R.id.roomNumberTV);
            ivOverFlow = v.findViewById(R.id.overflow_menuIV);

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
    public void onBindViewHolder(final VH holder, final int position) {

        holder.room=rooms.get(position) ;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBookClickListner.onRoomClick(rooms.get(position));
            }
        });
        holder.roomNameTV.setText(rooms.get(position).getDesc().toString());
        holder.roomNumberTV.setText(rooms.get(position).getNumber().toString());


        holder.ivOverFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder);
            }
        });

    }

    private void showPopupMenu(final VH holder) {
        PopupMenu popupMenu = new PopupMenu(context, holder.ivOverFlow);
        popupMenu.getMenu().add(1, ITEM_DELETE, 1, "Delete");
        popupMenu.getMenu().add(1, ITEM_EDIT, 2, "Edit");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == ITEM_DELETE) {
                    showConfirmationDeleteDialog(holder.room);
                    return true;
                } else if (item.getItemId() == ITEM_EDIT) {
                    onBookClickListner.editRoom(holder.room);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void showConfirmationDeleteDialog(final RoomModel room) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Room")
                .setMessage("Are u sure ?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBookClickListner.deleteRoom(room);
                    }
                })
                .show();
    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public interface onRoomClickListener {
        void onRoomClick(RoomModel room);

        void editRoom(RoomModel room);

        void deleteRoom(RoomModel room);

    }
}

