package com.sam.main.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.sam.R;

/**
 * Created by sammao525 on 2014/7/12.
 */
public class TeacherLogin extends Activity {
    private EditText passwordEditText;
    private  EditText loginEditText;
    private Button teaButton;
    // 两个已注册的用户名
    private  final String ID_A = "james";
    private  final String ID_B = "kobe";
    // 对应的两个密码
    private  final String PASS_A = "james";
    private  final String PASS_B = "kobe";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_login);

        initView();

        teaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = loginEditText.getText().toString();
                String passWord = passwordEditText.getText().toString();

                if ((userName.equals(ID_A)&&passWord.equals(PASS_A))||(userName.equals(ID_B)&&passWord.equals(PASS_B))){
                    Intent intent = new Intent(TeacherLogin.this,ClassOpen.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initView() {
        loginEditText = (EditText)findViewById(R.id.tea_username);
        passwordEditText = (EditText)findViewById(R.id.tea_password);
        teaButton =  (Button)findViewById(R.id.tea_button);
    }
}