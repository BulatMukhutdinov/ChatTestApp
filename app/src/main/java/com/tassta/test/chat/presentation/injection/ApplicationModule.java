package com.tassta.test.chat.presentation.injection;

import com.tassta.test.chat.IoManagerStub;
import com.tassta.test.chat.MessageHistoryModel;
import com.tassta.test.chat.MessageHistoryModelImpl;
import com.tassta.test.chat.noimpl.IoManger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    public IoManger provideIoManger() {
        return new IoManagerStub();
    }

    @Provides
    @Singleton
    public MessageHistoryModel provideMessageHistoryModel() {
        return new MessageHistoryModelImpl();
    }
}
