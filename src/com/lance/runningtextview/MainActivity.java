package com.lance.runningtextview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private Button mBtrmb;
	private Button mBtdollar;
	private Button mBturo;
	private com.lance.widget.RunningTextView mRunningtextview;
	private EditText mEdit;
	private Button mBtplay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindViews();
		// 设置数字格式，具体查DecimalFormat类的api
		mRunningtextview.setFormat("00.00");
		mBtplay.setOnClickListener(this);
		mBtrmb.setOnClickListener(this);
		mBtdollar.setOnClickListener(this);
		mBturo.setOnClickListener(this);
	}

	private void bindViews() {

		mBtrmb = (Button) findViewById(R.id.btrmb);
		mBtdollar = (Button) findViewById(R.id.btdollar);
		mBturo = (Button) findViewById(R.id.bturo);
		mRunningtextview = (com.lance.widget.RunningTextView) findViewById(R.id.runningtextview);
		mEdit = (EditText) findViewById(R.id.edit);
		mBtplay = (Button) findViewById(R.id.btplay);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btplay:
			// 播放数字动画
			String temp = mEdit.getText().toString();
			if (!temp.equals("") || !(temp == null)) {
				double number = Double.parseDouble(temp);
				mRunningtextview.playNumber(number);
			}
			break;

		case R.id.btrmb:
			Toast.makeText(this, "use the symbol ￥ ", Toast.LENGTH_SHORT).show();
			mRunningtextview.setFormat("￥00.00");
			break;
		case R.id.bturo:
			Toast.makeText(this, "use the symbol € ", Toast.LENGTH_SHORT).show();
			mRunningtextview.setFormat("€00.00");
			break;
		case R.id.btdollar:
			Toast.makeText(this, "use the symbol $ ", Toast.LENGTH_SHORT).show();
			mRunningtextview.setFormat("$00.00");
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(this, "Oh yeah~", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
