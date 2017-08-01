package bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by diy on 2017-07-31.
 */

public class Order extends BmobObject
{
    private String APART;
    private String DORMITORY;
    private String NAME;
    private String FOODNUM;

    public String getAPART()
    {
        return APART;
    }

    public void setAPART(String APART)
    {
        this.APART = APART;
    }

    public String getFOODNUM()
    {
        return FOODNUM;
    }

    public void setFOODNUM(String FOODNUM)
    {
        this.FOODNUM = FOODNUM;
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
}
