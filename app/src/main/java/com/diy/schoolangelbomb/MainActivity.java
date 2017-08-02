package com.diy.schoolangelbomb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import bean.Order;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
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
    //提交按钮点击事件
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

        //获取时间和订单号
        //String DATETIME = createDate_Time();
        String ORDERNUM = createOrderNum();
        //不为空可以提交了
        Order order = new Order();
        order.setORDERNUM(ORDERNUM);
        order.setAPART(apart);
        order.setDORMITORY(dormitory);
        order.setNAME(name);
        order.setFOODNUM(foodNum);
        //order.setSUBMITDATETIME(DATETIME);
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

    //生成订单编号，返回String编号
    public String createOrderNum(){
        String ORDERNUM;   //订单号
        String DATETIME;   //时间日期
        String NUM;        //后五位流水号
        DATETIME = createDateTime();
        NUM = createNum();
        ORDERNUM = DATETIME+NUM;
        return ORDERNUM;
        //http://www.cnblogs.com/Matrix54/archive/2012/05/01/2478158.html
        //JAVA中获取当前系统时间以及修改
    }

    //生成订单日期的时间串，20170802103509
    public String createDateTime(){
        String DATETIME;   //时间日期
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        DATETIME = dateFormat.format(date);  //获取的系统时间20170802094624
        return DATETIME;
    }
    //生成后五位流水号
    public String createNum(){
        String NUM="00001";        //后五位流水号
        ////获取后五位流水号
        String strNUM ="00000";
        boolean flag = true;
        if (flag){
            NUM=haoAddOne(strNUM);
            flag = false;
        }
        else {
            NUM = haoAddOne(NUM);
        }

        return NUM;
    }
    //后五位流水号加1
    public static String haoAddOne(String str){
        Integer intNUM = Integer.parseInt(str);
        intNUM++;
        String strNUM = intNUM.toString();
        while (strNUM.length()<5){
            strNUM = "0"+strNUM;
        }
        return strNUM;
    }
    //获取数据库中的最后一条记录的后五位流水号,程序有错，不用这个方法了
    /*public String getLastNum(){
        String bql ="select * from GameScore";
        new BmobQuery<Order>().doSQLQuery(bql,new SQLQueryListener<Order>(){

            @Override
            public void done(BmobQueryResult<Order> result, BmobException e) {
                if(e ==null){
                    List<Order> list = (List<Order>) result.getResults();
                    if(list!=null && list.size()>0){
                        Log.i("smile", "查询成功.");
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }
            }
        });

        return null;
    }*/

    //获取数据库中的最后一条记录的后五位流水号
    public String getLastNum(){
        final String[] arrNUM = {null};
        final String NUM = "";
        try{
            BmobQuery<String> query = new BmobQuery<String>();
            query.order("-createdAt");//降序排列
        }
        catch (Exception e){
            System.out.println("e:"+e.toString());
        }


        /*query.findObjects(new FindListener<Order>()
        {
            @Override
            public void done(List<Order> list, BmobException e)
            {
                String strNUM = list.get(0).getORDERNUM();
                System.out.println("最后一条订单号："+strNUM);
            }
        });*/
        return NUM;
    }

    //生成提交日期的时间串，2017-08-02 10：35：09
    /*public String createDate_Time(){
        String DATETIME;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DATETIME = dateFormat.format(date);
        return DATETIME;
    }*/
}
