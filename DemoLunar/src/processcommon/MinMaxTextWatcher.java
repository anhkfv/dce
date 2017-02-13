package processcommon;

import android.text.TextWatcher;

public abstract class MinMaxTextWatcher implements TextWatcher {
	int min, max;

	public MinMaxTextWatcher(int min, int max) {
		super();
		this.min = min;
		this.max = max;
	}

}