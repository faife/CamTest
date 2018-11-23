package faife.learn.camtest;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CamTest extends Activity {

	private CameraView camView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		camView = new CameraView(this);

		setContentView(camView);
	}

	@Override
	protected void onPause() {
		camView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		camView.onResume();
	}
}
