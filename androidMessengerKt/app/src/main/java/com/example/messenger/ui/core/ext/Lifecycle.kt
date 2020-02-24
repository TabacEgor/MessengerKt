package com.example.messenger.ui.core.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.messenger.domain.type.HandleOnce
import com.example.messenger.domain.type.exception.Failure

fun <T : Any, L : LiveData<T>> LifecycleOwner.onSuccess(livedata: L, body: (T?) -> Unit) =
    livedata.observe(this, Observer(body))

fun <L : LiveData<HandleOnce<Failure>>> LifecycleOwner.onFailure(livedata: L, body: (Failure?) -> Unit) =
    livedata.observe(this, Observer {
        it.getContentIfNotHandled()?.let(body)
    })