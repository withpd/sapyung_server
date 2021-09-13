package kr.co.sapyoung;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.sapyoung.db.DbDao;
import kr.co.sapyoung.db.DbFactory;

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
			String phone = req.getParameter("phone");
			
			System.out.println("phone : " + phone);
			
			
//			DbTest db = new DbTest("admin", "1234");
//			db.connect("database-1.cjvdgquniwjw.ap-northeast-2.rds.amazonaws.com", "3306", "geek9", "com.mysql.cj.jdbc.Driver");		// 접속	
////			db.insert("영수", "100000", "대전");	// 이름/폰/주소
////			db.delete("배성호");
////			db.update("인천", "배성호");
//			ArrayList<UserDto> voList = db.select(phone);
			
//			int result = voList.size();
//			System.out.println(result + "건");
			
			
			
//			String con = req.getParameter("condition");
//			mapper = new ObjectMapper();
//			out = new ByteArrayOutputStream();
//			mapper.writeValue(out, "BaeSungho");
//			UserVo vo = new UserVo();
//			vo.setName("TestUser");
//			vo.setPhone("12345678");
//			vo.setLocation("대구");
//			dao.insertUser(vo);
		} 
		
	    final byte[] data = out.toByteArray();
	    retJson = new String(data);
		resp.setContentType("text/json");
	    resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.getWriter().write(retJson);
	}
}