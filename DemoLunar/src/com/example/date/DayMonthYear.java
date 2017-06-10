package com.example.date;

import java.io.Serializable;

public class DayMonthYear implements Serializable {
	// day, month , year năm dương lịch , nm tháng âm lịch , leap tháng nhuận, full số ngày trong tháng âm lịch
	private int day, month, year, nm, leap, full;

	public DayMonthYear(int day, int month, int year, int nm, int leap) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.nm = nm;
		this.leap = leap;
	}

	public DayMonthYear(int day, int month, int year, int leap) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.leap = leap;
	}

	public DayMonthYear(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public DayMonthYear(int day, int month) {
		this.day = day;
		this.month = month;
		this.year = 0;
	}

	public DayMonthYear() {
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getNm() {
		return nm;
	}

	public void setNm(int nm) {
		this.nm = nm;
	}

	public int getLeap() {
		return leap;
	}

	public void setLeap(int leap) {
		this.leap = leap;
	}

	public int getFull() {
		return full;
	}

	public void setFull(int full) {
		this.full = full;
	}

	public void printInfo() {
		System.out
				.print("\n" + this.day + "\t" + this.month + "\t" + this.year);
	}

	public void printFullInfo() {
		System.out.println("\n ngay: " + this.day + "\t thang:  " + this.month + "\t nam :"
				+ this.year + "\t nm:" + this.nm + "\t leap: " + this.leap + "\t full:"
				+ this.full);
	}
}
