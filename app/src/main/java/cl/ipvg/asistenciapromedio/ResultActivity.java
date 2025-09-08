package cl.ipvg.asistenciapromedio; // O tu nombre de paquete real

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    TextView textViewNombreEstudianteResultado, textViewNota1Resultado,
            textViewNota2Resultado, textViewNota3Resultado,
            textViewPromedioResultado, textViewEstadoResultado;    LinearLayout layoutResult;

    // Puedes definir tu nota de aprobación aquí. En Chile, suele ser 4.0
    private static final double NOTA_APROBACION = 4.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Inicializar vistas
        textViewNombreEstudianteResultado = findViewById(R.id.textViewNombreEstudianteResultado);
        textViewNota1Resultado = findViewById(R.id.textViewNota1Resultado);
        textViewNota2Resultado = findViewById(R.id.textViewNota2Resultado);
        textViewNota3Resultado = findViewById(R.id.textViewNota3Resultado);
        textViewPromedioResultado = findViewById(R.id.textViewPromedioResultado);
        textViewEstadoResultado = findViewById(R.id.textViewEstadoResultado);
        layoutResult = findViewById(R.id.layoutResult);

        // Obtener datos del Intent
        Intent intent = getIntent();
        String nombreEstudiante = intent.getStringExtra("NOMBRE_ESTUDIANTE");
        double nota1 = intent.getDoubleExtra("NOTA1", 0.0);
        double nota2 = intent.getDoubleExtra("NOTA2", 0.0);
        double nota3 = intent.getDoubleExtra("NOTA3", 0.0);

        // Calcular promedio
        double promedio = (nota1 + nota2 + nota3) / 3.0;

        // Formatear números para mostrar (dos decimales)
        DecimalFormat df = new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.US)); // Usar Locale.US para asegurar el punto como separador

        // Mostrar datos en los TextViews
        textViewNombreEstudianteResultado.setText(nombreEstudiante);
        textViewNota1Resultado.setText(String.format(Locale.getDefault(),"Nota 1: %s", df.format(nota1)));
        textViewNota2Resultado.setText(String.format(Locale.getDefault(),"Nota 2: %s", df.format(nota2)));
        textViewNota3Resultado.setText(String.format(Locale.getDefault(),"Nota 3: %s", df.format(nota3)));
        textViewPromedioResultado.setText(String.format(Locale.getDefault(),"Promedio: %s", df.format(promedio)));

        // Determinar estado y color
        String estado;
        String mensajeToast;

        if (promedio >= NOTA_APROBACION - 0.001) { // Pequeña tolerancia para punto flotante
            estado = "APROBADO";
            textViewEstadoResultado.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
            // layoutResult.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAprobadoFondo)); // Opcional: Color de fondo
            mensajeToast = "Aprobado (promedio: " + df.format(promedio) + ")";
        } else {
            estado = "REPROBADO";
            textViewEstadoResultado.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
            // layoutResult.setBackgroundColor(ContextCompat.getColor(this, R.color.colorReprobadoFondo)); // Opcional: Color de fondo
            mensajeToast = "Reprobado (promedio: " + df.format(promedio) + ")";
        }
        textViewEstadoResultado.setText("Estado: " + estado);

        // Mostrar Toast final
        Toast.makeText(this, mensajeToast, Toast.LENGTH_LONG).show();
    }
}
