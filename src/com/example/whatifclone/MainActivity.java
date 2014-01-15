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
	TextView guideView; // �K�C�h�\��

	// �����R�C�����ABET���̊֘A��View��錾
	TextView wager;
	TextView win;
	TextView paid;
	TextView credit;

	RelativeLayout handLo;// ��D�\���̃��C�A�E�g
	LinearLayout coinLo;// �R�C������̃��C�A�E�g

	/* ********** ********** ********** ********** */

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �^�C�g���o�[���\���ɂ���
		setContentView(R.layout.activity_main);

		Log.d(TAG, "��ʐ����csuccess");

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

	// setCard�֐��c���\�[�X����52�����̃g�����v�̕������z��Ɏ擾����

	/* ********** ********** ********** ********** */

	// setCard�֐��c52�����̃J�[�h�̕\���p�������ǂݍ��ޏ���
	public void setCard() {
		Resources res = getResources();

		for (int i = 0; i < 52; i++) {
			String name = "card" + i;
			int strId = getResources().getIdentifier(name, "string",
					getPackageName());
			card.cardInfo[i] = res.getString(strId);
		}
	}

	// Deal�֐��c��D��5���J�[�h��z�鏈��
	public void Deal() {
		// TextView��z��Ő錾����
		int[] handId = { R.id.hand1, R.id.hand2, R.id.hand3, R.id.hand4,
				R.id.hand5 };
		TextView[] hand = new TextView[handId.length];
		for (int i = 0; i < handId.length; i++) {
			hand[i] = (TextView) findViewById(handId[i]);
		}

		layout = (TextView) findViewById(R.id.layout);// ��D�\��
		wager = (TextView) findViewById(R.id.wager);//
		win = (TextView) findViewById(R.id.win);//
		paid = (TextView) findViewById(R.id.paid);//
		credit = (TextView) findViewById(R.id.credit);//

		cc1 = (TextView) findViewById(R.id.cChain1); // �{�[�i�X�\��
		cc2 = (TextView) findViewById(R.id.cChain2); // �{�[�i�X�\��
		cc3 = (TextView) findViewById(R.id.cChain3); // �{�[�i�X�\��

		handLo = (RelativeLayout) findViewById(R.id.HandLayout); // ��D�\���̃��C�A�E�g
		coinLo = (LinearLayout) findViewById(R.id.CoinLayout); // �R�C������̃��C�A�E�g

		// ��D���\���ɂ��āA�R�C�������ʂ�\������
		handLo.setVisibility(View.GONE);

		// ���C�A�E�g�ɔz�u�������i��string.xml�̕������}������

		layout.setText("-");

		wager.setText("0");
		win.setText("0");
		paid.setText("0");
		credit.setText("100");

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

	}// Deal_**********

	// Click�֐��c�g�����v���������Ƃ��̋���
	public void Click(int x) {

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
		// �t�H���g�̃X�^�C���i�����A�ΐ��Ȃǁj��ύX����
		// �w�i�F��ύX����
		cc1.setBackgroundColor(0xFFFF0000);

		if (layout.getText().equals("-")) {

			// ��D�Ɏ�Dx��u��
			card.nowLayoutNum = card.nowHandNum[x];
			layout.setText(card.Display(card.nowHandNum[x]));

			// ��D�ɒu�����J�[�h�̐������K�C�h��ŋ����\������
			BoldNum(card.nowLayoutNum);

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

			Game();

		} else if ((card.Suit(card.nowLayoutNum) == card
				.Suit(card.nowHandNum[x]))
				|| (card.Rank(card.nowLayoutNum) == card
						.Rank(card.nowHandNum[x]))) {

			// �@1�O�ɏ�D�ɒu����Ă����J�[�h�̐������K�C�h�ォ�����
			DeleteNum(card.nowLayoutNum);

			// ��D�Ɏ�D��u��
			card.nowLayoutNum = card.nowHandNum[x];
			layout.setText(card.Display(card.nowHandNum[x]));

			// ��D�ɒu�����J�[�h�̐������K�C�h��ŋ����\������
			BoldNum(card.nowLayoutNum);

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

				// TextView�̕����F��ύX����i16�i���œ���2bit���A���t�@�l�A00�œ��ߗ�100%�j
				cc1.setTextColor(0x00FFFFFF);
				// �t�H���g�̃X�^�C���i�����A�ΐ��Ȃǁj��ύX����
				// �w�i�F��ύX����
				cc1.setBackgroundColor(0xFF0000FF);
			}

			Game();
		}
	}// Card.Click_**********

	// Game�֐��c�Q�[���t���O�̊Ǘ�
	public void Game() {
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
		} else if (card.chainNum == 52) {
			DeleteNum(card.nowLayoutNum);
			// log.setText("GAME CLEAR");
		}
	}// Card.Game_**********

	// BoldNum�֐��c��D�ɒu�����g�����v�̐������K�C�h��ő����E�V�A���ɂ��鏈��
	public void BoldNum(int x) {
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

	// DeleteNum�֐��c��D�ɒu�����g�����v�̐������K�C�h�ォ���������
	public void DeleteNum(int x) {
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

	// ////////////////////////////////////////////////
	// �{�^���N���b�N���̏���
	// ////////////////////////////////////////////////

	// ���Z�b�g�{�^���̑㉿
	// public void test_onClick(View View) {
	// if (card.gameFlag == 10) {
	// setContentView(R.layout.activity_main);
	// card.Shuffle();
	// Deal();
	// }
	// }

	// Layout�t�@�C���Ƀ��\�b�h�����L�q������@
	// ��D1�ɔz�u�����{�^�����N���b�N�������̏���
	public void hand1_onClick(View view) {
		TextView hand1 = (TextView) findViewById(R.id.hand1);

		if (!(hand1.getText().equals(" "))) {
			Click(0);
		}
	}

	// ��D2�ɔz�u�����{�^�����N���b�N�������̏���
	public void hand2_onClick(View view) {
		TextView hand2 = (TextView) findViewById(R.id.hand2);

		if (!(hand2.getText().equals(" "))) {
			Click(1);
		}

	}

	// ��D3�ɔz�u�����{�^�����N���b�N�������̏���
	public void hand3_onClick(View view) {
		TextView hand3 = (TextView) findViewById(R.id.hand3);

		if (!(hand3.getText().equals(" "))) {
			Click(2);
		}
	}

	// ��D4�ɔz�u�����{�^�����N���b�N�������̏���
	public void hand4_onClick(View view) {
		TextView hand4 = (TextView) findViewById(R.id.hand4);

		if (!(hand4.getText().equals(" "))) {
			Click(3);
		}
	}

	// ��D5�ɔz�u�����{�^�����N���b�N�������̏���
	public void hand5_onClick(View view) {
		TextView hand5 = (TextView) findViewById(R.id.hand5);

		if (!(hand5.getText().equals(" "))) {
			Click(4);
		}
	}

	// COLLECT�{�^�����������Ƃ��̏���

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
		if (coin.getWager() > coin.getMinbet()) {

			// ��D���\���ɂ��āA�R�C�������ʂ�\������
			handLo.setVisibility(View.VISIBLE);
			coinLo.setVisibility(View.GONE);
		}
	}

}// MainActivity_**********

