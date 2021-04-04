package com.ns.photosofthisname.navigator

import android.content.Context
import android.content.Intent
import android.net.Uri

class Navigator(private val context: Context) {

    fun openUrl(activityContext: Context, url:String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        activityContext.startActivity(intent)
    }
}