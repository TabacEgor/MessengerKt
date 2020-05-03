package com.example.messenger.ui.home

import android.os.Bundle
import com.example.messenger.ui.App
import com.example.messenger.ui.core.BaseActivity
import com.example.messenger.ui.core.BaseFragment

class MessagesActivity : BaseActivity() {

    override var fragment: BaseFragment = MessagesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}