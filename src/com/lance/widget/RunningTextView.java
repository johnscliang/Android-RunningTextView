package com.lance.widget;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * 一、设置跳跃的帧数，setFrames(int frames)，默认是25帧 二、设置数字格式 ， setFormat(String
 * pattern)，具体查DecimalFormat类的api 三、需要动画效果用，playNumber(double
 * number)方法代替setText()方法，小数如果超过3位小数会四舍五入保留2位小数
 * 
 * @author Lance
 *
 */
public class RunningTextView extends TextView {

	public double content;// 最后显示的数字
	private int frames = 25;// 总共跳跃的帧数,默认25跳
	private double nowNumber = 0.00;// 显示的时间
	private ExecutorService thread_pool;
	private Handler handler;
	private DecimalFormat formater;// 格式化时间，保留两位小数

	public RunningTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public RunningTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RunningTextView(Context context) {
		super(context);
		init();
	}

	public int getFrames() {
		return frames;
	}

	// 设置帧数
	public void setFrames(int frames) {
		this.frames = frames;
	}

	/**
	 * 设置数字格式，具体查DecimalFormat类的api
	 * @param pattern
	 */
	public void setFormat(String pattern) {
		formater = new DecimalFormat(pattern);
	}

	// 初始化
	private void init() {

		thread_pool = Executors.newFixedThreadPool(2);// 2个线程的线程池
		formater = new DecimalFormat("00.00");// 最多两位小数，而且不够两位整数用0占位。可以通过setFormat再次设置
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				RunningTextView.this.setText(formater.format(nowNumber)
						.toString());// 更新显示的数字
				nowNumber += Double.parseDouble(msg.obj.toString());// 跳跃arg1那么多的数字间隔
				Log.v("nowNumber增加之后的值", nowNumber + "");
				//
				if (nowNumber < content) {
					Message msg2 = handler.obtainMessage();
					msg2.obj = msg.obj;
					handler.sendMessage(msg2);// 继续发送通知改变UI
				} else {
					RunningTextView.this.setText(formater.format(content)
							.toString());// 最后显示的数字，动画停止
				}
			}
		};
	}

	/**
	 * 播放数字动画的方法
	 *
	 * @param moneyNumber
	 */
	public void playNumber(double moneyNumber) {
		if (moneyNumber == 0) {
			RunningTextView.this.setText("0.00");
			return;
		}
		content = moneyNumber;// 设置最后要显示的数字
		nowNumber = 0.00;// 默认都是从0开始动画
		thread_pool.execute(new Runnable() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				double temp = content / frames;
				msg.obj = temp < 0.01 ? 0.01 : temp;// 如果每帧的间隔比1小，就设置为1
				Log.v("每帧跳跃的数量：", "" + msg.obj.toString());
				handler.sendMessage(msg);// 发送通知改变UI
			}
		});
	}

}
