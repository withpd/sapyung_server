package kr.co.sapyoung.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.sapyoung.Constants;
import kr.co.sapyoung.vo.UserDto;

public class DbTest {
	
	// DB���� ����
	String host;
	String port;
	String sid;
	String driver;
	
	// ����� ���� -> �����ڸ� ���� get
	String id;
	String pw;
	
	Connection con = null;
	
	public DbTest() {
		
	}

	public DbTest(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
	
	public void connect(String host, String port, String sid, String driver) {
		
		try {
			Class.forName(driver);
			
			// 1. DB ����
			String url = "jdbc:mysql://" + host + ":" + port + "/" + sid;
			// jdbc:mysql://database-1.cjvdgquniwjw.ap-northeast-2.rds.amazonaws.com:3306/geek9
			con = DriverManager.getConnection(url, id, pw);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int delete(String condition) {
		
		// ������ ����(DELETE)
		String query = "DELETE FROM geek9.Test WHERE name = ?;";
		
		int result = 0;
		try {
			
			// 2. Delete Query ����
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, condition);
			result = pstmt.executeUpdate();
			
			// 3. ��� Ȯ��
			System.out.println("��� : " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public int insert(String name, String phone, String addr) {
		
		// ������ ����(INSERT)
		String query = "INSERT INTO geek9.Test(name, phone, addr) VALUES(?, ?, ?)";
//		String query = "INSERT INTO geek9.Test(name, phone, addr) VALUES('" + name + "', '" + phone + "', '" + addr + "')";
		// 			   "INSERT INTO geek9.Test(name, phone, addr) VALUES('    �輺ȣ    ',     '1111    ', '     ��õ     ')"
		int result = 0;
		try {
		
			
			// 2. Insert Query ����
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			pstmt.setString(3, addr);
			result = pstmt.executeUpdate();
			
			// 3. ��� Ȯ��
			System.out.println("��� : " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ArrayList<UserDto> select(String condition, int page) {		// ��ȣ
	
		condition = "%" + condition + "%";			// %��ȣ%
		
		// ������ ����(SELECT)
		String query  = "SELECT name,phone,addr FROM geek9.Test WHERE phone like ? LIMIT 3 OFFSET ?" ;
		
		ResultSet rs = null;
		ArrayList<UserDto> userList = new ArrayList<UserDto>();
		try {
			
			// 2. SELECT Query ����
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, condition);
			pstmt.setInt(2, 3*(page - 1) );
			
			rs = pstmt.executeQuery();	// select
			
			// 3. ��� Ȯ��
			while(rs.next()) {
				UserDto dto = new UserDto();
				
				String name = rs.getString(1);
				String phone = rs.getString(2);
				String location = rs.getString(3);
				
				dto.setName(name);
				dto.setPhone(phone);
				dto.setAddr(location);
				
				userList.add(dto);
				
//				System.out.println("�̸� : " + name);
//				System.out.println("��ȭ : " + phone);
//				System.out.println("���� : " + location);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	public int update(String oldVal, String newVal) {
		
		// ������ ����(UPDATE)
		String query = "UPDATE geek9.Test SET name=? where addr = ?;";
		
		int result = 0;
		try {
			
			// 2. UPDATE Query ����
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, newVal);
			pstmt.setString(2, oldVal);
			result = pstmt.executeUpdate();
			
			// 3. ��� Ȯ��
			System.out.println("��� : " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void main(String[] arg) {
		DbTest db = new DbTest("admin", Constants.pw);
		db.connect("database-1.cjvdgquniwjw.ap-northeast-2.rds.amazonaws.com", "3306", "geek9", "com.mysql.cj.jdbc.Driver");		// ����	
//		db.insert("����", "100000", "����");	// �̸�/��/�ּ�
//		db.delete("�輺ȣ");
//		db.update("��õ", "�輺ȣ");
//		ArrayList<UserDto> voList = db.select("0");
		
//		int result = voList.size();
//		System.out.println(result + "��");
	}
}
