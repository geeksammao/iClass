package com.sam.main.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.sam.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选课的主界面
 * 用于显示所有课程
 */
public class SelectClass extends Activity implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    private SearchView searchView; // 用于搜索课程的搜索框
    private ListView svShowList; // 展示自动完成的列表
    private SimpleAdapter simpleAdapter; // 为课程列表提供adapter

    private String [] classToSelect; // 可供选择的课程
    private List<Map<String,String>> listItems;

    private String stuID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_class);

        initView();

    }
// 重写返回键，使得添加课程后可以实时更新
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(SelectClass.this,ClassOverview.class);
            intent.putExtra("StuID",stuID);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        classToSelect = new String[] {"离散数学",
                "C语言程序设计","微积分（上）",
                "国际金融与商业概述","电工实习",
                "面向对象技术与设计","数据库技术","软件工程概论",
                "线性代数","电路理论"};
        stuID = getIntent().getStringExtra("StuID");

        searchView = (SearchView)findViewById(R.id.search_view);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);

        listItems = new ArrayList<Map<String,String>>(); // 为simpleadapter提供课程名称数据的列表
        //为列表添加数据
        for (int i = 0;i<classToSelect.length;i++){
            Map<String,String> map = new HashMap<String,String>();
            map.put("ClassName",classToSelect[i]);
            listItems.add(map);
        }

        svShowList = (ListView)findViewById(R.id.search_list);
        svShowList.setTextFilterEnabled(true);
        simpleAdapter = new SimpleAdapter(this,listItems,R.layout.class_list_item,
                new String[] {"ClassName"},new int[]{R.id.text_item}); // 实例化simpleAdapter
        svShowList.setAdapter(simpleAdapter);
        svShowList.setOnItemClickListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        if (TextUtils.isEmpty(newText)){
            svShowList.clearTextFilter();  // 清除listView的过滤
        }
        else {
            svShowList.setFilterText(newText); // 使用用户输入内容对listview列表项进行过滤
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(SelectClass.this,ClassDetail.class); // 点击任一个Item都打开课程详情页面
        intent.putExtra("ClassName",listItems.get(position).get("ClassName")); // 将该Item所代表的课程名称保存
        intent.putExtra("StuID", stuID);
        startActivity(intent);
    }
}