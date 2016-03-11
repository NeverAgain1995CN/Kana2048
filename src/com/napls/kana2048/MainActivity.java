package com.napls.kana2048;

import com.qhad.ads.sdk.adcore.Qhad;
import com.qhad.ads.sdk.interfaces.IQhBannerAd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
	private int best_score = 0;

	private GameView gameView;

	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	private Card[][] savedCardsMap = new Card[5][5];
	private int[][] savedCardsMapNum = new int[5][5];

	private RelativeLayout adContainer = null;
	private IQhBannerAd bannerad = null;

	public MainActivity() {
		mainActivity = this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		editor = getSharedPreferences("data", MODE_PRIVATE).edit();
		pref = getSharedPreferences("data", MODE_PRIVATE);

		currentScore = (TextView) findViewById(R.id.score_current);
		bestScore = (TextView) findViewById(R.id.score_best);
		addScoreText = (TextView) findViewById(R.id.add_score_anim);
		addScoreAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.add_score);
		addScoreComboAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.add_score_combo);
		gameView = (GameView) findViewById(R.id.game_view);

		newGame = (Button) findViewById(R.id.new_game);
		help = (Button) findViewById(R.id.help);
		kanaTable = (Button) findViewById(R.id.kana_table);

		showScore();
		startOldGame();
		adContainer = (RelativeLayout) findViewById(R.id.banner_adcontainer); // �������
																				// adContainer

		/**
		 * ��ʾ���
		 */
		addAds();

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
			addScoreText.setText("������ " + "+" + score + "��2");
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

	private void startOldGame() {
		String keyName;
		int xPosition, yPosition;
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				xPosition = x;
				yPosition = y;
				keyName = xPosition + "" + yPosition;
				savedCardsMap = (Card[][]) gameView.getCardsMap();
				savedCardsMapNum[x][y] = pref.getInt(keyName, 0);
			}
		}
		current_score = pref.getInt("cs", 0);
		best_score = pref.getInt("bs", 0);
		showScore();
		gameView.setCardsMapNum(savedCardsMapNum);
	}

	public int getCurrent_score() {
		return current_score;
	}

	public int getBest_score() {
		return best_score;
	}

	public void setBest_score(int best_score) {
		this.best_score = best_score;
		editor.putInt("bs", best_score);
		editor.commit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		savedCardsMap = (Card[][]) gameView.getCardsMap().clone();
		String keyName;
		int xPosition, yPosition;
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				xPosition = x;
				yPosition = y;
				savedCardsMapNum[x][y] = savedCardsMap[x][y].getNum();
				keyName = xPosition + "" + yPosition;
				editor.putInt(keyName, savedCardsMapNum[x][y]);
			}
		}
		editor.putInt("cs", current_score);
		editor.putInt("bs", best_score);
		editor.commit();
	}

	private void addAds() {
		final String adSpaceid = "55kGaoBe2K"; // ���λID adSpaceid
		bannerad = Qhad.showBanner(adContainer, MainActivity.this, adSpaceid, false); // ������
	}

}
