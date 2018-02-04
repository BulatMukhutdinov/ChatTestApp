package com.tassta.test.chat.presentation.chat;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tassta.test.chat.Message;
import com.tassta.test.chat.MessageHistory;
import com.tassta.test.chat.MessageHistoryModel;
import com.tassta.test.chat.R;
import com.tassta.test.chat.User;
import com.tassta.test.chat.presentation.message.MessagesFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.tassta.test.chat.Utils.imageToBitmap;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatItem> {
    private List<ChatRow> chatRows;
    private final ChatsFragment chatsFragment;
    private final Context context;
    private final MessageHistoryModel historyModel;

    ChatsAdapter(List<ChatRow> chatRows, ChatsFragment chatsFragment,
                 Context context, MessageHistoryModel historyModel) {
        this.chatRows = new ArrayList<>(chatRows);
        this.chatsFragment = chatsFragment;
        this.context = context;
        this.historyModel = historyModel;
    }

    @Override
    public ChatItem onCreateViewHolder(ViewGroup parent,
                                       int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item, parent, false);
        return new ChatItem(v, this);
    }

    @Override
    public void onBindViewHolder(ChatItem holder, int position) {
        chatRows.get(position);
        holder.icon.setImageBitmap(imageToBitmap(chatRows.get(position).getUser().getIcon()));
        holder.name.setText(chatRows.get(position).getUser().getName());
        holder.status.setText(chatRows.get(position).getUser().isOnline() ? "Online" : "Offline");
        MessageHistory messageHistory = (historyModel.getMessageHistory(chatRows.get(position).getUser()));
        if (messageHistory != null) {
            List<Message> history = messageHistory.getMessages();
            chatRows.get(position).setLastMessage(history.get(history.size() - 1));
        }
        if (chatRows.get(position).getLastMessage() != null) {
            holder.message.setText(chatRows.get(position).getLastMessage().getText());
        } else {
            holder.message.setText("");
        }
        if (chatRows.get(position).hasNewMessage()) {
            holder.message.setTextColor(context.getColor(R.color.colorPrimary));
        } else {
            holder.message.setTextColor(context.getColor(android.R.color.black));
        }
        holder.user = chatRows.get(position).getUser();
    }

    void addUser(User user) {
        chatRows.add(new ChatRow(user, null));
        notifyItemInserted(chatRows.size() - 1);
    }

    void removeUser(int userId) {
        ChatRow row = chatRows.stream()
                .filter(u -> u.getUser().getId() == userId)
                .findFirst()
                .orElse(null);
        if (row != null) {
            chatRows.remove(row);
            notifyDataSetChanged();
        }
        chatsFragment.ioManger.setUserRemovedHandler(userId);
    }

    void updateUser(int id, User newValue) {
        for (int i = 0; i < chatRows.size(); i++) {
            if (chatRows.get(i).getUser().getId() == id) {
                chatRows.set(i, new ChatRow(newValue, chatRows.get(i).getLastMessage()));
                notifyDataSetChanged();
                break;
            }
        }
    }

    void updateLastMessage(Message message) {
        for (int i = 0; i < chatRows.size(); i++) {
            if (chatRows.get(i).getUser().getId() == message.getSender().getId()) {
                ChatRow chatRow = new ChatRow(chatRows.get(i).getUser(), message);
                chatRow.setHasNewMessage(true);
                chatRows.set(i, chatRow);
                notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatRows.size();
    }


    static class ChatItem extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        final WeakReference<ChatsAdapter> adapterWeakReference;
        ImageView icon;
        TextView name;
        TextView time;
        TextView message;
        TextView status;
        User user;

        ChatItem(View itemView, ChatsAdapter chatsAdapter) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            message = itemView.findViewById(R.id.message);
            status = itemView.findViewById(R.id.status);
            this.adapterWeakReference = new WeakReference<>(chatsAdapter);
        }

        @Override
        public boolean onLongClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setItems(view.getResources().getStringArray(R.array.actions), (dialog, which) -> {
                if (which == 0 && adapterWeakReference.get() != null) {
                    adapterWeakReference.get().removeUser(user.getId());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        @Override
        public void onClick(View view) {
            if (adapterWeakReference.get() != null) {
                FragmentTransaction transaction = adapterWeakReference.get().chatsFragment.getActivity()
                        .getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                transaction.addToBackStack(null);
                MessagesFragment.user = user;
                transaction.replace(R.id.container, new MessagesFragment());
                transaction.commitAllowingStateLoss();
            }
        }
    }
}