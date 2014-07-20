package com.sam.main.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.sam.R;
import com.sam.main.student.StudentLogin;
import com.sam.main.teacher.TeacherLogin;

public class LoginActivity extends Activity {

    private Button studentLogin;   // 学生登录
    private  Button teacherLogin;  // 教师登录
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        initView();


    }

    private void initView() {
        studentLogin = (Button)findViewById(R.id.student_login);
        teacherLogin = (Button)findViewById(R.id.teacher_login);

        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, StudentLogin.class);
                startActivity(intent);
                finish(); // 需要修改的部分
            }
        });

        teacherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, TeacherLogin.class);
                startActivity(intent);
                finish(); // 需要修改的部分
            }
        });
    }
}
