package com.phototravel.services.threads;

import com.phototravel.services.scannerTask.taskCallback.TaskCallback;

public class QueuedItemContainer<E> {

    E item;
    TaskCallback callback;

    public E getItem() {
        return item;
    }

    public void setItem(E item) {
        this.item = item;
    }

    public TaskCallback getCallback() {
        return callback;
    }

    public void setCallback(TaskCallback callback) {
        this.callback = callback;
    }

    public boolean hasCallback() {
        return callback != null;
    }
}
