package com.graupnerservo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewer extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		setContentView(R.layout.webpage);
		WebView webview = (WebView)findViewById(R.id.webview);
		webview.loadUrl("http://www.facebook.com/GraupnerNews");
	}
}
