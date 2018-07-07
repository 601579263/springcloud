package entity;

public class SensorType {
    private Integer stId;

    private String sImeiType;

    private Integer ruleId;

    private String stName;

    private Integer stType;

    private Integer stCheckDay;

    private String stDescription;

    private String stImgUrl;

    public Integer getStId() {
        return stId;
    }

    public void setStId(Integer stId) {
        this.stId = stId;
    }

    public String getsImeiType() {
        return sImeiType;
    }

    public void setsImeiType(String sImeiType) {
        this.sImeiType = sImeiType == null ? null : sImeiType.trim();
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName == null ? null : stName.trim();
    }

    public Integer getStType() {
        return stType;
    }

    public void setStType(Integer stType) {
        this.stType = stType;
    }

    public Integer getStCheckDay() {
        return stCheckDay;
    }

    public void setStCheckDay(Integer stCheckDay) {
        this.stCheckDay = stCheckDay;
    }

    public String getStDescription() {
        return stDescription;
    }

    public void setStDescription(String stDescription) {
        this.stDescription = stDescription == null ? null : stDescription.trim();
    }

    public String getStImgUrl() {
        return stImgUrl;
    }

    public void setStImgUrl(String stImgUrl) {
        this.stImgUrl = stImgUrl == null ? null : stImgUrl.trim();
    }
}