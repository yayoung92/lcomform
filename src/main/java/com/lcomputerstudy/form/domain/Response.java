package com.lcomputerstudy.form.domain;

import java.util.List;

public class Response {
	private int sIdx;
	private List<Allanswer> aAnswer;
	private List<Answer> aAnswerlist;
	
	public List<Answer> getaAnswerlist() {
		return aAnswerlist;
	}
	public void setaAnswerlist(List<Answer> aAnswerlist) {
		this.aAnswerlist = aAnswerlist;
	}
	public int getsIdx() {
		return sIdx;
	}
	public void setsIdx(int sIdx) {
		this.sIdx = sIdx;
	}
	public List<Allanswer> getaAnswer() {
		return aAnswer;
	}
	public void setaAnswer(List<Allanswer> aAnswer) {
		this.aAnswer = aAnswer;
	}

}
