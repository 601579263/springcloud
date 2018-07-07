package entity;

import java.util.Date;

public class UserSensor {
    private Integer sId;

    private Integer stId;

    private String sImei;

    private String sName;

    private Date createDate;

    private String sXValue;

    private String sYValue;

    private Date sInstallDate;

    private Date sWorkDate;

    private String sDescription;

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getStId() {
        return stId;
    }

    public void setStId(Integer stId) {
        this.stId = stId;
    }

    public String getsImei() {
        return sImei;
    }

    public void setsImei(String sImei) {
        this.sImei = sImei == null ? null : sImei.trim();
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName == null ? null : sName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getsXValue() {
        return sXValue;
    }

    public void setsXValue(String sXValue) {
        this.sXValue = sXValue == null ? null : sXValue.trim();
    }

    public String getsYValue() {
        return sYValue;
    }

    public void setsYValue(String sYValue) {
        this.sYValue = sYValue == null ? null : sYValue.trim();
    }

    public Date getsInstallDate() {
        return sInstallDate;
    }

    public void setsInstallDate(Date sInstallDate) {
        this.sInstallDate = sInstallDate;
    }

    public Date getsWorkDate() {
        return sWorkDate;
    }

    public void setsWorkDate(Date sWorkDate) {
        this.sWorkDate = sWorkDate;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription == null ? null : sDescription.trim();
    }
}