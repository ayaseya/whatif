package com.example.whatifclone;

import java.util.ArrayList;
import java.util.Collections;

// �g�����v�iplaying cards�j�̏����Ǘ�����N���X
public class Card {

	int[] nowHandNum = new int[5]; // ���݂̎�D�̏��i�A�ԁj��ێ�����ϐ�
	int nowLayoutNum = 0; // ���݂̎R�D�̏��i�A�ԁj��ێ�����ϐ�
	int gameFlag = 0; // �Q�[���i�s�iGAMEOVE��GAMECLEAR�j�𔻒肷��ϐ�
	
	int[] rate52 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3,
			3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 10, 10, 12, 12, 14, 14, 16,
			18, 20, 22, 25, 30, 35, 40, 45, 50, 55, 60, 80, 100 };

	// �R�D�̏��Ԃ�ۑ�����z���錾����
	ArrayList<Integer> list;
	// �ʂ��ԍ�: 0�`12�̓X�y�[�h��1�`13
	// 13�`25�̓n�[�g��1�`13
	// 26�`38�̓N���u��1�`13
	// 39�`51�̓_�C����1�`13

	int deckNum = 0; // �R�D�������������:
	int chainNum = 0; // ��D�ɒu��������
	int suit; // ���: �X�y�[�h��0, �n�[�g��1, �N���u��2, �_�C����3
	int rank; // ��: 1�`13

	// �g�����v�̕�������i�[����z���錾����
	String[] cardInfo = new String[52];

	// shuffle�֐��c�J�[�h���V���b�t������
	void Shuffle() {

		// �R�D�̏��Ԃ�ۑ�����z�������������
		list = new ArrayList<Integer>();

		// list�z���0�`51�̘A�Ԃ��i�[����
		for (int i = 0; i < 52; i++) {
			list.add(i);
			// Log.d(TAG,i+1 + "����" + order[i]);
		}

		// ���X�g���̘A�Ԃ��V���b�t������
		Collections.shuffle(list);

		// Log.d(TAG, "���X�g" + list);
		// Log.d(TAG,1 + "����" + list.get(0));
		// Log.d(TAG,1 + "����" + "���" + card.Suit(list.get(0)) + "����" +
		// card.Rank(list.get(0)));
		// Log.d(TAG,1 + "����" + card.Display(list.get(0)));

	}// Shuffle_**********

	// Suit�֐��c�ʂ��ԍ�����J�[�h�̎�ނ𔻒肷��
	int Suit(int num) {
		if (num < 13) {
			suit = 0; // ���: �X�y�[�h��0
			return suit;
		} else if (12 < num && num < 26) {
			suit = 1; // ���: �n�[�g��1
			return suit;
		} else if (25 < num && num < 39) {
			suit = 2; // ���: �N���u��2
			return suit;
		} else {
			suit = 3; // ���: �_�C����3
			return suit;
		}

	}// Card.Suit_**********

	// Rank�֐��c�ʂ��ԍ�����J�[�h�̐��𔻒肷��
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

	// �֐��c0�`51�̘A�Ԃ��X�y�[�h�A �n�[�g�A �N���u�A�_�C���Ƃ�����������ɕϊ�����
	String Display(int x) {
		String str = cardInfo[x];
		return str;
	}// Display_**********

	public int rate52(int x){ 
	
	return rate52[x-1];

	}

}// Card_**********
