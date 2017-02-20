//package com.example.print;
//
//import java.util.List;
//
//import com.aspose.cells.Workbook;
//import com.aspose.cells.WorkbookDesigner;
//
//import android.app.Activity;
//import android.content.Context;
//import android.util.Log;
//import android.widget.Toast;
//
//public class PrintNote extends Activity{
//	String tempPath;
//	Context mContext;
//
//	public PrintNote(Context context,String tempPath) {
//		this.tempPath = tempPath;
//		this.mContext=context;
//	}
//
//	public void printNote(String savePath,List<NoteDto>note) {
//		try {
//			Log.d("path print ", tempPath);
//			Workbook workbook = new Workbook(mContext.getAssets().open(tempPath));
//		    WorkbookDesigner designer =new WorkbookDesigner();
//		    designer.setWorkbook(workbook);
//		    designer.setDataSource("note", note);
//		    designer.process(false);
//		    workbook.save(savePath);
//		    Toast.makeText(mContext, "thanh cong", Toast.LENGTH_LONG).show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
