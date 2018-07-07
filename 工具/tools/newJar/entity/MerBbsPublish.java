package entity;

import java.util.Date;

public class MerBbsPublish {
    private Long bbsId;

    private Long rId;

    private String merName;

    private String bbsTitle;

    private String bbsContent;

    private String bbsContentImage;

    private Integer bbsZanCount;

    private Integer bbsCommentCount;

    private Integer bbsState;

    private String bbsRemark;

    private Date createDate;

    public Long getBbsId() {
        return bbsId;
    }

    public void setBbsId(Long bbsId) {
        this.bbsId = bbsId;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName == null ? null : merName.trim();
    }

    public String getBbsTitle() {
        return bbsTitle;
    }

    public void setBbsTitle(String bbsTitle) {
        this.bbsTitle = bbsTitle == null ? null : bbsTitle.trim();
    }

    public String getBbsContent() {
        return bbsContent;
    }

    public void setBbsContent(String bbsContent) {
        this.bbsContent = bbsContent == null ? null : bbsContent.trim();
    }

    public String getBbsContentImage() {
        return bbsContentImage;
    }

    public void setBbsContentImage(String bbsContentImage) {
        this.bbsContentImage = bbsContentImage == null ? null : bbsContentImage.trim();
    }

    public Integer getBbsZanCount() {
        return bbsZanCount;
    }

    public void setBbsZanCount(Integer bbsZanCount) {
        this.bbsZanCount = bbsZanCount;
    }

    public Integer getBbsCommentCount() {
        return bbsCommentCount;
    }

    public void setBbsCommentCount(Integer bbsCommentCount) {
        this.bbsCommentCount = bbsCommentCount;
    }

    public Integer getBbsState() {
        return bbsState;
    }

    public void setBbsState(Integer bbsState) {
        this.bbsState = bbsState;
    }

    public String getBbsRemark() {
        return bbsRemark;
    }

    public void setBbsRemark(String bbsRemark) {
        this.bbsRemark = bbsRemark == null ? null : bbsRemark.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}