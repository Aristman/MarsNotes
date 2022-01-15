package ru.marslab.marsnotes.domain

interface Callback<T> {
    fun onSuccess(result: T)
    fun onError()
}
