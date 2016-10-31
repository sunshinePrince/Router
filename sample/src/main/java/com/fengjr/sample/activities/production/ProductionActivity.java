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

package com.fengjr.sample.activities.production;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.fengjr.sample.R;
import com.fengjr.sample.activities.BaseActivity;
import com.fengjr.sample.constants.ActionConstants;
import com.sunshineprince.router.Router;

import org.json.JSONObject;

import java.util.Random;

/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class ProductionActivity extends BaseActivity {


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.production);
		setContentView(R.layout.activity_production);
		TextView tv_id = (TextView) findViewById(R.id.tv_id);
		TextView tv_name = (TextView) findViewById(R.id.tv_name);
		TextView tv_price = (TextView) findViewById(R.id.tv_price);
		String id = getIntent().getStringExtra("proId");
		String name = getIntent().getStringExtra("proName");
		String data = getIntent().getStringExtra(Router.EXTRA_DATA);
		try {
			JSONObject jsonObject = new JSONObject(data);
			id = jsonObject.getString("id");
			name = jsonObject.getString("name");
			tv_id.setText(String.format("production id : %s",id));
			tv_name.setText(String.format("production name : %s",name));
			Random random = new Random();
			tv_price.setText(String.format("price : %s",random.nextInt(1000)+100));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void onBuyClick(View view){
		Router.builder(this).target(ActionConstants.BUY).start();
	}





}
