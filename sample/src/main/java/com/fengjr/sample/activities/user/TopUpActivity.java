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
import android.widget.EditText;
import android.widget.Toast;

import com.fengjr.sample.R;
import com.fengjr.sample.activities.BaseActivity;
import com.fengjr.sample.utils.UserUtils;

/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class TopUpActivity extends BaseActivity{


	EditText et_money;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.topup);
		setContentView(R.layout.activity_topup);
		et_money = (EditText) findViewById(R.id.et_money);
	}



	public void onTopUpClick(View view){
		String money = et_money.getText().toString();
		int amount = 0;
		try{
			amount = Integer.parseInt(money);
		}catch (Exception e){
		}
		if(0 == amount){
			Toast.makeText(TopUpActivity.this, "plz enter top-up amount!", Toast.LENGTH_SHORT).show();
		}
		UserUtils.saveMoney(this,amount);
		finish();
//		Router.builder(this).inStack(false).start();
	}



}
