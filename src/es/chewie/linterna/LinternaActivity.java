package es.chewie.linterna;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LinternaActivity extends Activity {

	private Boolean encendido = false;
	private Camera camara;

	@Override
	protected void onStop() {
		super.onStop();

		if (camara != null) {
			camara.release();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_linterna);

		Context context = this;
		PackageManager pm = context.getPackageManager();

		// Vemos si el dispositivo tiene camara
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Log.e("err", "Device has no camera!");
			return;
		}

		// Accedemos a la camara!
		camara = Camera.open();
		if (camara != null) {
			final Parameters p = camara.getParameters();

			((Button) findViewById(R.id.botonLinterna))
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (encendido) {
								p.setFlashMode(Parameters.FLASH_MODE_OFF);
								camara.setParameters(p);
								camara.stopPreview();
								encendido = false;
								Log.i("clickOFF", "OFF!!");
							} else {
								p.setFlashMode(Parameters.FLASH_MODE_TORCH);
								camara.setParameters(p);
								camara.startPreview();
								encendido = true;
								Log.i("clickON", "ON!!");
							}
						}
					});
		}

	}

}
