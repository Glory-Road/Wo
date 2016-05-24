package com.wo.healthy.patient.network.request;

import com.google.gson.annotations.SerializedName;

/**
 * 用户注册input
 * 
 * @author ligang
 *
 */
public class SignUpRequest {

	@SerializedName("phone")
	private String mMbile;

	@SerializedName("password")
	private String mPassword;

	@SerializedName("nickName")
	private String mNickName;

	@SerializedName("imei")
	private String mImei;

	@SerializedName("provinceId")
	private int mProvinceId;

	public SignUpRequest(String mobile, String password, String nickName, String imei, int provinceId) {
		mMbile = mobile;
		mPassword = password;
		mNickName = nickName;
		mImei = imei;
		mProvinceId = provinceId;
	}
}
