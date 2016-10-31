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

package com.sunshineprince.router.parser;

import android.net.Uri;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class URIParser {


	public static final String SEPARATOR = ".";


	public static final String A = "$";


	public static String getClassName(String packageName, String simpleName) {
		return packageName + SEPARATOR + simpleName;
	}


	public static String getPackageId(String host) {
		String id = null;
		if (!TextUtils.isEmpty(host) && host.length() > 2) {
			int end = host.indexOf(A);
			if (end > 0) {
				id = host.substring(0, end);
			}
		}
		return id;
	}


	public static String getSimpleName(String host) {
		String name = null;
		if (!TextUtils.isEmpty(host) && host.length() > 2) {
			int end = host.indexOf(A);
			if (end > 0 && end != host.length() - 1) {
				name = host.substring(end + 1);
			}
		}
		return name;
	}


	public static String getScheme(String uriStr) {
		Uri uri = Uri.parse(uriStr);
		return uri.getScheme();
	}


	public static String getHost(String uriStr) {
		Uri uri = Uri.parse(uriStr);
		return uri.getHost();
	}



	public static String getData(String uriStr) {
		Uri uri = Uri.parse(uriStr);
		return uri.getQueryParameter("data");
	}


	public static String getUrl(String uriStr) {
		Uri uri = Uri.parse(uriStr);
		return uri.getQueryParameter("url");
	}


	public static String getQueryForward(String uri) {
		String result = null;
		int index = uri.indexOf("?");
		if (index >= 0) {
			result = uri.substring(0, index);
		}
		return result;
	}


	public static String getQueryAfter(String uri){
		String result = null;
		int index = uri.indexOf("?");
		if (index >= 0 && uri.length()>index+1) {
			result = uri.substring(index+1, uri.length());
		}
		return result;
	}


	public static String getParamByRegex(String uri,String paramName){
		Pattern pattern = Pattern.compile(paramName+"=\\s*(.*)");
		Matcher matcher = pattern.matcher(uri);
		String value = null;
		if (matcher.find()){
			value = matcher.group(1);
		}
		return value;
	}



}
