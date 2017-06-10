package processcommon;

import java.io.File;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

@SuppressWarnings("deprecation")

public  class CheckCommon {
	public static final String localhost = "http://192.168.4.216:8080";

	public static boolean checkNetwork(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mMobileInternet = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mWifi.isConnected() || mMobileInternet.isConnected()) {
			return true;
		}
		return false;
	}

	public static void noNetwork(Context context) {
		Toast.makeText(context, "Không kết nối mạng", Toast.LENGTH_LONG).show();
	}

	public static boolean checkHasFile(String pathFile) {
		File file = new File(pathFile);
		if (file.exists())
			return true;
		else
			return false;

	}

}
