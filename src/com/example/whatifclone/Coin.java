package com.example.whatifclone;

public class Coin {

	private int credit = 100;// プレイヤーのコイン枚数（初期値100）
	private int wager = 0;// 掛け金
	private int win = 0;// 獲得金
	private int paid = 0;// 払戻金（獲得金)

	private int minbet = 1;// 最小BET数
	private int maxbet = 10;// 最大BET数

	// ひとつ前の状態を保存する変数
	private int before_credit = 0;// プレイヤーのコイン枚数（初期値100）
	private int before_wager = 0;// 掛け金
	private int before_win = 0;// 獲得金
	private int before_paid = 0;// 払戻金（獲得金)

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

		if ((10 <= credit) && (wager == 0) && (wager != maxbet)) {
			credit -= maxbet;
			wager += maxbet;

		} else if ((0 < credit) && (credit <= 10) && (wager != maxbet)) {
			// 所持コインが1～9枚の場合、
			if (credit + wager == maxbet) {
				credit = 0;
				wager = maxbet;
			} else if (credit + wager < maxbet) {
				wager += credit;
				credit = 0;

			} else if (credit + wager > maxbet) {
				credit -= maxbet - wager;
				wager = maxbet;
			}
		}
	}

	// 投入コインの枚数をキャンセルする処理
	public void cancelBet() {
		credit += wager;
		wager = 0;
	}

	// コインを加算する処理
	public void addCoin(int x) {

	}

	// コインを減算る処理
	public void removeCoin(int x) {

	}

	// 払い戻し処理
	public int paidCoin(int x) {
		// 0～win(獲得金)まで1ずつカウントアップしていく
		// あわせてcreditsのコイン枚数も1ずつカウントアップしていく

		// paid += wager;

		win = x;
		if (0 != x) {
			paid = x - wager;
		} else {
			paid = 0;
		}
		credit += paid;

		wager = 0;
		paid = 0;
		win = 0;

		return credit;

	}
	
	
	
	// ////////////////////////////////////////////////
	// getter、setter群
	// ////////////////////////////////////////////////

	public void setBefore_credit(int before_credit) {
		this.before_credit = before_credit;
	}

	public void setBefore_wager(int before_wager) {
		this.before_wager = before_wager;
	}

	public void setBefore_win(int before_win) {
		this.before_win = before_win;
	}

	public void setBefore_paid(int before_paid) {
		this.before_paid = before_paid;
	}

	public int getBefore_credit() {
		return before_credit;
	}

	public int getBefore_wager() {
		return before_wager;
	}

	public int getBefore_win() {
		return before_win;
	}

	public int getBefore_paid() {
		return before_paid;
	}

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
}