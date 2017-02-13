package com.example.sqlite_battu;

public class BatTu {
	String sao;
	String convat;
	String thuoc;
	String nenLam;
	String khongNen;
	String ngoaiLe;

	public BatTu() {

	}

	public BatTu(String sao, String convat, String thuoc, String nenLam, String khongNen, String ngoaiLe) {
		this.sao = sao;
		this.convat = convat;
		this.thuoc = thuoc;
		this.nenLam = nenLam;
		this.khongNen = khongNen;
		this.ngoaiLe = ngoaiLe;
	}

	public BatTu(String convat, String thuoc, String nenLam, String khongNen, String ngoaiLe) {
		this.convat = convat;
		this.thuoc = thuoc;
		this.nenLam = nenLam;
		this.khongNen = khongNen;
		this.ngoaiLe = ngoaiLe;
	}

	public String getSao() {
		return sao;
	}

	public void setSao(String sao) {
		this.sao = sao;
	}

	public String getConvat() {
		return convat;
	}

	public void setConvat(String convat) {
		this.convat = convat;
	}

	public String getThuoc() {
		return thuoc;
	}

	public void setThuoc(String thuoc) {
		this.thuoc = thuoc;
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

	public String getNgoaiLe() {
		return ngoaiLe;
	}

	public void setNgoaiLe(String ngoaiLe) {
		this.ngoaiLe = ngoaiLe;
	}

}
