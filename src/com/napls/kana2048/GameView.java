package com.napls.kana2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameView extends GridLayout {

	private Card[][] cardsMap = new Card[5][5];
	private int[][] cardsMapNum = new int[5][5];
	private List<Point> emptyPoints = new ArrayList<Point>();

	private Animation swipeLeftAnimation;
	private Animation swipeRightAnimation;
	private Animation swipeUpAnimation;
	private Animation swipeDownAnimation;

	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initGameView();

	}

	public GameView(Context context) {
		super(context);
		initGameView();

	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();

	}

	private void initGameView() {
		setColumnCount(5);
		// setBackgroundColor(0xffbbada0);// Game main background

		setOnTouchListener(new View.OnTouchListener() {

			private float startX, startY, offsetX, offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;
					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX < -5) {
							swipeLeft();
						} else if (offsetX > 5) {
							swipeRight();
						}
					} else {
						if (offsetY < -5) {
							swipeUp();
						} else if (offsetY > 5) {
							swipeDown();
						}
					}

					break;
				}
				return true;
			}
		});
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int cardWidth = (Math.min(w, h) - 10) / 5;
		addCards(cardWidth, cardWidth);
		int cardsNum0Counter = 0;
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				cardsMap[x][y].setNum(cardsMapNum[x][y]);
				if (cardsMap[x][y].getNum() == 0)
					cardsNum0Counter++;
			}
		}
		if (cardsNum0Counter == 25) {
			startGame();
		}
	}

	public void startNewGame() {
		startGame();
	}

	private void addCards(int cardWidth, int cardHeight) {
		Card card;
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				card = new Card(getContext());
				card.setNum(0);
				addView(card, cardWidth, cardHeight);

				cardsMap[x][y] = card;
			}
		}
	}

	private void startGame() {

		if (MainActivity.getMainActivity() != null) {
			MainActivity.getMainActivity().clearScore();
		}
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				cardsMap[x][y].setNum(0);
			}
		}
		addRandomNum();
		addRandomNum();
		addRandomNum();
	}

	private void addRandomNum() {
		emptyPoints.clear();
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				if (cardsMap[x][y].getNum() <= 0) {
					emptyPoints.add(new Point(x, y));
				}
			}
		}
		Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
		cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
	}

	private void checkComplete() {

		boolean complete = true;
		boolean gameWon = false;

		ALL: for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				if (cardsMap[x][y].getNum() == 0 || (x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y]))
						|| (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y]))
						|| (y > 0) && cardsMap[x][y].equals(cardsMap[x][y - 1])
						|| (y < 3) && cardsMap[x][y].equals(cardsMap[x][y + 1])) {
					complete = false;
					break ALL;
				}
			}
		}

		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				if (cardsMap[x][y].getNum() == 2048) {
					complete = true;
					gameWon = true;
				}
			}
		}

		if (complete) {
			if (gameWon) {
				AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.getMainActivity()).create();
				alertDialog.show();
				Window window = alertDialog.getWindow();
				window.setContentView(R.layout.dialog_judge_layout);
				TextView tv_message = (TextView) window.findViewById(R.id.dialog_judge_message);
				tv_message.setGravity(Gravity.CENTER);
				tv_message.setTextColor(Color.parseColor("#FFD700"));
				if (MainActivity.getMainActivity().getCurrent_score() > MainActivity.getMainActivity()
						.getBest_score()) {
					tv_message.setText("勝利！" + "\n\n" + "恭喜，您已创造了新记录！" + "\n\n" + "最高得分："
							+ MainActivity.getMainActivity().getCurrent_score());
					MainActivity.getMainActivity().setBest_score(MainActivity.getMainActivity().getCurrent_score());
				} else {
					tv_message.setText("勝利！" + "\n\n" + "恭喜，您已赢得了游戏！" + "\n\n" + "本局得分："
							+ MainActivity.getMainActivity().getCurrent_score());
				}
				startGame();
				// new
				// AlertDialog.Builder(getContext()).setTitle("hello!").setMessage("You
				// win!")
				// .setPositiveButton("Play Again", new
				// DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				// startGame();
				// }
				// }).show();

			} else {
				AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.getMainActivity()).create();
				alertDialog.show();
				Window window = alertDialog.getWindow();
				window.setContentView(R.layout.dialog_judge_layout);
				TextView tv_message = (TextView) window.findViewById(R.id.dialog_judge_message);
				tv_message.setGravity(Gravity.CENTER);
				tv_message.setTextColor(Color.parseColor("#FFF0F5"));
				tv_message.setText("失敗！" + "\n\n" + "很遗憾，游戏失败");
				startGame();
				// new
				// AlertDialog.Builder(getContext()).setTitle("hello!").setMessage("You
				// lose!")
				// .setPositiveButton("Play Again", new
				// DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				// startGame();
				// }
				// }).show();
			}
		}
	}

	private void swipeLeft() {
		boolean merge = false; // y是行，x是列
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				for (int x1 = x + 1; x1 < 5; x1++) { // 向右遍历
					if (cardsMap[x1][y].getNum() > 0) { // 右边不为空
						if (cardsMap[x][y].getNum() <= 0) { // 当前位置为空
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum()); // 当前位置设为右边值
							swipeLeftAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.swipe_left);
							cardsMap[x1][y].startAnimation(swipeLeftAnimation);
							cardsMap[x1][y].setNum(0); // 右边值设为空
							x--;
							merge = true;
						} else if (cardsMap[x][y].equals(cardsMap[x1][y])) { // 右边值与当前值相等
							swipeLeftAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.swipe_left);
							cardsMap[x1][y].startAnimation(swipeLeftAnimation);
							Rotate3dLeft rotate = new Rotate3dLeft();
							rotate.setDuration(200);
							cardsMap[x][y].measure(0, 0);
							rotate.setCenter(cardsMap[x][y].getMeasuredWidth() * 2, cardsMap[x][y].getMeasuredHeight());
							rotate.setFillAfter(true); // 使动画结束后定在最终画面，如果不设置为true，则将会回到初始画面
							cardsMap[x][y].startAnimation(rotate);
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2); // 当前值翻倍
							cardsMap[x1][y].setNum(0); // 右边值设为空

							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}

	private void swipeRight() {
		boolean merge = false;
		for (int y = 0; y < 5; y++) {
			for (int x = 4; x >= 0; x--) {
				for (int x1 = x - 1; x1 >= 0; x1--) { // 向右遍历
					if (cardsMap[x1][y].getNum() > 0) { // 右边不为空
						if (cardsMap[x][y].getNum() <= 0) { // 当前位置为空
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum()); // 当前位置设为右边值
							swipeRightAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.swipe_right);
							cardsMap[x1][y].startAnimation(swipeRightAnimation);
							cardsMap[x1][y].setNum(0); // 右边值设为空

							x++;
							merge = true;
						} else if (cardsMap[x][y].equals(cardsMap[x1][y])) { // 右边值与当前值相等
							swipeRightAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.swipe_right);
							cardsMap[x1][y].startAnimation(swipeRightAnimation);
							Rotate3dRight rotate = new Rotate3dRight();
							rotate.setDuration(200);
							cardsMap[x][y].measure(0, 0);
							rotate.setCenter(cardsMap[x][y].getMeasuredWidth() * 2, cardsMap[x][y].getMeasuredHeight());
							rotate.setFillAfter(true); // 使动画结束后定在最终画面，如果不设置为true，则将会回到初始画面
							cardsMap[x][y].startAnimation(rotate);
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2); // 当前值翻倍
							cardsMap[x1][y].setNum(0); // 右边值设为空

							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}

	private void swipeUp() {
		boolean merge = false;
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				for (int y1 = y + 1; y1 < 5; y1++) { // 向右遍历
					if (cardsMap[x][y1].getNum() > 0) { // 右边不为空
						if (cardsMap[x][y].getNum() <= 0) { // 当前位置为空
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum()); // 当前位置设为右边值
							swipeUpAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.swipe_up);
							cardsMap[x][y1].startAnimation(swipeUpAnimation);
							cardsMap[x][y1].setNum(0); // 右边值设为空

							y--;
							merge = true;
						} else if (cardsMap[x][y].equals(cardsMap[x][y1])) { // 右边值与当前值相等
							swipeUpAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.swipe_up);
							cardsMap[x][y1].startAnimation(swipeUpAnimation);
							Rotate3dUp rotate = new Rotate3dUp();
							rotate.setDuration(200);
							cardsMap[x][y].measure(0, 0);
							rotate.setCenter(cardsMap[x][y].getMeasuredWidth() * 2, cardsMap[x][y].getMeasuredHeight());
							rotate.setFillAfter(true); // 使动画结束后定在最终画面，如果不设置为true，则将会回到初始画面
							cardsMap[x][y].startAnimation(rotate);
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2); // 当前值翻倍
							cardsMap[x][y1].setNum(0); // 右边值设为空

							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}

	private void swipeDown() {
		boolean merge = false;
		for (int x = 0; x < 5; x++) {
			for (int y = 4; y >= 0; y--) {
				for (int y1 = y - 1; y1 >= 0; y1--) { // 向右遍历
					if (cardsMap[x][y1].getNum() > 0) { // 右边不为空
						if (cardsMap[x][y].getNum() <= 0) { // 当前位置为空
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum()); // 当前位置设为右边值
							swipeDownAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.swipe_down);
							cardsMap[x][y1].startAnimation(swipeDownAnimation);
							cardsMap[x][y1].setNum(0); // 右边值设为空

							y++;
							merge = true;
						} else if (cardsMap[x][y].equals(cardsMap[x][y1])) { // 右边值与当前值相等
							swipeDownAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.swipe_down);
							cardsMap[x][y1].startAnimation(swipeDownAnimation);
							Rotate3dDown rotate = new Rotate3dDown();
							rotate.setDuration(200);
							cardsMap[x][y].measure(0, 0);
							rotate.setCenter(cardsMap[x][y].getMeasuredWidth() * 2, cardsMap[x][y].getMeasuredHeight());
							rotate.setFillAfter(true); // 使动画结束后定在最终画面，如果不设置为true，则将会回到初始画面
							cardsMap[x][y].startAnimation(rotate);
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2); // 当前值翻倍
							cardsMap[x][y1].setNum(0); // 右边值设为空

							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}
		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}

	public Card[][] getCardsMap() {
		return cardsMap;
	}

	public void setCardsMap(Card[][] cardsMap) {
		this.cardsMap = cardsMap;
	}

	public void setCardsMapNum(int[][] cardsMapNum) {
		this.cardsMapNum = cardsMapNum;
	}

}

