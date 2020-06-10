package com.tnj.if_else.utils.helperClasses;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

public class Event<T> extends MutableLiveData<T> {

    private AtomicBoolean isHandled;

    public Event(T value){
        this();
        setValue(value);
    }
    public Event() {
        super();
        isHandled = new AtomicBoolean(false);
    }

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, t -> {
            if (isHandled.compareAndSet(false, true))
                observer.onChanged(t);
        });
    }

    @Override
    public void setValue(T value) {
        isHandled.set(false);
        super.setValue(value);
    }
}
