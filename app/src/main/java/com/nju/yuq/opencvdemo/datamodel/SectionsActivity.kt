package com.nju.yuq.opencvdemo.datamodel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.nju.yuq.opencvdemo.CharpterFirst1Activity

import com.nju.yuq.opencvdemo.R

class SectionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sections)
        val dto = this.intent.extras!!.getSerializable(AppConstants.ITEM_KEY) as ItemDto
        if (dto != null) {
            initListView(dto)
        }
    }

    private fun initListView(dto: ItemDto) {
        val listView = findViewById<View>(R.id.secction_listView) as ListView
        val commandAdaptor = SectionsListViewAdaptor(this)

        listView.adapter = commandAdaptor
        commandAdaptor.dataModel=ChapterUtils.getSections(dto.id.toInt())
        //commandAdaptor.dataModel.addAll(ChapterUtils.getSections(dto.id.toInt()))
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val command :String= commandAdaptor.dataModel[position].name.toString()
            goDemoView(command)
        }
        commandAdaptor.notifyDataSetChanged()
    }

    private fun goDemoView(command: String) {
        if (command == AppConstants.CHAPTER_1TH_PGM_01) {
            val intent = Intent(this.applicationContext, CharpterFirst1Activity::class.java)
            startActivity(intent)
        }

    }
}
