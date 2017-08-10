/*想在getOldLastFiveNum方法里面使用handler实现子线程的什么东西，最终还是没实现。*/
//提交按钮点击事件
public void bt_submit_onClick(View view){
        String strApart=mApart.getText().toString();
        String strDormitory=mDormitory.getText().toString();
        String strName=mName.getText().toString();
        String strFoodNum=mFoodNum.getText().toString();
        //判断一下为空
        if(strApart.equals("")||strDormitory.equals("")||strFoodNum.equals("")){
        return;
        }
        //获取时间和订单号
        //String DATETIME = createDate_Time();
        //String strOldLastFiveNum = getOldLastFiveNum();
        String strOrderNum=createOrderNum();
        Log.i("生成的新的流水号：",strOrderNum);
        //不为空可以提交了
        Order order=new Order();
        order.setORDERNUM(strOrderNum);
        order.setAPART(strApart);
        order.setDORMITORY(strDormitory);
        order.setNAME(strName);
        order.setFOODNUM(strFoodNum);
        //order.setSUBMITDATETIME(DATETIME);
        //Log.d("set:", "成功");

        //提交到服务器上
        order.save(new SaveListener<String>()
        {
@Override
public void done(String s,BmobException e)
        {
        if(e==null){
        Toast.makeText(MainActivity.this,"提交成功",Toast.LENGTH_LONG).show();
        }
        else{
        //System.out.println("提交失败"+e.toString());
        Toast.makeText(MainActivity.this,"提交失败"+e.toString(),Toast.LENGTH_LONG).show();
        }
        }
        });
        }

//生成订单编号，返回String编号,调用下面的生成日期和生成后五位流水号方法,num数据库已提交订单的最新后五位流水号
public String createOrderNum(){
        String strOrderNum;   //新订单号19位
        String strNewFiveNum;  //新订单后五位
        String strDateTime;   //时间日期
        //String NEWNUM;     //生成新订单的后五位流水号
        strDateTime=createDateTime();
        strNewFiveNum=createNewLastFiveNum();
        strOrderNum=strDateTime+strNewFiveNum;
        //Log.i("生成的新订单号：",strOrderNum);
        return strOrderNum;
        //http://www.cnblogs.com/Matrix54/archive/2012/05/01/2478158.html
        //JAVA中获取当前系统时间以及修改
        }

//生成订单日期的时间串，20170802103509
public String createDateTime(){
        String DATETIME;   //时间日期
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        DATETIME=dateFormat.format(date);  //获取的系统时间20170802094624
        return DATETIME;
        }
//生成新的后五位流水号
public String createNewLastFiveNum(){
        String strNewLastFiveNum=null;        //后五位流水号
        ////获取后五位流水号
        String strOldFiveLastNum=getOldLastFiveNum();
        Log.i("得到的旧五位流水号：",strOldFiveLastNum);
        strNewLastFiveNum=haoAddOne(strOldFiveLastNum);
        Log.i("生成新的后五位流水号:",strNewLastFiveNum);
        //Log.i("生成新的后五位流水号:",strNewLastFiveNum);
        return strNewLastFiveNum;
        }
//后五位流水号加1
public String haoAddOne(String strOldFiveLastNum){
        System.out.println("传递的旧五位流水号："+strOldFiveLastNum);
        int intNum=0;
        try
        {
        intNum=Integer.valueOf(strOldFiveLastNum).intValue();
        }
        catch(NumberFormatException e)
        {
        e.printStackTrace();
        }
        intNum++;
        System.out.println("+1之后的五位流水号："+intNum);
        //String strNum = intNum.toString();
        String strNum=Integer.toString(intNum);
        while(strNum.length()<5){
        strNum="0"+strNum;
        }
        return strNum;
        }

//获取数据库中的最后一条记录的后五位流水号
public String getOldLastFiveNum(){
        BmobQuery<Order> query=new BmobQuery<Order>();
        query.order("-createdAt");//降序排列
        query.setLimit(1);
        query.findObjects(new FindListener<Order>(){
@Override
public void done(List<Order> list,BmobException e){
        if(e==null){
        String strNum=list.get(0).getORDERNUM();
        strNum=strNum.substring(14,19);
        Log.i("取出来的后五位：",strNum);

        //haoAddOne(strNum);
        g_oldLastFiveNum=strNum;
        Message message=new Message();
        Handler handler=new Handler();
        message.what=1;
        handler.sendMessage(message);
        Log.i("最后一条后五位订单号",g_oldLastFiveNum);
        }
        else{
        Log.i("getOldLastFiveNum错误信息","错误信息为:"+e.getErrorCode()+","+e.getMessage());
        }
        }
        });
        System.out.println("get五位流水号："+g_oldLastFiveNum);
        return g_oldLastFiveNum;
        }

//生成提交日期的时间串，2017-08-02 10：35：09
    /*public String createDate_Time(){
        String DATETIME;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DATETIME = dateFormat.format(date);
        return DATETIME;
    }*/