package kr.co.sapyoung.impl;

public class Company {
	private String comName;
	
	public Company() {
//		System.out.println("Company 기본 생성자");
		comName = "A회사";
	}
	
	public Company(String str) {
//		System.out.println("Company 생성자 #1");
		comName = str;
	}

	public Company(int num) {
//		System.out.println("Company 생성자 #2");
		comName = num + "회사";
	}
	
	public String getCompName() {
		return comName;
	}
	public String getCompTel() {
		return "회사연락처";
	}
	public String getCompSite() {
		return "회사홈페이지";
	}
}
