package com.nju.yuq.opencvdemo.datamodel

import java.util.ArrayList


class ChapterUtils : AppConstants {
    companion object {

        val chapters: List<ItemDto>
            get() {
                val items = ArrayList<ItemDto>()
                val item1 = ItemDto(1, AppConstants.Companion.CHAPTER_1TH, "OpenCV For Android框架")
                val item2 = ItemDto(2, AppConstants.Companion.CHAPTER_2TH, "Mat与Bitmap对象")
                val item3 = ItemDto(3, AppConstants.Companion.CHAPTER_3TH, "像素操作")
                val item4 = ItemDto(4, AppConstants.Companion.CHAPTER_4TH, "图像操作")
                val item5 = ItemDto(5, AppConstants.Companion.CHAPTER_5TH, "分析与测量")
                val item6 = ItemDto(6, AppConstants.Companion.CHAPTER_6TH, "特征检测与匹配")
                val item7 = ItemDto(7, AppConstants.Companion.CHAPTER_7TH, "摄像头")
                val item8 = ItemDto(8, AppConstants.Companion.CHAPTER_8TH, "OCR识别")
                val item9 = ItemDto(9, AppConstants.Companion.CHAPTER_9TH, "人脸美化")
                val item10 = ItemDto(10, AppConstants.Companion.CHAPTER_10TH, "人眼实时跟踪与渲染")
                items.add(item1)
                items.add(item2)
                items.add(item3)
                items.add(item4)
                items.add(item5)
                items.add(item6)
                items.add(item7)
                items.add(item8)
                items.add(item9)
                items.add(item10)
                return items
            }

        fun getSections(chapterNum: Int): List<ItemDto> {
            val items = ArrayList<ItemDto>()
            if (chapterNum == 1) {
                items.add(ItemDto(1, AppConstants.Companion.CHAPTER_1TH_PGM_01, AppConstants.Companion.CHAPTER_1TH_PGM_01))
            }
            if (chapterNum == 2) {
                items.add(ItemDto(1, AppConstants.Companion.CHAPTER_2TH_PGM_01, AppConstants.Companion.CHAPTER_2TH_PGM_01))
            }
            if (chapterNum == 3) {
                items.add(ItemDto(1, AppConstants.Companion.CHAPTER_3TH_PGM, AppConstants.Companion.CHAPTER_3TH_PGM))
            }
            if (chapterNum == 4) {
                items.add(ItemDto(1, AppConstants.Companion.CHAPTER_4TH_PGM, AppConstants.Companion.CHAPTER_4TH_PGM))
            }
            if (chapterNum == 5) {
                items.add(ItemDto(1, AppConstants.Companion.CHAPTER_5TH_PGM, AppConstants.Companion.CHAPTER_5TH_PGM))
            }
            if (chapterNum == 6) {
                items.add(ItemDto(1, AppConstants.Companion.CHAPTER_6TH_PGM, AppConstants.Companion.CHAPTER_6TH_PGM))
            }
            if (chapterNum == 7) {
                items.add(ItemDto(1, AppConstants.Companion.CHAPTER_7TH_PGM, AppConstants.Companion.CHAPTER_7TH_PGM))
                items.add(ItemDto(2, AppConstants.Companion.CHAPTER_7TH_PGM_VIEW_MODE, AppConstants.Companion.CHAPTER_7TH_PGM_VIEW_MODE))
            }
            if (chapterNum == 8) {
                items.add(ItemDto(1, AppConstants.Companion.CHAPTER_8TH_PGM_OCR, AppConstants.Companion.CHAPTER_8TH_PGM_OCR))
                items.add(ItemDto(2, AppConstants.Companion.CHAPTER_8TH_PGM_ID_NUM, AppConstants.Companion.CHAPTER_8TH_PGM_ID_NUM))
                items.add(ItemDto(3, AppConstants.Companion.CHAPTER_8TH_PGM_DESKEW, AppConstants.Companion.CHAPTER_8TH_PGM_DESKEW))
            }
            if (chapterNum == 9) {
                items.add(ItemDto(1, AppConstants.Companion.CHAPTER_9TH_PGM_II, AppConstants.Companion.CHAPTER_9TH_PGM_II))
                items.add(ItemDto(2, AppConstants.Companion.CHAPTER_9TH_PGM_EPF, AppConstants.Companion.CHAPTER_9TH_PGM_EPF))
                items.add(ItemDto(3, AppConstants.Companion.CHAPTER_9TH_PGM_MASK, AppConstants.Companion.CHAPTER_9TH_PGM_MASK))
                items.add(ItemDto(4, AppConstants.Companion.CHAPTER_9TH_PGM_FACE, AppConstants.Companion.CHAPTER_9TH_PGM_FACE))
            }
            if (chapterNum == 10) {
                items.add(ItemDto(1, AppConstants.Companion.CHAPTER_10TH_PGM, AppConstants.Companion.CHAPTER_10TH_PGM))
            }
            return items
        }
    }
}
