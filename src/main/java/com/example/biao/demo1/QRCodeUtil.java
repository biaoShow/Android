package com.example.biao.demo1;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建二维码类
 * Created by biao on 2018/3/30.
 */

public class QRCodeUtil {
    /**
    *      创建二维码方法，并返回bitmap类型的二维码信息
    *      bitmap 储存生成二维码容器
    *
    * */
    public static Bitmap createQRImage(String content, int widthPix, int heightPix, Bitmap logoBm) {
        Bitmap bitmap = null;
        try {
            if (content == null || "".equals(content)) {
                return null;
            }
            //配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
            hints.put(EncodeHintType.MARGIN, 1); //default is 4
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = null;
            try {
                bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix,
                        heightPix, hints);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            int[] pixels = new int[widthPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            //生成二维码
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
