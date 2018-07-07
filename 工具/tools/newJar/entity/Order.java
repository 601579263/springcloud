package entity;

import java.util.Date;

public class Order {
    private String orderId;

    private String orderName;

    private Integer orderType;

    private Integer orderSource;

    private String bookId;

    private Double bPrice;

    private Integer state;

    private String uId;

    private Integer isUseCoupon;

    private String userCouponId;

    private Integer couponPrice;

    private Integer isUseJifen;

    private Integer useJifen;

    private String orderContent;

    private Double orderPrice;

    private Date createTime;

    private Date payTime;

    private Date effictiveDate;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName == null ? null : orderName.trim();
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }

    public Double getbPrice() {
        return bPrice;
    }

    public void setbPrice(Double bPrice) {
        this.bPrice = bPrice;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId == null ? null : uId.trim();
    }

    public Integer getIsUseCoupon() {
        return isUseCoupon;
    }

    public void setIsUseCoupon(Integer isUseCoupon) {
        this.isUseCoupon = isUseCoupon;
    }

    public String getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(String userCouponId) {
        this.userCouponId = userCouponId == null ? null : userCouponId.trim();
    }

    public Integer getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Integer couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Integer getIsUseJifen() {
        return isUseJifen;
    }

    public void setIsUseJifen(Integer isUseJifen) {
        this.isUseJifen = isUseJifen;
    }

    public Integer getUseJifen() {
        return useJifen;
    }

    public void setUseJifen(Integer useJifen) {
        this.useJifen = useJifen;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent == null ? null : orderContent.trim();
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getEffictiveDate() {
        return effictiveDate;
    }

    public void setEffictiveDate(Date effictiveDate) {
        this.effictiveDate = effictiveDate;
    }
}