package com.example.whatifclone;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	// LogCat�p�̃^�O��萔�Œ�`����
	public static final String TAG = "Test";

	/* ********** ********** ********** ********** */

	// �N���X��錾����
	Card card = new Card();
	Coin coin = new Coin();

	// ��ʏ��֘A��View��錾
	TextView layout; // ��D
	TextView count; // �J�E���^�[
	TextView cc1; // �{�[�i�X�\��
	TextView cc2; // �{�[�i�X�\��
	TextView cc3; // �{�[�i�X�\��
	TextView cb1; // �{�[�i�X�\��
	TextView cb2; // �{�[�i�X�\��
	TextView cb3; // �{�[�i�X�\��
	TextView guideView; // �K�C�h�\��

	// �����R�C�����ABET���̊֘A��View��錾
	TextView wager;
	TextView win;
	TextView paid;
	TextView credit;

	RelativeLayout handLo;// ��D�\���̃��C�A�E�g
	LinearLayout coinLo;// �R�C������̃��C�A�E�g
	LinearLayout guideLo;// �K�C�h�̃��C�A�E�g
	

	/* ********** ********** ********** ********** */

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �^�C�g���o�[���\���ɂ���
		setContentView(R.layout.activity_main);

		Log.d(TAG, "��ʐ����csuccess");

		setCard();
		prepareResource();
		redrawCoin();

		// ��D���\���ɂ��āA�R�C�������ʂ�\������
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

	// ���\�[�X
	public void prepareResource() {

		layout = (TextView) findViewById(R.id.layout);// ��D�\��
		wager = (TextView) findViewById(R.id.wager);//
		win = (TextView) findViewById(R.id.win);//
		paid = (TextView) findViewById(R.id.paid);//
		credit = (TextView) findViewById(R.id.credit);//

		cc1 = (TextView) findViewById(R.id.cChain1); // �{�[�i�X�\��
		cc2 = (TextView) findViewById(R.id.cChain2); // �{�[�i�X�\��
		cc3 = (TextView) findViewById(R.id.cChain3); // �{�[�i�X�\��

		cb1 = (TextView) findViewById(R.id.cBonus1); // �{�[�i�X�\��
		cb2 = (TextView) findViewById(R.id.cBonus2); // �{�[�i�X�\��
		cb3 = (TextView) findViewById(R.id.cBonus3); // �{�[�i�X�\��

		handLo = (RelativeLayout) findViewById(R.id.HandLayout); // ��D�\���̃��C�A�E�g
		coinLo = (LinearLayout) findViewById(R.id.CoinLayout); // �R�C������̃��C�A�E�g
		guideLo = (LinearLayout) findViewById(R.id.GuideLayout); // �R�C������̃��C�A�E�g

	}

	// setCard�֐��c���\�[�X����52�����̃g�����v�̕������z��Ɏ擾����
	public void setCard() {
		Resources res = getResources();

		for (int i = 0; i < 52; i++) {
			String name = "card" + i;
			int strId = getResources().getIdentifier(name, "string",
					getPackageName());
			card.cardInfo[i] = res.getString(strId);
		}
	}

	// dealCard�֐��c��D��5���J�[�h��z�鏈��
	public void dealCard() {
		// TextView��z��Ő錾����
		int[] handId = { R.id.hand1, R.id.hand2, R.id.hand3, R.id.hand4,
				R.id.hand5 };
		TextView[] hand = new TextView[handId.length];
		for (int i = 0; i < handId.length; i++) {
			hand[i] = (TextView) findViewById(handId[i]);
		}

		// ���C�A�E�g�ɔz�u�������i��string.xml�̕������}������

		layout.setText("-");
		Log.d(TAG, "test�csuccess");
		redrawCoin();

		cb1.setText(String.valueOf(card.rate52(1)));
		cb2.setText(String.valueOf(card.rate52(2)));
		cb3.setText(String.valueOf(card.rate52(3)));

		hand[0].setText(card.Display(card.list.get(0)));
		card.nowHandNum[0] = card.list.get(0);
		// ��D�Ɉ����Ă����J�[�h�̐������K�C�h��ŉ��F�ɕύX����
		yellowNum(card.nowHandNum[0]);

		hand[1].setText(card.Display(card.list.get(1)));
		card.nowHandNum[1] = card.list.get(1);
		// ��D�Ɉ����Ă����J�[�h�̐������K�C�h��ŉ��F�ɕύX����
		yellowNum(card.nowHandNum[1]);

		hand[2].setText(card.Display(card.list.get(2)));
		card.nowHandNum[2] = card.list.get(2);
		// ��D�Ɉ����Ă����J�[�h�̐������K�C�h��ŉ��F�ɕύX����
		yellowNum(card.nowHandNum[2]);

		hand[3].setText(card.Display(card.list.get(3)));
		card.nowHandNum[3] = card.list.get(3);
		// ��D�Ɉ����Ă����J�[�h�̐������K�C�h��ŉ��F�ɕύX����
		yellowNum(card.nowHandNum[3]);

		hand[4].setText(card.Display(card.list.get(4)));
		card.nowHandNum[4] = card.list.get(4);
		// ��D�Ɉ����Ă����J�[�h�̐������K�C�h��ŉ��F�ɕύX����
		yellowNum(card.nowHandNum[4]);

		card.deckNum = 4;
		card.chainNum = 0;

		// �{�[�i�X�����̃��C�A�E�g�ɖ���������
		cc1.setText(String.valueOf(card.chainNum + 1) + " CARDS");
		cc2.setText(String.valueOf(card.chainNum + 2) + " CARDS");
		cc3.setText(String.valueOf(card.chainNum + 3) + " CARDS");

		Log.d(TAG, "dealCard()�csuccess");
	}// Deal_**********

	// Click�֐��c�g�����v���������Ƃ��̋���
	public void onClick(int x) {

		// ��D��z��Ő錾����
		int[] handId = { R.id.hand1, R.id.hand2, R.id.hand3, R.id.hand4,
				R.id.hand5 };
		TextView[] hand = new TextView[handId.length];
		for (int i = 0; i < handId.length; i++) {
			hand[i] = (TextView) findViewById(handId[i]);
		}

		layout = (TextView) findViewById(R.id.layout); // ��D
		count = (TextView) findViewById(R.id.countView); // �J�E���^�[
		cc1 = (TextView) findViewById(R.id.cChain1); // �{�[�i�X�\��
		cc2 = (TextView) findViewById(R.id.cChain2); // �{�[�i�X�\��
		cc3 = (TextView) findViewById(R.id.cChain3); // �{�[�i�X�\��

		// TextView�̕����F��ύX����i16�i���œ���2bit���A���t�@�l�A00�œ��ߗ�100%�j
		cc1.setTextColor(0xFFFFFFFF);
		cb1.setTextColor(0xFFFF0000);
		// �t�H���g�̃X�^�C���i�����A�ΐ��Ȃǁj��ύX����
		// �w�i�F��ύX����
		cc1.setBackgroundColor(0xFFFF0000);
		cb1.setBackgroundColor(0xFFFFFFFF);

		if (layout.getText().equals("-")) {

			// ��D�Ɏ�Dx��u��
			card.nowLayoutNum = card.nowHandNum[x];
			layout.setText(card.Display(card.nowHandNum[x]));

			// ��D�ɒu�����J�[�h�̐������K�C�h��ŋ����\������
			boldNum(card.nowLayoutNum);

			// ��D�ɎR�D����ꖇ�����Ă���
			hand[x].setText(card.Display(card.list.get(card.deckNum + 1)));
			card.nowHandNum[x] = card.list.get(card.deckNum + 1);

			// ��D�Ɉ����Ă����J�[�h�̐������K�C�h��ŉ��F�ɕύX����
			yellowNum(card.nowHandNum[x]);

			// 1���������������Z����
			card.deckNum++;
			// 1���u�����������Z����
			card.chainNum++;
			count.setText(String.valueOf(card.chainNum));

			judgeGame();

		} else if ((card.Suit(card.nowLayoutNum) == card
				.Suit(card.nowHandNum[x]))
				|| (card.Rank(card.nowLayoutNum) == card
						.Rank(card.nowHandNum[x]))) {

			// �@1�O�ɏ�D�ɒu����Ă����J�[�h�̐������K�C�h�ォ�����
			deleteNum(card.nowLayoutNum);

			// ��D�Ɏ�D��u��
			card.nowLayoutNum = card.nowHandNum[x];
			layout.setText(card.Display(card.nowHandNum[x]));

			// ��D�ɒu�����J�[�h�̐������K�C�h��ŋ����\������
			boldNum(card.nowLayoutNum);

			if ((0 < card.chainNum) && (card.chainNum < 47)) {
				// ��D�ɎR�D����ꖇ�����Ă���
				hand[x].setText(card.Display(card.list.get(card.deckNum + 1)));
				card.nowHandNum[x] = card.list.get(card.deckNum + 1);

				// ��D�Ɉ����Ă����J�[�h�̐������K�C�h��ŉ��F�ɕύX����
				yellowNum(card.nowHandNum[x]);
				card.deckNum++;
				card.chainNum++;

			} else {
				hand[x].setText(" ");
				card.chainNum++;
			}

			count.setText(String.valueOf(card.chainNum));

			// �{�[�i�X�����̃��C�A�E�g�ɖ���������
			redrawBonus(card.chainNum);

			judgeGame();
		}
	}// Card.Click_**********

	// judgeGame�֐��c�Q�[���t���O�̊Ǘ�
	public void judgeGame() {
		// log = (TextView) findViewById(R.id.log);

		card.gameFlag = 0; // �Q�[���t���O�A��D�Ǝ�D1�`5�̎�ނƐ������r���A��D�ɏo�����D������������1���Z���Ă���
		for (int i = 0; i < 5; i++) {
			if (card.Suit(card.nowLayoutNum) != card.Suit(card.nowHandNum[i])) {
				card.gameFlag += 1;
			}
			if (card.Rank(card.nowLayoutNum) != card.Rank(card.nowHandNum[i])) {
				card.gameFlag += 1;
			}
		}

		if (card.gameFlag == 10) {
			// log.setText("GAME OVER");
			String.valueOf(coin.paidCoin(coin.getWager()
					* card.rate52[card.chainNum]));// ���ߋ�

			redrawCoin();

			// ��D���\���ɂ��āA�R�C�������ʂ�\������
			handLo.setVisibility(View.GONE);
			coinLo.setVisibility(View.VISIBLE);

			Log.d(TAG, "GAME OVER�csuccess");
		} else if (card.chainNum == 52) {
			deleteNum(card.nowLayoutNum);
			String.valueOf(coin.paidCoin(coin.getWager()
					* card.rate52[card.chainNum]));// ���ߋ�

			redrawCoin();

			// ��D���\���ɂ��āA�R�C�������ʂ�\������
			handLo.setVisibility(View.GONE);
			coinLo.setVisibility(View.VISIBLE);

			Log.d(TAG, "GAME CLEAR�csuccess");
			// log.setText("GAME CLEAR");
		}
		
		
	}// judgeGame_**********

	// boldNum�֐��c��D�ɒu�����g�����v�̐������K�C�h��ő����E�V�A���ɂ��鏈��
	public void boldNum(int x) {
		Resources res = getResources();
		int guideId = res.getIdentifier("card" + x, "id", getPackageName());
		guideView = (TextView) findViewById(guideId);
		// TextView�̕����F��ύX����i16�i���œ���2bit���A���t�@�l�A00�œ��ߗ�100%�j
		guideView.setTextColor(0xFF00BFFF);
		// �t�H���g�̃X�^�C���i�����A�ΐ��Ȃǁj��ύX����
		guideView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
	}// Card.BoldNum_**********

	// yellowNum�֐��c��D�ɒu�����g�����v�̐������K�C�h��ŉ��F�ɂ��鏈��
	public void yellowNum(int x) {
		Resources res = getResources();
		int guideId = res.getIdentifier("card" + x, "id", getPackageName());
		guideView = (TextView) findViewById(guideId);
		// TextView�̕����F��ύX����i16�i���œ���2bit���A���t�@�l�A00�œ��ߗ�100%�j
		guideView.setTextColor(0xFFFFD700);
		// �t�H���g�̃X�^�C���i�����A�ΐ��Ȃǁj��ύX����
		guideView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
		// �w�i�F��ύX����
		guideView.setBackgroundColor(0xFF7777FF);
	}// Card.yellowNum_**********

	// deleteNum�֐��c��D�ɒu�����g�����v�̐������K�C�h�ォ���������
	public void deleteNum(int x) {
		// getResources()�Ń��\�[�X�I�u�W�F�N�g��
		Resources res = getResources();
		// guideId�Ƃ����ϐ��Ƀ��\�[�X�̏ꏊ���i�[����
		// ("card" + x , "id" , getPackageName())
		// ��
		// (R.id.card***)
		int guideId = res.getIdentifier("card" + x, "id", getPackageName());
		guideView = (TextView) findViewById(guideId);
		guideView.setTextColor(0x00FFFFFF);
		// �w�i�F�����̐F�ɖ߂�
		guideView.setBackgroundColor(0xFF0000FF);
	}// Card.DeleteNum_**********

	public void redrawBonus(int x) {

		// �{�[�i�X�����̃��C�A�E�g�ɖ���������
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
		wager.setText(String.valueOf(coin.getWager()));
		win.setText(String.valueOf(coin.getWin()));
		paid.setText(String.valueOf(coin.getPaid()));
		credit.setText(String.valueOf(coin.getCredit()));
	}

	// ////////////////////////////////////////////////
	// �{�^���N���b�N���̏���
	// ////////////////////////////////////////////////

	// Layout�t�@�C���Ƀ��\�b�h�����L�q������@
	// ��D1�ɔz�u�����{�^�����N���b�N�������̏���
	public void hand1_onClick(View view) {
		TextView hand1 = (TextView) findViewById(R.id.hand1);

		if (!(hand1.getText().equals(" "))) {
			onClick(0);
		}
	}

	// ��D2�ɔz�u�����{�^�����N���b�N�������̏���
	public void hand2_onClick(View view) {
		TextView hand2 = (TextView) findViewById(R.id.hand2);

		if (!(hand2.getText().equals(" "))) {
			onClick(1);
		}

	}

	// ��D3�ɔz�u�����{�^�����N���b�N�������̏���
	public void hand3_onClick(View view) {
		TextView hand3 = (TextView) findViewById(R.id.hand3);

		if (!(hand3.getText().equals(" "))) {
			onClick(2);
		}
	}

	// ��D4�ɔz�u�����{�^�����N���b�N�������̏���
	public void hand4_onClick(View view) {
		TextView hand4 = (TextView) findViewById(R.id.hand4);

		if (!(hand4.getText().equals(" "))) {
			onClick(3);
		}
	}

	// ��D5�ɔz�u�����{�^�����N���b�N�������̏���
	public void hand5_onClick(View view) {
		TextView hand5 = (TextView) findViewById(R.id.hand5);

		if (!(hand5.getText().equals(" "))) {
			onClick(4);
		}
	}

	// COLLECT�{�^�����������Ƃ��̏���
	public void colBtn_onClick(View view) {

		if (0 < coin.getWager()) {
			coin.cancelBet();
			wager.setText(String.valueOf(coin.getWager()));
			credit.setText(String.valueOf(coin.getCredit()));
		}
	}

	// DOUBLE DOWN�{�^�����������Ƃ��̏���
	public void ddBtn_onClick(View view) {

		setContentView(R.layout.activity_main);
		Log.d(TAG, "�ĕ`��csuccess");

	}

	// 1 BET�{�^�����������Ƃ��̏���
	public void betBtn_onClick(View view) {
		coin.minBet();

		wager.setText(String.valueOf(coin.getWager()));
		credit.setText(String.valueOf(coin.getCredit()));
	}

	// MAX BET�{�^�����������Ƃ��̏���
	public void maxBtn_onClick(View view) {
		coin.maxBet();

		wager.setText(String.valueOf(coin.getWager()));
		credit.setText(String.valueOf(coin.getCredit()));

	}

	// PLAY�{�^�����������Ƃ��̏���
	public void playBtn_onClick(View view) {

		// �ŏ�BET���𖞂����Ă�����Q�[���J�n
		if (card.gameFlag == 0 && coin.getWager() >= coin.getMinbet()) {

			card.Shuffle();
			dealCard();

			// ��D���\���ɂ��āA�R�C�������ʂ�\������
			
			handLo.setVisibility(View.VISIBLE);
			coinLo.setVisibility(View.GONE);
			
			Log.d(TAG, "����v���C�csuccess");
			
		} else if (card.gameFlag == 10 && coin.getWager() >= coin.getMinbet()) {
			
			card.Shuffle();
			dealCard();

			// ��D���\���ɂ��āA�R�C�������ʂ�\������
			handLo.setVisibility(View.VISIBLE);
			coinLo.setVisibility(View.GONE);
			Log.d(TAG, "2��ڈȍ~�̃v���C�csuccess");

		} else if (card.chainNum == 52 && coin.getWager() >= coin.getMinbet()) {

			
			Log.d(TAG, "�N���A��̃v���C�csuccess");

		}

	}

}// MainActivity_**********

