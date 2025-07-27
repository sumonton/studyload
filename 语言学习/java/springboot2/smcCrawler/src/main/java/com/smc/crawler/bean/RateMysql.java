package com.smc.crawler.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 
 * @TableName rate_mysql
 */
@TableName(value ="rate_mysql")
@Data
@Component
public class RateMysql implements Serializable {
    /**
     * 
     */
    private Double usdRmb;

    /**
     * 
     */
    private Double usdInr;

    private Double usdIdr;

    /**
     * 
     */
    private Timestamp updatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RateMysql other = (RateMysql) that;
        return (this.getUsdRmb() == null ? other.getUsdRmb() == null : this.getUsdRmb().equals(other.getUsdRmb()))
            && (this.getUsdInr() == null ? other.getUsdInr() == null : this.getUsdInr().equals(other.getUsdInr()))
                && (this.getUsdIdr() == null ? other.getUsdIdr() == null : this.getUsdIdr().equals(other.getUsdIdr()))
                && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUsdRmb() == null) ? 0 : getUsdRmb().hashCode());
        result = prime * result + ((getUsdInr() == null) ? 0 : getUsdInr().hashCode());
        result = prime * result + ((getUsdIdr() == null) ? 0 : getUsdInr().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", usdRmb=").append(usdRmb);
        sb.append(", usdInr=").append(usdInr);
        sb.append(", usdIdr=").append(usdIdr);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}