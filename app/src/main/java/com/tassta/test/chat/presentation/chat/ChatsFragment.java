package com.tassta.test.chat.presentation.chat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tassta.test.chat.MainActivity;
import com.tassta.test.chat.MessageHistoryModel;
import com.tassta.test.chat.MessageHistoryModelImpl;
import com.tassta.test.chat.R;
import com.tassta.test.chat.databinding.FragmentChatsBinding;
import com.tassta.test.chat.noimpl.IoManger;
import com.tassta.test.chat.presentation.message.MessagesFragment;

import java.util.ArrayList;

import javax.inject.Inject;

public class ChatsFragment extends Fragment {

    private FragmentChatsBinding binding;
    private RecyclerView.Adapter adapter;

    @Inject
    IoManger ioManger;

    @Inject
    MessageHistoryModel historyModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chats, container, false);
        ((MainActivity) getActivity()).getApplicationComponent().inject(this);
        configureRecyclerView();

        ioManger.setUserAddedHandler(user -> {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> ((ChatsAdapter) adapter).addUser(user));
            }
        });
        ioManger.setUserStateChangeHandler((id, newValue) -> {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> ((ChatsAdapter) adapter).updateUser(id, newValue));
            }
        });

        ioManger.setReceiveMessageHandler(message -> {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> ((ChatsAdapter) adapter).updateLastMessage(message));
            }
            ((MessageHistoryModelImpl) historyModel).saveMessage(message.getSender(), message);
            if (MessagesFragment.user == null || MessagesFragment.user.getId() != message.getSender().getId()) {
                createNotification(message.getText());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ioManger.setReceiveMessageHandler(null);
        ioManger.setUserAddedHandler(null);
    }

    private NotificationManager notifManager;

    public void createNotification(String message) {
        final int NOTIFY_ID = 1002;

        // There are hardcoding only for show it's just strings
        String name = "my_package_channel";
        String id = "my_package_channel_1"; // The user-visible name of the channel.
        String description = "my_package_first_channel"; // The user-visible description of the channel.

        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager =
                    (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(getContext(), id);
            builder.setContentTitle(message)
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)
                    .setContentText(this.getString(R.string.app_name))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setTicker(message)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {
            builder = new NotificationCompat.Builder(getContext());
            builder.setContentTitle(message)
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)
                    .setContentText(this.getString(R.string.app_name))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setTicker(message)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).updateActionBar(getString(R.string.chats));
        }
    }

    private void configureRecyclerView() {
        binding.chats.setHasFixedSize(true);
        binding.chats.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chats.setLayoutManager(layoutManager);
        adapter = new ChatsAdapter(new ArrayList<>(), this, getContext(), historyModel);
        binding.chats.setAdapter(adapter);
    }
}