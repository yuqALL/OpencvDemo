package com.nju.yuq.opencvdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ListView
import com.nju.yuq.opencvdemo.datamodel.ChapterUtils
import com.nju.yuq.opencvdemo.datamodel.ItemDto
import com.nju.yuq.opencvdemo.datamodel.SectionsListViewAdaptor
import org.jetbrains.anko.find

import org.jetbrains.anko.toast
import org.opencv.android.OpenCVLoader
import com.nju.yuq.opencvdemo.datamodel.AppConstants
import com.nju.yuq.opencvdemo.datamodel.SectionsActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import org.jetbrains.anko.alert


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniLoadOpenCV()
        initListView()
        checkPermission()
    }

    fun iniLoadOpenCV(): Unit {
        val success = OpenCVLoader.initDebug();
        if (success) {
            Log.e("", "OpenCV Libraries loaded...")
        } else {
            toast("WARNING: Could not laod OpenCV Libraries!")
        }
    }

    fun initListView(): Unit {
        val listView: ListView = find(R.id.chapter_listview) as ListView
        val commmandAdapter: SectionsListViewAdaptor? = SectionsListViewAdaptor(this)
        commmandAdapter?.dataModel = ChapterUtils.chapters
        listView.adapter = commmandAdapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val dot = commmandAdapter?.dataModel!!.get(position)
            goSectionList(dot)
        }
        commmandAdapter?.notifyDataSetChanged()

    }

    fun goSectionList(dto: ItemDto): Unit {
        val intent = Intent(this.applicationContext, SectionsActivity::class.java)
        intent.putExtra(AppConstants.ITEM_KEY, dto)
        startActivity(intent)
    }

    private fun checkPermission() {
        if (enableCamera && enableReadExternalMemory && enableReadExternalMemory) {
            return
        } else {
//            alert("程序缺少必要的权限，为了模块的正常运行，请通过设置开启权限！否则程序可能异常退出！") {
//                title("警告")
//                positiveButton("设置") { settingPermission() }
//                negativeButton("取消") { }
//            }.show()
            settingPermission()
        }
    }

    private fun settingPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            val checkPermission1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            val checkPermission2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (checkPermission1 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, Array<String>(1) { android.Manifest.permission.CAMERA }, 111)
                return
            } else {
                enableCamera = true
            }
            if (checkPermission2 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, Array<String>(1) { android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 222)
                return
            } else {
                enableWriteExternalMemory = true
            }
            val checkPermission3 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            if (checkPermission3 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, Array<String>(1) { android.Manifest.permission.READ_EXTERNAL_STORAGE }, 333)
                return
            } else {
                enableReadExternalMemory = true
            }
        }
    }

    var enableCamera: Boolean = false
    var enableReadExternalMemory: Boolean = false
    var enableWriteExternalMemory: Boolean = false
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            111 -> enableCamera
            222 -> enableWriteExternalMemory = true
            333 -> enableReadExternalMemory = true
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        checkPermission()
    }

}
