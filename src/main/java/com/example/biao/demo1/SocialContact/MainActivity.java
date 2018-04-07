package com.example.biao.demo1.SocialContact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biao.demo1.R;

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
    public MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //初始化控件
        presenter = new MainActivityPresenter();
        btn_progress = findViewById(R.id.btn_progress);
        btn_share = findViewById(R.id.btn_share);
        tv_copy = findViewById(R.id.tv_copy);
        tv_address = findViewById(R.id.tv_address);
        iv_QRCode = findViewById(R.id.iv_QRCode);

        iv_QRCode.setImageBitmap(presenter.createQRCode());
        onClickAll();
    }

    /**
     * 所有控件点击事件实现
     * tv_copy 复制文本点击事件
     * iv_QRCode 二维码点击事件
     */
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
                presenter.emergeMyDialog(MainActivity.this);
            }
        });
    }



}
