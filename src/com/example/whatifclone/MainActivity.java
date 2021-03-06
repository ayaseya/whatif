package com.example.whatifclone;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity implements AnimationListener,
		ViewFactory {

	// LogCat用のタグを定数で定義する
	public static final String TAG = "Test";

	/* ********** ********** ********** ********** */

	// クラスを宣言する
	Card card = new Card();
	Coin coin = new Coin();

	// 画面情報関連のViewを宣言
	TextView layout; // 場札
	TextSwitcher count; // カウンター
	TextView cc1; // ボーナス表示
	TextView cc2; // ボーナス表示
	TextView cc3; // ボーナス表示
	TextView cb1; // ボーナス表示
	TextView cb2; // ボーナス表示
	TextView cb3; // ボーナス表示
	TextView guideView; // ガイド表示

	// 所持コイン数、BET数の関連のViewを宣言
	TextView wagerView;
	TextView winView;
	TextView paidView;
	TextView creditView;

	LinearLayout handLo;// 手札表示のレイアウト
	LinearLayout coinLo;// コイン操作のレイアウト
	LinearLayout guideLo;// ガイドのレイアウト

	boolean coin_flag = false;// コインの増減処理中であるか否かの判定、trueが処理中
	boolean skip_flag = false;// コインの増減処理中にtureに変更すると処理をスキップする

	Handler handler = new Handler();
	int credit = 0;// 増加前のコインの枚数を保持する変数
	int counter = 0;// カウントアップ（ダウン）用の変数

	FrameLayout fl;// アニメ用のレイアウト

	ImageView transView1;// 移動用の画像インスタンス
	ImageView transView2;
	ImageView transView3;
	ImageView transView4;
	ImageView transView5;

	int[] hand1Loc = new int[2];// 手札のxy座標を格納する配列
	int[] hand2Loc = new int[2];
	int[] hand3Loc = new int[2];
	int[] hand4Loc = new int[2];
	int[] hand5Loc = new int[2];
	int[] layoutLoc = new int[2];

	boolean animeFlag = false;// 手札の移動アニメが処理中か否かの判定フラグ

	// private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;//
	// レイアウトの画像幅と高さの定数

	Button btn1;// 手札ボタンのインスタンス
	Button btn2;
	Button btn3;
	Button btn4;
	Button btn5;

	private MediaPlayer mp;

	/* ********** ********** ********** ********** */

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバーを非表示にする
		setContentView(R.layout.activity_main);

		// ステータスバーの非表示
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Log.d(TAG, "画面生成…success");

		setCard();
		prepareResource();
		redrawCoin();

		// 手札を非表示にして、コイン操作画面を表示する
		handLo.setVisibility(View.GONE);
		coinLo.setVisibility(View.VISIBLE);

	}// onCreate_**********

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}// nCreateOptionsMenu_*********

	/* ********** ********** ********** ********** */

	// リソース
	public void prepareResource() {

		layout = (TextView) findViewById(R.id.layout);// 場札表示
		wagerView = (TextView) findViewById(R.id.wager);//
		winView = (TextView) findViewById(R.id.win);//
		paidView = (TextView) findViewById(R.id.paid);//
		creditView = (TextView) findViewById(R.id.credit);//

		cc1 = (TextView) findViewById(R.id.cChain1); // ボーナス表示
		cc2 = (TextView) findViewById(R.id.cChain2); // ボーナス表示
		cc3 = (TextView) findViewById(R.id.cChain3); // ボーナス表示

		cb1 = (TextView) findViewById(R.id.cBonus1); // ボーナス表示
		cb2 = (TextView) findViewById(R.id.cBonus2); // ボーナス表示
		cb3 = (TextView) findViewById(R.id.cBonus3); // ボーナス表示

		handLo = (LinearLayout) findViewById(R.id.HandLayout); // 手札表示のレイアウト
		coinLo = (LinearLayout) findViewById(R.id.CoinLayout); // コイン操作のレイアウト
		guideLo = (LinearLayout) findViewById(R.id.GuideLayout); // コイン操作のレイアウト

		fl = (FrameLayout) findViewById(R.id.FrameLayout);

		btn1 = (Button) findViewById(R.id.hand1);
		btn2 = (Button) findViewById(R.id.hand2);
		btn3 = (Button) findViewById(R.id.hand3);
		btn4 = (Button) findViewById(R.id.hand4);
		btn5 = (Button) findViewById(R.id.hand5);

		final Animation in = AnimationUtils.loadAnimation(this, R.anim.down_in);
		final Animation out = AnimationUtils.loadAnimation(this,
				R.anim.down_out);

		count = (TextSwitcher) findViewById(R.id.countView);
		count.setFactory(MainActivity.this);
		count.setInAnimation(in);
		count.setOutAnimation(out);

		count.setText(String.valueOf(card.chainNum));

		mp = MediaPlayer.create(this, R.raw.coin);

	}

	// setCard関数…リソースから52枚分のトランプの文字列を配列に取得する
	public void setCard() {
		Resources res = getResources();

		for (int i = 0; i < 52; i++) {
			String name = "card" + i;
			int strId = getResources().getIdentifier(name, "string",
					getPackageName());
			card.cardInfo[i] = res.getString(strId);
		}
	}

	// dealCard関数…手札に5枚カードを配る処理
	public void dealCard() {
		// TextViewを配列で宣言する
		int[] handId = { R.id.hand1, R.id.hand2, R.id.hand3, R.id.hand4,
				R.id.hand5 };
		TextView[] hand = new TextView[handId.length];
		for (int i = 0; i < handId.length; i++) {
			hand[i] = (TextView) findViewById(handId[i]);
		}

		// レイアウトに配置した部品にstring.xmlの文字列を挿入する

		layout.setText("-");
		layout.setTextColor(0x00000000);
		redrawCoin();

		cb1.setText(String.valueOf(card.rate52(1)));
		cb2.setText(String.valueOf(card.rate52(2)));
		cb3.setText(String.valueOf(card.rate52(3)));

		hand[0].setText(card.Display(card.list.get(0)));
		card.nowHandNum[0] = card.list.get(0);
		// 手札に引いてきたカードの数字をガイド上で黄色に変更する
		yellowNum(card.nowHandNum[0]);

		hand[1].setText(card.Display(card.list.get(1)));
		card.nowHandNum[1] = card.list.get(1);
		// 手札に引いてきたカードの数字をガイド上で黄色に変更する
		yellowNum(card.nowHandNum[1]);

		hand[2].setText(card.Display(card.list.get(2)));
		card.nowHandNum[2] = card.list.get(2);
		// 手札に引いてきたカードの数字をガイド上で黄色に変更する
		yellowNum(card.nowHandNum[2]);

		hand[3].setText(card.Display(card.list.get(3)));
		card.nowHandNum[3] = card.list.get(3);
		// 手札に引いてきたカードの数字をガイド上で黄色に変更する
		yellowNum(card.nowHandNum[3]);

		hand[4].setText(card.Display(card.list.get(4)));
		card.nowHandNum[4] = card.list.get(4);
		// 手札に引いてきたカードの数字をガイド上で黄色に変更する
		yellowNum(card.nowHandNum[4]);

		card.deckNum = 4;
		card.chainNum = 0;

		// ボーナス部分のレイアウトに枚数を入れる
		cc1.setText(String.valueOf(card.chainNum + 1) + " CARDS");
		cc2.setText(String.valueOf(card.chainNum + 2) + " CARDS");
		cc3.setText(String.valueOf(card.chainNum + 3) + " CARDS");

	}// Deal_**********

	// Click関数…トランプを押したときの挙動
	public void onClick(int x) {

		// 手札を配列で宣言する
		int[] handId = { R.id.hand1, R.id.hand2, R.id.hand3, R.id.hand4,
				R.id.hand5 };
		TextView[] hand = new TextView[handId.length];
		for (int i = 0; i < handId.length; i++) {
			hand[i] = (TextView) findViewById(handId[i]);
		}

		layout = (TextView) findViewById(R.id.layout); // 場札
		cc1 = (TextView) findViewById(R.id.cChain1); // ボーナス表示
		cc2 = (TextView) findViewById(R.id.cChain2); // ボーナス表示
		cc3 = (TextView) findViewById(R.id.cChain3); // ボーナス表示

		// TextViewの文字色を変更する（16進数で頭の2bitがアルファ値、00で透過率100%）
		cc1.setTextColor(0xFFFFFFFF);
		cb1.setTextColor(0xFFFF0000);
		// フォントのスタイル（太字、斜線など）を変更する
		// 背景色を変更する
		cc1.setBackgroundColor(0xFFFF0000);
		cb1.setBackgroundColor(0xFFFFFFFF);

		if (layout.getText().equals("-")) {

			layout.setTextColor(0xFF000000);

			// 場札に手札xを置く
			card.nowLayoutNum = card.nowHandNum[x];
			layout.setText(card.Display(card.nowHandNum[x]));

			// 場札に置いたカードの数字をガイド上で強調表示する
			boldNum(card.nowLayoutNum);

			// 手札に山札から一枚引いてくる
			hand[x].setText(card.Display(card.list.get(card.deckNum + 1)));
			card.nowHandNum[x] = card.list.get(card.deckNum + 1);

			// 手札に引いてきたカードの数字をガイド上で黄色に変更する
			yellowNum(card.nowHandNum[x]);

			// 1枚引いた分を加算する
			card.deckNum++;
			// 1枚置いた分を加算する
			card.chainNum++;
			count.setText(String.valueOf(card.chainNum));

			judgeGame();

		} else if ((card.Suit(card.nowLayoutNum) == card
				.Suit(card.nowHandNum[x]))
				|| (card.Rank(card.nowLayoutNum) == card
						.Rank(card.nowHandNum[x]))) {

			// 　1つ前に場札に置かれていたカードの数字をガイド上から消す
			deleteNum(card.nowLayoutNum);

			// 場札に手札を置く
			card.nowLayoutNum = card.nowHandNum[x];
			layout.setText(card.Display(card.nowHandNum[x]));

			// 場札に置いたカードの数字をガイド上で強調表示する
			boldNum(card.nowLayoutNum);

			if ((0 < card.chainNum) && (card.chainNum < 47)) {
				// 手札に山札から一枚引いてくる
				hand[x].setText(card.Display(card.list.get(card.deckNum + 1)));
				card.nowHandNum[x] = card.list.get(card.deckNum + 1);

				// 手札に引いてきたカードの数字をガイド上で黄色に変更する
				yellowNum(card.nowHandNum[x]);
				card.deckNum++;
				card.chainNum++;

			} else {
				hand[x].setText(" ");
				card.chainNum++;
			}

			count.setText(String.valueOf(card.chainNum));

			// ボーナス部分のレイアウトに枚数を入れる
			redrawBonus(card.chainNum);

			judgeGame();
		}
	}// Card.Click_**********

	// judgeGame関数…ゲームフラグの管理
	public void judgeGame() {

		card.gameFlag = 0; // ゲームフラグ、場札と手札1～5の種類と数字を比較し、場札に出せる手札が無かったら1加算していく
		for (int i = 0; i < 5; i++) {
			if (card.Suit(card.nowLayoutNum) != card.Suit(card.nowHandNum[i])) {
				card.gameFlag += 1;
			}
			if (card.Rank(card.nowLayoutNum) != card.Rank(card.nowHandNum[i])) {
				card.gameFlag += 1;
			}
		}

		if (card.gameFlag == 10) {
			cuCoin(Integer.parseInt(wagerView.getText().toString())
					* card.rate52[card.chainNum - 1]);// 払戻金

			// final Timer timer = new Timer();
			// timer.schedule(new TimerTask() {
			// @Override
			// public void run() {
			// // handlerを通じてUI Threadへ処理をキューイング
			// handler.post(new Runnable() {
			// public void run() {
			//
			// counter++;
			//
			// // Timer終了処理　
			// if (counter == 1) {
			// counter = 0;
			// // 手札を非表示にして、コイン操作画面を表示する
			// handLo.setVisibility(View.GONE);
			// coinLo.setVisibility(View.VISIBLE);
			//
			// timer.cancel();
			//
			// }
			// }
			// });
			//
			// }
			// }, 0, 1000);
			Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show();
			handLo.setVisibility(View.GONE);
			coinLo.setVisibility(View.VISIBLE);

			Log.d(TAG, "GAME OVER…success");
		} else if (card.chainNum == 52) {
			cuCoin(coin.getWager() * card.rate52[card.chainNum - 1]);// 払戻金

			// 手札を非表示にして、コイン操作画面を表示する
			handLo.setVisibility(View.GONE);
			coinLo.setVisibility(View.VISIBLE);

			Toast.makeText(this, "GAME CLEAR", Toast.LENGTH_LONG).show();
			// Log.d(TAG, "GAME CLEAR…success");
			// log.setText("GAME CLEAR");
		}

	}// judgeGame_**********

	// boldNum関数…場札に置いたトランプの数字をガイド上で太字・シアンにする処理
	public void boldNum(int x) {
		Resources res = getResources();
		int guideId = res.getIdentifier("card" + x, "id", getPackageName());
		guideView = (TextView) findViewById(guideId);
		// TextViewの文字色を変更する（16進数で頭の2bitがアルファ値、00で透過率100%）
		guideView.setTextColor(0xFF00BFFF);
		// フォントのスタイル（太字、斜線など）を変更する
		guideView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
	}// Card.BoldNum_**********

	// yellowNum関数…場札に置いたトランプの数字をガイド上で黄色にする処理
	public void yellowNum(int x) {
		Resources res = getResources();
		int guideId = res.getIdentifier("card" + x, "id", getPackageName());
		guideView = (TextView) findViewById(guideId);
		// TextViewの文字色を変更する（16進数で頭の2bitがアルファ値、00で透過率100%）
		guideView.setTextColor(0xFFFFD700);
		// フォントのスタイル（太字、斜線など）を変更する
		guideView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
		// 背景色を変更する
		guideView.setBackgroundColor(0xFF7777FF);
	}// Card.yellowNum_**********

	// deleteNum関数…場札に置いたトランプの数字をガイド上から消す処理
	public void deleteNum(int x) {
		// getResources()でリソースオブジェクトを
		Resources res = getResources();
		// guideIdという変数にリソースの場所を格納する
		// ("card" + x , "id" , getPackageName())
		// ↓
		// (R.id.card***)
		int guideId = res.getIdentifier("card" + x, "id", getPackageName());
		guideView = (TextView) findViewById(guideId);
		guideView.setTextColor(0x00FFFFFF);
		// 背景色を元の青色に戻す
		guideView.setBackgroundColor(0xFF0000FF);
	}// Card.DeleteNum_**********

	public void redrawGuide() {
		Resources res = getResources();
		int guideId;
		for (int i = 0; i < 52; i++) {
			guideId = res.getIdentifier("card" + i, "id", getPackageName());
			guideView = (TextView) findViewById(guideId);
			// 文字色を白に戻す
			guideView.setTextColor(0xFFFFFFFF);
			// 背景色を元の青色に戻す
			guideView.setBackgroundColor(0xFF0000FF);
		}
	}

	public void redrawBonus(int x) {

		// ボーナス部分のレイアウトに枚数を入れる
		if ((0 < card.chainNum) && (card.chainNum <= 50)) {
			cc1.setText(String.valueOf(card.chainNum) + " CARDS");
			cc2.setText(String.valueOf(card.chainNum + 1) + " CARDS");
			cc3.setText(String.valueOf(card.chainNum + 2) + " CARDS");

			cb1.setText(String.valueOf(coin.getWager() * card.rate52(x)));
			cb2.setText(String.valueOf(coin.getWager() * card.rate52(x + 1)));
			cb3.setText(String.valueOf(coin.getWager() * card.rate52(x + 2)));

		} else if (card.chainNum == 51) {
			cc1.setText(String.valueOf(card.chainNum) + " CARDS");
			cc2.setText(String.valueOf(card.chainNum + 1) + " CARDS");
			cc3.setText(" ");

			cb1.setText(String.valueOf(coin.getWager() * card.rate52(x)));
			cb2.setText(String.valueOf(coin.getWager() * card.rate52(x + 1)));
			cb3.setText(" ");

		}

		else if (card.chainNum == 52) {
			cc1.setText(String.valueOf(card.chainNum) + " CARDS");
			cc2.setText(" ");

			cc1.setTextColor(0x00FFFFFF);
			cc1.setBackgroundColor(0xFF0000FF);

			cb1.setText(String.valueOf(coin.getWager() * card.rate52(x)));
			cb2.setText(" ");

			cb1.setTextColor(0x00FFFFFF);
			cb1.setBackgroundColor(0xFFFF0000);
		}

	}

	public void redrawCoin() {
		wagerView.setText(String.valueOf(coin.getWager()));
		winView.setText(String.valueOf(0));
		paidView.setText(String.valueOf(0));
		creditView.setText(String.valueOf(coin.getCredit()));
	}

	public void transCard(View view) {

	}

	// cuCoin関数…cu = Count Upの略称、払い戻し時にコインの枚数が1枚ずつ
	// 増減する様子を表示する処理、引数は増減する枚数を渡す
	public void cuCoin(final int x) {
		// コイン増加表示の処理中かフラグ判定
		if (coin_flag == false && (x != 0)) {

			coin_flag = true;// コイン増加表示の処理中というフラグを立てる
			credit = coin.getCredit();// 増加前のコインの枚数を格納

			final Timer timer = new Timer();

			Log.d("Test", "timer_start x=" + x);

			winView.setText(String.valueOf(x));

			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// handlerを通じてUI Threadへ処理をキューイング
					handler.post(new Runnable() {
						public void run() {
							paidView.setText(String.valueOf(counter));
							creditView.setText(String.valueOf(credit + counter));
							counter++;

							// Timer終了処理　
							if (x == coin.getWager() && counter == x) {

								creditView.setText(String.valueOf(credit
										+ counter));
								coin.setCredit((credit + coin.getWager()));
								coin.setWager(0);

								wagerView.setText("0");
								winView.setText("0");
								paidView.setText("0");

								Log.d("Test", "timer_stop x=" + x + "counter="
										+ counter);
								coin_flag = false;
								counter = 0;
								timer.cancel();

							} else if (counter == (x - coin.getWager())
									&& x != coin.getWager()) {

								creditView.setText(String.valueOf(credit + x
										+ coin.getWager()));

								coin.setCredit((credit + x + coin.getWager()));
								coin.setWager(0);

								wagerView.setText("0");
								winView.setText("0");
								paidView.setText("0");

								Log.d("Test", "timer_stop x=" + x + "counter="
										+ counter);
								coin_flag = false;
								counter = 0;
								timer.cancel();
							}

							else if (skip_flag == true) {

								creditView.setText(String.valueOf(credit + x
										+ coin.getWager()));

								coin.setCredit((credit + x + coin.getWager()));
								coin.setWager(0);

								wagerView.setText("0");
								winView.setText("0");
								paidView.setText("0");

								Log.d("Test", "timer_stop_skip x=" + x
										+ "counter=" + counter);
								skip_flag = false;
								coin_flag = false;
								counter = 0;
								timer.cancel();
							}

						}
					});

				}
			}, 0, 50);

		} else if ((0 < counter) && (counter < (x - coin.getWager()))
				&& (x != 0)) {
			// コイン増加表示の処理中に再度ボタンを押した時に
			// 増加表示をスキップする処理
			counter = x - 1;
			Log.d("Test", "timer_skip");
		} else if (x == 0) {
			coin.setWager(0);
			redrawCoin();
		}

	}

	public void sleepTask(final int x) {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// handlerを通じてUI Threadへ処理をキューイング
				handler.post(new Runnable() {
					public void run() {

						counter++;

						// Timer終了処理　
						if (counter == x) {
							counter = 0;
							timer.cancel();

						}
					}
				});

			}
		}, 0, 1000);

	}

	// ////////////////////////////////////////////////
	// ボタンクリック時の処理
	// ////////////////////////////////////////////////

	// Layoutファイルにメソッド名を記述する方法
	// 手札1に配置したボタンをクリックした時の処理
	public void hand1_onClick(View view) {
		TextView hand1 = (TextView) findViewById(R.id.hand1);
		if (!(hand1.getText().equals(" "))) {
			onClick(0);
			if (transView1 == null) {
				transView1 = new ImageView(this);
				transView1.setImageResource(R.drawable.card4);

				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						layout.getWidth(), layout.getHeight());
				view.getLocationInWindow(hand1Loc);

				params.gravity = Gravity.NO_GRAVITY;
				params.leftMargin = hand1Loc[0];
				params.topMargin = hand1Loc[1];

				transView1.setLayoutParams(params);
				fl.addView(transView1);
				layout.getLocationInWindow(layoutLoc);

				TranslateAnimation translate = new TranslateAnimation(0,
						layoutLoc[0] - hand1Loc[0], 0, layoutLoc[1]
								- hand1Loc[1]);
				translate.setDuration(200);
				// translate.setFillAfter(true);
				transView1.startAnimation(translate);
				translate.setAnimationListener(this);

				transView1.setVisibility(View.INVISIBLE);

			} else if ((card.Suit(card.nowLayoutNum) == card
					.Suit(card.nowHandNum[0]))
					|| (card.Rank(card.nowLayoutNum) == card
							.Rank(card.nowHandNum[0]))) {

				TranslateAnimation translate = new TranslateAnimation(0,
						layoutLoc[0] - hand1Loc[0], 0, layoutLoc[1]
								- hand1Loc[1]);
				if (card.chainNum > 47) {
					btn1.setVisibility(View.INVISIBLE);
				}
				translate.setDuration(200);
				// translate.setFillAfter(true);
				transView1.startAnimation(translate);
				translate.setAnimationListener(this);

				transView1.setVisibility(View.INVISIBLE);
			}
		}

	}

	// 手札2に配置したボタンをクリックした時の処理
	public void hand2_onClick(View view) {
		TextView hand2 = (TextView) findViewById(R.id.hand2);

		if (!(hand2.getText().equals(" "))) {
			onClick(1);
			if (transView2 == null) {
				transView2 = new ImageView(this);
				transView2.setImageResource(R.drawable.card4);

				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						layout.getWidth(), layout.getHeight());
				view.getLocationInWindow(hand2Loc);

				params.gravity = Gravity.NO_GRAVITY;
				params.leftMargin = hand2Loc[0];
				params.topMargin = hand2Loc[1];

				transView2.setLayoutParams(params);
				fl.addView(transView2);
				layout.getLocationInWindow(layoutLoc);
				TranslateAnimation translate = new TranslateAnimation(0,
						layoutLoc[0] - hand2Loc[0], 0, layoutLoc[1]
								- hand2Loc[1]);
				translate.setDuration(200);
				// translate.setFillAfter(true);
				transView2.startAnimation(translate);
				translate.setAnimationListener(this);
				transView2.setVisibility(View.INVISIBLE);

			} else if ((card.Suit(card.nowLayoutNum) == card
					.Suit(card.nowHandNum[1]))
					|| (card.Rank(card.nowLayoutNum) == card
							.Rank(card.nowHandNum[1]))) {
				TranslateAnimation translate = new TranslateAnimation(0,
						layoutLoc[0] - hand2Loc[0], 0, layoutLoc[1]
								- hand2Loc[1]);
				if (card.chainNum > 47) {
					btn2.setVisibility(View.INVISIBLE);
				}
				translate.setDuration(200);
				// translate.setFillAfter(true);
				transView2.startAnimation(translate);
				translate.setAnimationListener(this);
				transView2.setVisibility(View.INVISIBLE);
			}
		}

	}

	// 手札3に配置したボタンをクリックした時の処理
	public void hand3_onClick(View view) {
		TextView hand3 = (TextView) findViewById(R.id.hand3);

		if (!(hand3.getText().equals(" "))) {
			onClick(2);
			if (transView3 == null) {
				transView3 = new ImageView(this);
				transView3.setImageResource(R.drawable.card4);

				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						layout.getWidth(), layout.getHeight());
				view.getLocationInWindow(hand3Loc);

				params.gravity = Gravity.NO_GRAVITY;
				params.leftMargin = hand3Loc[0];
				params.topMargin = hand3Loc[1];

				transView3.setLayoutParams(params);
				fl.addView(transView3);
				layout.getLocationInWindow(layoutLoc);
				TranslateAnimation translate = new TranslateAnimation(0,
						layoutLoc[0] - hand3Loc[0], 0, layoutLoc[1]
								- hand3Loc[1]);

				translate.setDuration(200);
				// translate.setFillAfter(true);
				transView3.startAnimation(translate);
				translate.setAnimationListener(this);
				transView3.setVisibility(View.INVISIBLE);

			} else if ((card.Suit(card.nowLayoutNum) == card
					.Suit(card.nowHandNum[2]))
					|| (card.Rank(card.nowLayoutNum) == card
							.Rank(card.nowHandNum[2]))) {

				TranslateAnimation translate = new TranslateAnimation(0,
						layoutLoc[0] - hand3Loc[0], 0, layoutLoc[1]
								- hand3Loc[1]);
				if (card.chainNum > 47) {
					btn3.setVisibility(View.INVISIBLE);
				}
				translate.setDuration(200);
				// translate.setFillAfter(true);
				transView3.startAnimation(translate);
				translate.setAnimationListener(this);
				transView3.setVisibility(View.INVISIBLE);
			}
		}

	}

	// 手札4に配置したボタンをクリックした時の処理
	public void hand4_onClick(View view) {
		TextView hand4 = (TextView) findViewById(R.id.hand4);

		if (!(hand4.getText().equals(" "))) {
			onClick(3);

			if (transView4 == null) {
				transView4 = new ImageView(this);
				transView4.setImageResource(R.drawable.card4);

				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						layout.getWidth(), layout.getHeight());
				view.getLocationInWindow(hand4Loc);

				params.gravity = Gravity.NO_GRAVITY;
				params.leftMargin = hand4Loc[0];
				params.topMargin = hand4Loc[1];

				transView4.setLayoutParams(params);
				fl.addView(transView4);
				layout.getLocationInWindow(layoutLoc);
				TranslateAnimation translate = new TranslateAnimation(0,
						layoutLoc[0] - hand4Loc[0], 0, layoutLoc[1]
								- hand4Loc[1]);
				translate.setDuration(200);
				// translate.setFillAfter(true);
				transView4.startAnimation(translate);
				translate.setAnimationListener(this);
				transView4.setVisibility(View.INVISIBLE);
			} else if ((card.Suit(card.nowLayoutNum) == card
					.Suit(card.nowHandNum[3]))
					|| (card.Rank(card.nowLayoutNum) == card
							.Rank(card.nowHandNum[3]))) {

				TranslateAnimation translate = new TranslateAnimation(0,
						layoutLoc[0] - hand4Loc[0], 0, layoutLoc[1]
								- hand4Loc[1]);
				if (card.chainNum > 47) {
					btn4.setVisibility(View.INVISIBLE);
				}
				translate.setDuration(200);
				// translate.setFillAfter(true);
				transView4.startAnimation(translate);
				translate.setAnimationListener(this);
				transView4.setVisibility(View.INVISIBLE);
			}
		}

	}

	// 手札5に配置したボタンをクリックした時の処理
	public void hand5_onClick(View view) {
		TextView hand5 = (TextView) findViewById(R.id.hand5);

		if (!(hand5.getText().equals(" "))) {
			onClick(4);
			if (transView5 == null) {
				transView5 = new ImageView(this);
				transView5.setImageResource(R.drawable.card4);

				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						layout.getWidth(), layout.getHeight());
				view.getLocationInWindow(hand5Loc);

				params.gravity = Gravity.NO_GRAVITY;
				params.leftMargin = hand5Loc[0];
				params.topMargin = hand5Loc[1];

				transView5.setLayoutParams(params);
				fl.addView(transView5);
				layout.getLocationInWindow(layoutLoc);
				TranslateAnimation translate = new TranslateAnimation(0,
						layoutLoc[0] - hand5Loc[0], 0, layoutLoc[1]
								- hand5Loc[1]);
				translate.setDuration(200);
				// translate.setFillAfter(true);
				transView5.startAnimation(translate);
				translate.setAnimationListener(this);
				transView5.setVisibility(View.INVISIBLE);
			} else if ((card.Suit(card.nowLayoutNum) == card
					.Suit(card.nowHandNum[4]))
					|| (card.Rank(card.nowLayoutNum) == card
							.Rank(card.nowHandNum[4]))) {

				TranslateAnimation translate = new TranslateAnimation(0,
						layoutLoc[0] - hand5Loc[0], 0, layoutLoc[1]
								- hand5Loc[1]);

				if (card.chainNum > 47) {
					btn5.setVisibility(View.INVISIBLE);
				}

				translate.setDuration(200);
				// translate.setFillAfter(true);
				transView5.startAnimation(translate);
				translate.setAnimationListener(this);
				transView5.setVisibility(View.INVISIBLE);
			}
		}

	}

	// COLLECTボタンを押したときの処理
	public void colBtn_onClick(View view) {

		if (coin_flag) {
			skip_flag = true;
		}

		if (0 < coin.getWager()) {
			coin.cancelBet();
			wagerView.setText(String.valueOf(coin.getWager()));
			creditView.setText(String.valueOf(coin.getCredit()));
		}
	}

	// HALF DOWNボタンを押したときの処理
	public void hdBtn_onClick(View view) {

		// coin.setWager(100);
		// redrawCoin();
		// coin.setCredit(90);
		// creditView.setText("90");
		// coin.setWager(10);
		// wagerView.setText("10");
		cuCoin(1000);

	}

	// 1 BETボタンを押したときの処理
	public void betBtn_onClick(View view) {
		if (coin_flag == false) {

			// SEが停止中の場合
			if (!mp.isPlaying()) {
				mp.start();
			} else {// SEが再生中の場合

				try {
					// 再生を停止
					mp.stop();
					mp.prepare();

				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			coin.minBet();

			wagerView.setText(String.valueOf(coin.getWager()));
			creditView.setText(String.valueOf(coin.getCredit()));
		}
	}

	// REPEAT BETボタンを押したときの処理
	public void repeatBtn_onClick(View view) {

		if (coin_flag == false) {
			coin.maxBet();

			wagerView.setText(String.valueOf(coin.getWager()));
			creditView.setText(String.valueOf(coin.getCredit()));
		}
	}

	// PAYOUTボタンを押したときの処理
	public void payoutBtn_onClick(View view) {

	}

	// DEALボタンを押したときの処理
	public void dealBtn_onClick(View view) {
		// 最小BET数を満たしていたらゲーム開始
		if (card.gameFlag == 0 && coin.getWager() >= coin.getMinbet()) {

			card.Shuffle();
			dealCard();

			// 手札を非表示にして、コイン操作画面を表示する

			btn1.setVisibility(View.VISIBLE);
			btn2.setVisibility(View.VISIBLE);
			btn3.setVisibility(View.VISIBLE);
			btn4.setVisibility(View.VISIBLE);
			btn5.setVisibility(View.VISIBLE);

			handLo.setVisibility(View.VISIBLE);
			coinLo.setVisibility(View.GONE);

		} else if (card.gameFlag == 10 && coin.getWager() >= coin.getMinbet()) {
			card.chainNum = 0;
			count.setText(String.valueOf(card.chainNum));
			redrawGuide();
			card.Shuffle();
			dealCard();
			btn1.setVisibility(View.VISIBLE);
			btn2.setVisibility(View.VISIBLE);
			btn3.setVisibility(View.VISIBLE);
			btn4.setVisibility(View.VISIBLE);
			btn5.setVisibility(View.VISIBLE);

			// 手札を非表示にして、コイン操作画面を表示する
			handLo.setVisibility(View.VISIBLE);
			coinLo.setVisibility(View.GONE);

		} else if (card.chainNum == 52 && coin.getWager() >= coin.getMinbet()) {
			card.chainNum = 0;
			count.setText(String.valueOf(card.chainNum));
			redrawGuide();
			card.Shuffle();
			dealCard();
			btn1.setVisibility(View.VISIBLE);
			btn2.setVisibility(View.VISIBLE);
			btn3.setVisibility(View.VISIBLE);
			btn4.setVisibility(View.VISIBLE);
			btn5.setVisibility(View.VISIBLE);

			// 手札を非表示にして、コイン操作画面を表示する
			handLo.setVisibility(View.VISIBLE);
			coinLo.setVisibility(View.GONE);

		}

	}

	// ////////////////////////////////////////////////
	// AnimationListener
	// ////////////////////////////////////////////////

	@Override
	public void onAnimationStart(Animation arg0) {
		animeFlag = true;
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		animeFlag = false;
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {

	}

	// ////////////////////////////////////////////////
	// ViewFactory
	// ////////////////////////////////////////////////
	@Override
	public View makeView() {
		TextView txt = new TextView(this);
		txt.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		txt.setTextColor(0xFFFFFFFF);
		txt.setTextSize(64);
		return txt;
	}

}// MainActivity_**********