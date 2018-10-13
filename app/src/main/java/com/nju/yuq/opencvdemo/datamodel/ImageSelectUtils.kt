package com.nju.yuq.opencvdemo.datamodel

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log

import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date


object ImageSelectUtils {
    private val TAG = "ImageSelectUtils"
    val saveFilePath: File?
        get() {
            val status = Environment.getExternalStorageState()
            if (status != Environment.MEDIA_MOUNTED) {
                Log.i(TAG, "SD Card is not suitable...")
                return null
            }
            val df = SimpleDateFormat("yyyyMMdd_hhmmss")
            val name = df.format(Date(System.currentTimeMillis())) + ".jpg"
            val filedir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath, "myOcrImages")
            filedir.mkdirs()
            val fileName = filedir.absolutePath + File.separator + name
            return File(fileName)
        }

    fun getRealPath(uri: Uri, appContext: Context): String? {
        var filePath: String? = null
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {//4.4及以上
            val wholeID = DocumentsContract.getDocumentId(uri)
            val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
            val column = arrayOf(MediaStore.Images.Media.DATA)
            val sel = MediaStore.Images.Media._ID + "=?"
            val cursor = appContext.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                    sel, arrayOf(id), null)
            val columnIndex = cursor!!.getColumnIndex(column[0])
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex)
            }
            cursor.close()
        } else {//4.4以下，即4.4以上获取路径的方法
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = appContext.contentResolver.query(uri, projection, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            filePath = cursor.getString(column_index)
        }
        Log.i(TAG, "selected image path : " + filePath!!)
        return filePath
    }

    fun saveImage(image: Mat) {
        val fileDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "mybook")
        if (!fileDir.exists()) {
            fileDir.mkdirs()
        }
        val name = System.currentTimeMillis().toString() + "_book.jpg"
        val tempFile = File(fileDir.absoluteFile.toString() + File.separator, name)
        Imgcodecs.imwrite(tempFile.absolutePath, image)
    }

}
