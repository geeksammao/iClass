package com.sam.main.student;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.sam.R;
import com.sam.main.database.MyDatabaseHelper;

/**
 * Created by sammao525 on 2014/7/20.
 */
public class ClassDelete extends Activity implements View.OnClickListener {
    private int numOfStu; // 选该门课的人数
    private String className;
    private String teacherName;
    private String day;
    private String stuID;

    private TextView classNameText;
    private TextView numOfStuText;
    private TextView teacherNameText;
    private TextView dayText;
    private Button deleteButton;

    private MyDatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_delete);

        init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(ClassDelete.this,ClassOverview.class);
            intent.putExtra("StuID",stuID);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init() {
        initData();
        initView();
    }

    private void initData() {
        stuID = getIntent().getStringExtra("StuID");
        className = getIntent().getStringExtra("className");
        teacherName = getIntent().getStringExtra("teacherName");
        day = getIntent().getStringExtra("day");
        dbHelper = new MyDatabaseHelper(this, "Class.db", null, 1);

        Cursor cursor_1 = dbHelper.getReadableDatabase().rawQuery("select * from class " +
                        "where classname=? " +
                        "and day=?",
                new String[]{className, day});
        numOfStu = cursor_1.getCount();
    }

    private void initView() {
        classNameText = (TextView) findViewById(R.id.class_name_delete);
        teacherNameText = (TextView) findViewById(R.id.teacher_name_delete);
        dayText = (TextView) findViewById(R.id.day_delete);
        numOfStuText = (TextView) findViewById(R.id.num_of_stu);
        deleteButton = (Button) findViewById(R.id.delete_button);

        classNameText.setText(className);
        teacherNameText.setText("老师：" + teacherName);
        dayText.setText("时间：" + day);
        numOfStuText.setText("人数：" + String.valueOf(numOfStu));

        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        deleteData(dbHelper.getReadableDatabase(),
                stuID,className,day);
        Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
        deleteButton.setEnabled(false);
    }

    private void deleteData(SQLiteDatabase db,String stuId,String cName,String date){
        db.execSQL("delete from class " +
                "where stu_id=?" +
                "and classname=?" +
                "and day=?",
                new String[]{stuId,cName,date});
    }
}