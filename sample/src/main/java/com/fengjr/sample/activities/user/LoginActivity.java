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
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fengjr.sample.R;
import com.fengjr.sample.activities.BaseActivity;
import com.fengjr.sample.constants.ActionConstants;
import com.fengjr.sample.utils.UserUtils;
import com.sunshineprince.router.Router;


/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class LoginActivity extends BaseActivity {

	EditText etUsername;
	EditText etPassword;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getString(R.string.login));
		setContentView(R.layout.activity_login);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
	}


	public void onLoginClick(View view) {
		String username = etUsername.getText().toString();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(LoginActivity.this, "plz enter the username", Toast.LENGTH_SHORT).show();
			return;
		}
		String password = etPassword.getText().toString();
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(LoginActivity.this, "plz enter the password", Toast.LENGTH_SHORT).show();
			return;
		}
		UserUtils.saveUser(LoginActivity.this, username);
		finish();
//		Router.builder(this).inStack(false).start();
	}

	public void onRegisterClick(View view) {
		Router.builder(this).target(ActionConstants.REGISTER).start();
	}


}
