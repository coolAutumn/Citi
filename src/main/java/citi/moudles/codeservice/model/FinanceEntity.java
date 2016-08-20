package citi.moudles.codeservice.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by coolAutumn on 7/29/16.
 */
@Entity
@Table(name = "finance", schema = "citi", catalog = "")
@IdClass(FinanceEntityPK.class)
public class FinanceEntity {
    private Date date;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal chg;
    private Long volume;
    private String code;
    private String dr;
    private String replay;
    private Double turnover;

    @Id
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "open")
    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    @Basic
    @Column(name = "high")
    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    @Basic
    @Column(name = "low")
    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    @Basic
    @Column(name = "close")
    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    @Basic
    @Column(name = "chg")
    public BigDecimal getChg() {
        return chg;
    }

    public void setChg(BigDecimal chg) {
        this.chg = chg;
    }

    @Basic
    @Column(name = "volume")
    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    @Id
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "dr")
    public String getDr() {
        return dr;
    }

    public void setDr(String dr) {
        this.dr = dr;
    }

    @Basic
    @Column(name = "replay")
    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    @Basic
    @Column(name = "turnover")
    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinanceEntity that = (FinanceEntity) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (open != null ? !open.equals(that.open) : that.open != null) return false;
        if (high != null ? !high.equals(that.high) : that.high != null) return false;
        if (low != null ? !low.equals(that.low) : that.low != null) return false;
        if (close != null ? !close.equals(that.close) : that.close != null) return false;
        if (chg != null ? !chg.equals(that.chg) : that.chg != null) return false;
        if (volume != null ? !volume.equals(that.volume) : that.volume != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (dr != null ? !dr.equals(that.dr) : that.dr != null) return false;
        if (replay != null ? !replay.equals(that.replay) : that.replay != null) return false;
        if (turnover != null ? !turnover.equals(that.turnover) : that.turnover != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (open != null ? open.hashCode() : 0);
        result = 31 * result + (high != null ? high.hashCode() : 0);
        result = 31 * result + (low != null ? low.hashCode() : 0);
        result = 31 * result + (close != null ? close.hashCode() : 0);
        result = 31 * result + (chg != null ? chg.hashCode() : 0);
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (dr != null ? dr.hashCode() : 0);
        result = 31 * result + (replay != null ? replay.hashCode() : 0);
        result = 31 * result + (turnover != null ? turnover.hashCode() : 0);
        return result;
    }
}
