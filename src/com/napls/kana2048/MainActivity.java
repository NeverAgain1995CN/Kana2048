package com.napls.kana2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView currentScore;
	private TextView bestScore;
	private TextView addScoreText;
	private Animation addScoreAnimation;
	private Animation addScoreComboAnimation;
	private int comboNum[] = new int[2];
	private int comboCounter = 0;

	private Button newGame;
	private Button help;
	private Button kanaTable;
	private int current_score = 0;
	private int best_score = 10000;

	public int w, h, oldw, oldh;

	public MainActivity() {
		mainActivity = this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		currentScore = (TextView) findViewById(R.id.score_current);
		bestScore = (TextView) findViewById(R.id.score_best);
		addScoreText = (TextView) findViewById(R.id.add_score_anim);
		addScoreAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.add_score);
		addScoreComboAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.add_score_combo);

		newGame = (Button) findViewById(R.id.new_game);
		help = (Button) findViewById(R.id.help);
		kanaTable = (Button) findViewById(R.id.kana_table);

		help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
				alertDialog.show();
				Window window = alertDialog.getWindow();
				window.setContentView(R.layout.dialog_layout);
				TextView tv_message = (TextView) window.findViewById(R.id.dialog_message);
				tv_message.setText("使用手指在游戏区滑动假名块" + "\n" + "相同行的假名块会并到一起并生成新一行的假名块。" + "\n"
						+ "如：あ行的“あ”与“お”并成か行的一个随机假名块，如“き”" + "\n\n" + "获胜条件：成功合成假名块“ん”" + "\n"
						+ "失败条件：在合成假名块“ん”之前，假名块填满游戏区" + "\n\n" + "提示：点击“假名表”可查看各行假名" + "\n\n" + "希望大家能通过此游戏更快地掌握日语假名！"
						+ "\n\n" + "作者：NA Personal Learning Studio" + "\n" + "联系邮箱：neveragain1995@163.com" + "\n"
						+ "QQ:503163740 (问题答案：丁晔澎）" + "\n" + "欢迎交流！");
			}
		});
		newGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < 2; i++) {
					comboNum[i] = 0;
				}
				GameView gameView = (GameView) findViewById(R.id.game_view);
				gameView.startNewGame();
			}
		});

		kanaTable.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
				alertDialog.show();
				Window window = alertDialog.getWindow();
				window.setContentView(R.layout.dialog_layout);
				TextView tv_message = (TextView) window.findViewById(R.id.dialog_message);
				tv_message.setText("あ行 あ(a): い(i) う(u) え(e) お(o) \n" + "\n" + "か行: か(ka) き(ki) く(ku) け(ke) こ(ko) \n"
						+ "\n" + "さ行: さ(sa) し(shi) す(su) せ(se) そ(so) \n" + "\n"
						+ "た行: た(ta) ち(chi) つ(tsu) て(te) と(to) \n" + "\n" + "な行: な(na) に(ni) ぬ(nu) ね(ne) の(no) \n"
						+ "\n" + "は行: は(ha) ひ(hi) ふ(fu) へ(he) ほ(ho) \n" + "\n" + "ま行: ま(ma) み(mi) む(mu) め(me) も(mo) \n"
						+ "\n" + "や行: や(ya) ゆ(yu) よ(yo) \n" + "\n" + "ら行: ら(ra) り(ri) る(ru) れ(re) ろ(ro) \n" + "\n"
						+ "わ行: わ(wa) を(o) \n" + "\n" + "ん(n)");
			}
		});
	}

	public void clearScore() {
		current_score = 0;
		showScore();
	}

	public void showScore() {
		currentScore.setText(current_score + "");
		bestScore.setText(best_score + "");
	}

	public void addScore(int score) {
		if (comboCounter == 0) {
			comboNum[comboCounter] = score;
			comboCounter = 1;
		} else {
			comboNum[comboCounter] = score;
			comboCounter = 0;
		}
		if (comboNum[0] == comboNum[1]) {
			VibratorUtil.Vibrate(MainActivity.getMainActivity(), 100);
			addScoreText.setText("连击！ " + "+" + score + "*2");
			addScoreText.setTextColor(Color.parseColor("#D94600"));
			addScoreComboAnimation.setFillAfter(true);
			addScoreText.startAnimation(addScoreComboAnimation);
			this.current_score += score;
		} else {
			addScoreText.setText("+" + score + "");
			addScoreText.setTextColor(Color.parseColor("#FFF0F5"));
			addScoreAnimation.setFillAfter(true);
			addScoreText.startAnimation(addScoreAnimation);
		}
		this.current_score += score;
		showScore();
	}

	private static MainActivity mainActivity = null;

	public static MainActivity getMainActivity() {
		return mainActivity;
	}

}
