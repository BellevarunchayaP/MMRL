package com.dergoogler.mmrl.webui.interfaces

import android.webkit.JavascriptInterface
import com.dergoogler.mmrl.platform.Platform
import com.dergoogler.mmrl.platform.hiddenApi.HiddenUserManager
import com.dergoogler.mmrl.webui.model.JavaScriptInterface
import com.dergoogler.mmrl.webui.model.UMUserInfo
import com.dergoogler.mmrl.webui.moshi

class UserManagerInterface(wxOptions: WXOptions) : WebUIInterface(wxOptions) {
    private val um get(): HiddenUserManager = Platform.userManager

    override var name: String = "\$userManager"

    companion object {
        fun factory() = JavaScriptInterface(UserManagerInterface::class.java)
    }

    @JavascriptInterface
    fun getUserCount(): Int = um.getUsers().size

    @JavascriptInterface
    fun getUsers(): String {
        val uis = um.getUsers()
        val uisMapped = uis.map {
            UMUserInfo(
                name = it.name,
                id = it.id,
                isPrimary = it.isPrimary,
                isAdmin = it.isAdmin,
                isEnabled = it.isEnabled
            )
        }
        val adapter = moshi.adapter(List::class.java)
        return adapter.toJson(uisMapped)
    }

    @JavascriptInterface
    fun getUserInfo(userId: Int): UMUserInfo {
        val ui = um.getUserInfo(userId)

        return UMUserInfo(
            name = ui.name,
            id = ui.id,
            isPrimary = ui.isPrimary,
            isAdmin = ui.isAdmin,
            isEnabled = ui.isEnabled
        )
    }
}