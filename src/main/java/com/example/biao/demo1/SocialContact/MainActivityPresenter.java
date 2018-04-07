package com.example.biao.demo1.SocialContact;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.biao.demo1.MyDialog;
import com.example.biao.demo1.QRCodeUtil;


/**
 * MainActivity 逻辑实现
 * 实现presenter接口，重写接口方法emergeMyDialog实现dialog的显示
 */
public class MainActivityPresenter implements Presenter {

    Bitmap bitmap;
    private CardFriendBean cfbean = new CardFriendBean();
    private MyDialog selectDialog;


    /**
     * dialog样式设置
     *
     * @param context 调用方法的上下文
     */
    @Override
    public void emergeMyDialog(Context context) {
        selectDialog = new MyDialog(context);
        selectDialog.show();
        //设置背景色为透明
        selectDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //selectDialog.setCanceledOnTouchOutside(false);//设置点击空白处不关闭dialog
        //WindowManager m = getWindowManager();
        //Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.height = 1460; //高度设置
        p.width = 1000; //宽度设置
        selectDialog.getWindow().setAttributes(p); //设置
    }


    //    调用QRCodeUtil类中静态方法createQRImage创建二维码
    public Bitmap createQRCode() {
        String str = cfbean.url = "www.baidu.com";
        bitmap = QRCodeUtil.createQRImage(str, 500, 500, null);
        return bitmap;
    }

    /**
     * dialog 动画效果组合
     * bgAnimation() 渐变
     * onScaleAnimationBySpringWay() 拉伸
     */
    public void bgAnimation() {
        selectDialog.bgAnimation();
        selectDialog.onScaleAnimationBySpringWay();
    }
}
