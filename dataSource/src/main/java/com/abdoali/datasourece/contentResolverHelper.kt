package com.abdoali.datasourece

//import androidx.media3.common.MediaItem
//import androidx.media3.common.MediaMetadata

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ContentResolverHelper @Inject constructor(@ApplicationContext val context: Context) {

    private var mcursor: Cursor? = null

    private val songList = mutableListOf<Song>()
    private val collection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)

    } else {
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    }

    private val selection = "${MediaStore.Audio.Media.DURATION} >= ?"
    private val selectionArgs = arrayOf(
        TimeUnit.MILLISECONDS.convert(1 , TimeUnit.MINUTES).toString()
    )
    private val projection = arrayOf(
        MediaStore.Audio.AudioColumns.DISPLAY_NAME ,
        MediaStore.Audio.AudioColumns._ID ,
        MediaStore.Audio.AudioColumns.ARTIST ,
        MediaStore.Audio.AudioColumns.DATA ,
        MediaStore.Audio.AudioColumns.DURATION ,
        MediaStore.Audio.Media.TITLE ,

        )
    val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

    @WorkerThread
    fun getAudioData(): List<Song> {
        return getCursor()
    }

//fun getMateData():List<MediaItem>{
//    return getCursor().map {song: Song ->
//    val metadata =MediaMetadata.Builder()
//        .setArtist(song.artists)
//        .setDisplayTitle(song.title)
//        .setArtworkUri(song.uri)
//        .build()
//MediaItem.Builder()
//    .setMediaMetadata(metadata)
//    .setUri(song.uri)
//    .build()
//    }

    private fun getCursor(): MutableList<Song> {
        //try and catch for API Less than 28
        try {

            val audioList = mutableListOf<Song>()

            mcursor = context.contentResolver.query(
                collection ,
                projection ,
                selection ,
                selectionArgs ,
                sortOrder
            )

            mcursor?.use { cursor ->
                // Cache column indices.
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                val durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
//            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
                val artistColumns =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)
                val titleColumns =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
                val dateColumns =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
                var index = 0
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val duration = cursor.getInt(durationColumn)
                    val artists = cursor.getString(artistColumns)
                    val title = cursor.getString(titleColumns)
                    val data = cursor.getString(durationColumn)
                    val contentUri: Uri =
                        ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI ,
                            id
                        )
                    audioList += Song(
                        uri = contentUri ,
                        displayName = name ,
                        id = id ,
                        artists = artists ,
                        duration = duration ,
                        title = title ,
                        date = data ,

                        )


                }

            }
            return audioList
        } catch (e: Exception) {

            return mutableListOf()
        }
    }

}