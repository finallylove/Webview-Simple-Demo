package com.example.demo2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private AutoCompleteTextView mUrl;
	private WebView mWebView;
	private Button backBtn;
	private Button forwardBtn;
	private Button refreshBtn;
	private Button homeBtn;

	private String[] bookArray = new String[] { "http://www.baidu.com",
			"http://www.163.com" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mWebView = (WebView) findViewById(R.id.show);
		WebSettings setting = mWebView.getSettings();
		setting.setJavaScriptEnabled(true);
		setting.setBuiltInZoomControls(true);
		
		backBtn=(Button)findViewById(R.id.back);
		forwardBtn=(Button)findViewById(R.id.forward);
		refreshBtn=(Button)findViewById(R.id.refresh);
		homeBtn=(Button)findViewById(R.id.home);
		backBtn.setOnClickListener(this);
		forwardBtn.setOnClickListener(this);
		refreshBtn.setOnClickListener(this);
		homeBtn.setOnClickListener(this);
		backBtn.setEnabled(false);
		forwardBtn.setEnabled(false);

		mUrl = (AutoCompleteTextView) findViewById(R.id.url);
		ArrayAdapter<String> urlAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, bookArray);
		mUrl.setAdapter(urlAdapter);
		mUrl.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					String strUrl = mUrl.getText().toString();
					Pattern p = Pattern
							.compile("http://([\\w-]+\\.)+[\\w-]+(/[\\w-\\./?%=]*)?");
					Matcher m = p.matcher(strUrl);
					if (!m.find()) {
						strUrl = "http://" + strUrl;
					}
					mWebView.loadUrl(strUrl);
					return true;
				}
				return false;
			}
		});

		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				mUrl.setText(url);
				return false;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				mUrl.setText(url);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				// super.onReceivedError(view, errorCode, description,
				// failingUrl);
				Toast.makeText(MainActivity.this, description,
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			mWebView.goBack();
			break;
		case R.id.forward:
			mWebView.goForward();
			break;
		case R.id.refresh:
			mWebView.reload();
			break;
		case R.id.home:
			mWebView.loadUrl(bookArray[0]);
			break;

		}

	}
}
