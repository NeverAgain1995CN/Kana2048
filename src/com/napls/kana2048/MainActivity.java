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
				tv_message.setText("ʹ����ָ����Ϸ������������" + "\n" + "��ͬ�еļ�����Ტ��һ��������һ�еļ����顣" + "\n"
						+ "�磺���еġ������롰�������ɤ��е�һ����������飬�硰����" + "\n\n" + "��ʤ�������ɹ��ϳɼ����顰��" + "\n"
						+ "ʧ���������ںϳɼ����顰��֮ǰ��������������Ϸ��" + "\n\n" + "��ʾ��������������ɲ鿴���м���" + "\n\n" + "ϣ�������ͨ������Ϸ������������������"
						+ "\n\n" + "���ߣ�NA Personal Learning Studio" + "\n" + "��ϵ���䣺neveragain1995@163.com" + "\n"
						+ "QQ:503163740 (����𰸣������죩" + "\n" + "��ӭ������");
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
				tv_message.setText("���� ��(a): ��(i) ��(u) ��(e) ��(o) \n" + "\n" + "����: ��(ka) ��(ki) ��(ku) ��(ke) ��(ko) \n"
						+ "\n" + "����: ��(sa) ��(shi) ��(su) ��(se) ��(so) \n" + "\n"
						+ "����: ��(ta) ��(chi) ��(tsu) ��(te) ��(to) \n" + "\n" + "����: ��(na) ��(ni) ��(nu) ��(ne) ��(no) \n"
						+ "\n" + "����: ��(ha) ��(hi) ��(fu) ��(he) ��(ho) \n" + "\n" + "����: ��(ma) ��(mi) ��(mu) ��(me) ��(mo) \n"
						+ "\n" + "����: ��(ya) ��(yu) ��(yo) \n" + "\n" + "����: ��(ra) ��(ri) ��(ru) ��(re) ��(ro) \n" + "\n"
						+ "����: ��(wa) ��(o) \n" + "\n" + "��(n)");
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
			addScoreText.setText("������ " + "+" + score + "*2");
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
