package citi.moudles.codeservice.action;

import Stock.Company;
import citi.moudles.codeservice.model.CompanyEntity;
import citi.moudles.codeservice.service.CodeService;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by coolAutumn on 7/29/16.
 */
@Component
public class GetCompanyDataAction implements Action {

    private String code;
    Company company = null;
    InputStream inputStream;

    @Autowired
    public CodeService codeService;

    public Company init() {
        List<CompanyEntity> result = codeService.getCompanyData(code);
        CompanyEntity c = result.get(0);

//        String sname = resultSet.getString(2); // 股票名称
//        String ccname = resultSet.getString(3); // 中文名
//        String ecname = resultSet.getString(4); // 英文名
//        String area = resultSet.getString(5); // 所属地域
//        String oname = resultSet.getString(6); // 曾用名
//        String trade = resultSet.getString(7); // 所属行业
//        String mbusiness = resultSet.getString(8); // 主营行业
//        String pname = resultSet.getString(9); // 产品名称
//        String link = resultSet.getString(10); // 公司网址

        company = new Company();
        company.setSname(c.getSname());
        company.setCcname(c.getCcname());
        company.setEcname(c.getEcname());
        company.setArea(c.getArea());
        company.setOname(c.getOname());
        company.setTrade(c.getTrade());
        company.setMbusiness(c.getMbussiness());
        company.setPname(c.getPname());
        company.setLink(c.getLink());
        return company;
    }

    public String toJson() throws Exception {
        init();
        if(company == null){
            return null;
        }
        String json = "{\n";
        json += "\"code\":\"" + code + "\",\n";
        json += "\"stockname\":\"" + company.getSname() + "\",\n";
        json += "\"chinesename\":\"" + company.getCcname() + "\",\n";
        json += "\"englishname\":\"" + company.getEcname() + "\",\n";
        json += "\"area\":\"" + company.getArea() + "\",\n";
        json += "\"oldname\":\"" + company.getOname() + "\",\n";
        json += "\"trade\":\"" + company.getTrade() + "\",\n";
        json += "\"mainbussiness\":\"" + company.getMbusiness() + "\",\n";
        json += "\"productname\":\"" + company.getPname() + "\",\n";
        json += "\"link\":\"" + company.getLink() + "\"\n";
        json += "}";
        return json;
    }

    public String execute() throws Exception {
        code = ServletActionContext.getRequest().getParameter("code");
        if(code == null){
            inputStream = new ByteArrayInputStream("paramslack".getBytes());
            return SUCCESS;
        }
        inputStream = new ByteArrayInputStream(toJson().getBytes());
        return SUCCESS;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
