package kr.co.sapyoung.impl;

public class Company {
	private String comName;
	
	public Company() {
//		System.out.println("Company �⺻ ������");
		comName = "Aȸ��";
	}
	
	public Company(String str) {
//		System.out.println("Company ������ #1");
		comName = str;
	}

	public Company(int num) {
//		System.out.println("Company ������ #2");
		comName = num + "ȸ��";
	}
	
	public String getCompName() {
		return comName;
	}
	public String getCompTel() {
		return "ȸ�翬��ó";
	}
	public String getCompSite() {
		return "ȸ��Ȩ������";
	}
}
