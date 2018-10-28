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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.model.ServicesResponce;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.VH> {

    private int rowLayout;
    private Context context;
    private onServiceClickListner onserviceClickListner;
    private ArrayList<ServicesResponce> services;
    private static final int ITEM_DELETE = 1;
    private static final int ITEM_EDIT = 2;

    public static class VH extends RecyclerView.ViewHolder {

        ImageView service_IV;
        TextView serviceTitleTV;
        TextView serviceDescriptionTV;
        ImageView ivOverFlow;
        ServicesResponce service;

        public VH(View v) {
            super(v);

            service_IV = v.findViewById(R.id.service_IV);
            serviceTitleTV = v.findViewById(R.id.serviceTitleTV);
            serviceDescriptionTV = v.findViewById(R.id.serviceDescriptionTV);
            ivOverFlow = v.findViewById(R.id.overflow_menuIV1);

        }
    }

    public ServicesAdapter(ArrayList<ServicesResponce> services, int rowLayout, Context context, onServiceClickListner listner) {

        this.services = services;
        this.rowLayout = rowLayout;
        this.context = context;
        this.onserviceClickListner = listner;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new VH(view);

    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {


        if (services.get(position).getImg() != null) {
            Glide.with(context).load(services.get(position).getImg()).into(holder.service_IV);
        }

        holder.service = services.get(position);

        holder.serviceTitleTV.setText(services.get(position).getName());
        holder.serviceDescriptionTV.setText(services.get(position).getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onserviceClickListner.onserviceClickListner(services.get(position));
            }
        });

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
                    showConfirmationDeleteDialog(holder.service);
                    return true;
                } else if (item.getItemId() == ITEM_EDIT) {
                    onserviceClickListner.editService(holder.service);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void showConfirmationDeleteDialog(final ServicesResponce servicesResponce) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Room")
                .setMessage("Are u sure ?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onserviceClickListner.deleteService(servicesResponce);
                    }
                })
                .show();
    }


    @Override
    public int getItemCount() {
        return services.size();
    }

    public interface onServiceClickListner {
        void onserviceClickListner(ServicesResponce service);

        void editService(ServicesResponce service);

        void deleteService(ServicesResponce service);

    }

}

