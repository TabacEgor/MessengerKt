package com.example.messenger.ui.account

import android.os.Bundle
import com.example.messenger.ui.App
import com.example.messenger.ui.core.BaseActivity
import com.example.messenger.ui.core.BaseFragment

class AccountActivity : BaseActivity() {

    override var fragment: BaseFragment = AccountFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }
}