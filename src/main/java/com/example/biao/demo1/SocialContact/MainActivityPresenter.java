package com.example.biao.demo1.SocialContact;

import android.content.Context;
import android.graphics.Bitmap;
import com.example.biao.demo1.MyDialog;
import com.example.biao.demo1.QRCodeUtil;

/**
 *
 * Created by biao on 2018/4/4.
 */

public class MainActivityPresenter implements Presenter {

    private CardFriendBean cfbean=new CardFriendBean();

    @Override
    public void emergeMyDialog(Context context) {
        MyDialog selectDialog = new MyDialog(context);
        selectDialog.show();
        //WindowManager m = getWindowManager();
        //Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.height = 1460; //高度设置
        p.width = 1000; //宽度设置
        selectDialog.getWindow().setAttributes(p); //设置
    }

    //    调用QRCodeUtil类中静态方法createQRImage创建二维码
    public Bitmap createQRCode(){
        String str = cfbean.url = "www.baidu.com";
        Bitmap bitmap = QRCodeUtil.createQRImage(str,500,500,null);
        return bitmap;
    }

}
