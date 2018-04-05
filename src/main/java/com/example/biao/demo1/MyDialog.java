package com.example.biao.demo1;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.example.biao.demo1.SocialContact.CardFriendBean;

/**
 * 自定义Dialog类
 * Created by biao on 2018/4/2.
 */

public class MyDialog extends Dialog {
    private CardFriendBean cfbean;
//    private Context context;
    private ImageView iv_dialog_rRCode;

//    public MyDialog(@NonNull Context context, CardFriendBean cfbean){
//        super(context);
//        this.cfbean = cfbean;
//        this.context = context;
//    }


    public MyDialog(@NonNull Context context) {
        super(context);
//        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog_layout);
        cfbean = new CardFriendBean();
        String str = cfbean.url = "www.baidu.com";
//        LinearLayout linearLayout = findViewById(R.id.ll_share_item);
//
//        int length = model.strArray.length;
//        for (int i = 0;i<length;i++){
//            View view = View.inflate(this.context,R.layout.title_layout,null);
//            linearLayout.addView(view);
//        }

        iv_dialog_rRCode = findViewById(R.id.iv_dialog_qRCode);
        Bitmap bitmap = QRCodeUtil.createQRImage(str, 500, 500, null);
        iv_dialog_rRCode.setImageBitmap(bitmap);
    }

}

