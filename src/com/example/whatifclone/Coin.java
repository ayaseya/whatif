package com.example.whatifclone;

public class Coin {

	private int credits = 100;// プレイヤーのコイン枚数（初期値100）
	private int wager = 0;// 掛け金
	private int win = 0;// 獲得金
	private int paid = 0;// 払戻金（獲得金)

	// コインを1枚ベットする処理
	public void minBet() {

	}

	// コインを掛け金のMAX(10枚)まで一度にベットする処理
	public void maxBet() {

	}

	// 投入コインの枚数をキャンセルする処理
	public void cancelBet() {

	}

	// コインを加算する処理
	public void addCoin(int x) {

	}

	// コインを減算る処理
	public void removeCoin(int x) {

	}
	
	//払い戻し処理
	public void paidCoin(){
		//0～win(獲得金)まで1ずつカウントアップしていく
		//あわせてcreditsのコイン枚数も1ずつカウントアップしていく
	}
	
	
	
	

}
