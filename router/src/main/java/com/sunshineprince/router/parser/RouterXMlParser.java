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

package com.sunshineprince.router.parser;

import android.util.Log;
import android.util.Xml;

import com.sunshineprince.router.action.Action;
import com.sunshineprince.router.action.Package;
import com.sunshineprince.router.action.Page;
import com.sunshineprince.router.action.RouterConfig;
import com.sunshineprince.router.interceptor.Interceptor;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class RouterXMlParser implements RouterParser<RouterConfig> {


	private final String TAG = getClass().getSimpleName();


	@Override
	public RouterConfig parse(InputStream is) throws Exception {
		long start = System.currentTimeMillis();
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "UTF-8");
		RouterConfig routerConfig = new RouterConfig();
		Package pack = null;
		String interceptorName = null;
		String interceptorClass = null;
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
				case XmlPullParser.START_TAG:
					switch (parser.getName()) {
						case RouterConfig.ROUTER:
							for (int i = 0; i < parser.getAttributeCount(); i++) {
								switch (parser.getAttributeName(i)) {
									case RouterConfig.PACKAGE:
										routerConfig.mPackage = parser.getAttributeValue(i);
										break;
									case RouterConfig.VERSION:
										routerConfig.versionCode = parser.getAttributeValue(i);
										break;
								}
							}
							break;
						case RouterConfig.PACKAGE:
							pack = new Package();
							for (int i = 0; i < parser.getAttributeCount(); i++) {
								switch (parser.getAttributeName(i)) {
									case Package.ID:
										pack.id = parser.getAttributeValue(i);
										break;
									case Package.NAME:
										pack.name = parser.getAttributeValue(i);
										break;
									case Package.PARENT_ID:
										pack.pid = parser.getAttributeValue(i);
										break;
								}
							}
							break;
						case RouterConfig.ACTION:
							parseAction(parser, routerConfig);
							break;
						case RouterConfig.PAGE:
							parsePage(parser,routerConfig);
							break;
						case RouterConfig.INTERCEPTOR:
							for (int i = 0; i < parser.getAttributeCount(); i++) {
								switch (parser.getAttributeName(i)) {
									case Interceptor.NAME:
										interceptorName = parser.getAttributeValue(i);
										break;
									case Interceptor.CLASS:
										interceptorClass = parser.getAttributeValue(i);
										break;
								}
							}
							break;
						default:
							Log.d(TAG, parser.getName());
							break;
					}
					break;
				case XmlPullParser.END_TAG:
					switch (parser.getName()) {
						case RouterConfig.PACKAGE:
							if (null != pack) {
								routerConfig.packages.put(pack.id, pack);
							}
							break;
						case RouterConfig.INTERCEPTOR:
							if (null != interceptorName && null != interceptorClass) {
								routerConfig.interceptors.put(interceptorName, interceptorClass);
							}
							break;
					}
					break;
			}
			eventType = parser.next();
		}
		is.close();
		Log.d(TAG, "解析耗时:" + (System.currentTimeMillis() - start) + "ms");
		return routerConfig;
	}


	/**
	 * parse target
	 *
	 * @param parser       XmlPullParser
	 * @param routerConfig {@link RouterConfig}
	 * @throws Exception
	 */
	private void parseAction(XmlPullParser parser, RouterConfig routerConfig) throws Exception {
		int eventType = parser.getEventType();
		Action action = new Action();
		String interceptorName = null;
		String interceptorClass = null;
		while (!(eventType == XmlPullParser.END_TAG && RouterConfig.ACTION.equals(
				parser.getName()))) {
			switch (eventType) {
				case XmlPullParser.START_TAG:
					switch (parser.getName()) {
						case RouterConfig.ACTION:
							for (int i = 0; i < parser.getAttributeCount(); i++) {
								switch (parser.getAttributeName(i)) {
									case Action.NAME:
										action.name = parser.getAttributeValue(i);
										break;
									case Action.FROM:
										action.from = parser.getAttributeValue(i);
										break;
									case Action.TO:
										action.to = parser.getAttributeValue(i);
										break;
									case Action.ACTION:
										action.action = parser.getAttributeValue(i);
										break;
								}
							}
							break;
						case RouterConfig.INTERCEPTOR:
							for (int i = 0; i < parser.getAttributeCount(); i++) {
								switch (parser.getAttributeName(i)) {
									case Interceptor.NAME:
										interceptorName = parser.getAttributeValue(i);
										break;
									case Interceptor.CLASS:
										interceptorClass = parser.getAttributeValue(i);
										break;
								}
							}
							break;
					}
					break;
				case XmlPullParser.END_TAG:
					switch (parser.getName()) {
						case RouterConfig.INTERCEPTOR:
							if (null != interceptorName) {
								action.interceptors.add(interceptorName);
								if(null != interceptorClass){
									routerConfig.interceptors.put(interceptorName, interceptorClass);
								}
							}
							break;
					}
					break;
			}
			eventType = parser.next();
		}
		routerConfig.actions.put(action.name, action);
	}




	private void parsePage(XmlPullParser parser, RouterConfig routerConfig) throws Exception {
		int eventType = parser.getEventType();
		Page page = new Page();
		String interceptorName = null;
		String interceptorClass = null;
		while (!(eventType == XmlPullParser.END_TAG && RouterConfig.PAGE.equals(
				parser.getName()))) {
			switch (eventType) {
				case XmlPullParser.START_TAG:
					switch (parser.getName()) {
						case RouterConfig.PAGE:
							for (int i = 0; i < parser.getAttributeCount(); i++) {
								switch (parser.getAttributeName(i)) {
									case Page.NAME:
										page.name = parser.getAttributeValue(i);
										break;
									case Page.CLASS:
										page.className = parser.getAttributeValue(i);
										break;
									case Page.FORWARD:
										page.forward = parser.getAttributeValue(i);
										break;
								}
							}
							break;
						case RouterConfig.INTERCEPTOR:
							for (int i = 0; i < parser.getAttributeCount(); i++) {
								switch (parser.getAttributeName(i)) {
									case Interceptor.NAME:
										interceptorName = parser.getAttributeValue(i);
										break;
									case Interceptor.CLASS:
										interceptorClass = parser.getAttributeValue(i);
										break;
								}
							}
							break;
					}
					break;
				case XmlPullParser.END_TAG:
					switch (parser.getName()) {
						case RouterConfig.INTERCEPTOR:
							if (null != interceptorName) {
								page.interceptors.add(interceptorName);
								if(null != interceptorClass){
									routerConfig.interceptors.put(interceptorName, interceptorClass);
								}
							}
							break;
					}
					break;
			}
			eventType = parser.next();
		}
		routerConfig.pages.put(page.name,page);
	}


}
