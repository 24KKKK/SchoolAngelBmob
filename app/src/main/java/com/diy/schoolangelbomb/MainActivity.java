package com.diy.schoolangelbomb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bean.Order;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import config.Constants;

public class MainActivity extends Activity
{
    //变量***********************************************************************
    private EditText mApart,mDormitory,mName,mFoodNum;
    //public String g_oldLastFiveNum = "";  //从数据库获取的流水号的后五位，生成新订单号需要用的

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化加载Bmob的SDK
        Bmob.initialize(this, Constants.Bmob_APPID);
        //实例化四个变量
        mApart = (EditText) findViewById(R.id.et_apart);
        mDormitory = (EditText) findViewById(R.id.et_dormitory);
        mName = (EditText) findViewById(R.id.et_name);
        mFoodNum = (EditText) findViewById(R.id.et_foodNum);
    }

    //提交按钮点击事件
    public void bt_submit_onClick(View view){

        //生成新的19位流水号***********************************************************
        //先获取数据库旧流水号的后五位
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.order("-createdAt");//降序排列
        query.setLimit(1);
        query.findObjects(new FindListener<Order>()
        {
            @Override
            public void done(List<Order> list, BmobException e)
            {
                if (e==null){

                    String strApart = mApart.getText().toString();
                    String strDormitory = mDormitory.getText().toString();
                    String strName = mName.getText().toString();
                    String strFoodNum = mFoodNum.getText().toString();
                    Log.i("从界面获得的数据：",strApart+"  "+strDormitory+"  "+strName+"  "+strFoodNum);
                    //判断一下为空
                    if(strApart.equals("")){
                        Toast.makeText(MainActivity.this,"楼号不能为空",Toast.LENGTH_LONG).show();
                        return ;
                    }
                    else if (strDormitory.equals("")){
                        Toast.makeText(MainActivity.this,"宿舍号不能为空",Toast.LENGTH_LONG).show();
                        return ;
                    }
                    else if (strFoodNum.equals("")){
                        Toast.makeText(MainActivity.this,"菜品号不能为空",Toast.LENGTH_LONG).show();
                        return ;
                    }

                    String strOldLastFiveNum = list.get(0).getORDERNUM();
                    strOldLastFiveNum = strOldLastFiveNum.substring(14,19);
                    Log.i("取出来的后五位：",strOldLastFiveNum);  //后五位获取成功

                    //将后五位转为整数，然后+1，再变为String型
                    String strNewLastFiveNum = haoAddOne(strOldLastFiveNum);
                    Log.i("旧五位+1之后：",strNewLastFiveNum);

                    //获取系统时间，例如20170810150738，来为下一步拼接19位流水号准备
                    String orderDateTime = getDateTime();
                    Log.i("获取的14位时间：",orderDateTime);

                    //拼接19位流水号
                    String strOrderNum = orderDateTime+strNewLastFiveNum;
                    Log.i("拼接的19位流水号：",strOrderNum);

                    //19位流水号拼接完成，将数据存入数据库
                    Order order = new Order();
                    order.setORDERNUM(strOrderNum);  //19位流水号
                    order.setAPART(strApart);
                    order.setDORMITORY(strDormitory);
                    order.setNAME(strName);
                    order.setFOODNUM(strFoodNum);

                    //提交到服务器上
                    order.save(new SaveListener<String>()
                    {
                        @Override
                        public void done(String s, BmobException e)
                        {
                            if (e==null) {
                                Toast.makeText(MainActivity.this,"提交成功",Toast.LENGTH_LONG).show();
                            }
                            else {
                                //System.out.println("提交失败"+e.toString());
                                Toast.makeText(MainActivity.this,"提交失败"+e.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });
    }

    //后五位流水号加1
    public String haoAddOne(String strOldFiveLastNum){
        System.out.println("传递的旧五位流水号："+strOldFiveLastNum);
        int intNum = 0;
        intNum = Integer.valueOf(strOldFiveLastNum).intValue();
        intNum++;
        String strNum = Integer.toString(intNum);
        while (strNum.length()<5){
            strNum = "0"+strNum;
        }
        System.out.println("+1之后的五位流水号："+strNum);
        return strNum;
    }

    //生成订单日期的时间串，20170802103509
    public String getDateTime(){
        String DATETIME;   //时间日期
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        DATETIME = dateFormat.format(date);  //获取的系统时间例如20170802094624
        return DATETIME;
    }
}
