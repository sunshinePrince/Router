package com.fengjr.sample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fengjr.sample.R;
import com.fengjr.sample.activities.user.LoginActivity;
import com.fengjr.sample.constants.ActionConstants;
import com.fengjr.sample.interceptors.user.LoginInterceptor;
import com.sunshineprince.router.Router;

import java.io.IOException;


/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class MainActivity extends BaseActivity {

	TextView tvConfig;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (null != getSupportActionBar()) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		}
		tvConfig = (TextView) findViewById(R.id.tv_config);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.select_config, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		tvConfig.setText(item.getTitle());
		switch (item.getItemId()) {
			case R.id.item_config_default:
				try {
					Router.init(getAssets().open("router.xml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.item_config_json:
				try {
					Router.init(getAssets().open("router.xml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case R.id.item_config_json_vip:
				try {
					Router.init(getAssets().open("router.xml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
		return super.onOptionsItemSelected(item);
	}


	public void onOpenPhotosClick(View view) {
		Router.builder(this).action(ActionConstants.OPEN_CAMERA).start();
		Router.builder(this).intentAction("android.media.action.IMAGE_CAPTURE").start();
	}


	public void onUserInfoClick(View view) {
		//				Router.builder(this).host("com.fengjr.sample.activities.user.UserInfoActivity").interceptor(new LoginInterceptor(this)).startActivity();
		Router.builder(this).packageName("com.fengjr.sample.activities.user").host(
				"UserInfoActivity").interceptor(new LoginInterceptor(this)).start();
		//				Router.builder(this).action(ActionConstants.TO_USER_INFO).startActivity();
	}


	public void onUserInfoStickyClick(View view) {
		Router.builder(this).action(ActionConstants.TO_USER_INFO).sticky(true).start();
	}


	public void onProListClick(View view) {
		//		Router.builder(this).packageId("2").host("LoginActivity").data("{name:sunny}").startActivity();
		Router.builder(this).action(ActionConstants.TO_LOGIN).requestCode(10).start();
	}


	public void onTradeClick(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}




	public void onStartActivitiesClick(View view){
		Intent intent1 = new Intent(this,LoginActivity.class);
		Intent intent2 = new Intent(this,LoginActivity.class);
		Intent intent3 = new Intent(this,LoginActivity.class);
		Intent[] intents = new Intent[]{intent1,intent2,intent3};
		startActivities(intents);
	}


	public void onH5Click(View view){
		Router.builder(this).packageId("1").host("BrowserActivity").start();
	}





	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Toast.makeText(MainActivity.this, "requestCode=" + requestCode, Toast.LENGTH_LONG).show();
	}
}
