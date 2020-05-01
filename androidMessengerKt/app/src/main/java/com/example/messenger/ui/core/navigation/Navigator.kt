package com.example.messenger.ui.core.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.messenger.R
import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.presentation.viewmodel.MediaViewModel
import com.example.messenger.remote.service.IApiService
import com.example.messenger.ui.account.AccountActivity
import com.example.messenger.ui.core.PermissionManager
import com.example.messenger.ui.home.HomeActivity
import com.example.messenger.ui.login.LoginActivity
import com.example.messenger.ui.register.RegisterActivity
import com.example.messenger.ui.user.UserActivity
import java.net.Authenticator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor(
    private val authenticator: com.example.messenger.presentation.Authenticator,
    private val permissionManager: PermissionManager
) {

    fun showMain(context: Context) {
        authenticator.userLoggedIn {
            if (it) showHome(context, false) else showLogin(context, false)
        }
    }

    fun showHome(context: Context, newTask: Boolean = true) = context.startActivity<HomeActivity>(newTask = newTask)

    fun showLogin(context: Context, newTask: Boolean = true) = context.startActivity<LoginActivity>(newTask = newTask)

    fun showSignUp(context: Context) = context.startActivity<RegisterActivity>()

    fun showEmailNotFoundDialog(context: Context, email: String) {
        AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.message_promt_app))

            .setPositiveButton(android.R.string.yes) { dialog, which ->
                showEmailInvite(context, email)
            }

            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    fun showEmailInvite(context: Context, email: String) {
        val appPackageName = context.packageName
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.resources.getString(R.string.message_subject_promt_app))
        emailIntent.putExtra(Intent.EXTRA_TEXT, context.resources.getString(R.string.message_text_promt_app)
                + " " + context.resources.getString(R.string.url_google_play) + appPackageName)

        context.startActivity(Intent.createChooser(emailIntent, "Send"))
    }

    fun showAccount(context: Context) {
        context.startActivity<AccountActivity>()
    }

    fun showUser(context: Context, friendEntity: FriendEntity) {
        val bundle = Bundle()
        bundle.putString(IApiService.PARAM_IMAGE, friendEntity.image)
        bundle.putString(IApiService.PARAM_NAME, friendEntity.name)
        bundle.putString(IApiService.PARAM_EMAIL, friendEntity.email)
        bundle.putString(IApiService.PARAM_STATUS, friendEntity.status)
        context.startActivity<UserActivity>(args = bundle)
    }

    fun showPickFromDialog(activity: AppCompatActivity, onPick: (fromCamera: Boolean) -> Unit) {
        val options = arrayOf<CharSequence>(
            activity.getString(R.string.camera),
            activity.getString(R.string.gallery)
        )

        val builder = AlertDialog.Builder(activity)

        builder.setTitle(activity.getString(R.string.pick))
        builder.setItems(options) { _, item ->
            when (options[item]) {
                activity.getString(R.string.camera) -> {
                    permissionManager.checkCameraPermission(activity) {
                        onPick(true)
                    }
                }
                activity.getString(R.string.gallery) -> {
                    permissionManager.checkWritePermission(activity) {
                        onPick(false)
                    }
                }
            }
        }
        builder.show()
    }

    fun showCamera(activity: AppCompatActivity, uri: Uri) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        activity.startActivityForResult(intent, MediaViewModel.CAPTURE_IMAGE_REQUEST_CODE)
    }

    fun showGallery(activity: AppCompatActivity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"

        activity.startActivityForResult(intent, MediaViewModel.PICK_IMAGE_REQUEST_CODE)
    }

    // newTask – определяет, очищать(если true), или не очищать(если false) backstack приложения
    private inline fun <reified T> Context.startActivity(newTask: Boolean = false, args: Bundle? = null) {
        this.startActivity(Intent(this, T::class.java).apply {
            if (newTask) {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // флаги очищения стека
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            putExtra("args", args)
        })
    }
}