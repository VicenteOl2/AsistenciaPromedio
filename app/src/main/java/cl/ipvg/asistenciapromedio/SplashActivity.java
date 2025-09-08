package cl.ipvg.asistenciapromedio; // O tu nombre de paquete real

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

// Si tu minSdkVersion es 31+ y no usas la API SplashScreen nativa de Android 12,
// esta anotación evita una advertencia del linter. Es opcional para minSdk < 31.
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    // Duración de la pantalla de splash en milisegundos
    private static final int SPLASH_DISPLAY_LENGTH = 2000; // 2 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Vincula esta clase con el layout XML

        // Usar un Handler para retrasar la navegación a MainActivity
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Crea un Intent para iniciar MainActivity
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);

                // Cierra SplashActivity para que el usuario no pueda volver a ella presionando "Atrás"
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}