package com.example.hackermail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TemplateListAdapter extends RecyclerView.Adapter<TemplateListAdapter.TemplateViewHolder> {

    private final LayoutInflater mInflater;
    private List<Template> mTemplates; // Cached copy of words
    private static ClickListener clickListener;

    TemplateListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public TemplateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TemplateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TemplateViewHolder holder, int position) {
        if (mTemplates != null) {
            Template current = mTemplates.get(position);

            holder.content.setText(current.getContent());
        } else {
            // Covers the case of data not being ready yet.
            // holder.wordItemView.setText("No Word");

            holder.content.setText(R.string.nothing);
        }


    }

    /**
     *     Associate a list of Templates with this adapter
     */

    void setTemplates(List<Template> Templates) {
        mTemplates = Templates;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTemplates != null)
            return mTemplates.size();
        else return 0;
    }

    public Template getTemplateAtPosition(int position) {
        return mTemplates.get(position);
    }


    class TemplateViewHolder extends RecyclerView.ViewHolder {

        private final TextView content;

        private TemplateViewHolder(View itemView) {
            super(itemView);

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
        TemplateListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}
