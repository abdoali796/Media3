package com.abdoali.mymidia3.data.downloed

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DownloadFile @Inject constructor(@ApplicationContext val context: Context) {
    fun downloadFile(
        url: String,
        folderName: String,
        folderName2: String,
        fileName:String,
        onProgress: ((Long, Long) -> Unit)? = null,
        onComplete: (() -> Unit)? = null
    ) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setDescription("Downloading...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, "abdo/$folderName/$folderName2/$fileName.mp3")
//        .setDestinationInExternalFilesDir(context,Environment.DIRECTORY_DOWNLOADS ,fileName )
//        .setDestinationInExternalPublicDownloadsDir(Environment.DIRECTORY_DOWNLOADS, fileName)


        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)

//        val query = DownloadManager.Query().setFilterById(d
        Toast.makeText(context, "download $fileName يمكن متابعه التنزيل في لوحه الاشعارات " , Toast.LENGTH_LONG).show()
        Toast.makeText(context, "يتم التنزيل في مجلد music/abdoali" , Toast.LENGTH_LONG).show()

//        if (onProgress != null) {
//            Thread {
//                var downloadedBytes = 0L
//                var totalBytes = 0L
//                while (true) {
//                    val cursor = downloadManager.query(query)
//                    if (cursor.moveToFirst()) {
//                        val status =
//                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
//                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
//                            totalBytes =
//                                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
//                            break
//                        } else if (status == DownloadManager.STATUS_RUNNING) {
//
//                            onProgress(downloadedBytes, totalBytes)
//                        }
//                    }
//                    cursor.close()
//                    Thread.sleep(500L)
//                }
//                onComplete?.invoke()
//            }.start()
//        }
//    }
}}
/*
* if (onProgress != null) {
            Thread {
                val query = DownloadManager.Query().setFilterById(downloadId)
                var downloadedBytes = 0L
                var totalBytes = 0L
                while (true) {
                    val cursor = downloadManager.query(query)
                    if (cursor.moveToFirst()) {
                        val status =
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            totalBytes =
                                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                            break
                        } else if (status == DownloadManager.STATUS_RUNNING) {
                            downloadedBytes =
                                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                            totalBytes =
                                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                            onProgress(downloadedBytes, totalBytes)
                        }
                    }
                    cursor.close()
                    Thread.sleep(500L)
                }
                onComplete?.invoke()
            }.start()
        }*/