package com.nju.yuq.opencvdemo

import android.app.Activity
import android.content.ContentResolver

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import org.jetbrains.anko.find
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import android.provider.MediaStore
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Log
import com.nju.yuq.opencvdemo.datamodel.ImageSelectUtils
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import java.io.File
import java.io.IOException


class CharpterFirst1Activity : AppCompatActivity(), View.OnClickListener {

    var fileUri: Uri? = null
    val REQUEST_CAPTURE_IMAGE = 1
    val REQUEST_CHOOSE_IMAGE = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_first1)
        val button_img: Button = find(R.id.process_btn)
        button_img.setOnClickListener(this)
        val button_img_recovery: Button = find(R.id.recovery_pic_btn)
        button_img_recovery.setOnClickListener(this)
        val takePicBtn = this.findViewById<View>(R.id.select_pic_btn) as Button
        takePicBtn.setOnClickListener(this)

        val selectPicBtn = this.findViewById<View>(R.id.take_pic_btn) as Button
        selectPicBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.process_btn -> convert2Gray()
            R.id.take_pic_btn -> start2Camera()
            R.id.select_pic_btn -> pickUpImage()
            R.id.recovery_pic_btn -> displaySelectedImage()
        }

    }

    fun convert2Gray(): Unit {
        var src=Mat();
        if (fileUri != null) {
            Log.e("uri test", fileUri!!.path)
            src = Imgcodecs.imread(fileUri!!.path)

        }else{
            val bitmap=BitmapFactory.decodeResource(resources,R.drawable.lena)
            Utils.bitmapToMat(bitmap,src)
        }

        if (src.empty()) {
            return
        }
        var dst = Mat();
        //Utils.bitmapToMat(bitmap, src);
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY)
        val bitmap = grayMat2Bitmap(dst)
        //Utils.matToBitmap(dst, bitmap);
        val iv: ImageView = find(R.id.sample_img);
        iv.setImageBitmap(bitmap)
        src.release()
        dst.release()
    }

    private fun grayMat2Bitmap(result: Mat): Bitmap {
        var image: Mat? = null
//        if (result.cols() > 1000 || result.rows() > 1000) {
//            image = Mat()
//            Imgproc.resize(result, image, Size(result.cols() / 4.0, result.rows() / 4.0))
//        } else {
//            image = result
//        }
        image=result//取消缩小图片
        val bitmap = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.ARGB_8888)
        Imgproc.cvtColor(image, image, Imgproc.COLOR_GRAY2RGBA)
        Utils.matToBitmap(image, bitmap)
        image.release()
        return bitmap
    }

    var file: File? = null;
    private fun start2Camera() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        file = ImageSelectUtils.saveFilePath
        if (file != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file!!)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            } else {
                fileUri = Uri.fromFile(file)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)

            }
            startActivityForResult(intent, REQUEST_CAPTURE_IMAGE)
        }
    }


    private fun pickUpImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "图像选择..."), REQUEST_CHOOSE_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHOOSE_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val uri = data.data
                Log.e("activity_choospic_file", uri!!.path);
                file = File(ImageSelectUtils.getRealPath(uri!!, this))
                fileUri = Uri.fromFile(file)
                Log.e("activity_result_file", fileUri!!.path);
            }
        } else if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            fileUri = Uri.fromFile(file)

        }
        displaySelectedImage()
    }


    private fun displaySelectedImage() {
        val imageView = this.findViewById<View>(R.id.sample_img) as ImageView
        if (fileUri == null) {
            imageView.setImageResource(R.drawable.lena)
            return
        }

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(fileUri!!.getPath(), options)
        val w = options.outWidth
        val h = options.outHeight
        var inSample = 1
//        if (w > 1000 || h > 1000) {
//            while (Math.max(w / inSample, h / inSample) > 1000) {
//                inSample *= 2
//            }
//        }
        options.inJustDecodeBounds = false
        options.inSampleSize = inSample
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bm = BitmapFactory.decodeFile(fileUri!!.getPath(), options)

        val degree = readPictureDegree(fileUri!!.path)
        /**
         * 把图片旋转为正的方向
         */
        val newbitmap = rotaingImageView(degree, bm)
        imageView.setImageBitmap(newbitmap)

    }

    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    open fun readPictureDegree(path: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path);
            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90

                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180

                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return degree
    }

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    fun rotaingImageView(angle: Int, bitmap: Bitmap): Bitmap {
        //旋转图片 动作
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        // 创建新的图片
        val resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
}
