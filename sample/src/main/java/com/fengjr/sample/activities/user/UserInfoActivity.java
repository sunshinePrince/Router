/*
 * Copyright (C) 2016 fengjr, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fengjr.sample.activities.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.fengjr.sample.R;
import com.fengjr.sample.activities.BaseActivity;
import com.fengjr.sample.utils.UserUtils;

/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class UserInfoActivity extends BaseActivity {


	@SuppressWarnings("ConstantConditions")
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		setTitle(getString(R.string.user_info));
		TextView tv_name = (TextView) findViewById(R.id.tv_name);
		TextView tv_balance = (TextView) findViewById(R.id.tv_balance);
		tv_name.setText(String.format("hello!%s!",UserUtils.getUsername(UserInfoActivity.this)));
		tv_balance.setText(String.format("balance : %s",UserUtils.getBalance(this)));
	}



	public void onLogoutClick(View view){
		UserUtils.logout(UserInfoActivity.this);
		finish();
	}





}
