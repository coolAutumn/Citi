package citi.moudles.codeservice.action;

import Stock.Announcement;
import citi.moudles.codeservice.model.AnnouncementEntity;
import citi.moudles.codeservice.service.CodeService;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by coolAutumn on 7/29/16.
 */
@Component
public class GetAnnouncementList implements Action {
    public InputStream inputStream;

    private String code = null;
    private int limit_day = 700;
    private ArrayList<Announcement> newsArrayList;

    @Autowired
    CodeService codeService;

    public String execute() throws Exception {
        if(code == null){
            inputStream = new ByteArrayInputStream("paramslack".getBytes());
            return SUCCESS;
        }
        inputStream = new ByteArrayInputStream(toJson().getBytes());
        return SUCCESS;
    }

    public ArrayList<Announcement> init() {
        code = ServletActionContext.getRequest().getParameter("code");

        try {
            ArrayList<Announcement> arrayList = new ArrayList<Announcement>();
            List<AnnouncementEntity> result = codeService.getAnnouncementList(code);
            Date now = new Date();
            for(AnnouncementEntity announcementEntity : result){
                Date pDate = announcementEntity.getPtime();
                int day = daybetween(pDate, now);

                if (day < limit_day) {
                    Announcement announcement = new Announcement();
                    announcement.setPtime(pDate);
                    announcement.setTitle(announcementEntity.getTitle());
                    announcement.setUrl(announcementEntity.getLink());
                    arrayList.add(announcement);
                } else {
                    break;
                }
            }
            this.newsArrayList = arrayList;
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int daybetween(Date smalldate, Date bigdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smalldate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bigdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public String toJson() {
        init();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String json = "{\"" + code + "\":[\n";
        for (Announcement a : newsArrayList) {
            json += "{\"pubDate\":\"" + format.format(a.getPtime()) + "\"" + ",\"title\":\"" + a.getTitle() + "\""
                    + ",\"url\":\"" + a.getUrl() + "\"" + "},\n";
        }
        json = json.substring(0, json.length() - 2);
        json += "\n]}";
        return json;
    }

    public InputStream getInputStream() {

        return inputStream;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

