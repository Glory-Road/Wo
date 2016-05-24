package com.wo.healthy.patient;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wo.healthy.patient.network.ApiCenter;

/**
 * Created by wangnianqing on 2016/5/23.
 */
public class CustomApplication extends Application {

	private static CustomApplication instance;

	private final ApiCenter mApiCenter = new ApiCenter();

	public static CustomApplication getApplication() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		Fresco.initialize(instance);
	}

	public ApiCenter getApiCenter() {
		return mApiCenter;
	}
}
