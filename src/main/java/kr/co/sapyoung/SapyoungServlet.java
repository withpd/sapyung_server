package kr.co.sapyoung;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.sapyoung.db.DbDao;
import kr.co.sapyoung.db.DbFactory;
import kr.co.sapyoung.db.DbTest;
import kr.co.sapyoung.vo.UserDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/* 
 	mvn tomcat7:run
*/

@WebServlet(name = "SapyoungServlet", urlPatterns = "/sapyoung")
public class SapyoungServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	DbDao dao = new DbDao(DbFactory.getSqlSessionFactory());

	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String type = req.getParameter("type");
		
	    System.out.printf(Constants.LOG_START_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), type);
		String retJson = null;
		ObjectMapper mapper = null;
		ByteArrayOutputStream out = null;
		
		if("all".equals(type)) {
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
//			mapper.writeValue(out, productList);
		} else if(Constants.DB_RANGE.equals(type)) {
//			String brand = req.getParameter("brand");
//			String offset = req.getParameter("offset");
//			productList = dao.selectRange(offsetInt, offsetInt + Constants.READ_COUNT);
//			mapper = new ObjectMapper();
//			out = new ByteArrayOutputStream();
//			mapper.writeValue(out, productList);
		} else if(type.equals("sapyoung")) {
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, "hello");
		} else if(type.equals("select")) {
			mapper = new ObjectMapper();
			String phone = req.getParameter("phone");
			
			DbTest db = new DbTest("admin", Constants.pw);
			db.connect("database-1.cjvdgquniwjw.ap-northeast-2.rds.amazonaws.com", "3306", "geek9", "com.mysql.cj.jdbc.Driver");		// 立加	
			ArrayList<UserDto> voList = db.select(phone, 1);
			
			int result = voList.size();
			System.out.println(result + "扒");
			
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, voList);
		} else if(type.equals("insert")) {
			mapper = new ObjectMapper();
			String phone = req.getParameter("phone");
			String name = req.getParameter("name");
			String addr = req.getParameter("addr");
			
			DbTest db = new DbTest("admin", Constants.pw);
			db.connect("database-1.cjvdgquniwjw.ap-northeast-2.rds.amazonaws.com", "3306", "geek9", "com.mysql.cj.jdbc.Driver");		// 立加	
			int result = db.insert(name, phone, addr);
			
//			int result = voList.size();
//			System.out.println(result + "扒");
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, result);			
		} else if(type.equals("paging")) {
			
			String page = req.getParameter("page");
			int intPage = Integer.parseInt(page);
			
			mapper = new ObjectMapper();
			
			DbTest db = new DbTest("admin", Constants.pw);
			db.connect("database-1.cjvdgquniwjw.ap-northeast-2.rds.amazonaws.com", "3306", "geek9", "com.mysql.cj.jdbc.Driver");		// 立加	
			ArrayList<UserDto> voList = db.select("", intPage);
			
			int result = voList.size();
			System.out.println(result + "扒");
			
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, voList);
		}
		
	    final byte[] data = out.toByteArray();
	    retJson = new String(data);
		resp.getWriter().write(retJson);
	}
}