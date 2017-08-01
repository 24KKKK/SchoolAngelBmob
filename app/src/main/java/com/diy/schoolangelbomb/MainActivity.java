package com.diy.schoolangelbomb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import bean.Order;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends Activity
{
    //变量***********************************************************************
    private EditText mApart,mDormitory,mName,mFoodNum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("进入oncreate:", " 成功");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化加载Bmob的SDK
        Bmob.initialize(this,"a6f582ebc9e3bbfba14543b08551c623");
        //实例化四个变量
        mApart = (EditText) findViewById(R.id.et_apart);
        mDormitory = (EditText) findViewById(R.id.et_dormitory);
        mName = (EditText) findViewById(R.id.et_name);
        mFoodNum = (EditText) findViewById(R.id.et_foodNum);
        Log.d("oncreate", "成功");
    }

    public void bt_submit_onClick(View view){
        String apart = mApart.getText().toString();
        String dormitory = mDormitory.getText().toString();
        String name = mName.getText().toString();
        String foodNum = mFoodNum.getText().toString();
        Log.d("onclick", "成功");
        //判断一下为空
        if(apart.equals("")||dormitory.equals("")||foodNum.equals("")){
            return ;
        }

        //不为空可以提交了
        Order order = new Order();
        order.setAPART(apart);
        order.setDORMITORY(dormitory);
        order.setNAME(name);
        order.setFOODNUM(foodNum);
        Log.d("set:", "成功");

        //提交到服务器上
        order.save(new SaveListener<String>()
        {
            @Override
            public void done(String s, BmobException e)
            {
                if (e==null)
                {
                    Toast.makeText(MainActivity.this,"提交成功",Toast.LENGTH_LONG).show();
                }
                else
                {
                    System.out.println("提交失败"+e.toString());
                    Toast.makeText(MainActivity.this,"提交失败"+e.toString(),Toast.LENGTH_LONG).show();
                }
            }


        });
    }
}
