package donation.solutions.hamza.com.hotingoadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import donation.solutions.hamza.com.hotingoadmin.R;
import donation.solutions.hamza.com.hotingoadmin.model.NotificationModel;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.VH> {

    private int rowLayout;
    private Context context;
    private onNotificationClickListener onNotificationClickListner;
    private ArrayList<NotificationModel> notifications;


    public static class VH extends RecyclerView.ViewHolder {
        TextView notificationText;
        TextView notificationDate;

        public VH(View v) {
            super(v);

            notificationText = v.findViewById(R.id.notificationTV);
            notificationDate = v.findViewById(R.id.notificationDateTV);

        }
    }

    public NotificationAdapter(int rowLayout, Context context, ArrayList<NotificationModel> notficts, onNotificationClickListener listner) {

        this.rowLayout = rowLayout;
        this.context = context;
        this.onNotificationClickListner = listner;
        this.notifications = notficts;

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
                onNotificationClickListner.onNotificationClick(notifications.get(position));
            }
        });
        holder.notificationText.setText(notifications.get(position).getText().toString());
        holder.notificationDate.setText(notifications.get(position).getCreationDate().subSequence(0,10));

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public interface onNotificationClickListener {
        void onNotificationClick(NotificationModel notification);

    }
}

