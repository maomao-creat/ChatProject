package com.zykj.samplechat.ui.widget;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

public class ScrollText extends TextView {

	public static final int TEXT_TIMER = 100;
	private float textLength = 0f;// 文本长度
	private float viewWidth = 0f;
	private float step = 0f;// 文字的横坐标
	private float y = 0f;// 文字的纵坐标
	private float temp_view_plus_text_length = 0.0f;// 用于计算的临时变量
	private float temp_view_plus_two_text_length = 0.0f;// 用于计算的临时变量
	private boolean isStarting = false;// 是否开始滚动
	private int left = 0;
	private int right = 0;
	private Paint paint = null;// 绘图样式
	private Bitmap txtBmp;
	private Canvas txtCanvas;
	private FontMetrics fontMetrics;
	private Timer timer = new Timer();
	private String stateStr;

	Handler handler;

	TimerTask task = new TimerTask() {
		public void run() {
			if (handler != null && isStarting) {
				Message msg = Message.obtain();
				msg.what = TEXT_TIMER;
				handler.sendMessage(msg);
			}
		}
	};

	public ScrollText(Context context) {
		super(context);
	}

	public ScrollText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/** */
	/**
	 * 文本初始化，每次更改文本内容或者文本效果等之后都需要重新初始化一下
	 */
	public void init(Handler handler) {
		try {
			this.handler = handler;
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStyle(Style.STROKE);
			paint.setTextSize(getTextSize());
			paint.setColor(getCurrentTextColor());
			textLength = 0;
			// textLength = paint.measureText(text);
			float xLen = paint.measureText(stateStr);
			textLength += xLen;
			left = this.getPaddingLeft();
			right = this.getPaddingRight();
			step = textLength;
			fontMetrics = paint.getFontMetrics();

			y = getPaddingTop();// getTextSize() + getPaddingTop();
			txtBmp = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void scrollText() {
		if (!isStarting) {
			return;
		}
		if (viewWidth < textLength) {
			invalidate();
			step += 0.5;
			if (step > temp_view_plus_two_text_length) {
				step = textLength;
			}
		}
	}

	public void setStateList(String stateStr) {
		this.stateStr = stateStr;
	}

	private void setTxtBmp() {
		if (txtBmp == null && fontMetrics != null) {
			y = -paint.ascent();// fontMetrics.bottom -
			// fontMetrics.ascent;//(this.getHeight() -
			// (int)fontMetrics.ascent)/2;
			viewWidth = getWidth() - left - right;
			temp_view_plus_text_length = textLength;
			temp_view_plus_two_text_length = textLength * 2;
			txtCanvas = new Canvas();

			int width = (int) viewWidth;
			float height = getHeight();
			txtBmp = Bitmap.createBitmap(width, (int) height, Config.ARGB_8888);
			txtCanvas.setBitmap(txtBmp);

		}
	}

	/** */
	/**
	 * 开始滚动
	 */
	public void startScroll() {
		isStarting = true;
	}

	/** */
	/**
	 * 停止滚动
	 */
	public void stopScroll() {
		isStarting = false;
		// invalidate();
	}

	public void start() {
		timer.schedule(task, 10, 20);
	}

	public void stop() {
		timer.cancel();
	}

	@Override
	public void onDraw(Canvas canvas) {
		try {
			setTxtBmp();
			if (txtBmp == null) {
				return;
			}
			Paint txtPaint = new Paint();
			txtPaint.setColor(0xffefefef);
			txtPaint.setStyle(Style.FILL);
			txtCanvas.drawRect(0, 0, txtBmp.getWidth(), txtBmp.getHeight(), txtPaint);
			txtPaint.setAntiAlias(true);
			//txtPaint.setStyle(Style.STROKE);
			//txtPaint.setStrokeWidth(1);
			txtPaint.setTextSize(getTextSize());
			txtPaint.setColor(getCurrentTextColor());
			float x = 0;
			// step为text的宽度
			if (viewWidth < textLength) {
				x = temp_view_plus_text_length - step;
			}
			float curLen = x;
			String strContent = stateStr;
			strContent = strContent.replaceAll("\n", " ");
			//因为x的值一直减少，所以文字可以滚动
			txtCanvas.drawText(strContent+strContent, curLen, y+2, txtPaint);
			canvas.drawBitmap(txtBmp, left, 0, paint);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		txtBmp = null;
		setTxtBmp();
	}
}