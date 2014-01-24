package com.example.whatifclone;

import java.util.ArrayList;

// トランプ（playing cards）の情報を管理するクラス
public class Card {

	int[] nowHandNum = new int[5]; // 現在の手札の情報（連番）を保持する変数
	int nowLayoutNum = 0; // 現在の山札の情報（連番）を保持する変数
	int gameFlag = 0; // ゲーム進行（GAMEOVEやGAMECLEAR）を判定する変数
	
	int[] rate52 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3,
			3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 10, 10, 12, 12, 14, 14, 16,
			18, 20, 22, 25, 30, 35, 40, 45, 50, 55, 60, 80, 100 };

	// 山札の順番を保存する配列を宣言する
	ArrayList<Integer> list;
	// 通し番号: 0～12はスペードの1～13
	// 13～25はハートの1～13
	// 26～38はクラブの1～13
	// 39～51はダイヤの1～13

	int deckNum = 0; // 山札から引いた枚数:
	int chainNum = 0; // 場札に置いた枚数
	int suit; // 種類: スペードは0, ハートは1, クラブは2, ダイヤは3
	int rank; // 数: 1～13

	// トランプの文字列を格納する配列を宣言する
	String[] cardInfo = new String[52];

	// shuffle関数…カードをシャッフルする
	void Shuffle() {

		// 山札の順番を保存する配列を初期化する
		list = new ArrayList<Integer>();

		// list配列に0～51の連番を格納する
		for (int i = 0; i < 52; i++) {
			list.add(i);
			// Log.d(TAG,i+1 + "枚目" + order[i]);
		}

		// リスト内の連番をシャッフルする
//		Collections.shuffle(list);

		// Log.d(TAG, "リスト" + list);
		// Log.d(TAG,1 + "枚目" + list.get(0));
		// Log.d(TAG,1 + "枚目" + "種類" + card.Suit(list.get(0)) + "数字" +
		// card.Rank(list.get(0)));
		// Log.d(TAG,1 + "枚目" + card.Display(list.get(0)));

	}// Shuffle_**********

	// Suit関数…通し番号からカードの種類を判定する
	int Suit(int num) {
		if (num < 13) {
			suit = 0; // 種類: スペードは0
			return suit;
		} else if (12 < num && num < 26) {
			suit = 1; // 種類: ハートは1
			return suit;
		} else if (25 < num && num < 39) {
			suit = 2; // 種類: クラブは2
			return suit;
		} else {
			suit = 3; // 種類: ダイヤは3
			return suit;
		}

	}// Card.Suit_**********

	// Rank関数…通し番号からカードの数を判定する
	int Rank(int num) {
		if (num < 13) {
			rank = num + 1;
			return rank;
		} else if (12 < num && num < 26) {
			rank = num - 12;
			return rank;
		} else if (25 < num && num < 39) {
			rank = num - 25;
			return rank;
		} else {
			rank = num - 38;
			return rank;
		}
	}// Card.Rank_**********

	// 関数…0～51の連番をスペード、 ハート、 クラブ、ダイヤといった文字列に変換する
	String Display(int x) {
		String str = cardInfo[x];
		return str;
	}// Display_**********

	public int rate52(int x){ 
	
	return rate52[x-1];

	}

}// Card_**********