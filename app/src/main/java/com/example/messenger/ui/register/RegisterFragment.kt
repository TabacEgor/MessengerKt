package com.example.messenger.ui.register

import android.os.Bundle
import android.view.View
import com.example.messenger.R
import com.example.messenger.domain.account.AccountEntity
import com.example.messenger.domain.type.None
import com.example.messenger.presentation.viewmodel.AccountViewModel
import com.example.messenger.ui.App
import com.example.messenger.ui.core.BaseFragment
import com.example.messenger.ui.core.ext.onFailure
import com.example.messenger.ui.core.ext.onSuccess
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_register
    override val titleToolbar = R.string.register

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        accountViewModel = viewModel {
            onSuccess(registerData, ::handleRegister)
            onSuccess(accountData, ::handleLogin)
            onFailure(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNewMembership.setOnClickListener {
            register()
        }

        btnAlreadyHaveAcc.setOnClickListener {
            activity?.finish()
        }
    }

    private fun validateFields(): Boolean {
        val allFields = arrayOf(etEmail, etPassword, etConfirmPassword, etUserName)
        var allValid = true
        for (field in allFields) {
            allValid = field.testValidity() && allValid
        }
        return allValid && validatePasswords()
    }

    private fun validatePasswords(): Boolean {
        val valid = etPassword.text.toString() == etConfirmPassword.text.toString()
        if (!valid) {
            showMessage(getString(R.string.error_password_mismatch))
        }
        return valid
    }

    private fun register() {
        hideSoftKeyboard()

        val allValid = validateFields()

        if (allValid) {
            showProgress()
            accountViewModel.register(
                etEmail.text.toString(),
                etUserName.text.toString(),
                etPassword.text.toString()
            )
        }
    }

    private fun handleRegister(none: None? = None()) {
        accountViewModel.login(etEmail.text.toString(), etPassword.text.toString())
    }

    private fun handleLogin(accountEntity: AccountEntity?) {
        activity?.let {
            navigator.showHome(it)
            it.finish()
        }
    }
}