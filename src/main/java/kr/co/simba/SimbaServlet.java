package kr.co.simba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.simba.brand.maje.MajeSelectTask;
import kr.co.simba.db.DbDao;
import kr.co.simba.db.DbFactory;
import kr.co.simba.vo.ItemVO;
import kr.co.simba.vo.Order2VO;
import kr.co.simba.vo.OrderVO;
import kr.co.simba.vo.ProductVO;
import kr.co.simba.vo.UserVO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/* 
 	mvn tomcat7:run
*/

@WebServlet(name = "simbaservlet", urlPatterns = "/Simba")
public class SimbaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	DbDao dao = new DbDao(DbFactory.getSqlSessionFactory());
	@Override
		public void init() throws ServletException {
		super.init();
		if(!Constants.isRun) {
			Constants.isRun = true;
			System.out.printf(Constants.LOG_START_FORMAT, this.getClass().getName(), "INIT", Constants.getDate(Constants.DATE_LOG_FORMAT), Constants.isRun);

			dao.deleteAll();
			MajeSelectTask majeTask = new MajeSelectTask(dao);
			majeTask.start();
		}
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
		List<ProductVO> productList = null;
		ObjectMapper mapper = null;
		ByteArrayOutputStream out = null;
		if(Constants.DB_ALL.equals(type)) {
//			String brand = req.getParameter("brand");
//			String offset = req.getParameter("offset");
			productList = dao.selectAll();
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, productList);
		} else if(Constants.DB_RANGE.equals(type)) {
//			String brand = req.getParameter("brand");
			String offset = req.getParameter("offset");
			int offsetInt = Integer.parseInt(offset);
			productList = dao.selectRange(offsetInt, offsetInt + Constants.READ_COUNT);
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, productList);
		} else if(Constants.INSERT_USER.equals(type)) {
			String id = req.getParameter("id");
			String accessToken = req.getParameter("accessToken");
			String name = req.getParameter("name");
			String thumbNailImg = req.getParameter("thumbNailImg");
			String isEmailValid = req.getParameter("isEmailValid");
			String isEmailVerified = req.getParameter("isEmailVerified");
			String mail = req.getParameter("mail");
			String loginType = req.getParameter("loginType");
			UserVO vo = new UserVO();
			
			vo.setId(id);
			vo.setAccessToken(accessToken);
			vo.setName(name);
			vo.setThumbNailImg(thumbNailImg);
			vo.setEmailValid(Boolean.parseBoolean(isEmailValid));
			vo.setEmailVerified(Boolean.parseBoolean(isEmailVerified));
			vo.setMail(mail);
			vo.setLoginType(loginType);
			
			int result = dao.insertUser(vo);
			
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, result);
			
			System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), Constants.INSERT_USER, result);
		} else if(Constants.SELECT_USER.equals(type)) {
			String mail = req.getParameter("mail");
			List<UserVO> userList = dao.selectUserByMail(mail);
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			if(userList == null) {
				mapper.writeValue(out, 0);
				System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), Constants.SELECT_USER, 0);
			} else {
				mapper.writeValue(out, userList.size());
				System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), Constants.SELECT_USER, userList.size());
			}
		} else if(Constants.UPDATE_ACCESSTOKEN.equals(type)) {
			String accessToken = req.getParameter("accessToken");
			String mail = req.getParameter("mail");
			
			int updateCnt = dao.updateAccessToken(accessToken, mail);
			
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, updateCnt);
			
			System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), Constants.UPDATE_ACCESSTOKEN, updateCnt);
		} else if(Constants.INSERT_ITEM.equals(type)) {
			
			// "&mail=" + mail + "&name=" + name + "&color=" + color + "&length=" + length + "&amount=" + amount + "&price=" + price + "&isLike=" + isLike + "&imgUrl=" + imgUrl + "&saveType=" + saveType
			
			String mail = req.getParameter("mail");
			String name = req.getParameter("name");
			String color = req.getParameter("color");
			String length = req.getParameter("length");
			String amount = req.getParameter("amount");
			String imgUrl = req.getParameter("imgUrl");
			String saveType = req.getParameter("saveType");
			String price = req.getParameter("price");
			String title = req.getParameter("title");
			String cate = req.getParameter("cate");
			title = title.replace("XX", "%");
			ItemVO vo = new ItemVO();
			
			vo.setColor(color);
			vo.setImgUrl(imgUrl);
			vo.setLength(length);
			vo.setMail(mail);
			vo.setName(name);
			vo.setPrice(price);
			vo.setSaveType(saveType);
			vo.setAmount(amount);
			vo.setTitle(title);
			vo.setCate(cate);
			vo.setTime(Constants.getDate(Constants.DATE_DB_FORMAT));
			
			int result = dao.insertItem(vo);
			
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, result);
			
			System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), Constants.INSERT_ITEM + " " + type, result);
		} else if(Constants.SELECT_ITEM.equals(type)) {
			// mail, name, selectColor, title, saveType
			
			String mail = req.getParameter("mail");
//			String name = req.getParameter("name");
			String color = req.getParameter("color");
			String length = req.getParameter("length");
//			String amount = req.getParameter("amount");
//			String imgUrl = req.getParameter("imgUrl");
			String saveType = req.getParameter("saveType");
//			String price = req.getParameter("price");
			String title = req.getParameter("title");
			
			ItemVO vo = new ItemVO();
			vo.setMail(mail);
			vo.setColor(color);
			vo.setLength(length);
			vo.setTitle(title);
			vo.setSaveType(saveType);
			int result = dao.selectItem(vo);
			
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, result);
			
			System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), Constants.SELECT_ITEM + " " + saveType, result);
		} else if(Constants.UPDATE_AMOUNT.equals(type)) {
			String mail = req.getParameter("mail");
			String name = req.getParameter("name");
			String color = req.getParameter("color");
			String length = req.getParameter("length");
			String saveType = req.getParameter("saveType");
			String title = req.getParameter("title");
			String updateAmount = req.getParameter("updateAmount");
			
			ItemVO vo = new ItemVO();
			vo.setMail(mail);
			vo.setName(name);
			vo.setColor(color);
			vo.setLength(length);
			vo.setSaveType(saveType);
			vo.setTitle(title);
			vo.setAmount(updateAmount);
			vo.setUpdateTime(Constants.getDate(Constants.DATE_DB_FORMAT));
			int result = dao.updateAmount(vo);
			
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, result);
			
			System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), "" + Constants.UPDATE_AMOUNT + " " + saveType, result);
		} else if(Constants.SELECT_ITEM_BY_TYPE.equals(type)) {
			String mail = req.getParameter("mail");
			String saveType = req.getParameter("saveType");
			
			ItemVO vo = new ItemVO();
			vo.setMail(mail);
			vo.setSaveType(saveType);
			
			List<ItemVO> itemList = dao.selectItemByType(vo);
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, itemList);
			
			System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), "" + Constants.SELECT_ITEM_BY_TYPE + " " + saveType, itemList.size());
		} else if(Constants.DELETE_LIKE_ITEM.equals(type)) {
			String delListEncode = req.getParameter("json");
			String delListDecode;
			if(delListEncode != null && !delListEncode.equals("")) {
				byte[] decoded = Base64.getDecoder().decode(delListEncode);
				delListDecode = new String(decoded);
				String[] delList = delListDecode.split(Constants.BASE64_DELIM);
				ArrayList<ItemVO> aList = new ArrayList<ItemVO>();
				for(String delItem : delList) {
					mapper = new ObjectMapper();
					aList.add(mapper.readValue(delItem, ItemVO.class));
				}
				int result = dao.delItem(aList);
				mapper = new ObjectMapper();
				out = new ByteArrayOutputStream();
				mapper.writeValue(out, result);
				
				System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), "" + Constants.DELETE_LIKE_ITEM + " ", result);
			}
			
		} else if(Constants.ORDER.equals(type)) {
			String orderListEncode = req.getParameter("json");
			String orderListDecode;
			byte[] decoded = Base64.getDecoder().decode(orderListEncode);
			orderListDecode = new String(decoded);
			String[] orderItemList = orderListDecode.split(Constants.BASE64_DELIM);
			
			String deliveryPrice = req.getParameter("order_delivery_price").replace(",", "");
			String productPrice = req.getParameter("order_product_price").replace(",", "");
			String totalPrice = req.getParameter("order_total_price").replace(",", "");
			String mail = req.getParameter("order_email");
			String name= req.getParameter("order_name");
			String phone= req.getParameter("order_phone");
			String addr = req.getParameter("order_addr");
			String tong = req.getParameter("order_tong");
			
			ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();
			for(String orderItem : orderItemList) {
				OrderVO orderVo = new OrderVO();
				orderVo.setAddr(addr);
				orderVo.setDeliveryPrice(deliveryPrice);
				orderVo.setOrderStatus("0");
				orderVo.setOrderTime(Constants.getDate(Constants.DATE_DB_FORMAT));
				orderVo.setUpdateTime(Constants.getDate(Constants.DATE_DB_FORMAT));
				orderVo.setPhone(phone);
				orderVo.setProductPrice(productPrice);
				orderVo.setTong(tong);
				orderVo.setTotalPrice(totalPrice);
				orderVo.setName(name);
				orderVo.setMail(mail);
				
				
				ItemVO itemVo = new ItemVO();
				mapper = new ObjectMapper();
				itemVo = mapper.readValue(orderItem, ItemVO.class);
				orderVo.setItemVo(itemVo);
				orderList.add(orderVo);
				
			}
			
			int result = dao.insertOrder(orderList);
			
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, result);
			
