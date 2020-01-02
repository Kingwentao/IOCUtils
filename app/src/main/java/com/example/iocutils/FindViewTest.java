package com.example.iocutils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baselibrary.OnClick;
import com.example.baselibrary.ViewById;
import com.example.baselibrary.ViewUtils;

public class FindViewTest extends AppCompatActivity {

    @ViewById(R.id.tvFile)
    TextView tvFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        tvFile.setText("注解成功");
    }


    @OnClick({R.id.tvFile})
    private void onClick(View v) {
        Toast.makeText(this,"注入点击方法成功",Toast.LENGTH_LONG).show();
    }
}
