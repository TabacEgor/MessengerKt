package com.example.messenger.ui.user

import android.os.Bundle
import com.example.messenger.ui.App
import com.example.messenger.ui.core.BaseActivity
import com.example.messenger.ui.core.BaseFragment

class UserActivity : BaseActivity() {

    override var fragment: BaseFragment = UserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}