//			RequestDispatcher view = req.getRequestDispatcher("/like.html").include(req, resp);
			req.getRequestDispatcher("/like.html").include(req, resp);
//			view.forward(req, resp);
			
			System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), "" + Constants.ORDER + " ", result);
			return;
		} else if(Constants.SELECT_ORDER_BY_USER.equals(type)) {
			String mail = req.getParameter("mail");
			OrderVO vo = new OrderVO();
			vo.setMail(mail);
			List<Order2VO> orderList = dao.selectOrderByUser(vo);
			
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			mapper.writeValue(out, orderList);
			
			System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), "" + Constants.SELECT_ORDER_BY_USER + " ", orderList.size());
		} else if(Constants.SELECT_USER_BY_MAIL.equals(type)) {
			String mail = req.getParameter("mail");
			List<UserVO> userList = dao.selectUserByMail(mail);
			mapper = new ObjectMapper();
			out = new ByteArrayOutputStream();
			if(userList == null || userList.size() == 0) {
				mapper.writeValue(out, -1);
				System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), Constants.SELECT_USER_BY_MAIL, -1);
			} else {
				mapper.writeValue(out, userList);
				System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "doGet", Constants.getDate(Constants.DATE_LOG_FORMAT), Constants.SELECT_USER_BY_MAIL, userList.size());
			}
			
		}
		
		
		
	    final byte[] data = out.toByteArray();
	    retJson = new String(data);
		resp.setContentType("text/json");
		resp.getWriter().write(retJson);
	}
}