package com.example.mrv.wqhrd;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    public static final Integer TEXT_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
    }

    public void help(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("帮助");
        builder.setMessage("通过移动各种棋子，帮助曹操从初始位置移到期盼最下方中部，从出口逃走。");
        builder.setPositiveButton("我知道了",null);
        builder.show();
    }

    public void choose_level(View view) {
        Intent intent = new Intent(this, RoundActivity.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }
}
