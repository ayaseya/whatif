package com.example.whatifclone;

public class Coin {

	private int credit = 100;// プレイヤーのコイン枚数（初期値100）
	private int wager = 0;// 掛け金
	private int win = 0;// 獲得金
	private int paid = 0;// 払戻金（獲得金)

	private int minbet = 1;// 最小BET数
	private int maxbet = 10;// 最大BET数

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getWager() {
		return wager;
	}

	public void setWager(int wager) {
		this.wager = wager;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}

	public int getMinbet() {
		return minbet;
	}
	
	public void setMinbet(int minbet) {
		this.minbet = minbet;
	}
	
	public int getMaxbet() {
		return maxbet;
	}
	
	public void setMaxbet(int maxbet) {
		this.maxbet = maxbet;
	}
	
	// コインを1枚ベットする処理
	public void minBet() {

		// 所持コインが0以上かつ掛け金が10以下の場合にベット可能
		if ((0 < credit) && (wager < maxbet)) {
			credit--;
			wager++;
		}
	}

	// コインを掛け金のMAX(10枚)まで一度にベットする処理
	public void maxBet() {
		// 所持コインが0以上かつ掛け金が10以下の場合にベット可能
		if ((0 < credit) && (wager == 0)) {
			credit -= maxbet;
			wager += maxbet;
		}else{
			
			credit = credit-(maxbet-wager);
			wager = maxbet;
			
		}
	}

	// 投入コインの枚数をキャンセルする処理
	public void cancelBet() {
		credit +=wager;
		wager=0;
		
	}

	// コインを加算する処理
	public void addCoin(int x) {

	}

	// コインを減算る処理
	public void removeCoin(int x) {

	}

	// 払い戻し処理
	public void paidCoin() {
		// 0～win(獲得金)まで1ずつカウントアップしていく
		// あわせてcreditsのコイン枚数も1ずつカウントアップしていく
	}

}
