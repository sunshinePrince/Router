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

package com.fengjr.sample.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class UserUtils {

	private static final String USER_INFO = "user_info";

	private static final String USERNAME = "username";

	public static void saveUser(Context context,String username){
		SharedPreferences sp = getSharedPreferences(context);
		sp.edit().putString(USERNAME,username).apply();
	}

	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
	}


	public static String getUsername(Context context){
		SharedPreferences sp = getSharedPreferences(context);
		return sp.getString(USERNAME,null);
	}



	public static boolean isLogin(Context context){
		SharedPreferences sp = getSharedPreferences(context);
		return !TextUtils.isEmpty(sp.getString(USERNAME,null));
	}


	public static void logout(Context context){
		SharedPreferences sp = getSharedPreferences(context);
		sp.edit().remove(USERNAME).apply();
	}




}
