package com.example.biao.demo1;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

/**
 * 自定义Dialog类
 * Created by biao on 2018/4/2.
 */

public class MyDialog extends Dialog {
    private MainActivity myMainActivity;
    private View view;

    public MyDialog(@NonNull Context context, int dialog) {
        super(context);
    }
    private ImageView iv_Dialog_QRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog_layout);

        myMainActivity = (MainActivity) getOwnerActivity();
        iv_Dialog_QRCode = findViewById(R.id.iv_Dialog_QRCode);
        Bitmap bitmap = QRCodeUtil.createQRImage(MainActivity.str,500,500,null);
        iv_Dialog_QRCode.setImageBitmap(bitmap);
    }

    public void setView(View view) {
        this.view = view;
    }
}

