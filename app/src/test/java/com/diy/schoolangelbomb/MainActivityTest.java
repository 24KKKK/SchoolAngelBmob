package com.diy.schoolangelbomb;

import org.junit.Test;

/**
 * Created by diy on 2017-08-02.
 */
public class MainActivityTest
{
    MainActivity mainActivity = new MainActivity();
    @Test
    public void createOrderNum() throws Exception
    {
        String ORDERNUM = mainActivity.createOrderNum();
        System.out.println("订单号为："+ORDERNUM);
    }
}