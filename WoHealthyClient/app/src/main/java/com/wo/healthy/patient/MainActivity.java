package com.wo.healthy.patient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wo.healthy.patient.network.ApiCenter;
import com.wo.healthy.patient.network.model.BaseModel;
import com.wo.healthy.patient.network.request.SignUpRequest;
import com.wo.healthy.patient.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by wangnianqing on 2016/5/23.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EventBus.getDefault().register(this);
	}

	public void click(View view) {
		final SignUpRequest request = new SignUpRequest("13354282828", "111111", "qing"
				, "233256454", 0);
		CustomApplication customApplication = CustomApplication.getApplication();
		ApiCenter mApi= CustomApplication.getApplication().getApiCenter();
		mApi.regist(request);
	}

	@Subscribe
	public void onEvent(BaseModel event) {
		Utils.customerToast("注册");
	}

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
}
