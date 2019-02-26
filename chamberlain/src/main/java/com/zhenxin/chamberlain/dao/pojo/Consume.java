package com.zhenxin.chamberlain.dao.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Consume implements Serializable {
    @Id
    @Column(name = "consume_id")
    private String consumeId;

    /**
     * 标题
     */
    private String title;

    /**
     * 金额
     */
    private String amount;

    /**
     * 发生时间
     */
    @Column(name = "happen_date")
    private Date happenDate;

    @Column(name = "del_flg")
    private Boolean delFlg;

    private static final long serialVersionUID = 1L;

    /**
     * @return consume_id
     */
    public String getConsumeId() {
        return consumeId;
    }

    /**
     * @param consumeId
     */
    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId == null ? null : consumeId.trim();
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取金额
     *
     * @return amount - 金额
     */
    public String getAmount() {
        return amount;
    }

    /**
     * 设置金额
     *
     * @param amount 金额
     */
    public void setAmount(String amount) {
        this.amount = amount == null ? null : amount.trim();
    }

    /**
     * 获取发生时间
     *
     * @return happen_date - 发生时间
     */
    public Date getHappenDate() {
        return happenDate;
    }

    /**
     * 设置发生时间
     *
     * @param happenDate 发生时间
     */
    public void setHappenDate(Date happenDate) {
        this.happenDate = happenDate;
    }

    /**
     * @return del_flg
     */
    public Boolean getDelFlg() {
        return delFlg;
    }

    /**
     * @param delFlg
     */
    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", consumeId=").append(consumeId);
        sb.append(", title=").append(title);
        sb.append(", amount=").append(amount);
        sb.append(", happenDate=").append(happenDate);
        sb.append(", delFlg=").append(delFlg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}