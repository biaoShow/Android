package com.example.biao.demo1;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biao.demo1.SocialContact.CardFriendBean;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * 自定义Dialog类
 * Created by biao on 2018/4/2.
 */

public class MyDialog extends Dialog {
    private Bitmap bitmap;
    private Animation animation;
    private CardFriendBean cfbean;
    private TextView tv_save, tv_cansel;
    private Context context;
    private ImageView iv_dialog_rRCode;
    private LinearLayout ll_share_allitem;

//    public MyDialog(@NonNull Context context, CardFriendBean cfbean){
//        super(context);
//        this.cfbean = cfbean;
//        this.context = context;
//    }


    public MyDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shareqrcode_dialog);
        iv_dialog_rRCode = findViewById(R.id.iv_dialog_qRCode);
        tv_save = findViewById(R.id.tv_save);
        tv_cansel = findViewById(R.id.tv_cancel);
        ll_share_allitem = findViewById(R.id.ll_share_allitem);
        cfbean = new CardFriendBean();
        String str = cfbean.url = "www.baidu.com";
        //初始化动画
        animation = AnimationUtils.loadAnimation(context, R.anim.ll_dialog_animation);
        bitmap = QRCodeUtil.createQRImage(str, 500, 500, null);
        iv_dialog_rRCode.setImageBitmap(bitmap);

        //点击保存图片按钮，调用saveQRcoed犯法保存图片到本地
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQRcoed(bitmap, context);
            }
        });

        //点击取消按钮，关闭dialog
        tv_cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    /**
     * 保存图片（二维码）到本地方法
     *
     * @param bitmap  图片资源
     * @param context 调用方法上下文
     */
    public void saveQRcoed(Bitmap bitmap, Context context) {
        //获取外置内存状态
        String state = Environment.getExternalStorageState();
        //判断是否为挂载状态
        //未挂载
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context,"内存卡未挂载!",Toast.LENGTH_SHORT);
        } else {
            //已挂载
            //获取储存地址
            File floder = new File(Environment.getExternalStorageDirectory(), "Img");
            //判断地址是否存在
            if (!floder.exists()) {
                //不存在，则创建
                floder.mkdirs();
            }

            //创建文件名
            String fileName = UUID.randomUUID().toString();
            File file = new File(floder, fileName + ".jpg");

            try {
                //创建输出流
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                Toast.makeText(context, "保存成功！", Toast.LENGTH_SHORT).show();

                //发送广播通知更新数据库
                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * dialog 渐变效果
     */
    public void bgAnimation() {
        ll_share_allitem.startAnimation(animation);
    }


    /**
     * dialog 动画拉伸效果
     */
    public void onScaleAnimationBySpringWay() {
        SpringSystem springSystem = SpringSystem.create();
        Spring spring = springSystem.createSpring();
        spring.setCurrentValue(0.5f);
        spring.setSpringConfig(new SpringConfig(100, 10));
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                super.onSpringUpdate(spring);
                float currentValue = (float) spring.getCurrentValue();
                ll_share_allitem.setScaleX(currentValue);
                ll_share_allitem.setScaleY(currentValue);
            }
        });
        spring.setEndValue(1.0f);
    }

}

