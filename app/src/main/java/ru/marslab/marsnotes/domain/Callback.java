package ru.marslab.marsnotes.domain;

public interface Callback<T> {

    void onSuccess(T result);
}
