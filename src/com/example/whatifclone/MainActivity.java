package com.example.whatifclone;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {

	// LogCat用のタグを定数で定義する
	public static final String TAG = "Test";

	/* ********** ********** ********** ********** */

	// Cardクラスを宣言する
	Card card = new Card();

	// 変数の宣言
	TextView layout; // 場札
	TextView count; // カウンター
	TextView cc1; // ボーナス表示
	TextView cc2; // ボーナス表示
	TextView cc3; // ボーナス表示
	TextView log; // ログ表示
	TextView guideView; // ガイド表示

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバーを非表示にする
		setContentView(R.layout.activity_main);

		Log.d(TAG, "画面生成…success");

		card.Shuffle();
		setCard();
		Deal();
	}// onCreate_**********

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}// nCreateOptionsMenu_*********

	// setCard関数…52枚分のカードの表示用文字列を読み込む処理
	public void setCard() {
		// リソースから52枚分のトランプの文字列を配列に取得する
		Resources res = getResources();

		for (int i = 0; i < 52; i++) {
			String name = "card" + i;
			int strId = getResources().getIdentifier(name, "string",
					getPackageName());
			card.cardInfo[i] = res.getString(strId);
			// Log.d(TAG,"カード" + cardInfo[i]);
		}
	}

	// Deal関数…手札に5枚カードを配る処理
	public void Deal() {
		// TextViewを配列で宣言する
		int[] handId = { R.id.hand1, R.id.hand2, R.id.hand3, R.id.hand4,
				R.id.hand5 };
		TextView[] hand = new TextView[handId.length];
		for (int i = 0; i < handId.length; i++) {
			hand[i] = (TextView) findViewById(handId[i]);
		}

		layout = (TextView) findViewById(R.id.layout);// 場札表示

		cc1 = (TextView) findViewById(R.id.cChain1); // ボーナス表示
		cc2 = (TextView) findViewById(R.id.cChain2); // ボーナス表示
		cc3 = (TextView) findViewById(R.id.cChain3); // ボーナス表示

		// レイアウトに配置した部品にstring.xmlの文字列を挿入する

		layout.setText("-");

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
	public void Click(int x) {

		// 手札を配列で宣言する
		int[] handId = { R.id.hand1, R.id.hand2, R.id.hand3, R.id.hand4,
				R.id.hand5 };
		TextView[] hand = new TextView[handId.length];
		for (int i = 0; i < handId.length; i++) {
			hand[i] = (TextView) findViewById(handId[i]);
		}

		layout = (TextView) findViewById(R.id.layout); // 場札
		count = (TextView) findViewById(R.id.countView); // カウンター
		cc1 = (TextView) findViewById(R.id.cChain1); // ボーナス表示
		cc2 = (TextView) findViewById(R.id.cChain2); // ボーナス表示
		cc3 = (TextView) findViewById(R.id.cChain3); // ボーナス表示

		// TextViewの文字色を変更する（16進数で頭の2bitがアルファ値、00で透過率100%）
		cc1.setTextColor(0xFFFFFFFF);
		// フォントのスタイル（太字、斜線など）を変更する
		// 背景色を変更する
		cc1.setBackgroundColor(0xFFFF0000);

		if (layout.getText().equals("-")) {

			// 場札に手札xを置く
			card.nowLayoutNum = card.nowHandNum[x];
			layout.setText(card.Display(card.nowHandNum[x]));

			// 場札に置いたカードの数字をガイド上で強調表示する
			BoldNum(card.nowLayoutNum);

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

			/*
			 * Log.d(TAG, "山札" + card.Display(card.layout) + "(" + card.layout +
			 * ") " + "Suit:" + card.Suit(card.layout) + "Rank" +
			 * card.Rank(card.layout) + " 手札" + card.Display(card.Hand[x]) + "("
			 * + card.Hand[x] + ")" + "Suit:" + card.Suit(card.Hand[x]) + "Rank"
			 * + card.Rank(card.Hand[x]));
			 */
			Game();

		} else if ((card.Suit(card.nowLayoutNum) == card
				.Suit(card.nowHandNum[x]))
				|| (card.Rank(card.nowLayoutNum) == card
						.Rank(card.nowHandNum[x]))) {

			// 　1つ前に場札に置かれていたカードの数字をガイド上から消す
			DeleteNum(card.nowLayoutNum);

			// 場札に手札を置く
			card.nowLayoutNum = card.nowHandNum[x];
			layout.setText(card.Display(card.nowHandNum[x]));

			// 場札に置いたカードの数字をガイド上で強調表示する
			BoldNum(card.nowLayoutNum);

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
			if ((0 < card.chainNum) && (card.chainNum <= 50)) {
				cc1.setText(String.valueOf(card.chainNum) + " CARDS");
				cc2.setText(String.valueOf(card.chainNum + 1) + " CARDS");
				cc3.setText(String.valueOf(card.chainNum + 2) + " CARDS");
			} else if (card.chainNum == 51) {
				cc1.setText(String.valueOf(card.chainNum) + " CARDS");
				cc2.setText(String.valueOf(card.chainNum + 1) + " CARDS");
				cc3.setText(" ");

			}

			else if (card.chainNum == 52) {
				cc1.setText(String.valueOf(card.chainNum) + " CARDS");
				cc2.setText(" ");

				// TextViewの文字色を変更する（16進数で頭の2bitがアルファ値、00で透過率100%）
				cc1.setTextColor(0x00FFFFFF);
				// フォントのスタイル（太字、斜線など）を変更する
				// 背景色を変更する
				cc1.setBackgroundColor(0xFF0000FF);
			}

			/*
			 * Log.d(TAG, "山札" + card.Display(card.layout) + "(" + card.layout +
			 * ") " + "Suit:" + card.Suit(card.layout) + "Rank" +
			 * card.Rank(card.layout) + " 手札" + card.Display(card.Hand[x]) + "("
			 * + card.Hand[x] + ")" + "Suit:" + card.Suit(card.Hand[x]) + "Rank"
			 * + card.Rank(card.Hand[x]));
			 */
			Game();
		}
	}// Card.Click_**********

	// Game関数…ゲームフラグの管理
	public void Game() {
		log = (TextView) findViewById(R.id.log);

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
			log.setText("GAME OVER");
		} else if (card.chainNum == 52) {
			DeleteNum(card.nowLayoutNum);
			// layout.setText(" ");
			log.setText("GAME CLEAR");
		}
		// Log.d(TAG, "フラグ" + gameFlag);
	}// Card.Game_**********

	// BoldNum関数…場札に置いたトランプの数字をガイド上で太字・シアンにする処理
	public void BoldNum(int x) {
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

	// DeleteNum関数…場札に置いたトランプの数字をガイド上から消す処理
	public void DeleteNum(int x) {

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

	// ////////////////////////////////////////////////
	// ボタンクリック時の処理
	// ////////////////////////////////////////////////

	// リセットボタンの代価
	public void test_onClick(View View) {
		if (card.gameFlag == 10) {
			setContentView(R.layout.activity_main);
			card.Shuffle();
			Deal();
		}
	}

	// 手札1に配置したボタンをクリックした時の処理
	public void hand1_onClick(View view) {
		TextView hand1 = (TextView) findViewById(R.id.hand1);

		if (!(hand1.getText().equals(" "))) {
			Click(0);
		}
	}

	// 手札2に配置したボタンをクリックした時の処理
	public void hand2_onClick(View view) {
		TextView hand2 = (TextView) findViewById(R.id.hand2);

		if (!(hand2.getText().equals(" "))) {
			Click(1);
		}

	}

	// 手札3に配置したボタンをクリックした時の処理
	public void hand3_onClick(View view) {
		TextView hand3 = (TextView) findViewById(R.id.hand3);

		if (!(hand3.getText().equals(" "))) {
			Click(2);
		}
	}

	// 手札4に配置したボタンをクリックした時の処理
	public void hand4_onClick(View view) {
		TextView hand4 = (TextView) findViewById(R.id.hand4);

		if (!(hand4.getText().equals(" "))) {
			Click(3);
		}
	}

	// 手札5に配置したボタンをクリックした時の処理
	public void hand5_onClick(View view) {
		TextView hand5 = (TextView) findViewById(R.id.hand5);

		if (!(hand5.getText().equals(" "))) {
			Click(4);
		}
	}

}// MainActivity_**********

