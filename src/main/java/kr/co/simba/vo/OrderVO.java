package kr.co.simba.vo;

import java.util.ArrayList;

public class OrderVO {

	private String mail;
	private String name;
	private String phone;
	private String addr;
	private String tong;
	private String orderTime;
	private String updateTime;
	private String deliveryPrice;
	private String productPrice;
	private String totalPrice;
	private String orderStatus; 		// 0:주문확인, 1:결제대기, 2:결제완료, 3:배송중, 4:배송완료, 5:반품신청, 6:반품확인중, 7:반품대기, 8:반품완료
	private ItemVO itemVo;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemVO getItemVo() {
		return itemVo;
	}

	public void setItemVo(ItemVO itemVo) {
		this.itemVo = itemVo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTong() {
		return tong;
	}

	public void setTong(String tong) {
		this.tong = tong;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(String deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}
