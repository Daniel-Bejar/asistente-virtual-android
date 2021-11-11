package com.example.chatbot.actividades.notas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbot.R;

public class ItemAdapter extends ListAdapter<Item, ItemAdapter.ItemAdaptorHolder> {
    private MyOnItemClickListener myOnItemClickListener;

    protected ItemAdapter() {
        super(DIFF_UTIL_ITEM_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Item> DIFF_UTIL_ITEM_CALLBACK
            = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getItemTitle().equals(newItem.getItemTitle())
                    && oldItem.getItemDescription().equals(newItem.getItemDescription())
                    && oldItem.getItemTime().equals(newItem.getItemTime());
        }
    };

    @NonNull
    @Override
    public ItemAdaptorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ItemAdaptorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemAdaptorHolder holder, final int position) {
        final Item item = getItem(position);
        holder.textViewTitle.setText(item.getItemTitle());
        holder.textViewDescription.setText(item.getItemDescription());
        holder.textViewTime.setText(item.getItemTime());
    }

    class ItemAdaptorHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDescription;
        private final TextView textViewTime;

        public ItemAdaptorHolder(@NonNull final View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_item_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewTime = itemView.findViewById(R.id.text_view_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (myOnItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        myOnItemClickListener.myOnItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public Item getItemAt(int position) {
        return getItem(position);
    }

    public interface MyOnItemClickListener {
        void myOnItemClick(Item item);
    }

    public void mySetOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }
}
