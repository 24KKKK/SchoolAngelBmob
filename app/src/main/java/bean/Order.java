package bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by diy on 2017-07-31.
 */

public class Order extends BmobObject
{
    private String ORDERNUM;
    private String APART;
    private String DORMITORY;
    private String NAME;
    private String FOODNUM;
    //private String SUBMITDATETIME;

    public String getORDERNUM()
    {
        return ORDERNUM;
    }

    public void setORDERNUM(String ORDERNUM)
    {
        this.ORDERNUM = ORDERNUM;
    }

    public String getAPART()
    {
        return APART;
    }

    public void setAPART(String APART)
    {
        this.APART = APART;
    }

    public String getDORMITORY()
    {
        return DORMITORY;
    }

    public void setDORMITORY(String DORMITORY)
    {
        this.DORMITORY = DORMITORY;
    }

    public String getNAME()
    {
        return NAME;
    }

    public void setNAME(String NAME)
    {
        this.NAME = NAME;
    }

    public String getFOODNUM()
    {
        return FOODNUM;
    }

    public void setFOODNUM(String FOODNUM)
    {
        this.FOODNUM = FOODNUM;
    }

    /*public String getSUBMITDATETIME()
    {
        return SUBMITDATETIME;
    }

    public void setSUBMITDATETIME(String SUBMITDATETIME)
    {
        this.SUBMITDATETIME = SUBMITDATETIME;
    }*/
}
