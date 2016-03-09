package com.napls.kana2048;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {

	private int num = 0;
	private TextView label;
	private double probability;

	public Card(Context context) {
		super(context);
		label = new CircleView(getContext());
		label.setTextSize(32);
		label.setBackgroundColor(0x33ffffff);
		label.setGravity(Gravity.CENTER);
		LayoutParams lp = new LayoutParams(-1, -1);
		lp.setMargins(10, 10, 0, 0);
		addView(label, lp);

		setNum(0);
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
		if (num <= 0) {
			label.setGravity(Gravity.CENTER);
			label.setBackgroundColor(0x33ffffff);
			label.setText("");
			removeView(label);
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.setMargins(10, 10, 0, 0);
			addView(label, lp);
		} else if (num == 2) {
			label.setGravity(Gravity.CENTER);
			label.setBackgroundColor(Color.parseColor("#F0FFF0"));
			setCharacter(num);
			removeView(label);
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.setMargins(10, 10, 0, 0);
			addView(label, lp);
		} else if (num == 2 * 2) {
			label.setGravity(Gravity.CENTER);
			label.setBackgroundColor(Color.parseColor("#CEFFCE"));
			probability = Math.random();
			setCharacter(num);
			removeView(label);
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.setMargins(10, 10, 0, 0);
			addView(label, lp);
		} else if (num == 2 * 2 * 2) {
			label.setGravity(Gravity.CENTER);
			label.setBackgroundColor(Color.parseColor("#A6FFA6"));
			setCharacter(num);
			removeView(label);
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.setMargins(10, 10, 0, 0);
			addView(label, lp);
		} else if (num == 2 * 2 * 2 * 2) {
			label.setGravity(Gravity.CENTER);
			label.setBackgroundColor(Color.parseColor("#79FF79"));
			setCharacter(num);
			removeView(label);
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.setMargins(10, 10, 0, 0);
			addView(label, lp);
		} else if (num == 2 * 2 * 2 * 2 * 2) {
			label.setGravity(Gravity.CENTER);
			label.setBackgroundColor(Color.parseColor("#53FF53"));
			setCharacter(num);
			removeView(label);
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.setMargins(10, 10, 0, 0);
			addView(label, lp);
		} else if (num == 2 * 2 * 2 * 2 * 2 * 2) {
			label.setGravity(Gravity.CENTER);
			label.setBackgroundColor(Color.parseColor("#00EC00"));
			setCharacter(num);
			removeView(label);
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.setMargins(10, 10, 0, 0);
			addView(label, lp);
		} else if (num == 2 * 2 * 2 * 2 * 2 * 2 * 2) {
			label.setGravity(Gravity.CENTER);
			label.setBackgroundColor(Color.parseColor("#00DB00"));
			setCharacter(num);
			removeView(label);
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.setMargins(10, 10, 0, 0);
			addView(label, lp);
		} else if (num == 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2) {
			label.setGravity(Gravity.CENTER);
			label.setBackgroundColor(Color.parseColor("#00BB00"));
			setCharacter(num);
			removeView(label);
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.setMargins(10, 10, 0, 0);
			addView(label, lp);
		} else if (num == 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2) {
			label.setGravity(Gravity.CENTER);
			label.setBackgroundColor(Color.parseColor("#009100"));
			setCharacter(num);
			removeView(label);
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.setMargins(10, 10, 0, 0);
			addView(label, lp);
		} else if (num == 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2) {
			label.setGravity(Gravity.CENTER);
			label.setBackgroundColor(Color.parseColor("#007500"));
			setCharacter(num);
			removeView(label);
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.setMargins(10, 10, 0, 0);
			addView(label, lp);
		} else if (num == 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2) {
			label.setGravity(Gravity.CENTER);
			label.setBackgroundColor(Color.parseColor("#006000"));
			setCharacter(num);
			removeView(label);
			LayoutParams lp = new LayoutParams(-1, -1);
			lp.setMargins(10, 10, 0, 0);
			addView(label, lp);
		}
	}

	public boolean equals(Card o) {
		return getNum() == o.getNum();
	}

	private void setCharacter(int num) {
		probability = Math.random();
		if (num == 2) {
			if (probability < 0.2) {
				label.setText("��");
			} else if (probability < 0.4) {
				label.setText("��");
			} else if (probability < 0.6) {
				label.setText("��");
			} else if (probability < 0.8) {
				label.setText("��");
			} else {
				label.setText("��");
			}
		} else if (num == 2 * 2) {
			if (probability < 0.2) {
				label.setText("��");
			} else if (probability < 0.4) {
				label.setText("��");
			} else if (probability < 0.6) {
				label.setText("��");
			} else if (probability < 0.8) {
				label.setText("��");
			} else {
				label.setText("��");
			}
		} else if (num == 2 * 2 * 2) {
			if (probability < 0.2) {
				label.setText("��");
			} else if (probability < 0.4) {
				label.setText("��");
			} else if (probability < 0.6) {
				label.setText("��");
			} else if (probability < 0.8) {
				label.setText("��");
			} else {
				label.setText("��");
			}
		} else if (num == 2 * 2 * 2 * 2) {
			if (probability < 0.2) {
				label.setText("��");
			} else if (probability < 0.4) {
				label.setText("��");
			} else if (probability < 0.6) {
				label.setText("��");
			} else if (probability < 0.8) {
				label.setText("��");
			} else {
				label.setText("��");
			}
		} else if (num == 2 * 2 * 2 * 2 * 2) {
			if (probability < 0.2) {
				label.setText("��");
			} else if (probability < 0.4) {
				label.setText("��");
			} else if (probability < 0.6) {
				label.setText("��");
			} else if (probability < 0.8) {
				label.setText("��");
			} else {
				label.setText("��");
			}
		} else if (num == 2 * 2 * 2 * 2 * 2 * 2) {
			if (probability < 0.2) {
				label.setText("��");
			} else if (probability < 0.4) {
				label.setText("��");
			} else if (probability < 0.6) {
				label.setText("��");
			} else if (probability < 0.8) {
				label.setText("��");
			} else {
				label.setText("��");
			}
		} else if (num == 2 * 2 * 2 * 2 * 2 * 2 * 2) {
			if (probability < 0.2) {
				label.setText("��");
			} else if (probability < 0.4) {
				label.setText("��");
			} else if (probability < 0.6) {
				label.setText("��");
			} else if (probability < 0.8) {
				label.setText("��");
			} else {
				label.setText("��");
			}
		} else if (num == 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2) {
			if (probability < 1.0 / 3) {
				label.setText("��");
			} else if (probability < 2.0 / 3) {
				label.setText("��");
			} else {
				label.setText("��");
			}
		} else if (num == 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2) {
			if (probability < 0.2) {
				label.setText("��");
			} else if (probability < 0.4) {
				label.setText("��");
			} else if (probability < 0.6) {
				label.setText("��");
			} else if (probability < 0.8) {
				label.setText("��");
			} else {
				label.setText("��");
			}
		} else if (num == 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2) {
			if (probability < 0.5) {
				label.setText("��");
			} else {
				label.setText("��");
			}
		} else if (num == 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2) {
			label.setText("��");
		}
	}
}
