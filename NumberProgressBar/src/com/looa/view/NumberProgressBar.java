package com.looa.view;

import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.looa.numberprogressbar.R;

/**
 * 
 * @author ran
 * 
 */
public class NumberProgressBar extends LinearLayout {

	private TextView leftTextView;
	private TextView rightTextView;
	private TextView percentTextView;

	private LayoutParams leftParam;
	private LayoutParams rightParam;
	private LayoutParams percentParam;

	private int mNumberColor;
	private float mNumberSize;
	private boolean mNumberVisibility;
	private String mDefaultText;
	private float mNumProHeight;

	private int defaultPercentLength = 0;

	private final static float DEFAULT_TEXT_SIZE = 13;
	private final static float DEFAULT_BAR_HEIGHT = 5;
	private final static int DEFAULT_TEXT_COLOR = Color.parseColor("#00a8f3");
	private final static int DEFAULT_COLOR = Color.parseColor("#acacac");
	private final static String DEFAULT_TEXT = "0.0%";

	private int dataMax;
	private int dataDurLength;

	private Context context;

	public NumberProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public NumberProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		this.context = context;

		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.NumberProgressBar);

		mNumberColor = ta.getColor(R.styleable.NumberProgressBar_Numbercolor,
				DEFAULT_TEXT_COLOR);
		mNumberSize = ta.getDimension(R.styleable.NumberProgressBar_NumberSize,
				DEFAULT_TEXT_SIZE);
		mNumberVisibility = ta.getBoolean(
				R.styleable.NumberProgressBar_NumberVisibility, true);
		mNumProHeight = ta.getDimension(R.styleable.NumberProgressBar_NumProHeight,
				DEFAULT_BAR_HEIGHT);
		mDefaultText = DEFAULT_TEXT;

		ta.recycle();

		initLayout();
	}

	private void initLayout() {
		leftTextView = new TextView(context);
		rightTextView = new TextView(context);
		percentTextView = new TextView(context);

		leftTextView.setBackgroundColor(mNumberColor);
		leftTextView.setHeight((int) mNumProHeight);

		rightTextView.setBackgroundColor(DEFAULT_COLOR);
		rightTextView.setHeight((int) mNumProHeight);

		percentTextView.setText(mDefaultText);
		percentTextView.setTextColor(mNumberColor);
		percentTextView.setTextSize(mNumberSize);
		percentTextView.setVisibility(getVisibility(mNumberVisibility));
		percentTextView.setGravity(Gravity.CENTER);

		leftParam = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
		leftParam.gravity = Gravity.CENTER_VERTICAL;
		addView(leftTextView, leftParam);

		percentParam = new LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		percentParam.setMargins(5, 0, 5, 0);
		percentParam.gravity = Gravity.CENTER_VERTICAL;
		addView(percentTextView, percentParam);

		rightParam = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
		rightParam.gravity = Gravity.CENTER_VERTICAL;
		rightParam.weight = 1;
		addView(rightTextView, rightParam);

	}

	/**
	 * ���������ܳ���
	 * 
	 * @param dataMax
	 */
	public void setMax(int dataMax) {
		this.dataMax = dataMax;
	}

	public int getMax() {
		return dataMax;
	}

	/**
	 * ���õ�ǰ�������Ľ���
	 * 
	 * @param dataDurLength
	 */
	public void setProgress(int dataDurLength) {

		if (dataDurLength < 0) {
			this.dataDurLength = 0;
		} else if (dataDurLength > dataMax) {
			this.dataDurLength = dataMax;
		} else {
			this.dataDurLength = dataDurLength;
		}
		setPercent(this.dataDurLength);

	}

	/**
	 * ��ȡ��ǰ���ȣ�����Ϊ100
	 * 
	 * @param dataDurLength
	 * @return
	 */
	public float getPercent(int dataDurLength) {
		float percent = 0.0f;
		DecimalFormat df = new DecimalFormat("#.0");

		if (dataDurLength != 0) {
			percent = Float.parseFloat(df
					.format((dataDurLength * 1.0f / dataMax) * 100));
		}

		return percent;
	}

	private void setPercent(int dataDurLength) {

		if (defaultPercentLength == 0) {
			defaultPercentLength = percentTextView.getWidth();
		}

		float percent;
		float weight = 0;

		percent = getPercent(dataDurLength);
		weight = percent / 100.0f;

		percentTextView.setLines(1);
		percentTextView.setText(percent + "%");

		leftParam.weight = weight;
		rightParam.weight = 1 - weight;

	}

	/**
	 * ���������Ƿ�ɼ�
	 * 
	 * @param param
	 */
	public void setNumberVisibility(boolean param) {
		percentTextView.setVisibility(getVisibility(param));
	}

	private int getVisibility(boolean param) {
		if (param) {
			return View.VISIBLE;
		} else {
			return View.GONE;
		}
	}
}
