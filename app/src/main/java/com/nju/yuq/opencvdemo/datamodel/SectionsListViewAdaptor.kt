package com.nju.yuq.opencvdemo.datamodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.nju.yuq.opencvdemo.R
import kotlinx.android.synthetic.main.activity_main.view.*


class SectionsListViewAdaptor(private val appContext: Context) : BaseAdapter() {
    var dataModel: List<ItemDto>

    var inflater:LayoutInflater?=null
    init {
        dataModel = ArrayList()
        this.inflater = appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
//    constructor(appContext: Context,list:List<ItemDto>):this(appContext){
//        dataModel=list
//    }

    override fun getCount(): Int {
        return dataModel.size
    }

    override fun getItem(position: Int): Any {
        return dataModel[position]
    }

    override fun getItemId(position: Int): Long {
        return dataModel[position].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var holder:ViewHolder
        var retView:View

        if(convertView == null){
            retView = inflater!!.inflate(R.layout.row_layout, parent, false) //error in this line
            holder = ViewHolder()

            holder.textView = retView.findViewById(R.id.row_textView) as TextView
            // populate the text into UI
            holder.textView!!.text = dataModel[position].desc
            retView.tag=holder

        } else {
            holder = convertView.tag as ViewHolder
            retView=convertView
        }

        return retView
    }
    internal class ViewHolder {
        var textView:TextView ?=null

    }
}
