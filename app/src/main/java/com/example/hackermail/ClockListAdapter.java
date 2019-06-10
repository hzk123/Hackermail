package com.example.hackermail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ClockListAdapter extends RecyclerView.Adapter<ClockListAdapter.ClockViewHolder> {

    private final LayoutInflater mInflater;
    private List<Clock> mClocks; // Cached copy of words
    private static ClickListener clickListener;

    ClockListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ClockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ClockViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClockViewHolder holder, int position) {
        if (mClocks != null) {
            Clock current = mClocks.get(position);
            holder.toemail.setText(current.getToemail());
            holder.time.setText(current.getTime());
            holder.content.setText(current.getContent());
        } else {
            // Covers the case of data not being ready yet.
            // holder.wordItemView.setText("No Word");
            holder.toemail.setText(R.string.nothing);
            holder.time.setText(R.string.nothing);
            holder.content.setText(R.string.nothing);
        }


    }

    /**
     *     Associate a list of clocks with this adapter
     */

    void setClocks(List<Clock> clocks) {
        mClocks = clocks;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mClocks != null)
            return mClocks.size();
        else return 0;
    }

    public Clock getClockAtPosition(int position) {
        return mClocks.get(position);
    }


    class ClockViewHolder extends RecyclerView.ViewHolder {
        private final TextView toemail;
        private final TextView time;
        private final TextView content;

        private ClockViewHolder(View itemView) {
            super(itemView);
            toemail = itemView.findViewById(R.id.textViewEmail);
            time = itemView.findViewById(R.id.textViewTime);
            content = itemView.findViewById(R.id.textViewContent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ClockListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
