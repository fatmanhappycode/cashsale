package com.cashsale.bean;

import java.util.List;

/**
 * @Description:商品互动数目
 * @Author: 8-0416
 * @Date: 2018/11/26
 */
public class InteractDTO {
    /** 评论数 */
    private int commentsNumber;
    /** 点赞数 */
    private int likeNumber;
    /** 分享数 */
    private int shareNumber;
    /** 具体评论 */
    private List<InteractInfoDTO> comments;
    /** 具体点赞 */
    private List<InteractInfoDTO> like;
    /** 具体分享 */
    private List<InteractInfoDTO> share;

    public int getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(int commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getShareNumber() {
        return shareNumber;
    }

    public void setShareNumber(int shareNumber) {
        this.shareNumber = shareNumber;
    }

    public List<InteractInfoDTO> getComments() {
        return comments;
    }

    public void setComments(List<InteractInfoDTO> comments) {
        this.comments = comments;
    }

    public List<InteractInfoDTO> getLike() {
        return like;
    }

    public void setLike(List<InteractInfoDTO> like) {
        this.like = like;
    }

    public List<InteractInfoDTO> getShare() {
        return share;
    }

    public void setShare(List<InteractInfoDTO> share) {
        this.share = share;
    }
}
