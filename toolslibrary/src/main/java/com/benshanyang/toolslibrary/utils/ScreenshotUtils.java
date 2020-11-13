package com.benshanyang.toolslibrary.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @ClassName: ScreenshotUtils
 * @Description: 截图工具
 * @Author: YangKuan
 * @Date: 2020/6/22 11:43
 */
public class ScreenshotUtils {
    /**
     * 截取scrollview的屏幕
     *
     * @param viewGroup
     * @return
     */
    public static Bitmap getBitmapByView(ViewGroup viewGroup) {
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(viewGroup.getWidth(), viewGroup.getHeight(), Bitmap.Config.ARGB_8888);
        viewGroup.draw(new Canvas(bitmap));
        return compressImage(bitmap);
    }

    //保存图片
    public static boolean saveBitmap(Context context, Bitmap bmp, String imageName) {
        boolean saveFlag = true;
        try {
            File childFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String fileName = childFolder.getAbsolutePath() + "/" + (TextUtils.isEmpty(imageName) ? System.currentTimeMillis() : imageName) + ".png";
            File imageFile = new File(fileName);
            OutputStream fOut = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //适配Android 10
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DESCRIPTION, "This is an image");
                values.put(MediaStore.Images.Media.DISPLAY_NAME, "Image.png");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                values.put(MediaStore.Images.Media.TITLE, "Image.png");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/");

                ContentResolver resolver = context.getContentResolver();
                Uri insertUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (insertUri != null) {
                    fOut = resolver.openOutputStream(insertUri);
                }
            } else {
                fOut = new FileOutputStream(imageFile);
            }
            if (fOut != null) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 60, fOut);//将bg输出至文件
                fOut.flush();
                fOut.close(); // do not forget to close the stream
            }
            //保存图片后发送广播通知更新数据库
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));
        } catch (Exception e) {
            e.printStackTrace();
            saveFlag = false;
        }
        return saveFlag;
    }

    /**
     * 压缩图片
     *
     * @param image
     * @return
     */
    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于250K,大于继续压缩
        while (baos.toByteArray().length / 1024 > 1024 && options > 10) {
            // 重置baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 每次都减少10
            options -= 10;
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

}
