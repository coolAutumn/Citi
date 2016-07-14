package citi.global;


import java.io.*;

/**
 * 常量
 */
public class Constant {
	/**
	 * 公用
	 */
	public final static String EXECUTE_SUCCESS = "1";//执行成功
	public final static String EXECUTE_FAIL = "0";//执行失败
	public final static String EXECUTE_ERROR = "-1";//执行错误
	public static int maxSessionNumber=-1;
	public static int currentSessionNumber=0;
	/**
	 * 上传类型
	 */
	public final static String USER_UPLOAD_AVATAR = "0";//上传头像
	public final static String USER_UPLOAD_IMAGES="1";//上传图片
	public final static String USER_UPLOAD_3DMODEL="2";//上传3D模型
	public final static String USER_UPLOAD_PDF="3";//上传PDF
	public final static String USER_UPLOAD_PPT="4";//上传PPT

	/**
	 * 文件类型
	 */
	public final static String FILE_IMAGE = "image";//图片
	public final static String FILE_3DMODEL="3dmodel";//3d模型
	public final static String FILE_PDF="pdf";//pdf文件
	public final static String FILE_PPT="ppt";//ppt文件
	public final static String FILE_WORD="word";//word文件
	public final static String FILE_EXCEL="excel";//excel文件
	/**
	 * Application key
	 */
	public final static String LOGIN_STUDENT_MAP = "loginStudentMap";//用户id
	public final static String LOGIN_TEACHER_MAP = "loginTeacherMap";//用户id
	public final static String LOGIN_MANAGER_MAP = "loginManagerMap";//用户id

	/**
	 * 用户类型
	 */
	public final static String USER_TYPE_STUDENT = "1";//学生
	public final static String USER_TYPE_TEACHER = "2";//教师
	public final static String USER_TYPE_MANAGER = "3";//管理员
	public final static String USER_TYPE_PLATFORM = "4";//平台用户



	/**
	 * Session key
	 */
	public final static String USER_ID = "userID";//用户id
	public final static String USER_NAME = "userName";//用户名
	public final static String USER_TYPE = "userType";//用户类型
	public final static String USER_AVATAR = "userAvatar";//用户头像
	public final static String COLLEGE_ID = "collegeID";//大学id
	public final static String COLLEGE_NAME = "collegeName";//大学名称
	public final static String COLLEGE_LGOG = "collegeLogo";//大学Logo路径
	public final static String DEPARTMENT_ID = "deptID";//院系名称


	/**
	 * 文件类型
	 */
	public final static String FILE_COURSE_IMAGE = "image";//课程图片
	public final static String FILE_COURSE_3DMODEL="3dmodel";//课程3d模型
	public final static String FILE_COURSE_COMPOSER="composer";//课程composer3d模型
	public final static String FILE_COURSE_SOLIDWORK="solidwork";//课程solidwork3d模型
	public final static String FILE_COURSE_PDF="pdf";//课程pdf文件
	public final static String FILE_COURSE_PPT="ppt";//课程ppt文件
	public final static String FILE_COURSE_WORD="word";//课程word文件
	public final static String FILE_COURSE_EXCEL="excel";//课程excel文件
	public final static String FILE_IMAGE_AVATAR="avatar";//头像
	public final static String FILE_COLLEGE_LOGO_IMAGE = "college_logo";//大学logo图片
	public final static String FILE_Item_LOGO_IMAGE = "item_logo";//条目logo图片
	public final static String FILE_QUESTION_IMAGE="question_img";//题目的图片
	public final static String FILE_QUESTION_VIDEO="question_video";//题目视频
	public final static String FILE_ATTACHMENT_COVER="cover";


	//分页
	public final static int PAGE_SIZE=10;//每页默认显示的条目数

	//教师编辑卷子类型
	public final static int TEST_PAPER=0;//普通用于考试的卷子
	public final static int QUIZ_PAPER_AFTER_CLASS=1;//教师布置的用于课后练习的卷子
}
