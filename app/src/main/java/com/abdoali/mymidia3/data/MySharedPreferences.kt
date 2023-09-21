package com.abdoali.mymidia3.data

import android.content.Context
import android.content.SharedPreferences
import com.abdoali.mymidia3.ui.settings.Theme

object MySharedPreferences {
    private var myShared: SharedPreferences? = null
    private const val SHARED_NAME = "mySharedPreferencesMedia3"
    private const val COLOR = "COLOR_COLOR"

    fun initShared(context: Context) {
        myShared = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
    }

    var theme: String?
        get() = myShared?.getString(COLOR, "")
        set(value) {
            myShared?.edit()?.putString(COLOR, value)?.apply()
        }



}

fun String.asTheme():Theme{
    return when{
        this=="${Theme.Red}"->Theme.Red
        this=="${Theme.Blue}"->Theme.Blue



        else->Theme.Brown
    }

}
