package com.example.biao.demo1;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.TransactionTooLargeException;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biao.demo1.SocialContact.CardFriendBean;
import com.example.biao.demo1.SocialContact.MainActivityPresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * 自定义Dialog类
 * Created by biao on 2018/4/2.
 */

public class MyDialog extends Dialog {
    Bitmap bitmap;
    private CardFriendBean cfbean;
    private TextView tv_save,tv_cansel;
    private Context context;
    private ImageView iv_dialog_rRCode;
    MainActivityPresenter mainActivityPresenter = new MainActivityPresenter();

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
        setContentView(R.layout.mydialog_layout);
        iv_dialog_rRCode = findViewById(R.id.iv_dialog_qRCode);
        tv_save = findViewById(R.id.tv_save);
        tv_cansel = findViewById(R.id.tv_cancel);
        cfbean = new CardFriendBean();
        String str = cfbean.url = "www.baidu.com";
//        LinearLayout linearLayout = findViewById(R.id.ll_share_item);
//
//        int length = model.strArray.length;
//        for (int i = 0;i<length;i++){
//            View view = View.inflate(this.context,R.layout.title_layout,null);
//            linearLayout.addView(view);
//        }


        bitmap = QRCodeUtil.createQRImage(str, 500, 500, null);
        iv_dialog_rRCode.setImageBitmap(bitmap);

        //保存图片到本地
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取外置内存状态
                String state = Environment.getExternalStorageState();
                //判断是否为挂载状态
                //未挂载
                if (!state.equals(Environment.MEDIA_MOUNTED)) {
                    return;
                } else {
                    //已挂载
                    //获取储存地址
                   // String url = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Img/qrcodeImg/";
                    File floder = new File(Environment.getExternalStorageDirectory(),"Img");
                    Log.i("XXXXX:",Environment.getExternalStorageDirectory().toString());
                    //判断地址是否存在
                    if (!floder.exists()) {
                        //不存在，则创建
                        floder.mkdirs();
                    }

                    //创建文件名
                    String fileName = UUID.randomUUID().toString();
                    File file = new File(floder,fileName + ".jpg");

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
        });

        tv_cansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

