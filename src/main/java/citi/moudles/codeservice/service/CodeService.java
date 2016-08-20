package citi.moudles.codeservice.service;

import citi.moudles.codeservice.model.AnnouncementEntity;
import citi.moudles.codeservice.model.CompanyEntity;
import citi.moudles.codeservice.model.FinanceEntity;
import citi.moudles.codeservice.model.NewsEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by coolAutumn on 7/29/16.
 */
@Transactional
public interface CodeService {
    public List<CompanyEntity> getCodeList();
    public List<AnnouncementEntity> getAnnouncementList(String code);
    public List<CompanyEntity> getCompanyData(String code);
    public List<FinanceEntity> getFrequency1(String code);
    public List<FinanceEntity> getFrequency2(String code);
    public List<NewsEntity> getNewsList(String code);
    public List<FinanceEntity> getStockData(String code);
}

