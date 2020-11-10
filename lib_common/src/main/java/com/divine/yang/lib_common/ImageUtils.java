package com.divine.yang.lib_common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public class ImageUtils {
    /**
     * base64字符串转换成为bitmap对象
     *
     * @param string
     * @return
     */
    public Bitmap base64ToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                                                   bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * bitmap对象，转换为base64字符串
     *
     * @param bitmap
     * @return
     */
    public String bitmapToBase64(Bitmap bitmap) {
        //将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * 获取图片的旋转角度
     *
     * @param filePath
     * @return
     */
    public static int getRotateAngle(String filePath) {
        int rotateAngle = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotateAngle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotateAngle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotateAngle = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotateAngle;
    }

    /**
     * 旋转Bitmap
     *
     * @param rotateDegree
     * @param bitmap
     * @return
     */
    public static Bitmap getRotateBitmap(int rotateDegree, Bitmap bitmap) {
        return getRotateBitmap((float) rotateDegree, bitmap);
    }

    /**
     * 旋转Bitmap
     *
     * @param bitmap
     * @param rotateDegree
     * @return
     */
    public static Bitmap getRotateBitmap(float rotateDegree, Bitmap bitmap) {
        if (bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotateDegree);
            //最后一个参数如果为true，生成的bitmap的大小会比用false小一点，清晰度感觉会差一些，但不是很大，连贯性会比较好，像素之间过渡得比较好，看着柔和一些。
            //false或者true，其实相差很小
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 采样率压缩（设置图片的采样率，降低图片像素）
     *
     * @param filePath     源文件路径
     * @param targetWidth  目标宽度
     * @param targetHeight 目标高度
     * @return bitmap
     */
    public static Bitmap compressBySample(String filePath, int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 为true的时候不会真正加载图片，而是得到图片的宽高信息。只解析图片边沿，获取宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > targetHeight || width > targetWidth) {
            int heightRatio = Math.round((float) height / (float) targetHeight);
            int widthRatio = Math.round((float) width / (float) targetWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        // 采样率
        options.inSampleSize = inSampleSize;
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        return bitmap;
    }

    /**
     * 尺寸压缩（设置图片的采样率，降低图片像素）
     *
     * @param filePath     源文件路径
     * @param targetWidth  目标宽度
     * @param targetHeight 目标高度
     * @return bitmap
     */
    public static Bitmap compressBySize(String filePath, int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 为true的时候不会真正加载图片，而是得到图片的宽高信息。只解析图片边沿，获取宽高
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        int height = options.outHeight;
        int width = options.outWidth;
        int scaleSize = 1;
        if (height > targetHeight || width > targetWidth) {
            int heightRatio = Math.round((float) height / (float) targetHeight);
            int widthRatio = Math.round((float) width / (float) targetWidth);
            scaleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        Canvas canvas = new Canvas(bitmap);
        Rect rect = new Rect(0, 0, width / scaleSize, height / scaleSize);
        canvas.drawBitmap(bitmap, null, rect, null);
        return bitmap;
    }

    /**
     * 图片压缩-质量压缩,降低图片的质量，像素不会减少
     *
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */
    public static Bitmap compressByQuality(String filePath, int targetFileSize) {
        int quality = 90;
        Bitmap sourceBitmap = BitmapFactory.decodeFile(filePath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        sourceBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        int fileSize = byteArrayOutputStream.size();
        while (fileSize > targetFileSize) {
            quality = quality - 10;
            sourceBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }
}
