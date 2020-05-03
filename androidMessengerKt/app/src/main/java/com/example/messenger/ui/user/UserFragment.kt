package com.example.messenger.ui.user

import android.os.Bundle
import android.view.View
import com.example.messenger.R
import com.example.messenger.remote.service.IApiService
import com.example.messenger.ui.core.BaseFragment
import com.example.messenger.ui.core.GlideHelper
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_user

    override val titleToolbar = R.string.screen_user

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        base {
            val args = intent.getBundleExtra("args")
            if (args != null) {
                val image = args.getString(IApiService.PARAM_IMAGE)
                val name = args.getString(IApiService.PARAM_NAME)
                val email = args.getString(IApiService.PARAM_EMAIL)
                val status = args.getString(IApiService.PARAM_STATUS)

                val id = args.getLong(IApiService.PARAM_CONTACT_ID)

                GlideHelper.loadImage(
                    requireContext(),
                    image,
                    imgPhoto,
                    R.drawable.ic_account_circle
                )

                tvName.text = name
                tvEmail.text = email
                tvStatus.text = status

                if (tvStatus.text.isEmpty()) {
                    tvStatus.visibility = View.GONE
                    tvHintStatus.visibility = View.GONE
                }

                imgPhoto.setOnClickListener {
                    navigator.showImageDialog(requireContext(), imgPhoto.drawable)
                }

                btnSendMessage.setOnClickListener {
                    navigator.showChatWithContact(id, name, requireContext())
                }
            }
        }
    }
}