package com.sam.main.student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.sam.R;


public class StudentLogin extends Activity {
    private EditText passwordEditText;
    private AutoCompleteTextView loginEditText;
    private Button stuButton;
    // 三个已注册的用户名
    private final String ID_A = "U201315088";
    private final String ID_B = "U201315089";
    private final String ID_C = "U201315090";
    // 对应的三个密码
    private final String PASS_A = "123";
    private final String PASS_B = "456";
    private final String PASS_C = "789";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login);

        initView();

        stuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = loginEditText.getText().toString();
                String passWord = passwordEditText.getText().toString();

                if ((userName.equals(ID_A) && passWord.equals(PASS_A)) || (userName.equals(ID_B) && passWord.equals(PASS_B)) || (userName.equals(ID_C) && passWord.equals(PASS_C))) {
                    Intent intent = new Intent(StudentLogin.this, ClassOverview.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initView() {
        loginEditText = (AutoCompleteTextView) findViewById(R.id.stu_username);
        passwordEditText = (EditText) findViewById(R.id.stu_password);
        stuButton = (Button) findViewById(R.id.stu_button);

        String[] id = {ID_A, ID_B, ID_C}; // 存储用户名
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, id);
        loginEditText.setAdapter(adapter);

    }
}
