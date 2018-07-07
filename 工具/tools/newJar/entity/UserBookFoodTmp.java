package entity;

public class UserBookFoodTmp {
    private Long bId;

    private Long uId;

    private Long rId;

    private Long cbId;

    private String cbName;

    private Integer cbNum;

    private Double cbPrice;

    public Long getbId() {
        return bId;
    }

    public void setbId(Long bId) {
        this.bId = bId;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public Long getCbId() {
        return cbId;
    }

    public void setCbId(Long cbId) {
        this.cbId = cbId;
    }

    public String getCbName() {
        return cbName;
    }

    public void setCbName(String cbName) {
        this.cbName = cbName == null ? null : cbName.trim();
    }

    public Integer getCbNum() {
        return cbNum;
    }

    public void setCbNum(Integer cbNum) {
        this.cbNum = cbNum;
    }

    public Double getCbPrice() {
        return cbPrice;
    }

    public void setCbPrice(Double cbPrice) {
        this.cbPrice = cbPrice;
    }
}