package com.sam.main.student;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.sam.R;
import com.sam.main.database.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 展示课程详情的页面
 */
public class ClassDetail extends Activity implements  AdapterView.OnItemClickListener {
    private String stuID;
    private String className;
    private String teacherName;
    private String [] days;
    private int [] clickedNumber; // 记录listitem被点击的次数

    private List<Map<String,String>> listItems; // adater的数据来源

    private ListView listView; // 显示课程详情的列表视图
    private SimpleAdapter simpleAdapter;

    private MyDatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_detail);

        init();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init() {
        stuID = getIntent().getStringExtra("StuID");
        className = getIntent().getStringExtra("ClassName"); // 获取课程名称
        days = new String[]{"周二","周三"};
        clickedNumber = new int[]{0,0}; // 初始化为0
        listItems = new ArrayList<Map<String, String>>();

        dbHelper = new MyDatabaseHelper(this,"Class.db",null,1);

        listView = (ListView)findViewById(R.id.class_detail_list);
        for (int i = 0;i < 2;i++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("Class",className);
            map.put("Teacher","老师:" + getTeacherName());
            map.put("Day","时间:" + days[i]);
            listItems.add(map);
        }
        simpleAdapter = new SimpleAdapter(this,listItems,
                R.layout.class_detail_item,new String[]{"Class","Teacher","Day"},
                new int[]{R.id.class_name_item,R.id.teacher_name_item,R.id.day_item});

        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);
    }

    private String getTeacherName() {  // 默认为两个老师各分配五门课
        if (className.equals("离散数学")||
                className.equals("电工实习")||
                className.equals("线性代数")||
                className.equals("电路理论")||
                className.equals("数据库技术")){

            teacherName = "kobe";
        }
        else {
            teacherName = "james";
        }

        return teacherName;
    }

    //    执行插入数据的方法
    private void insertData(SQLiteDatabase db,String stuID,String teaID,String className,String day){
        db.execSQL("insert into class (stu_id,tea_id,classname,day) " +
                "values (?,?,?,?)",new String[]{
                stuID,teaID,className,day
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        clickedNumber[position]++; // 该item被点击的次数增加一次

        if (isClassExist(position)){   // 如果课程已存在则无法重复添加
            Toast.makeText(this, "该课程已存在", Toast.LENGTH_SHORT).show();
        }

        else {
            if (clickedNumber[position] == 1){
                Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                insertData(dbHelper.getReadableDatabase(), stuID, teacherName, className, days[position]);
//          view.setEnabled(false);
            }
            else if (clickedNumber[position] > 1){
                Toast.makeText(this,"请勿重复添加",Toast.LENGTH_SHORT).show();
            }
        }
    }

// 判断该课程是否已被添加过
    private boolean isClassExist(int position) {
        Boolean isExist = false;
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select * from class " +
                "where classname=? and " +
                "day=? and " +
                "stu_id=?",
                new String[]{className,days[position],stuID});
        while (cursor.moveToNext()){
            String currentClassName = cursor.getString(1);
            String currentDay = cursor.getString(2);
            String currentStuId = cursor.getString(3);
            if (currentClassName.equals(className)&&currentDay.equals(days[position])&&currentStuId.equals(stuID)){
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null){
            dbHelper.close(); // 关闭数据库

        }
    }
}