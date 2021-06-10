package kr.co.simba.vo;

public class UserVO {
	private String id;
	private String accessToken;
	private String name;
	private String mail;
	private String thumbNailImg;
	private String loginType;
	private boolean isEmailValid;
	private boolean isEmailVerified;
	
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getThumbNailImg() {
		return thumbNailImg;
	}
	public void setThumbNailImg(String thumbNailImg) {
		this.thumbNailImg = thumbNailImg;
	}
	public boolean isEmailValid() {
		return isEmailValid;
	}
	public void setEmailValid(boolean isEmailValid) {
		this.isEmailValid = isEmailValid;
	}
	public boolean isEmailVerified() {
		return isEmailVerified;
	}
	public void setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}
	
}