class Rotate3dLeft extends Animation {
	private float mCenterX = 0;
	private float mCenterY = 0;

	public void setCenter(float centerX, float centerY) {
		mCenterX = centerX;
		mCenterY = centerY;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		Matrix matrix = t.getMatrix();
		Camera camera = new Camera();
		camera.save();
		camera.rotateY(360 * interpolatedTime);
		camera.getMatrix(matrix);
		camera.restore();
		matrix.preTranslate(-mCenterX, -mCenterY);
		matrix.postTranslate(mCenterX, mCenterY);
	}

}

class Rotate3dRight extends Animation {
	private float mCenterX = 0;
	private float mCenterY = 0;

	public void setCenter(float centerX, float centerY) {
		mCenterX = centerX;
		mCenterY = centerY;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		Matrix matrix = t.getMatrix();
		Camera camera = new Camera();
		camera.save();
		camera.rotateY(-360 * interpolatedTime);
		camera.getMatrix(matrix);
		camera.restore();
		matrix.preTranslate(-mCenterX, -mCenterY);
		matrix.postTranslate(mCenterX, mCenterY);
	}

}

class Rotate3dUp extends Animation {
	private float mCenterX = 0;
	private float mCenterY = 0;

	public void setCenter(float centerX, float centerY) {
		mCenterX = centerX;
		mCenterY = centerY;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		Matrix matrix = t.getMatrix();
		Camera camera = new Camera();
		camera.save();
		camera.rotateX(360 * interpolatedTime);
		camera.getMatrix(matrix);
		camera.restore();
		matrix.preTranslate(-mCenterX, -mCenterY);
		matrix.postTranslate(mCenterX, mCenterY);
	}

}

class Rotate3dDown extends Animation {
	private float mCenterX = 0;
	private float mCenterY = 0;

	public void setCenter(float centerX, float centerY) {
		mCenterX = centerX;
		mCenterY = centerY;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		Matrix matrix = t.getMatrix();
		Camera camera = new Camera();
		camera.save();
		camera.rotateX(-360 * interpolatedTime);
		camera.getMatrix(matrix);
		camera.restore();
		matrix.preTranslate(-mCenterX, -mCenterY);
		matrix.postTranslate(mCenterX, mCenterY);
	}

}
