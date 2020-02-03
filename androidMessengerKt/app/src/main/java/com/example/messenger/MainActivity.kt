package com.example.messenger

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.messenger.cache.AccountCacheImpl
import com.example.messenger.cache.SharedPrefsManager
import com.example.messenger.data.account.AccountRepositoryImpl
import com.example.messenger.domain.account.IAccountRepository
import com.example.messenger.domain.account.Register
import com.example.messenger.remote.account.AccountRemoteImpl
import com.example.messenger.remote.core.NetworkHandler
import com.example.messenger.remote.core.Request
import com.example.messenger.remote.service.ServiceFactory
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = this.getSharedPreferences(this.packageName, Context.MODE_PRIVATE)

        val accountCache = AccountCacheImpl(SharedPrefsManager(sharedPrefs))
        val accountRemote = AccountRemoteImpl(Request(NetworkHandler(this)),
            ServiceFactory.makeService(true))

        val accountRepository: IAccountRepository = AccountRepositoryImpl(accountRemote, accountCache)

        accountCache.saveToken("12345")

        val register = Register(accountRepository)
        register(Register.Params("test23@test.com", "test", "testtest")) {
            it.either({
                Toast.makeText(this, it.javaClass.simpleName, Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
