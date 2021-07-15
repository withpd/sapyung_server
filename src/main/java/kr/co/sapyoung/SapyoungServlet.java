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
		if(Constants.DB_ALL.equals(type)) {
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
		}
		
	    final byte[] data = out.toByteArray();
	    retJson = new String(data);
		resp.setContentType("text/json");
		resp.getWriter().write(retJson);
	}
}