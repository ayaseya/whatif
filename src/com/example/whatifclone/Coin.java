package com.example.whatifclone;

public class Coin {

	private int credit = 100;// �v���C���[�̃R�C�������i�����l100�j
	private int wager = 0;// �|����
	private int win = 0;// �l����
	private int paid = 0;// ���ߋ��i�l����)

	private int minbet = 1;// �ŏ�BET��
	private int maxbet = 10;// �ő�BET��

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
	
	// �R�C����1���x�b�g���鏈��
	public void minBet() {

		// �����R�C����0�ȏォ�|������10�ȉ��̏ꍇ�Ƀx�b�g�\
		if ((0 < credit) && (wager < maxbet)) {
			credit--;
			wager++;
		}
	}

	// �R�C�����|������MAX(10��)�܂ň�x�Ƀx�b�g���鏈��
	public void maxBet() {
		// �����R�C����0�ȏォ�|������10�ȉ��̏ꍇ�Ƀx�b�g�\
		if ((0 < credit) && (wager == 0)) {
			credit -= maxbet;
			wager += maxbet;
		}else{
			
			credit = credit-(maxbet-wager);
			wager = maxbet;
			
		}
	}

	// �����R�C���̖������L�����Z�����鏈��
	public void cancelBet() {
		credit +=wager;
		wager=0;
		
	}

	// �R�C�������Z���鏈��
	public void addCoin(int x) {

	}

	// �R�C�������Z�鏈��
	public void removeCoin(int x) {

	}

	// �����߂�����
	public void paidCoin() {
		// 0�`win(�l����)�܂�1���J�E���g�A�b�v���Ă���
		// ���킹��credits�̃R�C��������1���J�E���g�A�b�v���Ă���
	}

}
