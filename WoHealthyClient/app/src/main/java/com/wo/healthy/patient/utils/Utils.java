package com.wo.healthy.patient.utils;

import android.widget.Toast;

import com.wo.healthy.patient.CustomApplication;

/**
 * Created by wangnianqing on 2016/5/23.
 */
public class Utils {

	private Utils() {};

	public static void customerToast(String content) {
		Toast.makeText(CustomApplication.getApplication(), content, Toast.LENGTH_SHORT).show();
	}
}
