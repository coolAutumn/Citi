package citi.moudles.codeservice.model;

import javax.persistence.*;

/**
 * Created by coolAutumn on 7/29/16.
 */
@Entity
@Table(name = "company", schema = "citi", catalog = "")
public class CompanyEntity {
    private String code;
    private String sname;
    private String ccname;
    private String ecname;
    private String area;
    private String oname;
    private String trade;
    private String mbussiness;
    private String pname;
    private String link;

    @Id
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "sname")
    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Basic
    @Column(name = "ccname")
    public String getCcname() {
        return ccname;
    }

    public void setCcname(String ccname) {
        this.ccname = ccname;
    }

    @Basic
    @Column(name = "ecname")
    public String getEcname() {
        return ecname;
    }

    public void setEcname(String ecname) {
        this.ecname = ecname;
    }

    @Basic
    @Column(name = "area")
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Basic
    @Column(name = "oname")
    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    @Basic
    @Column(name = "trade")
    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    @Basic
    @Column(name = "mbussiness")
    public String getMbussiness() {
        return mbussiness;
    }

    public void setMbussiness(String mbussiness) {
        this.mbussiness = mbussiness;
    }

    @Basic
    @Column(name = "pname")
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Basic
    @Column(name = "link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyEntity that = (CompanyEntity) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (sname != null ? !sname.equals(that.sname) : that.sname != null) return false;
        if (ccname != null ? !ccname.equals(that.ccname) : that.ccname != null) return false;
        if (ecname != null ? !ecname.equals(that.ecname) : that.ecname != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (oname != null ? !oname.equals(that.oname) : that.oname != null) return false;
        if (trade != null ? !trade.equals(that.trade) : that.trade != null) return false;
        if (mbussiness != null ? !mbussiness.equals(that.mbussiness) : that.mbussiness != null) return false;
        if (pname != null ? !pname.equals(that.pname) : that.pname != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (sname != null ? sname.hashCode() : 0);
        result = 31 * result + (ccname != null ? ccname.hashCode() : 0);
        result = 31 * result + (ecname != null ? ecname.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (oname != null ? oname.hashCode() : 0);
        result = 31 * result + (trade != null ? trade.hashCode() : 0);
        result = 31 * result + (mbussiness != null ? mbussiness.hashCode() : 0);
        result = 31 * result + (pname != null ? pname.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }
}
