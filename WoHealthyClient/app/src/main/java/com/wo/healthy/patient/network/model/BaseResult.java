/**
 * 
 */
package com.wo.healthy.patient.network.model;

/**
 * @author menghui
 * @createtime 2015年7月14日 下午1:22:40  
 * @description 
 *
 */
public class BaseResult {
	private int code;
	private String msg;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
