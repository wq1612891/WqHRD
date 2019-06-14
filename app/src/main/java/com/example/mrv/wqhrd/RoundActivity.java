package com.example.mrv.wqhrd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class RoundActivity extends AppCompatActivity {
    public static final Integer TEXT_REQUEST = 1;
    public static final String EXTRA_MESSAGE =
            "com.example.mrv.huarongdao.extra.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
    }


    public void back(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    public void level1(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 1);
        startActivityForResult(intent, TEXT_REQUEST);
    }
    public void level2(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 2);
        startActivityForResult(intent, TEXT_REQUEST);
    }
    public void level3(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 3);
        startActivityForResult(intent, TEXT_REQUEST);
    }
    public void level4(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 4);
        startActivityForResult(intent, TEXT_REQUEST);
    }
    public void level5(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 5);
        startActivityForResult(intent, TEXT_REQUEST);
    }
}
