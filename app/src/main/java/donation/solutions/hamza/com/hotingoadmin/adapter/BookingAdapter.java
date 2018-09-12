package donation.solutions.hamza.com.hotingoadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.VH> {

    private int rowLayout;
    private Context context;
    private onBookClickListner onBookClickListner;


    public static class VH extends RecyclerView.ViewHolder {



        public VH(View v) {
            super(v);


        }
    }

    public BookingAdapter(int rowLayout, Context context, onBookClickListner listner) {

        this.rowLayout = rowLayout;
        this.context = context;
        this.onBookClickListner = listner;

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
                onBookClickListner.onBookClickListner();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public interface onBookClickListner {
        void onBookClickListner();

    }


}

