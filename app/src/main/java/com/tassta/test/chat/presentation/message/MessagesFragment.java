package com.tassta.test.chat.presentation.message;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.tassta.test.chat.MainActivity;
import com.tassta.test.chat.MessageHistoryModel;
import com.tassta.test.chat.MessageHistoryModelImpl;
import com.tassta.test.chat.MessageImpl;
import com.tassta.test.chat.R;
import com.tassta.test.chat.User;
import com.tassta.test.chat.databinding.FragmentMessagesBinding;
import com.tassta.test.chat.noimpl.IoManger;

import java.lang.ref.WeakReference;
import java.util.Date;

import javax.inject.Inject;

public class MessagesFragment extends Fragment {

    public static User user;

    private FragmentMessagesBinding binding;

    private RecyclerView.Adapter adapter;

    @Inject
    IoManger ioManger;

    @Inject
    MessageHistoryModel historyModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messages, container, false);
        ((MainActivity) getActivity()).getApplicationComponent().inject(this);
        if (getActivity() != null) {
            ((MainActivity) getActivity()).updateActionBar(user.getName());
        }
        configureRecyclerView();

        RxView.clicks(binding.send)
                .filter(v -> binding.message.getText().length() > 0)
                .subscribe(v -> {
                    new SendMessageTask(this).execute(null, binding.message.getText().toString());
                    ((MessagesAdapter) adapter).addMessage(new MessageImpl(new Date(),
                            binding.message.getText().toString(), MainActivity.me, null));
                    binding.messages.smoothScrollToPosition(adapter.getItemCount() - 1);
                    binding.message.getText().clear();

                });

        ioManger.setReceiveMessageHandler(message -> {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    ((MessagesAdapter) adapter).addMessage(message);
                    binding.messages.smoothScrollToPosition(adapter.getItemCount() - 1);
                });
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        user = null;
    }

    private void configureRecyclerView() {
        binding.messages.setHasFixedSize(true);
        binding.messages.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        binding.messages.setLayoutManager(layoutManager);
        adapter = new MessagesAdapter(((MessageHistoryModelImpl) historyModel.getMessageHistory(user)).getMessages());
        binding.messages.setAdapter(adapter);
    }

    static class SendMessageTask extends AsyncTask<Object, Void, Void> {
        WeakReference<MessagesFragment> fragmentWeakReference;

        SendMessageTask(MessagesFragment messagesFragment) {
            fragmentWeakReference = new WeakReference<>(messagesFragment);
        }

        protected Void doInBackground(Object... params) {
            if (fragmentWeakReference.get() != null) {
                try {
                    fragmentWeakReference.get().ioManger.sendMessage((User) params[0], params[1].toString());
                } catch (Exception e) {
                    Log.d(this.getClass().getSimpleName(), e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }
}