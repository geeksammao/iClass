package com.sam.main.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import com.sam.R;
import com.sam.main.student.StudentLogin;

/**
 * 启动页面
 * 用来判定跳转到哪个页面，此功能暂未完成
 */
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

      if (SystemClock.currentThreadTimeMillis() >= 2){  // 播放2秒的启动动画
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}