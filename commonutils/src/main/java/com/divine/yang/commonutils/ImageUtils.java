package com.divine.yang.commonutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author: Divine
 * CreateDate: 2020/10/20
 * Describe:
 */
public class ImageUtils {

    public static String setImageName() {
        String name;
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddd");
        Date date = new Date(System.currentTimeMillis());
        name = sf.format(date) + getNonce_str();
        return name;
    }

    /**
     * 生成8位随机数
     *
     * @return
     */
    public static String getNonce_str() {
        String SYMBOLS = "0123456789";
        Random RANDOM = new SecureRandom();
        char[] nonceChars = new char[8];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

    public static String getImageName(String path) {
        String temp[] = path.replaceAll("\\\\", "/").split("/");
        String fileName = "";
        if (temp.length > 1) {
            fileName = temp[temp.length - 1];
        }
        return fileName;
    }

    /**
     * 旋转Bitmap
     *
     * @param b
     * @param rotateDegree
     * @return
     */
    public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) rotateDegree);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
        return rotaBitmap;
    }

    public Bitmap stringToBitmap(String string) {
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

    public String bitmapToString(Bitmap bitmap) {
        //将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * 图片压缩-质量压缩
     *
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */

    public static String compressImage(String filePath,String targetPath) {
        //压缩文件路径 照片路径/
        int quality = 90;//压缩比例0-100
        //        Bitmap bm = BitmapFactory.decodeFile(filePath);//获取一定尺寸的图片
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片

        int degree = getRotateAngle(filePath);//获取相片拍摄角度

        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = setRotateAngle(degree, bm);
        }
        File outputFile = new File(targetPath);
        try {
            //            if (!outputFile.exists()) {
            //                outputFile.getParentFile().mkdirs();
            //                //outputFile.createNewFile();
            //            } else {
            //                outputFile.delete();
            //            }

            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            //            new File(filePath).delete();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
        return outputFile.getPath();
    }


    /**
     * 图片压缩-质量压缩
     *
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */

    //    public static String compressImage(String filePath) {
    //
    //
    //
    //        //压缩文件路径 照片路径/
    //        String targetPath = FileUtil.getAppPath() + "/IMG_" + System.currentTimeMillis() + ".jpg";
    //        int quality = 90;//压缩比例0-100
    //        Bitmap bm = BitmapFactory.decodeFile(filePath);//获取一定尺寸的图片
    //
    //        ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    //        int degree = getRotateAngle(filePath);//获取相片拍摄角度
    //
    //        if (degree != 0) {//旋转照片角度，防止头像横着显示
    //            bm = setRotateAngle(degree,bm);
    //        }
    //        File outputFile = new File(targetPath);
    //        try {
    //
    //            FileOutputStream out = new FileOutputStream(outputFile);
    //            while (baos.toByteArray().length / 1024 > 1024){
    //                baos.reset();
    //                bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
    //                quality-=10;
    //            }
    //            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(baos.toByteArray());
    //            Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream,null,null);
    //            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
    //            out.close();
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            return filePath;
    //        }
    //        return outputFile.getPath();
    //    }

    /**
     * 获取图片的旋转角度
     *
     * @param filePath
     * @return
     */
    public static int getRotateAngle(String filePath) {
        int rotate_angle = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate_angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate_angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate_angle = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate_angle;
    }

    /**
     * 旋转图片角度
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap setRotateAngle(int angle, Bitmap bitmap) {

        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(angle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                         bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;

    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
