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

package com.sunshineprince.router.action;

import android.content.Context;
import android.text.TextUtils;

import com.sunshineprince.router.interceptor.Interceptor;
import com.sunshineprince.router.parser.URIParser;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class RouterConfig {

	public static final String ROUTER = "router";

	public static final String PACKAGE = "package";

	public static final String VERSION = "version";

	public static final String SCHEME = "scheme";

	public static final String ACTION = "target";

	public static final String PAGE = "page";

	public static final String INTERCEPTOR = "interceptor";


	public String mPackage;

	public String versionCode;

	public String scheme;

	public HashMap<String, Package> packages = new HashMap<>();

	public HashMap<String, Action> actions = new HashMap<>();

	public HashMap<String, String> interceptors = new HashMap<>();

	public HashMap<String, Page> pages = new HashMap<>();




	public Action getActionByName(String name) {
		return actions.get(name);
	}


	public Page getPageByName(String name){
		return pages.get(name);
	}


	public Package getPackageById(String id) {
		return packages.get(id);
	}


	@SuppressWarnings("unchecked")
	public Interceptor getInterceptorByName(String name, Context context) throws Exception{
		String className = interceptors.get(name);
		Interceptor interceptor = null;
		if (!TextUtils.isEmpty(className)) {
			String packageId = URIParser.getPackageId(className);
			if (!TextUtils.isEmpty(packageId)) {
				String packageName = getPackageNameById(packageId);
				String simpleName = URIParser.getSimpleName(className);
				className = URIParser.getClassName(packageName, simpleName);
			}
			Class clz = Class.forName(className);
			Constructor constructor = clz.getConstructor(Context.class);
			if(!constructor.isAccessible()){
				constructor.setAccessible(true);
			}
			interceptor = (Interceptor) constructor.newInstance(context);
		}
		return interceptor;
	}




	public String getPackageNameById(String id) {
		String name = "";
		Package pg = packages.get(id);
		while (null != pg && !TextUtils.isEmpty(pg.pid)) {
			Package parent = getPackageById(pg.pid);
			if (null != parent && !TextUtils.isEmpty(parent.name)) {
				name = parent.name + "." + pg.name;
			}
			pg = parent;
		}
		if(TextUtils.isEmpty(name) && null !=pg){
			name = pg.name;
		}
		name = mPackage + "." + name;
		return name;
	}


}
