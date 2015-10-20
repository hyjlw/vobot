package com.vobot.dto;

import java.util.List;

public class Response {
	private List<String> result;
	private String err_msg;
	private String sn;
	private String corpus_no;
	private int err_no;
	
	public List<String> getResult() {
		return result;
	}
	public void setResult(List<String> result) {
		this.result = result;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getCorpus_no() {
		return corpus_no;
	}
	public void setCorpus_no(String corpus_no) {
		this.corpus_no = corpus_no;
	}
	public int getErr_no() {
		return err_no;
	}
	public void setErr_no(int err_no) {
		this.err_no = err_no;
	}
}
