package citi.moudles.loginandregister.model;

import javax.persistence.*;

/**
 * Created by coolAutumn on 7/21/16.
 */
@Entity
@Table(name = "userinfo", schema = "citi", catalog = "")
public class UserinfoEntity {
    private String phone;
    private String passwd;
    private String uname;

    @Id
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "passwd")
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Basic
    @Column(name = "uname")
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserinfoEntity that = (UserinfoEntity) o;

        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (passwd != null ? !passwd.equals(that.passwd) : that.passwd != null) return false;
        if (uname != null ? !uname.equals(that.uname) : that.uname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = phone != null ? phone.hashCode() : 0;
        result = 31 * result + (passwd != null ? passwd.hashCode() : 0);
        result = 31 * result + (uname != null ? uname.hashCode() : 0);
        return result;
    }
}
