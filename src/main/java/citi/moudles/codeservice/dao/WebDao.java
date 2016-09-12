package citi.moudles.codeservice.dao;

import citi.moudles.codeservice.model.AnnouncementEntity;
import citi.moudles.codeservice.model.CompanyEntity;
import citi.moudles.codeservice.model.FinanceEntity;
import citi.moudles.codeservice.model.NewsEntity;
import citi.util.cmk.dbOp.DBConnection;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by coolAutumn on 7/29/16.
 */
@Named("webDao")
public class WebDao {

    @Autowired
    @Qualifier("sessionFactory")
    SessionFactory sessionFactory;

    public List<CompanyEntity> getCodeList(){
        List<CompanyEntity> list = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT * FROM company ORDER BY code")
                .addEntity(CompanyEntity.class).list();
        DBConnection connection  =  new DBConnection();
        try {
            ResultSet resultSet = connection.getCon().createStatement().executeQuery("SELECT * FROM company ORDER BY code");
            while(resultSet.next())
                System.out.println(resultSet.getString(1)+","+resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<AnnouncementEntity> getAnnouncementList(String code){
        List<AnnouncementEntity> list = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT * FROM announcement WHERE code='"+code+"' ORDER  BY ptime DESC;")
                .addEntity(AnnouncementEntity.class).list();
        return list;
    }

    public List<CompanyEntity> getCompanyData(String code){
        List<CompanyEntity> list = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT * FROM company WHERE code='"+code+"';")
                .addEntity(CompanyEntity.class).list();
        return list;
    }

    public List<FinanceEntity> getFrequency1(String code){
        List<FinanceEntity> list = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT date,chg,dr,replay FROM finance WHERE code='" + code + "' ORDER BY date")
                .addEntity(FinanceEntity.class).list();
        return list;
    }

    public List<FinanceEntity> getFrequency2(String code){
        List<FinanceEntity> list = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT date,open,close,dr,replay FROM finance WHERE code='" + code + "' ORDER BY date")
                .addEntity(FinanceEntity.class).list();
        return list;
    }

    public List<NewsEntity> getNewsList(String code){
        List<NewsEntity> list = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT * FROM news WHERE code='" + code + "' ORDER BY ptime DESC ")
                .addEntity(NewsEntity.class).list();
        return list;
    }

    public List<FinanceEntity> getStockData(String code){
        List<FinanceEntity> list = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT * FROM finance WHERE code='" + code + "' ORDER BY date DESC ")
                .addEntity(FinanceEntity.class).list();
        return list;
    }
}
