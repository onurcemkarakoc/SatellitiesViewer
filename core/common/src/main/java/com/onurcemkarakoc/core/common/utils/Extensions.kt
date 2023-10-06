package com.onurcemkarakoc.core.common.utils

import android.content.Context
import android.net.Uri
import androidx.navigation.NavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun Context.readJsonAsset(fileName: String): String {
    val inputStream = assets.open(fileName)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    return String(buffer, Charsets.UTF_8)
}
fun RecyclerView.addDivider() =
    this.addItemDecoration(
        DividerItemDecoration(context, (this.layoutManager as LinearLayoutManager).orientation)
    )

fun NavController.toDetail(satelliteId: String,satelliteName: String) {
    val uri = Uri.parse("tknsyn://satellite_detail/?satelliteId=$satelliteId&satelliteName=$satelliteName")
    this.navigate(uri)
}