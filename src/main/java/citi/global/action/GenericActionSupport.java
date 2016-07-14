package citi.global.action;

import citi.global.Constant;
import citi.util.DateJsonValueProcessorUtil;
import citi.util.MD5;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Peng on 14-2-9.
 *
 */
@Component
public class GenericActionSupport extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware{
    protected Map<String, Object> mSessionMap;
    protected HttpServletRequest mServletRequest;
    protected HttpServletResponse mServletResponse;
    protected ServletContext application;


    public String savePath;	//保存长传文件的路径
    //返回到前台的JSON字符串
    public String jsonString;

    public void setServletRequest(HttpServletRequest httpServletRequest) {
        mServletRequest = httpServletRequest;
        application = httpServletRequest.getSession().getServletContext();
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        mServletResponse = httpServletResponse;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        mSessionMap = stringObjectMap;
    }

    public Map<String, Object> getSessionMap() {
        return mSessionMap;
    }

    public HttpServletRequest getServletRequest() {
        return mServletRequest;
    }

    public HttpServletResponse getServletResponse() {
        return mServletResponse;
    }

    public ServletContext getApplication() {
        return application;
    }

    public HttpSession getHttpSession() {
        if(mServletRequest != null) {
            return mServletRequest.getSession();
        } else {
            return null;
        }
    }

    public String getSavePath() {
        return ServletActionContext.getServletContext().getRealPath("/" + savePath);
    }
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
    /**
     * @Enclosing_Method: outJsonString
     * @Description: 将JSON返回到前台
     * @param str
     */
    public void outJsonString(String str) {
        getResponse().setContentType("text/javascript;charset=UTF-8");
        outString(str);
    }

    /**
     * @Enclosing_Method: outString
     * @Description: 将字符串返回到前台
     * @param str
     */
    public void outString(String str) {
        try {
            PrintWriter out = getResponse().getWriter();
            if(str.startsWith("[")){
                str=str.substring(1, str.length()-1);
            }
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Enclosing_Method: outXMLString
     * @Description: 将XML格式的数据返回到前台
     * @param xmlStr
     */
    public void outXMLString(String xmlStr) {
        getResponse().setContentType("application/xml;charset=UTF-8");
        outString(xmlStr);
    }

    /**
     * @Enclosing_Method: getRequest
     * @Description:获取HttpServletRequest对象
     * @return
     */
    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * @Enclosing_Method: getResponse
     * @Description: 获得Response对象
     * @return
     */
    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    /**
     * @Enclosing_Method: getSession
     * @Description: 获取Session对象
     * @return
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }


    public void writeJson(Object object) {
        try {
            String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
            ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
            ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
            ServletActionContext.getResponse().getWriter().write(json);
            ServletActionContext.getResponse().getWriter().flush();
            ServletActionContext.getResponse().getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //把对象转化为Json对象
    public void writeJsonArray(Object object){
        try{
            PrintWriter out = mServletResponse.getWriter();

            //对日期进行了处理，把它转换成字符串
            JsonConfig jf = new JsonConfig();
            jf.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessorUtil("yyyy-MM-dd HH:mm:ss"));
            jf.registerJsonValueProcessor(Date.class, new DateJsonValueProcessorUtil("yyyy-MM-dd HH:mm:ss"));

            JSONArray jsonArray= JSONArray.fromObject(object,jf);
           /* System.out.println("拉出来溜溜");

            System.out.println(jsonArray);*/
            out.print(jsonArray);
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //获取展示的基本路径
    public String getShowBasePath()  {
        try {
            return mServletRequest.getScheme() + "://" + InetAddress.getLocalHost().getHostAddress() + ":" + mServletRequest.getServerPort();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "fuck";
        }
    }

    //获取保存的基本路径
    public String getSaveBasePath(){
        return mServletRequest.getServletContext().getRealPath("/");
    }

    public void printForAjax(String s){
        try {
            PrintWriter out = mServletResponse.getWriter();
            out.print(s);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Timestamp getCurrnetDate() throws ParseException {
        //生成精度为19的日期时间
        String  time = new Timestamp(new Date().getTime()).toString().subSequence(0, 19).toString();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new Timestamp(format.parse(time).getTime());
    }

    /**
     * 根据参数获得文件的保存路径，如任一目录不存在，则创建
     * @param nSchoolID 学校Id
     * @param nDepartID 院系Id
     * @param MajorID   专业Id
     * @param nCourseID 课程Id
     * @param fileType  文件类型
     * @return 路径
     * 学校id和文件类型必须有，其它的三个id可以视情况而定，如果没有，设为0
     */
    protected String generateSavePath(int nSchoolID,int nDepartID,int MajorID,int nCourseID, String fileType) throws InterruptedException{
        String sSchool = MD5.GetMD5Code("WHU20160103" + nSchoolID).substring(0,8) + File.separator;
        String sDepart = "-1"+File.separator;
        String sMajor = "-1"+File.separator;
        String sCourse = "-1"+File.separator;
        if(nDepartID != 0){
            sDepart = MD5.GetMD5Code("ISS20160103" + nDepartID).substring(0, 8)+ File.separator;
            if (MajorID != 0){
                sMajor = MD5.GetMD5Code("SPATIAL20160103" + MajorID).substring(0, 8)+ File.separator;
                if (nCourseID != 0){
                    sCourse = MD5.GetMD5Code("C++20160103" + nCourseID).substring(0,8)+ File.separator;
                }
            }
        }
        String sFileType = MD5.GetMD5Code("C++20160103" + fileType).substring(0, 8);
        String sDir = sSchool + sDepart +  sMajor + sCourse + sFileType;
        String sFullPath = getSaveBasePath() + "upload" + File.separator + sDir;
        if(isExitsPath(sFullPath))
            return sFullPath;
        else
            return "oh~no!";
    }

    /**
     * Java 判断多级路径是否存在，不存在就创建
     * @param path
     * @return
     * @throws InterruptedException
     */
    public boolean isExitsPath(String path)throws InterruptedException{
        String [] paths = path.replaceAll("\\\\", "/").split("/");//处理了Windows和Unix的两种情况
        StringBuffer fullPath = new StringBuffer();
        for (int i = 0; i < paths.length; i++) {
            fullPath.append(paths[i]).append("\\");
            File file=new File(fullPath.toString());
            if(!file.exists()){
                file.mkdir();
                System.out.println("创建目录为："+fullPath.toString());
                //Thread.sleep(1500);
            }
        }
        File file = new File(fullPath.toString());//目录全路径
        if (!file.exists()) {
            return false;
        }else{
            return true;
        }
    }

    //判断有效的Session是否存在
    public boolean isSessionExist(){
       return mSessionMap.containsKey(Constant.USER_ID)&& mSessionMap.containsKey(Constant.USER_NAME)&&mSessionMap.containsKey(Constant.USER_TYPE);
    }

    public boolean deleteFileByPath(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }

    public String getBasePath()  {
        try {
            return mServletRequest.getScheme() + "://" + InetAddress.getLocalHost().getHostAddress() + ":" + mServletRequest.getServerPort();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "fuck";
        }
    }
    /**
     * @Enclosing_Method: getRealyPath
     * @Description: 获取项目路径
     * @param path
     * @return
     */
    public String getRealyPath(String path) {
        return getServletContext().getRealPath(path);
    }

    /**
     * @Enclosing_Method: getServletContext
     * @Description: 获取ServletContext对象
     * @return
     */
    public ServletContext getServletContext() {
        return ServletActionContext.getServletContext();
    }

}
