package com.graupnerservo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

public class Impressum extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	protected AndroidApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		app = (AndroidApplication) getApplication();
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.impressum);
		TextView impressumText = (TextView) findViewById(R.id.txtImpressumLong);
		impressumText.setText(Html.fromHtml(app.impressum));
	}

	/**
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// TODO Put your code here
	}
}
