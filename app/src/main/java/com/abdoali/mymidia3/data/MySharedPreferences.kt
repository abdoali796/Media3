package com.abdoali.mymidia3.data

import android.content.Context
import android.content.SharedPreferences
import com.abdoali.mymidia3.ui.settings.Theme

object MySharedPreferences {
    private var myShared: SharedPreferences? = null
    private const val SHARED_NAME = "mySharedPreferencesMedia3"
    private const val COLOR = "COLOR_COLOR"
    private const val TEXT= "TEXT_COLOR"
    private const val ITEM = "ITEM_ITEM"

    fun initShared(context: Context) {
        myShared = context.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE)
    }

    var theme: String?
        get() = myShared?.getString(COLOR , "")
        set(value) {
            myShared?.edit()?.putString(COLOR , value)?.apply()
        }

    var text: String?
        get() = myShared?.getString(TEXT , "")
        set(value) {
            myShared?.edit()?.putString(TEXT , value)?.apply()
        }
    var item: Int?
        get() = myShared?.getInt(ITEM , 0)
        set(value) {
            myShared?.edit()?.putInt(ITEM , value!!)?.apply()
        }

}

fun String.asTheme(): Theme {
    return when {
        this == "${Theme.Red}" -> Theme.Red
        this == "${Theme.Brown}" -> Theme.Brown

        else -> Theme.Blue
    }

}
