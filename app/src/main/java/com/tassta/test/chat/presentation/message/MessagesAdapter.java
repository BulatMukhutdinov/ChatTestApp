package com.tassta.test.chat.presentation.message;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tassta.test.chat.Message;
import com.tassta.test.chat.R;
import com.tassta.test.chat.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.tassta.test.chat.Utils.imageToBitmap;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ChatItem> {
    private List<Message> messages;

    MessagesAdapter(List<Message> messages) {
        this.messages = new ArrayList<>(messages);
    }

    @Override
    public ChatItem onCreateViewHolder(ViewGroup parent,
                                       int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new ChatItem(v);
    }

    @Override
    public void onBindViewHolder(ChatItem holder, int position) {
        holder.icon.setImageBitmap(imageToBitmap(messages.get(position).getSender().getIcon()));
        holder.name.setText(messages.get(position).getSender().getName());
        holder.time.setText(Utils.formatDate(messages.get(position).getDate()));
        holder.message.setText(messages.get(position).getText());
    }

    void addMessage(Message message) {
        messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ChatItem extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        TextView time;
        TextView message;

        ChatItem(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.chat_icon);
            name = itemView.findViewById(R.id.chat_name);
            time = itemView.findViewById(R.id.chat_time);
            message = itemView.findViewById(R.id.chat_message);
        }
    }
}