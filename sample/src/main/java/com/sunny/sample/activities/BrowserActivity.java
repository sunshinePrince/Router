package com.sunny.sample.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sunny.sample.R;
import com.sunshineprince.router.Router;

/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class BrowserActivity extends BaseActivity {


	WebView webView;

	String url;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser);
		webView = (WebView) findViewById(R.id.browser);
		init();
	}

	private void init() {
		WebSettings settings = webView.getSettings();
		settings.setDefaultTextEncodingName("utf-8");
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);

		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);

		url = getIntent().getStringExtra(Router.EXTRA_URL);

		webView.setWebViewClient(new MyWebViewClient());
		webView.clearCache(true);

		webView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack() && KeyEvent.ACTION_UP == event.getAction()) {
					webView.goBack();
					return true;
				}
				return false;
			}
		});
		settings.setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new JSObject(), "sample");
		if (!TextUtils.isEmpty(url)) {
			webView.loadUrl(url);
		} else {
			webView.loadUrl("file:///android_asset/sample.html");
		}
	}


	public class JSObject {

		@JavascriptInterface
		public void fromJS(final String url) {
			webView.post(new Runnable() {
				@Override
				public void run() {
//					Log.d(TAG,"url:"+url);
				}
			});
		}
	}


	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if(url.startsWith("sunny://")){
				Router.builder(BrowserActivity.this).uri(url).start();
			}else{
				webView.loadUrl(url);
			}
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			String title = view.getTitle();
			if(!TextUtils.isEmpty(title)){
				setTitle(title);
			}
		}
	}


}
