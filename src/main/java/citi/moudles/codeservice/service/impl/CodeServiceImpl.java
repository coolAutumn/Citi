package citi.moudles.codeservice.service.impl;

import citi.moudles.codeservice.dao.WebDao;
import citi.moudles.codeservice.model.AnnouncementEntity;
import citi.moudles.codeservice.model.CompanyEntity;
import citi.moudles.codeservice.model.FinanceEntity;
import citi.moudles.codeservice.model.NewsEntity;
import citi.moudles.codeservice.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by coolAutumn on 7/29/16.
 */
@Component
@Transactional
public class CodeServiceImpl implements CodeService {

    @Autowired
    public WebDao webDao;

    public List<CompanyEntity> getCodeList() {
        return webDao.getCodeList();
    }

    public List<AnnouncementEntity> getAnnouncementList(String code) {
        return webDao.getAnnouncementList(code);
    }

    public List<CompanyEntity> getCompanyData(String code) {
        return webDao.getCompanyData(code);
    }

    public List<FinanceEntity> getFrequency1(String code) {
        return webDao.getFrequency1(code);
    }

    public List<FinanceEntity> getFrequency2(String code) {
        return webDao.getFrequency2(code);
    }

    public List<NewsEntity> getNewsList(String code) {
        return webDao.getNewsList(code);
    }

    public List<FinanceEntity> getStockData(String code) {
        return webDao.getStockData(code);
    }
}
