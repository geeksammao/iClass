package com.sam.main.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.sam.R;
import com.sam.main.student.StudentLogin;
import com.sam.main.teacher.TeacherLogin;

public class MainActivity extends Activity {

    private Button studentLogin;   // 学生登录
    private  Button teacherLogin;  // 教师登录
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initView();

        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StudentLogin.class);
                startActivity(intent);
            }
        });

        teacherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TeacherLogin.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        studentLogin = (Button)findViewById(R.id.student_login);
        teacherLogin = (Button)findViewById(R.id.teacher_login);
    }
}
