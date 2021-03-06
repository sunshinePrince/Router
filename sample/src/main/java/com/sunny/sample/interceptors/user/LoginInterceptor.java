/*
 * Copyright (C) 2016
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

package com.sunny.sample.interceptors.user;

import android.content.Context;
import android.content.Intent;

import com.sunny.sample.constants.ActionConstants;
import com.sunny.sample.utils.UserUtils;
import com.sunshineprince.router.Router;
import com.sunshineprince.router.interceptor.Interceptor;

/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */

public class LoginInterceptor extends Interceptor{


	public LoginInterceptor(Context context) {
		super(context);
	}

	@Override
	public boolean intercept(Intent intent) {
		if (UserUtils.isLogin(mContext)){
			return false;
		}
		Router.builder(mContext).target(ActionConstants.LOGIN).inStack(false).start();
		return true;
	}





}
