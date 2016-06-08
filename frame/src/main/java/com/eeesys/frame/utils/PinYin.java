package com.eeesys.frame.utils;

import java.util.ArrayList;

import com.eeesys.frame.utils.HanziToPinyin.Token;


import android.annotation.SuppressLint;



public class PinYin {
	//汉字返回拼音，字母原样返回，都转换为小写
	@SuppressLint("DefaultLocale") 
	public static String getFullPinYin(String input) {
		ArrayList<com.eeesys.frame.utils.HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
		StringBuilder sb = new StringBuilder();
		if (tokens != null && tokens.size() > 0) {
			for (Token token : tokens) {
				if (Token.PINYIN == token.type) {
					sb.append(token.target);
				} else {
					sb.append(token.source);
				}
			}
		}
		return sb.toString().toLowerCase();
	}
	
	//汉字返回拼音，字母原样返回，都转换为小写
		@SuppressLint("DefaultLocale") 
		public static String getFirstPinYin(String input) {
			ArrayList<Token> tokens = HanziToPinyin.getInstance().get(input);
			StringBuilder sb = new StringBuilder();
			if (tokens != null && tokens.size() > 0) {
				for (Token token : tokens) {
					if (Token.PINYIN == token.type) {
						if(token.target != null && !"".equals(token.target))
							sb.append(token.target.charAt(0));
					} else {
						if(token.source != null && !"".equals(token.source))
							sb.append(token.source.charAt(0));
					}
				}
			}
			return sb.toString().toLowerCase();
		}
}
