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
import android.widget.RelativeLayout;
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
 * bitmap 生成二维码图片
 * tv_qrcode_save 二维码保存按钮
 * tv_close_dialog 关闭dialog按钮
 * mv_qrcode dialog中自定义view二维码
 * rl_share_qrcode dialog布局父布局
 */

public class MyDialog extends Dialog {
    private Bitmap bitmap;
    private Animation animation;
    private CardFriendBean cfbean;
    private TextView tv_qrcode_save, tv_close_dialog;
    private Context context;
    private MyView mv_qrcode;
    private RelativeLayout rl_share_qrcode;


    public MyDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shareqrcode_dialog);
        mv_qrcode = findViewById(R.id.mv_qrcode);
        tv_qrcode_save = findViewById(R.id.tv_qrcode_save);
        tv_close_dialog = findViewById(R.id.tv_close_dialog);
        rl_share_qrcode = findViewById(R.id.rl_share_qrcode);
        cfbean = new CardFriendBean();
        String str = cfbean.url = "www.baidu.com";
        //初始化动画
        animation = AnimationUtils.loadAnimation(context, R.anim.rl_dialog_animation);
        bitmap = QRCodeUtil.createQRImage(str, 500, 500, null);
        mv_qrcode.setImageBitmap(bitmap);

        //点击保存图片按钮，调用saveQRcoed方法保存图片到本地
        tv_qrcode_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQRcoed(bitmap, context);
            }
        });

        //点击取消按钮，关闭dialog
        tv_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    /**
     * 保存图片（二维码）到本地方法
     * @param bitmap  图片资源
     * @param context 调用方法上下文
     */
    private void saveQRcoed(Bitmap bitmap, Context context) {
        //获取外置内存状态
        String state = Environment.getExternalStorageState();
        //判断是否为挂载状态
        //未挂载
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context,"内存卡未挂载!",Toast.LENGTH_SHORT).show();
        } else {
            //已挂载
            //获取储存地址
            File floder = new File(Environment.getExternalStorageDirectory(), "Img");
            //判断地址是否存在
            if (!floder.exists()) {
                //不存在，则创建
                boolean s = floder.mkdirs();
                if(s){
                    Toast.makeText(context,"创建文件夹成功！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"创建文件夹失败！",Toast.LENGTH_SHORT).show();
                }
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
        rl_share_qrcode.startAnimation(animation);
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
                rl_share_qrcode.setScaleX(currentValue);
                rl_share_qrcode.setScaleY(currentValue);
            }
        });
        spring.setEndValue(1.0f);
    }

}

