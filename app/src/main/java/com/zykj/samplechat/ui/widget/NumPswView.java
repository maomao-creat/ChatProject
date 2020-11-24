package com.zykj.samplechat.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.zykj.samplechat.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by dongqing on 2017/3/2.
 * 
 * 数字密码输入框
 */

public class NumPswView extends AppCompatEditText {

    private int textLength;

    private int borderColor;
    private float borderWidth;
    private float borderRadius;
    private float borderMargin;

    private int passwordLength;
    private int passwordColor;
    private int solidColor;
    private float passwordWidth;
    private float passwordRadius;

    private Paint passwordPaint = new Paint(ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(ANTI_ALIAS_FLAG);


    public NumPswView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final Resources res = getResources();

        final int defaultBorderColor = res
                .getColor(R.color.default_ev_border_color);
        final float defaultBorderWidth = res
                .getDimension(R.dimen.default_ev_border_width);
        final float defaultBorderRadius = res
                .getDimension(R.dimen.default_ev_border_radius);
        final float defaultBorderMargin = res
                .getDimension(R.dimen.default_ev_border_margin);

        final int defaultPasswordLength = res
                .getInteger(R.integer.default_ev_password_length);
        final int defaultPasswordColor = res
                .getColor(R.color.default_ev_password_color);
        final int defaultSolidColor = res
                .getColor(R.color.default_ev_solid_color);
        final float defaultPasswordWidth = res
                .getDimension(R.dimen.default_ev_password_width);
        final float defaultPasswordRadius = res
                .getDimension(R.dimen.default_ev_password_radius);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.NumPswView, 0, 0);
        try {
            borderColor = a.getColor(R.styleable.NumPswView_npborderColor,
                    defaultBorderColor);
            borderWidth = a.getDimension(
                    R.styleable.NumPswView_npborderWidth,
                    defaultBorderWidth);
            borderRadius = a.getDimension(
                    R.styleable.NumPswView_npborderRadius,
                    defaultBorderRadius);
            borderMargin = a.getDimension(
                    R.styleable.NumPswView_npborderMargin,
                    defaultBorderMargin);
            passwordLength = a.getInt(
                    R.styleable.NumPswView_nppasswordLength,
                    defaultPasswordLength);
            passwordColor = a.getColor(
                    R.styleable.NumPswView_nppasswordColor,
                    defaultPasswordColor);
            solidColor = a.getColor(R.styleable.NumPswView_npsolidColor,
                    defaultSolidColor);
            passwordWidth = a.getDimension(
                    R.styleable.NumPswView_nppasswordWidth,
                    defaultPasswordWidth);
            passwordRadius = a.getDimension(
                    R.styleable.NumPswView_nppasswordRadius,
                    defaultPasswordRadius);
        } finally {
            a.recycle();
        }

        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);
        passwordPaint.setStrokeWidth(passwordWidth);
        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(passwordColor);

        ViewTreeObserver observer = getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                int width = getWidth();
                int height = (int) (width - (passwordLength - 1) * borderMargin)
                        / passwordLength;
                setHeight(height);
                return true;
            }
        });

        // 禁止复制
        setLongClickable(false);
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        // 外边框
        RectF rect = new RectF(0, 0, width, height);
        borderPaint.setColor(borderColor);
        canvas.drawRoundRect(rect, borderRadius, borderRadius, borderPaint);
        //
        // // 内容区
        // RectF rectIn = new RectF(rect.left + defaultContMargin, rect.top
        // + defaultContMargin, rect.right - defaultContMargin,
        // rect.bottom - defaultContMargin);
        // borderPaint.setColor(solidColor);
        // canvas.drawRoundRect(rectIn, borderRadius, borderRadius,
        // borderPaint);
        //
        // // 分割线
        // borderPaint.setColor(borderColor);
        // borderPaint.setStrokeWidth(defaultSplitLineWidth);
        // for (int i = 1; i < passwordLength; i++) {
        // float x = width * i / passwordLength;
        // canvas.drawLine(x, 0, x, height, borderPaint);
        // }
        //
        // // 密码
        // float cx, cy = height / 2;
        // float half = width / passwordLength / 2;
        // for (int i = 0; i < textLength; i++) {
        // cx = width * i / passwordLength + half;
        // canvas.drawCircle(cx, cy, passwordWidth, passwordPaint);
        // }

        // add by dq

        for (int i = 0; i < passwordLength; i++) {
            borderPaint.setColor(solidColor);
            float x1 = i
                    * ((width - (passwordLength - 1) * borderMargin)
                    / passwordLength + borderMargin);
            float x2 = (i + 1)
                    * ((width - (passwordLength - 1) * borderMargin) / passwordLength)
                    + i * borderMargin;
            RectF r = new RectF(x1, 0, x2, height);
            canvas.drawRoundRect(r, borderRadius, borderRadius, borderPaint);
        }
        // 密码
        float cx, cy = height / 2;
        for (int i = 0; i < textLength; i++) {
            cx = ((width - (passwordLength - 1) * borderMargin) / passwordLength)
                    * (2 * i + 1) / 2 + i * borderMargin;
            canvas.drawCircle(cx, cy, passwordWidth, passwordPaint);
        }
        setCursorVisible(false);//隐藏光标
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                setSelection(getText().length());//设置光标在末端
                return false;
            }
        });
    }

    @Override
    protected void onTextChanged(CharSequence text, int start,
                                 int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.toString().length();
        invalidate();
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        borderPaint.setStrokeWidth(borderWidth);
        invalidate();
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
        invalidate();
    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
        invalidate();
    }

    public int getPasswordColor() {
        return passwordColor;
    }

    public void setPasswordColor(int passwordColor) {
        this.passwordColor = passwordColor;
        passwordPaint.setColor(passwordColor);
        invalidate();
    }

    public float getPasswordWidth() {
        return passwordWidth;
    }

    public void setPasswordWidth(float passwordWidth) {
        this.passwordWidth = passwordWidth;
        passwordPaint.setStrokeWidth(passwordWidth);
        invalidate();
    }

    public float getPasswordRadius() {
        return passwordRadius;
    }

    public void setPasswordRadius(float passwordRadius) {
        this.passwordRadius = passwordRadius;
        invalidate();
    }
}
