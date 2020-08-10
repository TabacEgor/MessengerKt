package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.messenger.domain.account.*
import com.example.messenger.domain.type.None
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    val registerUseCase: Register,
    val loginUseCase: Login,
    val getAccountUseCase: GetAccount,
    val logoutUseCase: Logout,
    val editAccountUseCase: EditAccount,
    val updateLastSeenUseCase: UpdateLastSeen
) : BaseViewModel() {

    val registerData: MutableLiveData<None> = MutableLiveData()
    val accountData: MutableLiveData<AccountEntity> = MutableLiveData()
    val logoutData: MutableLiveData<None> = MutableLiveData()
    val editAccountData: MutableLiveData<AccountEntity> = MutableLiveData()

    fun register(email: String, name: String, password: String) {
        registerUseCase(Register.Params(email, name, password)) { it.either(::handleFailure, ::handleRegister) }
    }

    fun login(email: String, password: String) {
        loginUseCase(Login.Params(email, password)) { it.either(::handleFailure, ::handleAccount) }
    }

    fun getAccount() {
        getAccountUseCase(None()) { it.either(::handleFailure, ::handleAccount) }
    }

    fun logout() {
        logoutUseCase(None()) { it.either(::handleFailure, ::handleLogout) }
    }

    fun editAccount(entity: AccountEntity) {
        editAccountUseCase(entity) { it.either(::handleFailure, ::handleEditAccount) }
    }

    fun updateLastSeen() {
        updateLastSeenUseCase(None()) { it.either(::handleFailure) {} }
    }

    private fun handleEditAccount(account: AccountEntity) {
        this.editAccountData.value = account
    }

    private fun handleRegister(none: None) {
        this.registerData.value = none
    }

    private fun handleAccount(account: AccountEntity) {
        this.accountData.value = account
    }

    private fun handleLogout(none: None) {
        this.logoutData.value = none
    }

    override fun onCleared() {
        super.onCleared()
        registerUseCase.unsubscribe()
        loginUseCase.unsubscribe()
        getAccountUseCase.unsubscribe()
        logoutUseCase.unsubscribe()
        updateLastSeenUseCase.unsubscribe()
    }
}