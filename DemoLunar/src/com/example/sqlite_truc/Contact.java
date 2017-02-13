package com.example.sqlite_truc;

public class Contact {

	// private variables
	String truc;
	String nenLam;
	String khongNen;

	// Empty constructor
	public Contact() {

	}

	// constructor
	public Contact(String truc, String nenLam, String khongNen) {
		this.truc = truc;
		this.nenLam = nenLam;
		this.khongNen = khongNen;
	}

	public Contact(String nenLam, String khongNen) {
		this.nenLam = nenLam;
		this.khongNen = khongNen;
	}

	public String getTruc() {
		return truc;
	}

	public void setTruc(String truc) {
		this.truc = truc;
	}

	public String getNenLam() {
		return nenLam;
	}

	public void setNenLam(String nenLam) {
		this.nenLam = nenLam;
	}

	public String getKhongNen() {
		return khongNen;
	}

	public void setKhongNen(String khongNen) {
		this.khongNen = khongNen;
	}

}
