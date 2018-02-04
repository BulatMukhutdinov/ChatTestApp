package com.tassta.test.chat;

import com.tassta.test.chat.noimpl.IoManger;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class IoManagerStub implements IoManger {

    static boolean sent;
    private final CompositeDisposable disposables = new CompositeDisposable();
    PublishSubject publishSubject;

    @Inject
    public IoManagerStub() {
        publishSubject = PublishSubject.create();
    }

    @Override
    public void sendMessage(User receiver, String text) throws Exception {

    }

    @Override
    public void setReceiveMessageHandler(Consumer<Message> handler) {
        User sender = new UserImpl("Timur", 2, true, null);
        if (handler != null) {
            disposables.add(Observable.interval(5, TimeUnit.SECONDS).subscribe(v ->
                    handler.accept(new MessageImpl(new Date(), String.valueOf(v), sender, MainActivity.me))));
        } else {
            disposables.clear();
        }
    }

    @Override
    public void setUserStateChangeHandler(UserStateChangeHandler handler) {

    }

    @Override
    public void setUserAddedHandler(Consumer<User> handler) {
        User user = new UserImpl("Timur", 2, true, null);
        if (handler != null) {
            handler.accept(user);
        }
    }

    @Override
    public void setUserRemovedHandler(int userId) {

    }
}