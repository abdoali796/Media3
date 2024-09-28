//package com.abdoali.playservice
//
//import android.content.Context
//import android.content.SharedPreferences
//import android.os.Build
//import androidx.annotation.RequiresApi
//
////import com.abdoali.mymidia3.ui.settings.Theme
//
//object MySharedPreferences {
//    private var myShared: SharedPreferences? = null
//    private const val SHARED_NAME = "mySharedPreferencesMedia3"
//    private const val COLOR = "COLOR_COLOR"
//    private const val TEXT= "TEXT_COLOR"
//
////    fun initShared(context: Context) {
////        myShared = context.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE)
////    }
//
//    var theme: String?
//        get() = myShared?.getString(COLOR , "")
//        @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
//        set(value) {
//            myShared?.edit()?.putString(COLOR , value)?.apply()
//        }
//
//    var text: String?
//        get() = myShared?.getString(TEXT , "")
//
//        set(value) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//                myShared?.edit()?.putString(TEXT , value)?.apply()
//            }
//        }
//
//}
//
////fun String.asTheme(): Theme {
////    return when {
////        this == "${Theme.Red}" -> Theme.Red
////        this == "${Theme.Brown}" -> Theme.Brown
////
////        else -> Theme.Blue
////    }
//
//}
