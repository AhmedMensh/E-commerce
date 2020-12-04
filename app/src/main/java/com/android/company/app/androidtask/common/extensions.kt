package com.android.company.app.androidtask.common

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.widget.ImageView
import android.widget.TextView
import com.android.company.app.androidtask.R
import com.bumptech.glide.Glide


fun String.setIcon(imageView: ImageView){

    Glide.with(imageView).load(this).placeholder(R.drawable.ic_launcher_background).into(imageView)
}

fun TextView.highLight(textToHighlight: String){

    val tvt = this.text.toString().toLowerCase()
    var ofe = tvt.indexOf(textToHighlight.toLowerCase(), 0)
    val wordToSpan: Spannable = SpannableString(this.text)
    var ofs = 0
    while (ofs < tvt.length && ofe != -1) {
        ofe = tvt.indexOf(textToHighlight.toLowerCase(), ofs)
        if (ofe == -1) break else {
            // set color here
            wordToSpan.setSpan(
                BackgroundColorSpan(-0x100),
                ofe,
                ofe + textToHighlight.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            this.setText(wordToSpan, TextView.BufferType.SPANNABLE)
        }
        ofs = ofe + 1
    }
}
