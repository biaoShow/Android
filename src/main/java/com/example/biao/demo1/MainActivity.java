package com.example.biao.demo1;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
* btn_progress 推荐进度按钮
* btn_share 立即分享按钮
* myRecyclerView RecyclerView控件对象
*
* */
public class MainActivity extends AppCompatActivity {
    //定义控件
    private Button btn_progress,btn_share;
    private TextView tv_copy,tv_address;
    private ImageView iv_QRCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //初始化控件
        btn_progress = findViewById(R.id.btn_progress);
        btn_share = findViewById(R.id.btn_share);
        tv_copy = findViewById(R.id.tv_copy);
        tv_address = findViewById(R.id.tv_address);
        iv_QRCode = findViewById(R.id.iv_QRCode);

        createQRCode();
        onClickAll();
    }
//    调用QRCodeUtil类中静态方法createQRImage创建二维码
    public void createQRCode(){
        Bitmap bitmap = QRCodeUtil.createQRImage((String) tv_address.getText(),500,500,null);
        iv_QRCode.setImageBitmap(bitmap);
    }

    public void onClickAll(){
        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,tv_address.getText(),Toast.LENGTH_SHORT).show();
            }
        });

        iv_QRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
