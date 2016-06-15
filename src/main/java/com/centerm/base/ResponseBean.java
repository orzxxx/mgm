package com.centerm.base;

public class ResponseBean {
	/**
	 * 执行状态码
	 */
	private int code;
	/**
	 * 错误信息
	 */
	private String message;
	/**
	 * 返回数据
	 */
	private Object data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
