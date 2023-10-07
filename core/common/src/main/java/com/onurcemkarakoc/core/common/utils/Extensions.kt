package com.onurcemkarakoc.core.common.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.onurcemkarakoc.core.common.CustomDividerItemDecoration
import com.onurcemkarakoc.core.common.R

fun Context.readJsonAsset(fileName: String): String {
    val inputStream = assets.open(fileName)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    return String(buffer, Charsets.UTF_8)
}

fun Context.drawable(@DrawableRes resId: Int) =
    ContextCompat.getDrawable(this, resId)

fun RecyclerView.addDivider() =
    this.addItemDecoration(
        CustomDividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
            context?.drawable(R.drawable.divider_darker_gray)?.let {
                setDrawable(it)
            }
        })

fun NavController.toDetail(satelliteId: String,satelliteName: String) {
    val uri = Uri.parse("onurcem://satellite_detail/?satelliteId=$satelliteId&satelliteName=$satelliteName")
    this.navigate(uri)
}

fun String?.makeHtml(): Spanned? {
    if (this.isNullOrEmpty()) return null
    return if (hasMinimumSdk(Build.VERSION_CODES.N)) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else Html.fromHtml(this)
}
@ChecksSdkIntAtLeast(parameter = 0)
fun hasMinimumSdk(minimumSdk: Int): Boolean {
    return Build.VERSION.SDK_INT >= minimumSdk
}