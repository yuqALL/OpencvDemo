package com.nju.yuq.opencvdemo.datamodel

import java.io.Serializable


class ItemDto(var id: Long, var name: String?, var desc: String?) : Serializable {
    var comments: String? = null
}
