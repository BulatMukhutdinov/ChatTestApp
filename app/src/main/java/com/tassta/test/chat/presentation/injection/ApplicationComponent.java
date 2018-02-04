package com.tassta.test.chat.presentation.injection;

import com.tassta.test.chat.MainActivity;
import com.tassta.test.chat.presentation.chat.ChatsFragment;
import com.tassta.test.chat.presentation.message.MessagesFragment;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent extends AndroidInjector<MainActivity> {
    void inject(MessagesFragment messagesFragment);

    void inject(ChatsFragment chatsFragment);
}
