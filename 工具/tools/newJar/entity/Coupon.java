package entity;

import java.util.Date;

public class Coupon extends CouponKey {
    private String rName;

    private String couName;

    private String couImage;

    private String couDetail;

    private Date couExpiryStart;

    private Date couExpiryEnd;

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName == null ? null : rName.trim();
    }

    public String getCouName() {
        return couName;
    }

    public void setCouName(String couName) {
        this.couName = couName == null ? null : couName.trim();
    }

    public String getCouImage() {
        return couImage;
    }

    public void setCouImage(String couImage) {
        this.couImage = couImage == null ? null : couImage.trim();
    }

    public String getCouDetail() {
        return couDetail;
    }

    public void setCouDetail(String couDetail) {
        this.couDetail = couDetail == null ? null : couDetail.trim();
    }

    public Date getCouExpiryStart() {
        return couExpiryStart;
    }

    public void setCouExpiryStart(Date couExpiryStart) {
        this.couExpiryStart = couExpiryStart;
    }

    public Date getCouExpiryEnd() {
        return couExpiryEnd;
    }

    public void setCouExpiryEnd(Date couExpiryEnd) {
        this.couExpiryEnd = couExpiryEnd;
    }
}