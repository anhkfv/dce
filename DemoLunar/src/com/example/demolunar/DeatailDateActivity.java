package com.example.demolunar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.date.DayMonthYear;
import com.example.date.Lunar;
import com.example.sqlite_battu.BatTu;
import com.example.sqlite_battu.DatabaseHandlerBatTu;
import com.example.sqlite_truc.Contact;
import com.example.sqlite_truc.DatabaseHandler;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

public class DeatailDateActivity extends Activity {
	DayMonthYear dmyt;
	EditText editDate, editTietKhi, editTruc, editSao, editTrucInfo, editBatTuInfo;
	TextView tvDate, tvThu;
	TextView tvHd, tvTietKhi, tvTruc, tvSao, tvtrucInfo, tvBatTuInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_date);
		dmyt = (DayMonthYear) getIntent().getSerializableExtra("detailDate");
		editDate = (EditText) findViewById(R.id.editDate);
		tvThu = (TextView) findViewById(R.id.textThu);
		tvDate = (TextView) findViewById(R.id.textDate);
		tvTietKhi = (TextView) findViewById(R.id.textTietKhi);
		editTietKhi = (EditText) findViewById(R.id.editTietKhi);
		tvTruc = (TextView) findViewById(R.id.textTruc);
		editTruc = (EditText) findViewById(R.id.editTruc);
		tvSao = (TextView) findViewById(R.id.textSao);
		editSao = (EditText) findViewById(R.id.editSao);
		tvtrucInfo = (TextView) findViewById(R.id.textTrucInfo);
		editTrucInfo = (EditText) findViewById(R.id.editTrucInfo);
		tvBatTuInfo = (TextView) findViewById(R.id.textBatTuInfo);
		editBatTuInfo = (EditText) findViewById(R.id.editBatTuInfo);
		tvHd = (TextView) findViewById(R.id.textHd);

		String hd = "";
		String tk = "";
		String tr = "";
		String sa = "";
		String sx = "";
		String bt = "";
		Lunar ln = new Lunar();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr = ln.saoTot(dmyt);
		if (arr.size() == 0) {
			sa = "Không có sao tốt";

		} else {
			sa += "Sao tốt :";
			for (int k = 0; k < arr.size(); k++) {
				sa += " " + Lunar.SAOTOT[arr.get(k)] + ",";

			}
		}
		if (Lunar.saoXau(dmyt).size() == 0) {
			sx = "Không có sao xấu";
		} else {
			sx += "Sao xấu: ";
			for (int k = 0; k < Lunar.saoXau(dmyt).size(); k++) {
				sx += " " + Lunar.SAOXAU[Lunar.saoXau(dmyt).get(k)] + ",";
			}
		}

		tr = Lunar.TRUC[Lunar.truc(dmyt)];
		bt = Lunar.SAO[Lunar.nhiThapBatTu(dmyt)];
		tk = Lunar.TIETKHI[Lunar.tietKhi(dmyt)];
		for (int i = 0; i < 6; i++) {
			if (i == 2)
				hd += "\n";
			hd += "    " + Lunar.GIO[Lunar.gioHoangDao(dmyt)[i]];
		}
		tvThu.setTextColor(Color.GREEN);
		tvThu.setTextSize(25);
		tvThu.setText(Lunar.THU[Lunar.thu(dmyt)]);

		tvDate.setTextColor(Color.GREEN);
		tvDate.setTextSize(15);
		tvDate.setGravity(Gravity.CENTER);
		tvDate.setText(dmyt.getDay() + "-" + dmyt.getMonth() + "-" + dmyt.getYear());

		tvTruc.setTextSize(20);
		tvTruc.setTextColor(Color.GREEN);
		tvTruc.setText("\n Trực");
		tvTruc.setGravity(Gravity.CENTER);
		tvTruc.setFocusable(false);

		editTruc.setTextSize(20);
		editTruc.setTextColor(Color.BLACK);
		editTruc.setText("" + tr);
		editTruc.setGravity(Gravity.CENTER);
		editTruc.setFocusable(false);

		tvTietKhi.setTextSize(20);
		tvTietKhi.setTextColor(Color.GREEN);
		tvTietKhi.setText("\n Tiết Khí");
		tvTietKhi.setGravity(Gravity.CENTER);
		tvTietKhi.setFocusable(false);

		editTietKhi.setTextSize(20);
		editTietKhi.setTextColor(Color.BLACK);
		editTietKhi.setText("" + tk);
		editTietKhi.setGravity(Gravity.CENTER);
		editTietKhi.setFocusable(false);

		tvSao.setTextSize(20);
		tvSao.setTextColor(Color.GREEN);
		tvSao.setText("\n Sao");
		tvSao.setGravity(Gravity.CENTER);
		tvSao.setFocusable(false);

		editSao.setTextSize(20);
		editSao.setTextColor(Color.BLACK);
		editSao.setText("" + sa + "\n" + sx);
		editSao.setGravity(Gravity.CENTER);
		editSao.setFocusable(false);

		tvHd.setTextSize(20);
		tvHd.setTextColor(Color.GREEN);
		tvHd.setText("\n Giờ Hoàng Đạo");
		tvHd.setGravity(Gravity.CENTER);
		tvHd.setFocusable(false);

		editDate.setTextSize(20);
		editDate.setTextColor(Color.BLACK);
		editDate.setText("" + hd);
		editDate.setGravity(Gravity.CENTER);
		editDate.setFocusable(false);

		tvtrucInfo.setTextSize(20);
		tvtrucInfo.setTextColor(Color.GREEN);
		tvtrucInfo.setText("\n Xem ngày theo trực");
		tvtrucInfo.setGravity(Gravity.CENTER);
		tvtrucInfo.setFocusable(false);

		editTrucInfo.setTextSize(20);
		editTrucInfo.setTextColor(Color.BLACK);
		editTrucInfo.setText(trucSqlite(tr));
		editTrucInfo.setGravity(Gravity.CENTER);
		editTrucInfo.setFocusable(false);
		tvBatTuInfo.setTextSize(20);
		tvBatTuInfo.setTextColor(Color.GREEN);
		tvBatTuInfo.setText("\n Xem ngày theo nhị thập bát tú");
		tvBatTuInfo.setGravity(Gravity.CENTER);
		tvBatTuInfo.setFocusable(false);

		editBatTuInfo.setTextSize(20);
		editBatTuInfo.setTextColor(Color.BLACK);
		editBatTuInfo.setText(batTuSqlite(bt));
		editBatTuInfo.setGravity(Gravity.CENTER);
		editBatTuInfo.setFocusable(false);
	}

	public String trucSqlite(String temp) {
		String tmp = "";
		DatabaseHandler db = new DatabaseHandler(this);
		try {
			db.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Contact> contacts = db.getAllContacts();
		for (Contact cn : contacts) {
			if (cn.getTruc().trim().equals(temp.trim())) {
				tmp = "Nên làm: " + cn.getNenLam() + " \nKhông nên: " + cn.getKhongNen();
			}

		}
		return tmp;
	}

	public String batTuSqlite(String temp) {
		String tmp = "";
		DatabaseHandlerBatTu db = new DatabaseHandlerBatTu(this);
		try {
			db.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<BatTu> battu = db.getAllBatTu();
		for (BatTu cn : battu) {

			if (cn.getSao().trim().equals(temp.trim())) {

				tmp = "Con vật:" + cn.getConvat() + "\n Thuộc:" + cn.getThuoc() + "\n Nên làm: " + cn.getNenLam()
						+ " \nKhông nên: " + cn.getKhongNen() + "\n Ngoại Lệ" + cn.getNgoaiLe();
			}

		}
		return tmp;
	}

}
