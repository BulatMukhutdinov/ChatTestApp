package com.tassta.test.chat;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.tassta.test.chat.noimpl.IoManger;
import com.tassta.test.chat.presentation.chat.ChatsFragment;
import com.tassta.test.chat.presentation.injection.ApplicationComponent;
import com.tassta.test.chat.presentation.injection.DaggerApplicationComponent;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    public static User me;

    @Inject
    IoManger ioManger;

    private ApplicationComponent applicationComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applicationComponent = DaggerApplicationComponent.builder().build();
        applicationComponent.inject(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new ChatsFragment());
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        me = new UserImpl("Bulat", 1, true, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        me = new UserImpl("Bulat", 1, false, null);
    }

    public void updateActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}