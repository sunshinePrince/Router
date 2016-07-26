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

package com.sunshineprince.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.sunshineprince.router.action.Action;
import com.sunshineprince.router.action.Package;
import com.sunshineprince.router.action.RouterConfig;
import com.sunshineprince.router.builder.PostActivityStarter;
import com.sunshineprince.router.interceptor.Interceptor;
import com.sunshineprince.router.parser.RouterXMlParser;
import com.sunshineprince.router.parser.URIParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * router
 * <p/>
 * <pre>
 * scheme://host/biz?data={name:sunny}&result=1&sticky=0&url=xxx?cascade=1
 *
 * scheme:
 *
 * mHost:
 *
 * biz:
 *
 * data:
 *
 * cascade:
 *
 * result:
 *
 * sticky:
 *
 *
 * </pre>
 * <p/>
 * <p/>
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class Router {


	public static final String TAG = "Router";

	public static final String URI_STICKY = "router_sticky";

	public static final String URI_TO = "router_to";

	public static final String URI_IN_STACK = "router_in_stack";

	public static RouterConfig mRouterConfig;

	private Context mContext;

	private Builder mBuilder;


	public static void init(InputStream is) {
		try {
			//解析配置
			mRouterConfig = new RouterXMlParser().parse(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private Router(Builder builder) {
		this.mBuilder = builder;
		this.mContext = builder.mContext;
	}


	/**
	 * create a {@link Builder} instance
	 *
	 * @param context A Context of the application package implementing
	 *                this class.
	 * @return a {@link Builder} instance to chain calls
	 */
	public static Builder builder(Context context) {
		return new Builder(context);
	}


	/**
	 * startActivity this action
	 *
	 * @return a {@link PostActivityStarter} instance to provide starter methods
	 */
	public PostActivityStarter start() {
		if (checkIntercept(mRouterConfig, mContext)) {
			return new PostActivityStarter(mContext);
		}
		if(!TextUtils.isEmpty(mBuilder.mUri)){
//			mBuilder.m
		}
		String action = getAction(mRouterConfig);
		String className = getClassName(mRouterConfig);
		int flags = getFlags();
		String data = getData(mRouterConfig);
		Intent intent = new Intent();
		if (!TextUtils.isEmpty(action)) {
			intent.setAction(action);
		}
		if (!TextUtils.isEmpty(className)) {
			intent.setClassName(mContext, className);
		}
		if (-1 != flags) {
			intent.setFlags(flags);
		}
		if (!TextUtils.isEmpty(data)) {
			intent.putExtra("", data);
		}
		if (!checkSticky(className, intent)) {
			return new PostActivityStarter(mContext);
		}
		startActivity(intent);
		return new PostActivityStarter(mContext);
	}


	private boolean checkSticky(String className, Intent intent) {
		if (mContext instanceof Activity) {
			Activity activity = (Activity) mContext;
			Intent getIntent = activity.getIntent();
			boolean isSticky = mBuilder.mSticky || getIntent.getBooleanExtra(URI_STICKY, false);
			String to = getIntent.getStringExtra(URI_TO);
			to = TextUtils.isEmpty(to) ? className : to;
			boolean inStack = mBuilder.mInStack && getIntent.getBooleanExtra(URI_IN_STACK, true);
			if (!inStack) {
				activity.finish();
			}
			if (!TextUtils.isEmpty(to) && !activity.getClass().getName().equals(to)) {
				intent.putExtra(URI_STICKY, isSticky);
				if (isSticky) {
					intent.setClassName(mContext, to);
					intent.putExtra(URI_TO, to);
				}
			} else {
				return false;
			}
		}
		return true;
	}


	/**
	 * check intercept
	 *
	 * @param config
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private boolean checkIntercept(RouterConfig config, Context context) {
		if (null != mBuilder.mInterceptors && 0 != mBuilder.mInterceptors.size()) {
			for (Interceptor interceptor : mBuilder.mInterceptors) {
				if (interceptor.intercept()) {
					return true;
				}
			}
		} else if (!TextUtils.isEmpty(mBuilder.mAction) && null != config) {
			Action action = config.getActionByName(mBuilder.mAction);
			if (null != action) {
				for (String interceptorName : action.interceptors) {
					try {
						Interceptor interceptor = config.getInterceptorByName(interceptorName,
								context);
						if (interceptor.intercept()) {
							return true;
						}
					} catch (Exception e) {
						Log.e(TAG,e.getMessage(),e);
					}
				}
			}
		}
		return false;
	}


	private String getData(RouterConfig config) {


		return "";
	}

	private int getFlags() {
		return mBuilder.mFlags;
	}


	private String getClassName(RouterConfig config) {
		String result = null;
		if (!TextUtils.isEmpty(mBuilder.mPackageName) && !TextUtils.isEmpty(mBuilder.mHost)) {
			result = URIParser.getClassName(mBuilder.mPackageName, mBuilder.mHost);
		} else if (!TextUtils.isEmpty(mBuilder.mPackageId) && !TextUtils.isEmpty(mBuilder.mHost)) {
			result = URIParser.getClassName(config.getPackageNameById(mBuilder.mPackageId),
					mBuilder.mHost);
		} else if (!TextUtils.isEmpty(mBuilder.mHost) && null != config) {
			String id = URIParser.getPackageId(mBuilder.mHost);
			if (TextUtils.isEmpty(id)) {
				result = mBuilder.mHost;
			} else {
				Package pg = config.getPackageById(id);
				if (null != pg && !TextUtils.isEmpty(pg.name)) {
					result = URIParser.getClassName(pg.name, mBuilder.mHost);
				}
			}
		} else if (!TextUtils.isEmpty(mBuilder.mAction) && null != config) {
			Action action = config.getActionByName(mBuilder.mAction);
			if (null != action && !TextUtils.isEmpty(action.to)) {
				String id = URIParser.getPackageId(action.to);
				if (TextUtils.isEmpty(id)) {
					result = action.to;
				} else {
					Package pg = config.getPackageById(id);
					if (null != pg && !TextUtils.isEmpty(pg.name)) {
						String simpleName = URIParser.getSimpleName(action.to);
						String packageName = config.getPackageNameById(id);
						result = URIParser.getClassName(packageName, simpleName);
					}
				}
			}
		}
		return result;
	}


	private String getAction(RouterConfig config) {
		String result = null;
		if (!TextUtils.isEmpty(mBuilder.mIntentAction)) {
			return mBuilder.mIntentAction;
		}
		if (null != config) {
			//根据名字找到action
			Action action = config.getActionByName(mBuilder.mAction);
			if (null != action && !TextUtils.isEmpty(action.action)) {
				result = action.action;
			}
		}
		return result;
	}


	/**
	 * start activity
	 *
	 * @param intent
	 */
	private void startActivity(Intent intent) {
		try {
			if (-1 != mBuilder.mRequestCode) {
				if (mContext instanceof Activity) {
					((Activity) mContext).startActivityForResult(intent, mBuilder.mRequestCode);
				} else {
					//can't startActivityForResult,throw a exception
					throw new RuntimeException(
							"current context isn't Activity Context,couldn't startActivityForResult");
				}
			} else {
				if (!mBuilder.cascade) {
					if (!(mContext instanceof Activity)) {
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					}
					mContext.startActivity(intent);
				} else {
					if (!(mContext instanceof Activity)) {
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					}
					//				mContext.startActivities(intent);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}


	/**
	 *
	 */
	public static class Builder {

		private Context mContext;

		private int mRequestCode = -1;

		private String mData;

		private String mHost;

		private String mPackageName;

		private String mPackageId;

		private List<Interceptor> mInterceptors;

		private int mFlags = -1;

		private String mAction;

		private String mIntentAction;

		private boolean mSticky;

		private boolean mInStack = true;

		private boolean cascade;

		private String mUri;


		/**
		 * @param context
		 */
		private Builder(Context context) {
			this.mContext = context;
		}

		/**
		 * set uri
		 *
		 * @param uri
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder uri(String uri) {
			this.mUri = uri;
			return this;
		}

		/**
		 * set packageName
		 *
		 * @param packageName
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder packageName(String packageName) {
			this.mPackageName = packageName;
			return this;
		}

		/**
		 * set packageId
		 *
		 * @param packageId
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder packageId(String packageId) {
			this.mPackageId = packageId;
			return this;
		}

		/**
		 * set mHost
		 *
		 * @param host
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder host(String host) {
			this.mHost = host;
			return this;
		}

		/**
		 * action
		 *
		 * @param action
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder action(String action) {
			this.mAction = action;
			return this;
		}

		/**
		 * @param intentAction {@link Intent#setAction(String)}
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder intentAction(String intentAction) {
			this.mIntentAction = intentAction;
			return this;
		}

		/**
		 * @param sticky
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder sticky(boolean sticky) {
			this.mSticky = sticky;
			return this;
		}

		/**
		 * @param inStack
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder inStack(boolean inStack) {
			this.mInStack = inStack;
			return this;
		}


		/**
		 * @param flags parameter for {@link Intent#setFlags(int)}
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder flags(int flags) {
			if (-1 == mFlags) {
				this.mFlags = flags;
			} else {
				this.mFlags |= flags;
			}
			return this;
		}

		/**
		 * @param cascade
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder cascade(boolean cascade) {
			this.cascade = cascade;
			return this;
		}

		/**
		 * forResult
		 *
		 * @param requestCode mRequestCode,must greater -1 else invalidate
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder requestCode(int requestCode) {
			if (requestCode >= 0) {
				this.mRequestCode = requestCode;
			}
			return this;
		}

		/**
		 * @param data
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder data(String data) {
			this.mData = data;
			return this;
		}

		/**
		 * add a {@link Interceptor} in {@link Builder#mInterceptors}
		 *
		 * @param interceptor {@link Interceptor}
		 * @return a {@link Builder} instance to chain calls
		 */
		public Builder interceptor(Interceptor interceptor) {
			if (null == mInterceptors) {
				mInterceptors = new ArrayList<>();
			}
			if (!mInterceptors.contains(interceptor)) {
				mInterceptors.add(interceptor);
			}
			return this;
		}

		/**
		 * startActivity this action
		 *
		 * @return a {@link PostActivityStarter} instance to provide starter methods
		 */
		public PostActivityStarter start() {
			Router router = new Router(this);
			return router.start();
		}


	}


}
