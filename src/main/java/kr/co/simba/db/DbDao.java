package kr.co.simba.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.co.simba.Constants;
import kr.co.simba.vo.ItemVO;
import kr.co.simba.vo.Order2VO;
import kr.co.simba.vo.OrderVO;
import kr.co.simba.vo.ProductVO;
import kr.co.simba.vo.UserVO;

public class DbDao {
	private SqlSessionFactory sqlSessionFactory = null;

	public DbDao(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
	
	public List<ProductVO> selectRange(int offset, int readCount) {
		List<ProductVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		HashMap<String, Integer> param = new HashMap<String, Integer>();
		param.put("offset", offset);
		param.put("readCount", readCount);

		try {
			list = session.selectList("product.selectRange", param);
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			session.commit();
			session.close();
		}
		System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "DBDAO", Constants.getDate(Constants.DATE_LOG_FORMAT), param, list.size());
		return list;
	}
	
	public List<ProductVO> selectAll() {
		List<ProductVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			list = session.selectList("product.selectAll");
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			session.commit();
			session.close();
		}

		return list;
	}

	public ProductVO selectProductById(int id) {
		ProductVO vo = null;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			vo = session.selectOne("product.selectProductById", id);
		} catch(Exception e){
			System.out.println(e.getMessage());
			vo = null;
		} finally {
			session.commit();
			session.close();
		}
		return vo;
	}

	public int insert(ProductVO vo) {
		String brand = vo.getBrand();
		int insertCnt = -1;
		SqlSession session = sqlSessionFactory.openSession();

		try {
			insertCnt = session.insert("product.insert", vo);
		} catch(Exception e){
			System.out.println(e.getMessage());
			insertCnt = -1;
		} finally {
			session.commit();
			session.close();
		}
		System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "DBDAO", Constants.getDate(Constants.DATE_LOG_FORMAT), brand + " insert", insertCnt);
		return insertCnt;
	}
	
	public int insertList(List<ProductVO> voList) {
		int insertCnt = -1;
		SqlSession session = sqlSessionFactory.openSession();

		try {
			insertCnt = session.insert("product.insertList", voList);
		} catch(Exception e){
			System.out.println(e.getMessage());
			insertCnt = -1;
		}
		finally {
			session.commit();
			session.close();
		}
		System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "DBDAO", Constants.getDate(Constants.DATE_LOG_FORMAT), "" + " insertList", insertCnt);
		return insertCnt;
	}

	public void update(ProductVO vo) {
		SqlSession session = sqlSessionFactory.openSession();

		try {
			session.update("product.update", vo);
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			session.commit();
			session.close();
		}
	}

	public void deleteAll() {
		SqlSession session = sqlSessionFactory.openSession();
		int delCnt = -1;
		try {
			delCnt = session.delete("product.deleteAll");
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			session.commit();
			session.close();
		}
		System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "DBDAO", Constants.getDate(Constants.DATE_LOG_FORMAT), "deleteAll", delCnt);
	}
	
	public void delete(int id) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.delete("product.delete", id);
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			session.commit();
			session.close();
		}
	}

	public List<UserVO> selectUserByMail(String mail) {
		SqlSession session = sqlSessionFactory.openSession();
		List<UserVO> userList = null;
		try {
			userList = session.selectList("user.selectUserByMail", mail);
//			System.out.println("userList.size() : " + userList.size());
			if(userList.size() != 0) {
				return userList;
			} else {
				userList = null;
			}
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally {
			session.commit();
			session.close();
		}
		return null;
	}
	
	public int insertUser(UserVO vo) {
		SqlSession session = sqlSessionFactory.openSession();
		int insertCnt = -1;
		try {
			insertCnt = session.insert("user.insertUser", vo);
		} catch(Exception e){
			System.out.println(e.getMessage());
			insertCnt = -1;
		}
		finally {
			session.commit();
			session.close();
		}
		return insertCnt;
	}
	
	public int insertItem(ItemVO vo) {
		SqlSession session = sqlSessionFactory.openSession();
		int insertCnt = -1;
		try {
			insertCnt = session.insert("item.insertItem", vo);
		} catch(Exception e){
			System.out.println(e.getMessage());
			insertCnt = -1;
		}
		finally {
			session.commit();
			session.close();
		}
		return insertCnt;
	}
	
	public int selectItem(ItemVO vo) {
		SqlSession session = sqlSessionFactory.openSession();
		ItemVO result = null;
		int retVal = -1;
		try {
			result = session.selectOne("item.selectItem", vo);
//			System.out.println("userList.size() : " + userList.size());
			if(result != null) {
				retVal = Integer.parseInt(result.getAmount());
			} 
		} catch(Exception e){
			System.out.println(e.getMessage());
			retVal = -1;
		}
		finally {
			session.commit();
			session.close();
		}
		return retVal;
	}
	
	public int updateAccessToken(String accessToken, String mail) {
		SqlSession session = sqlSessionFactory.openSession();
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("accessToken", accessToken);
		param.put("mail", mail);
		int updateCnt = -1;
		try {
			updateCnt = session.update("user.updateAccessToken", param);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			updateCnt = -1;
		} finally {
			session.commit();
			session.close();
		}
		
//		System.out.println("param : " + param);
		return updateCnt;
	}

	public int updateAmount(ItemVO vo) {
		SqlSession session = sqlSessionFactory.openSession();
		int result = -1;
		try {
			result = session.update("item.updateAmount", vo);
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}

	public List<ItemVO> selectItemByType(ItemVO vo) {
		List<ItemVO> retList = null;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			retList = session.selectList("item.selectItemByType", vo);
		} catch(Exception e){
			System.out.println(e.getMessage());
			retList = null;
		} finally {
			session.commit();
			session.close();
		}
		return retList;
	}

	public int delItem(ArrayList<ItemVO> aList) {
		SqlSession session = sqlSessionFactory.openSession();
		int result = -1;
		try {
			result = session.delete("item.deleteItem", aList);
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			session.commit();
			session.close();
		}
		return result;
	}

	public int insertOrder(ArrayList<OrderVO> orderList) {
		SqlSession session = sqlSessionFactory.openSession();
		int insertCnt = -1;
		try {
			insertCnt = session.insert("order.insertOrder", orderList);
		} catch(Exception e){
			System.out.println(e.getMessage());
			insertCnt = -1;
		}
		finally {
			session.commit();
			session.close();
		}
		return insertCnt;
	}

	public List<Order2VO> selectOrderByUser(OrderVO vo) {
		List<Order2VO> retList = null;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			retList = session.selectList("order.selectOrderByUser", vo);
		} catch(Exception e){
			System.out.println(e.getMessage());
			retList = null;
		} finally {
			session.commit();
			session.close();
		}
		return retList;
	}
}
