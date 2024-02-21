package com.abdoali.mymidia3.data

import android.net.Uri

sealed class UIEvent {
    object PlayPause : UIEvent()
    object Backward : UIEvent()
    object Forward : UIEvent()
    object PlayNext : UIEvent()
    object PlayPre : UIEvent()
    data class SeekToIndex(val index: Int) : UIEvent()
    data class UpdateProgress(val newProgress: Float) : UIEvent()
    data class Shuffle(val shuffle: Boolean) : UIEvent()
    data class Timer(val time: Int) : UIEvent()
    data class SetPlayList(val list: List<Int>) : UIEvent()
    data class Repeat(val repeat: Boolean) : UIEvent()
    data class Download(val uri: String , val fileName:String):UIEvent()

}

sealed class DataEvent {
    object Local : DataEvent()
    object NewApi : DataEvent()
    object AllApi : DataEvent()
    object FovApi : DataEvent()
}

sealed class UIState {
    object Initial : UIState()
    object Ready : UIState()
}

//enum class ServiceRun {
//    Stop , Run , Kill
//}