package kr.co.sapyoung;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
	
	public static final String KEY_IMG = "img";
	public static final String KEY_LINK = "link";
	public static final String KEY_COLOR = "color";
	public static final String KEY_SIZE = "size";
	public static final String KEY_PRICE = "price";
	public static final String KEY_TITLE = "title";
	
	public static final String DELIM = ",";
	
	public static final String DATE_LOG_FORMAT = "yyyy-MM-dd HH:mm:ss"; 
	public static final String DATE_DB_FORMAT = "yyyyMMddHHmmss";
	
	public static final String LOG_START_FORMAT = "[%s_%s] %s START : %s\n";
	public static final String LOG_END_FORMAT = "[%s_%s] %s ENDED : %s : %d\n";
	public static final Object KEY_URL = "url";
	
	public static final int READ_COUNT = 10;
	
	public static final String DB_ALL = "all";
	public static final String DB_RANGE = "range";
	
	public static final String BRAND_ALL = "all";
	public static final String BRAND_MAJE = "Maje";
	public static final String INSERT_USER = "insertUser";
	public static final String SELECT_USER_BY_MAIL = "selectUserByMail";
	public static final String SELECT_USER = "selectUser";
	public static final String UPDATE_ACCESSTOKEN = "updateAccessToken";
	public static final String INSERT_ITEM = "insertItem";
	public static final String SELECT_ITEM = "selectItem";
	public static final String UPDATE_AMOUNT = "updateAmount";
	public static final String SELECT_ITEM_BY_TYPE = "selectItemByType";
	public static final String DELETE_LIKE_ITEM = "deleteLikeItem";
	public static final String SELECT_ORDER_BY_USER = "selectOrderByUser";
	
	public static boolean isRun = false;
	public static final String ORDER = "order";

	public static final String BASE64_DELIM = "yyyyyy";
	
	public static String getDate(String type) {
		Date d = new Date();
		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat(type);
		return sdf.format(d);
	}
	
}
