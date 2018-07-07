package entity;

import java.util.Date;

public class Shop {
    private Long sId;

    private String sName;

    private Integer sCity;

    private Date sCreatetime;

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName == null ? null : sName.trim();
    }

    public Integer getsCity() {
        return sCity;
    }

    public void setsCity(Integer sCity) {
        this.sCity = sCity;
    }

    public Date getsCreatetime() {
        return sCreatetime;
    }

    public void setsCreatetime(Date sCreatetime) {
        this.sCreatetime = sCreatetime;
    }
}