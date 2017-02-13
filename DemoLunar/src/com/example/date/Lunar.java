package com.example.date;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class Lunar implements Serializable {
	static final double LOCAL_TIMEZONE = 7.0;
	static final double PI = Math.PI;

	static final double[] SUNLONG_MAJOR = new double[] { 19 * PI / 12, 5 * PI / 3, 7 * PI / 4, 11 * PI / 6,
			23 * PI / 12, 0, PI / 12, PI / 6, PI / 4, PI / 3, 5 * PI / 12, PI / 2, 7 * PI / 12, 2 * PI / 3, 3 * PI / 4,
			5 * PI / 6, 11 * PI / 12, PI, 13 * PI / 12, 7 * PI / 6, 5 * PI / 4, 4 * PI / 3, 17 * PI / 12, 3 * PI / 2 };

	public static final String[] SAO = new String[] { "Bích", "Khuê", "Lâu", "Vĩ", "Mão", "Tất", "Chủy", "Sâm", "Tỉnh",
			"Quỷ", "Liễu", "Tinh", "Trương", "Dực", "Chẩn", "Giác", "Cang", "Đê", "Phòng", "Tâm", "Vĩ", "Cơ", "Đẩu",
			"Ngưu", "Nữ", "Hư", "Nguy", "Thất" };

	public static final String[] SAOTOT = new String[] { "Thiên Đức", "Nguyệt Đức", "Thiên Giải", "Thiên Hỷ",
			"Thiên Quý", "Tam Hợp", "Sinh Khí", "Thiên Thành", "Thiên Quan", "Lộc Mã", "Phục Sinh", "Giải Thần",
			"Thiên Ân" };

	public static final String[] SAOXAU = new String[] { "Thiên Cương", "Thụ Tử ", "Đại Hao\tTử Khí\tQuan Phù",
			"Tiểu Hao", "Sát Chủ", "Thiên Hỏa", "Địa Hỏa", "Hỏa Tai", "Nguyệt Phá", "Băng Tiêu Ngọa Giải", "Thổ Cấm",
			"Thổ Kỵ\tVãng Vong", "Cô Thần", "Quả Tú", "Trùng Tang", "Trùng Phục" };

	public static final String[] TIETKHI = new String[] { "Tiểu hàn", "Đại hàn", "Lập xuân ", "Vũ thủy", "Kinh trập",
			"Xuân phân", "Thanh minh", "Cốc vũ", "Lập hạ", "Tiểu mãn", "Mang chủng", "Hạ chí", "Tiểu thử", "Đại thử",
			"Lập thu", "Xử thử", "Bạch lộ", "Thu phân", "Hàn lộ", "Sương giáng", "Lập đông", "Tiểu tuyết", "Đại tuyết",
			"Dong chi" };

	public static final String[] TRUC = new String[] { "Trực Kiến", "Trực Trừ", "Trực Mãn", "Trực Bình", "Trực Định",
			"Trực Chấp", "Trực Phá", "Trực Nguy", "Trực Thành", "Trực Thâu", "Trực Khai", "Trực Bế" };

	public static final String[] CAN = new String[] { "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ", "Canh", "Tân", "Nhâm",
			"Quý" };

	public static final String[] CHI = new String[] { "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân",
			"Dậu", "Tuất", "Hợi" };
	public static final String[] THU = new String[] { "Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy",
			"Chủ Nhật" };

	public static final String[] GIO = new String[] { "23h-1h", "1h-3h", "3h-5h", "5h-7h", "7h-9h", "9h-11h", "11h-13h",
			"13h-15h", "15h-17h", "17h-19h", "19h-21h", "21h-23h" };

	public static int INT(double d) {
		return (int) Math.floor(d);
	}

	public static int MOD(int x, int y) {
		int z = x - (int) (y * Math.floor(((double) x / y)));
		if (z == 0) {
			z = y;
		}
		return z;
	}

	// doi duong lich ra julius
	public static double universalToJD(DayMonthYear dmy) {
		double JD;
		int D = dmy.getDay();
		int M = dmy.getMonth();
		int Y = dmy.getYear();

		if (Y > 1582 || (Y == 1582 && M > 10) || (Y == 1582 && M == 10 && D > 14)) {
			JD = 367 * Y - INT(7 * (Y + INT((M + 9) / 12)) / 4) - INT(3 * (INT((Y + (M - 9) / 7) / 100) + 1) / 4)
					+ INT(275 * M / 9) + D + 1721028.5;
		} else {
			JD = 367 * Y - INT(7 * (Y + 5001 + INT((M - 9) / 7)) / 4) + INT(275 * M / 9) + D + 1729776.5;
		}
		return JD;
	}

	// doi julius ra duong lich
	public static DayMonthYear universalFromJD(double JD) {
		int Z, A, alpha, B, C, D, E, dd, mm, yyyy;
		double F;
		Z = INT(JD + 0.5);
		F = (JD + 0.5) - Z;
		if (Z < 2299161) {
			A = Z;
		} else {
			alpha = INT((Z - 1867216.25) / 36524.25);
			A = Z + 1 + alpha - INT(alpha / 4);
		}
		B = A + 1524;
		C = INT((B - 122.1) / 365.25);
		D = INT(365.25 * C);
		E = INT((B - D) / 30.6001);
		dd = INT(B - D - INT(30.6001 * E) + F);
		if (E < 14) {
			mm = E - 1;
		} else {
			mm = E - 13;
		}
		if (mm < 3) {
			yyyy = C - 4715;
		} else {
			yyyy = C - 4716;
		}

		return new DayMonthYear(dd, mm, yyyy);
	}

	// julius ra duong lich (theo gio Viet Nam)
	public static DayMonthYear localFromJD(double JD) {
		return universalFromJD(JD + LOCAL_TIMEZONE / 24.0);
	}

	// duong lich ra julius (theo gio Viet Nam)
	public static double localToJD(DayMonthYear dmy) {
		return universalToJD(dmy) - LOCAL_TIMEZONE / 24.0;
	}

	// tinh thoi diem soc
	public static double newMoon(int k) {
		double T = k / 1236.85; // Time in Julian centuries from 1900 January
								// 0.5
		double T2 = T * T;
		double T3 = T2 * T;
		double dr = PI / 180;
		double Jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * T2 - 0.000000155 * T3;
		Jd1 = Jd1 + 0.00033 * Math.sin((166.56 + 132.87 * T - 0.009173 * T2) * dr); // Mean
																					// new
																					// moon
		double M = 359.2242 + 29.10535608 * k - 0.0000333 * T2 - 0.00000347 * T3; // Sun's
																					// mean
																					// anomaly
		double Mpr = 306.0253 + 385.81691806 * k + 0.0107306 * T2 + 0.00001236 * T3; // Moon's
																						// mean
																						// anomaly
		double F = 21.2964 + 390.67050646 * k - 0.0016528 * T2 - 0.00000239 * T3; // Moon's
																					// argument
																					// of
																					// latitude
		double C1 = (0.1734 - 0.000393 * T) * Math.sin(M * dr) + 0.0021 * Math.sin(2 * dr * M);
		C1 = C1 - 0.4068 * Math.sin(Mpr * dr) + 0.0161 * Math.sin(dr * 2 * Mpr);
		C1 = C1 - 0.0004 * Math.sin(dr * 3 * Mpr);
		C1 = C1 + 0.0104 * Math.sin(dr * 2 * F) - 0.0051 * Math.sin(dr * (M + Mpr));
		C1 = C1 - 0.0074 * Math.sin(dr * (M - Mpr)) + 0.0004 * Math.sin(dr * (2 * F + M));
		C1 = C1 - 0.0004 * Math.sin(dr * (2 * F - M)) - 0.0006 * Math.sin(dr * (2 * F + Mpr));
		C1 = C1 + 0.0010 * Math.sin(dr * (2 * F - Mpr)) + 0.0005 * Math.sin(dr * (2 * Mpr + M));
		double deltat;
		if (T < -11) {
			deltat = 0.001 + 0.000839 * T + 0.0002261 * T2 - 0.00000845 * T3 - 0.000000081 * T * T3;
		} else {
			deltat = -0.000278 + 0.000265 * T + 0.000262 * T2;
		}
		;
		double JdNew = Jd1 + C1 - deltat;
		return JdNew;
	}

	// tinh vi tri mat troi luc 00:00 (dai diem tuy dau vao day julius)
	public static double sunLongitude(double jdn) {
		double T = (jdn - 2451545.0) / 36525; // Time in Julian centuries from
												// 2000-01-01 12:00:00 GMT
		double T2 = T * T;
		double dr = PI / 180; // degree to radian
		double M = 357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2; // mean
																						// anomaly,
																						// degree
		double L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2; // mean
																	// longitude,
																	// degree
		double DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * Math.sin(dr * M);
		DL = DL + (0.019993 - 0.000101 * T) * Math.sin(dr * 2 * M) + 0.000290 * Math.sin(dr * 3 * M);
		double L = L0 + DL; // true longitude, degree
		L = L * dr;
		L = L - PI * 2 * (INT(L / (PI * 2))); // Normalize to (0, 2*PI)
		return L;
	}

	public static DayMonthYear lunarMonth11(int Y) {
		DayMonthYear dmy = new DayMonthYear(31, 12, Y);
		double off = localToJD(dmy) - 2415021.076998695;
		int k = INT(off / 29.530588853);
		double jd = newMoon(k);
		dmy = localFromJD(jd);
		double sunLong = sunLongitude(localToJD(dmy)); // sun
														// longitude
														// at
														// local
														// midnight
		if (sunLong > 3 * PI / 2) {
			jd = newMoon(k - 1);
		}
		return localFromJD(jd);
	}

	public static DayMonthYear[] lunarYear(int Y) {
		DayMonthYear[] ret = null;
		DayMonthYear month11A = lunarMonth11(Y - 1);
		double jdMonth11A = localToJD(month11A);
		int k = (int) Math.floor(0.5 + (jdMonth11A - 2415021.076998695) / 29.530588853);
		DayMonthYear month11B = lunarMonth11(Y);
		double off = localToJD(month11B) - jdMonth11A;
		boolean leap = off > 365.0;
		if (!leap) {
			ret = new DayMonthYear[13];
		} else {
			ret = new DayMonthYear[14];
		}
		ret[0] = month11A;
		ret[ret.length - 1] = month11B;

		for (int i = 1; i < ret.length; i++) {
			double nm = newMoon(k + i);
			ret[i] = localFromJD(nm);
			ret[i - 1].setFull((int) daysBetween2Dates(ret[i - 1], ret[i]));
		}

		for (int i = 0; i < ret.length; i++) {
			ret[i].setNm(MOD(i + 11, 12));
		}
		if (leap) {
			initLeapYear(ret);
		}
		return ret;
	}

	static void initLeapYear(DayMonthYear[] ret) {
		double[] sunLongitudes = new double[ret.length];
		for (int i = 0; i < ret.length; i++) {
			DayMonthYear dmy = ret[i];
			double jdAtMonthBegin = localToJD(dmy);
			sunLongitudes[i] = sunLongitude(jdAtMonthBegin);
		}
		boolean found = false;
		for (int i = 0; i < ret.length; i++) {
			if (found) {
				ret[i].setNm(MOD(i + 10, 12));
				continue;
			}
			double sl1 = sunLongitudes[i];
			double sl2 = sunLongitudes[i + 1];
			boolean hasMajorTerm = Math.floor(sl1 / PI * 6) != Math.floor(sl2 / PI * 6);
			if (!hasMajorTerm) {
				found = true;
				ret[i].setLeap(1);
				ret[i].setNm(MOD(i + 10, 12));
			}
		}
	}

	/* bat dau chuyen doi ngay am duong */
	public static DayMonthYear solar2Lunar(DayMonthYear dmy) {
		int yy = dmy.getYear();
		int Y = yy;
		DayMonthYear[] ly = lunarYear(Y); // Please cache the result of this
		// computation for later use!!!
		DayMonthYear month11 = ly[ly.length - 1];
		double jdToday = localToJD(dmy);
		double jdMonth11 = localToJD(month11);
		if (jdToday >= jdMonth11) {
			ly = lunarYear(Y + 1);
			yy = Y + 1;
		}
		int i = ly.length - 1;
		while (jdToday < localToJD(ly[i])) {
			i--;
		}
		int dd = (int) (jdToday - localToJD(ly[i])) + 1;
		int mm = ly[i].getNm();
		if (mm >= 11) {
			yy--;
		}

		DayMonthYear ret = new DayMonthYear(dd, mm, yy, ly[i].getLeap());
		ret.setFull(ly[i].getFull());

		return ret;
	}

	// dmy: ngay, thang, nam, leap
	public static DayMonthYear lunar2Solar(DayMonthYear dmy) {
		int yy = dmy.getYear();
		if (dmy.getMonth() >= 11) {
			yy = dmy.getYear() + 1;
		}
		DayMonthYear[] lunarYear = lunarYear(yy);
		DayMonthYear lunarMonth = null;
		for (int i = 0; i < lunarYear.length; i++) {
			DayMonthYear lm = lunarYear[i];
			if (lm.getNm() == dmy.getMonth() && lm.getLeap() == dmy.getLeap()) {
				lunarMonth = lm;
				break;
			}
		}
		if (lunarMonth != null) {
			double jd = localToJD(lunarMonth);
			return localFromJD(jd + dmy.getDay() - 1);
		} else {
			throw new RuntimeException("Incorrect input!");
		}
	}

	/* ket thuc chuyen doi ngay am duong */
	/* bat dau can chi */

	public static int[] can(DayMonthYear dmy) {
		DayMonthYear lunar = solar2Lunar(dmy);
		int y = lunar.getYear();

		// return can ngay - thang - nam
		return new int[] { INT(localToJD(dmy) + 10.5) % 10, (y * 12 + lunar.getMonth() + 3) % 10, (y + 6) % 10 };
	}

	public static int[] chi(DayMonthYear dmy) {
		DayMonthYear lunar = solar2Lunar(dmy);

		// return chi ngay - thang - nam
		return new int[] { INT(localToJD(dmy) + 2.5) % 12, (lunar.getMonth() + 1) % 12, (lunar.getYear() + 8) % 12 };
	}

	public static int thu(DayMonthYear dmy) {
		return (int) (localToJD(dmy) + 1.5) % 7;
	}

	/* ket thuc can chi */
	public static int maxDayOfMonth(int m, int y) {
		if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12)
			return 31;
		if (m == 4 || m == 6 || m == 9 || m == 11)
			return 30;
		if (m == 2) {
			if ((y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0)) {
				return 29;
			} else
				return 28;
		}
		return 0;
	}

	public static DayMonthYear addDay(DayMonthYear dmy, int add) {
		int D = dmy.getDay();
		int M = dmy.getMonth();
		int Y = dmy.getYear();

		D += add;
		do {
			if (D > maxDayOfMonth(M, Y)) {
				D -= maxDayOfMonth(M, Y);
				M++;
				if (M > 12) {
					M = 1;
					Y++;
				}
			}

			if (D <= 0) {
				M--;
				if (M < 1) {
					M = 12;
					Y--;
					D += 31;
				} else {
					D += maxDayOfMonth(M, Y);
				}
			}
		} while (D < 0 || D > maxDayOfMonth(M, Y));

		return new DayMonthYear(D, M, Y);
	}

	public static long daysBetween2Dates(DayMonthYear dmy1, DayMonthYear dmy2) {

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		Date date1 = Date.valueOf(dmy1.getYear() + "-" + dmy1.getMonth() + "-" + dmy1.getDay());
		Date date2 = Date.valueOf(dmy2.getYear() + "-" + dmy2.getMonth() + "-" + dmy2.getDay());

		c1.setTime(date1);
		c2.setTime(date2);

		long noDay = (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);

		return noDay;
	}

	public static DayMonthYear[] tietKhiMoc(int Y) {
		DayMonthYear[] ret = new DayMonthYear[25];

		int i = 0;
		DayMonthYear dmy = new DayMonthYear(5, 1, Y);

		while (i < 24) {

			if (i == 5)
				while (sunLongitude(localToJD(dmy)) > SUNLONG_MAJOR[6]) {
					dmy = addDay(dmy, 1);
				}
			else
				while (sunLongitude(localToJD(dmy)) < SUNLONG_MAJOR[i]) {
					dmy = addDay(dmy, 1);
				}

			ret[i] = addDay(dmy, -1);

			dmy = addDay(dmy, 14);
			i++;
		}

		ret[i] = new DayMonthYear(31, 12, Y);

		return ret;
	}

	public static int tietKhi(DayMonthYear dmy) {
		DayMonthYear[] moc = tietKhiMoc(dmy.getYear());
		long a, b;

		if (dmy.getDay() == 31 && dmy.getMonth() == 12)
			return 23;
		else {
			for (int i = 0; i < 24; i++) {
				a = daysBetween2Dates(moc[i], dmy);
				b = daysBetween2Dates(moc[i + 1], dmy);
				if (a < 0) {
					return 23;
				} else {
					if (b < 0) {
						return i;
					}
				}
			}
		}

		return -1;
	}

	public static int truc(DayMonthYear dmy) {

		for (int i = 0; i < 24; i += 2) {
			if (daysBetween2Dates(dmy, tietKhiMoc(dmy.getYear())[i]) > 0) {
				return (chi(dmy)[0] + 12 - i / 2) % 12;
			}
		}

		return (chi(dmy)[0]);
	}

	public static int nhiThapBatTu(DayMonthYear dmy) {

		return (int) daysBetween2Dates(new DayMonthYear(1, 1, 1975), dmy) % 28;

	}

	// result= 0: ngay hac dao
	// result= 1: ngay hoang dao
	public static int ngayHoangDao(DayMonthYear dmy) {

		int[] hoangDao = new int[] { 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0 };

		return hoangDao[(chi(dmy)[0] - ((solar2Lunar(dmy).getMonth() * 2 - 2) % 12) + 12) % 12];

	}

	public static int[] gioHoangDao(DayMonthYear dmy) {
		int chi = chi(dmy)[0];
		int[] gio;

		switch (chi) {
		case 0:
		case 6:
			gio = new int[] { 0, 1, 3, 6, 8, 9 };
			break;

		case 1:
		case 7:
			gio = new int[] { 2, 3, 5, 8, 10, 11 };
			break;

		case 2:
		case 8:
			gio = new int[] { 0, 1, 4, 5, 7, 10 };
			break;

		case 3:
		case 9:
			gio = new int[] { 0, 2, 3, 6, 7, 9 };
			break;

		case 4:
		case 10:
			gio = new int[] { 2, 4, 5, 8, 9, 11 };
			break;

		case 5:
		case 11:
			gio = new int[] { 1, 4, 6, 7, 10, 11 };
			break;

		default:
			gio = null;
			break;
		}

		return gio;
	}

	public ArrayList<Integer> saoTot(DayMonthYear dmy) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		int[][] sao = { { 5, 7, 9, 11, 1, 3, 5, 7, 9, 11, 1, 3 }, { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 },
				{ 6, 8, 10, 0, 2, 4, 6, 8, 10, 0, 2, 4 }, { 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 },
				{ 2, 8, 3, 9, 4, 10, 5, 11, 6, 0, 7, 1 }, { 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5 },
				{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 }, { 7, 9, 11, 1, 3, 5, 7, 9, 7, 1, 3, 5 },
				{ 10, 0, 2, 4, 6, 8, 10, 0, 2, 4, 6, 8 }, { 6, 8, 10, 0, 2, 4, 6, 8, 10, 0, 2, 4 },
				{ 9, 3, 10, 4, 11, 5, 0, 6, 1, 7, 2, 8 }, { 8, 8, 10, 10, 0, 0, 2, 2, 4, 4, 6, 6 },
				{ 10, 1, 2, 5, 9, 3, 0, 6, 8, 4, 8, 7 } };

		int m = solar2Lunar(dmy).getMonth();
		int child = chi(dmy)[0];

		for (int i = 0; i < 13; i++) {
			if (sao[i][m - 1] == child) {
				ret.add(i);
			}
		}

		return ret;
	}

	public static ArrayList<Integer> saoXau(DayMonthYear dmy) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		int[][] saoChi = { { 5, 0, 7, 2, 9, 4, 11, 6, 1, 8, 3, 10 }, { 10, 4, 11, 5, 0, 6, 1, 7, 2, 8, 3, 9 },
				{ 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5 }, { 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4 },
				{ 0, 5, 7, 3, 8, 10, 1, 11, 6, 9, 2, 4 }, { 0, 3, 6, 9, 0, 3, 6, 9, 0, 3, 6, 9 },
				{ 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 11 }, { 1, 7, 2, 8, 3, 9, 4, 10, 5, 11, 0, 6 },
				{ 8, 10, 10, 11, 1, 1, 2, 4, 4, 5, 7, 7 }, { 5, 0, 1, 8, 3, 10, 11, 6, 7, 2, 9, 4 },
				{ 11, 11, 11, 2, 2, 2, 5, 5, 5, 8, 8, 8 }, { 2, 5, 8, 11, 3, 6, 9, 0, 4, 7, 10, 1 },
				{ 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 4, 5, 6, 7, 8, 9, 10, 11, 0, 1, 2, 3 } };
		int[][] saoCan = { { 0, 1, 4, 2, 3, 5, 6, 7, 5, 8, 9, 4 }, { 6, 7, 5, 8, 9, 4, 0, 1, 5, 2, 3, 4 } };

		int m = solar2Lunar(dmy).getMonth();

		int chi = chi(dmy)[0];
		for (int i = 0; i < 14; i++) {
			if (chi == saoChi[i][m - 1]) {
				ret.add(i);
			}
		}

		int can = can(dmy)[0];
		for (int i = 0; i < 2; i++) {
			if (can == saoCan[i][m - 1]) {
				ret.add(i);
			}
		}

		return ret;
	}

	// array cac ngay bat tuong
	public static int[][] arrayBatTuong(int counter) {
		int[][] ret = null;

		switch (counter) {
		case 0:
		case 1:
			ret = new int[][] { { 2, 2 }, { 3, 3 }, { 4, 4 }, { 3, 1 }, { 4, 2 }, { 5, 3 }, { 6, 4 }, { 6, 2 },
					{ 7, 3 }, { 7, 1 }, { 2, 4 } };
			break;
		case 2:
		case 3:
			ret = new int[][] { { 2, 2 }, { 3, 3 }, { 2, 0 }, { 5, 3 }, { 4, 0 }, { 6, 2 }, { 7, 3 } };
			break;
		case 4:
		case 5:
			ret = new int[][] { { 1, 1 }, { 2, 0 }, { 3, 1 }, { 2, 10 }, { 4, 0 }, { 5, 1 }, { 4, 10 }, { 6, 0 },
					{ 6, 10 } };
			break;
		case 6:
		case 7:
			ret = new int[][] { { 1, 1 }, { 3, 1 }, { 1, 9 }, { 5, 1 }, { 3, 9 }, { 5, 9 } };
			break;
		case 8:
		case 9:
			ret = new int[][] { { 0, 0 }, { 0, 10 }, { 2, 0 }, { 0, 8 }, { 1, 9 }, { 2, 10 }, { 4, 0 }, { 2, 8 },
					{ 3, 9 }, { 4, 10 } };
			break;
		case 10:
		case 11:
			ret = new int[][] { { 9, 9 }, { 0, 10 }, { 9, 7 }, { 0, 8 }, { 1, 9 }, { 2, 10 }, { 1, 7 }, { 2, 8 },
					{ 4, 0 }, { 5, 7 } };
			break;
		case 12:
		case 13:
			ret = new int[][] { { 8, 8 }, { 9, 9 }, { 0, 10 }, { 8, 6 }, { 9, 7 }, { 0, 8 }, { 1, 9 }, { 0, 6 },
					{ 1, 7 }, { 4, 10 }, { 5, 7 }, { 8, 10 } };
			break;
		case 14:
		case 15:
			ret = new int[][] { { 5, 5 }, { 8, 8 }, { 9, 9 }, { 8, 6 }, { 9, 7 }, { 0, 8 }, { 1, 9 }, { 9, 5 },
					{ 0, 6 }, { 1, 7 }, { 1, 5 }, { 4, 6 }, { 5, 7 } };
			break;
		case 16:
		case 17:
			ret = new int[][] { { 4, 4 }, { 5, 5 }, { 7, 7 }, { 7, 5 }, { 8, 6 }, { 9, 7 }, { 0, 8 }, { 8, 4 },
					{ 9, 5 }, { 0, 6 }, { 0, 4 }, { 4, 6 } };
			break;
		case 18:
		case 19:
			ret = new int[][] { { 5, 5 }, { 6, 6 }, { 7, 7 }, { 7, 5 }, { 8, 6 }, { 9, 7 }, { 9, 5 }, { 9, 3 },
					{ 4, 6 }, { 5, 7 } };
			break;
		case 20:
		case 21:
			ret = new int[][] { { 4, 4 }, { 6, 6 }, { 4, 2 }, { 5, 3 }, { 6, 4 }, { 8, 6 }, { 6, 2 }, { 7, 3 },
					{ 8, 4 }, { 8, 2 }, { 9, 3 }, { 4, 6 } };
			break;
		case 22:
		case 23:
			ret = new int[][] { { 3, 3 }, { 4, 4 }, { 5, 5 }, { 3, 1 }, { 5, 3 }, { 6, 4 }, { 7, 5 }, { 8, 4 },
					{ 7, 1 }, { 3, 5 } };
			break;
		}

		return ret;
	}

	// lay ngay bat tuong trong khoang cho truoc
	public static ArrayList<DayMonthYear> ngayBatTuong(DayMonthYear dmy1, DayMonthYear dmy2) {

		ArrayList<DayMonthYear> ret = new ArrayList<DayMonthYear>();
		DayMonthYear dmy = dmy1;
		int[][] arrBatTuong;
		int tietKhi, can, chi;
		boolean flag = true;

		while (flag) {

			tietKhi = tietKhi(dmy);
			arrBatTuong = arrayBatTuong(tietKhi);
			can = can(dmy)[0];
			chi = chi(dmy)[0];

			for (int i = 0; i < arrBatTuong.length; i++) {
				if (can == arrBatTuong[i][0] && chi == arrBatTuong[i][1]) {
					ret.add(dmy);
					// dmy.printInfo();
				}
			}

			dmy = addDay(dmy, 1);

			if (daysBetween2Dates(dmy, dmy2) == 0) {
				flag = false;
				break;
			}

		}

		return ret;
	}

	// lay 11 ngay
	public static ArrayList<DayMonthYear> ngayTot(DayMonthYear dmy1, DayMonthYear dmy2) {

		ArrayList<DayMonthYear> ret = new ArrayList<DayMonthYear>();

		int[][] arrNgayTot = new int[][] { { 2, 2 }, { 3, 3 }, { 2, 5 }, { 4, 2 }, { 5, 3 }, { 2, 10 }, { 4, 0 },
				{ 6, 2 }, { 8, 2 }, { 9, 3 }, { 1, 5 } };

		DayMonthYear dmy = dmy1;
		int can, chi;
		boolean flag = true;

		while (flag) {

			can = can(dmy)[0];
			chi = chi(dmy)[0];

			for (int i = 0; i < 11; i++) {
				if (can == arrNgayTot[i][0] && chi == arrNgayTot[i][1]) {
					ret.add(dmy);

					// System.out.println(d + "\t" + m);
				}
			}

			dmy = addDay(dmy, 1);
			if (daysBetween2Dates(dmy, dmy2) == 0) {
				flag = false;
				break;
			}
		}

		return ret;
	}

	// lay ra ngay trong thang trong khoang cho truoc
	public static ArrayList<DayMonthYear> trucThanh(DayMonthYear dmy1, DayMonthYear dmy2) {
		ArrayList<DayMonthYear> ret = new ArrayList<DayMonthYear>();

		DayMonthYear dmy = dmy1;
		boolean flag = true;

		while (flag) {

			if (truc(dmy) == 8) {
				ret.add(dmy);
			}

			dmy = addDay(dmy, 1);

			if (daysBetween2Dates(dmy, dmy2) == 0) {
				flag = false;
				break;
			}
		}

		return ret;
	}

	public static int[] thangTot(DayMonthYear dmy) {

		int[] ret = new int[2];
		int chi = chi(dmy)[2];

		switch (chi) {
		case 0:
		case 6:
			ret[0] = 6;
			ret[1] = 12;
			break;
		case 1:
		case 7:
			ret[0] = 5;
			ret[1] = 11;
			break;
		case 2:
		case 8:
			ret[0] = 2;
			ret[1] = 8;
			break;
		case 3:
		case 9:
			ret[0] = 1;
			ret[1] = 7;
			break;
		case 4:
		case 10:
			ret[0] = 4;
			ret[1] = 10;
			break;
		case 5:
		case 11:
			ret[0] = 3;
			ret[1] = 9;
			break;
		}

		return ret;

	}

	// sap xep ngaythang nam
	public static void sortResult(ArrayList<DayMonthYear> result) {

		Collections.sort(result, new Comparator<DayMonthYear>() {

			@Override
			public int compare(DayMonthYear o1, DayMonthYear o2) {
				// TODO Auto-generated method stub
				long noDay = daysBetween2Dates(o1, o2);
				if (noDay > 0)
					return -1;
				else {
					if (noDay == 0)
						return 0;
					else
						return 1;
				}
			}
		});

	}

	// loai bo ngay xau nhu tam nuong...
	public static void ngayXau(ArrayList<DayMonthYear> result) {

		DayMonthYear dmy = new DayMonthYear();
		int mua, chi, d, m;

		for (int i = 0; i < result.size(); i++) {
			dmy = result.get(i);
			chi = chi(dmy)[0];
			mua = tietKhi(dmy);
			d = solar2Lunar(dmy).getDay();
			m = solar2Lunar(dmy).getMonth();

			// 3 nguyet ki,6 tam nuong
			if (d == 5 || d == 14 || d == 23 || d == 3 || d == 7 || d == 13 || d == 18 || d == 22 || d == 27) {
				result.remove(i);
			}

			// 13 ngayduong cong nhat ki
			if ((d == 11 && m == 2) || (d == 9 && m == 3) || (d == 8 && m == 7) || (d == 29 && m == 7)
					|| (d == 25 && m == 9) || (d == 21 && m == 11) || (d == 19 && m == 12)) {
				result.remove(i);
			}

			//
			if (mua < 2 || mua >= 20) {
				if (chi == 1 || chi == 2 || chi == 8 || chi == 9)
					result.remove(i);
			}

			//
			if (mua >= 2 && mua < 8) {
				if (chi == 0 || chi == 4 || chi == 5 || chi == 9)
					result.remove(i);
			}

			//
			if (mua >= 8 && mua < 14) {
				if (chi == 1 || chi == 3 || chi == 7 || chi == 10)
					result.remove(i);
			}

			//
			if (mua >= 14 && mua < 20) {
				if (chi == 2 || chi == 3 || chi == 6 || chi == 8 || chi == 10)
					result.remove(i);
			}
		}
	}

	// co hu sat
	public static void coHuSat(ArrayList<DayMonthYear> result, DayMonthYear dmyMen, DayMonthYear dmyWoman) {

		int month;

		int[] hu = new int[2];

		int co = (22 - (can(dmyMen)[2] - chi(dmyMen)[2] + 12) % 12) % 12;
		int coHuSatWoman = (can(dmyWoman)[2] - chi(dmyWoman)[2] + 12) % 12;

		switch (coHuSatWoman) {
		case 0:
			hu[0] = 2;
			hu[1] = 4;
			break;
		case 2:
			hu[0] = 1;
			hu[1] = 3;
			break;
		case 4:
			hu[0] = 11;
			hu[1] = 12;
			break;
		case 6:
			hu[0] = 9;
			hu[1] = 10;
			break;
		case 8:
			hu[0] = 7;
			hu[1] = 8;
			break;
		case 10:
			hu[0] = 5;
			hu[1] = 6;
			break;
		}

		for (int i = 0; i < result.size(); i++) {
			month = solar2Lunar(result.get(i)).getMonth();
			if (month == co || month == co - 1 || month == hu[0] || month == hu[1]) {
				result.remove(i);
			}
		}
	}

	// nam xau theo nam sinh
	public static int[] namXau(DayMonthYear dmyMen, DayMonthYear dmyWoman) {
		int chiMen = chi(dmyMen)[2];
		int chiWoman = chi(dmyWoman)[2];
		int[] y = new int[2];

		y[0] = (chiMen + 7) % 12;
		y[1] = (15 - chiWoman) % 12;

		return y;
	}

	// chon ngay trong khoang cho truoc
	public static ArrayList<DayMonthYear> cuoiGa(DayMonthYear dmy1, DayMonthYear dmy2, DayMonthYear dmyMen,
			DayMonthYear dmyWoman) {

		ArrayList<DayMonthYear> ret = ngayBatTuong(dmy1, dmy2);
		ArrayList<DayMonthYear> resultNgayTot = ngayTot(dmy1, dmy2);
		ArrayList<DayMonthYear> resultTrucThanh = trucThanh(dmy1, dmy2);

		int counter = ret.size();
		int k = 0;
		int[] y = namXau(dmyMen, dmyWoman);

		for (int i = 0; i < resultNgayTot.size(); i++) {
			for (int j = 0; j < counter; j++) {
				if (resultNgayTot.get(i).getDay() == ret.get(j).getDay()
						&& resultNgayTot.get(i).getMonth() == ret.get(j).getMonth()) {
					k = 0;
					break;
				} else
					k++;

			}

			if (k == counter) {
				ret.add(resultNgayTot.get(i));
			}

			k = 0;
		}

		counter = ret.size();
		k = 0;
		for (int i = 0; i < resultTrucThanh.size(); i++) {
			for (int j = 0; j < counter; j++) {
				if (resultTrucThanh.get(i).getDay() == ret.get(j).getDay()
						&& resultTrucThanh.get(i).getMonth() == ret.get(j).getMonth()) {
					k = 0;
					break;
				} else
					k++;

			}

			if (k == counter) {
				ret.add(resultTrucThanh.get(i));
			}

			k = 0;
		}

		ngayXau(ret);
		coHuSat(ret, dmyMen, dmyWoman);
		sortResult(ret);

		y = namXau(dmyMen, dmyWoman);
		for (int i = dmy1.getYear(); i < dmy1.getYear() + 11; i++) {
			int chiNam = chi(new DayMonthYear(5, 5, i))[2];
			if (chiNam == y[0] || chiNam == y[1]) {
				System.out.println("Nam: " + i + " khong tot");
			}
		}

		return ret;
	}

}
