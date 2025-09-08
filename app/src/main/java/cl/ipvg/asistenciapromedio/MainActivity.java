package cl.ipvg.asistenciapromedio; // O tu nombre de paquete real

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText editTextNombreEstudiante, editTextNota1, editTextNota2, editTextNota3;
    Button btnCalcular, btnLimpiar, btnCompartir;

    // Para formatear y parsear números decimales consistentemente
    private DecimalFormat decimalFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Vincula con activity_main.xml

        // Inicializar vistas
        editTextNombreEstudiante = findViewById(R.id.editTextNombreEstudiante);
        editTextNota1 = findViewById(R.id.editTextNota1);
        editTextNota2 = findViewById(R.id.editTextNota2);
        editTextNota3 = findViewById(R.id.editTextNota3);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnCompartir = findViewById(R.id.btnCompartir); // Aunque esté oculto, lo referenciamos

        // Configurar el DecimalFormat para aceptar tanto punto como coma
        // Usaremos el Locale del sistema para el formateo, pero parsearemos ambos
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        // No forzamos un símbolo específico para parsear, intentaremos ambos
        decimalFormat = new DecimalFormat("#.##", symbols); // Formato para mostrar, parseo será más flexible


        // Configurar Listeners para los botones
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularYEnviarPromedio();
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampos();
            }
        });

        // btnCompartir.setOnClickListener(...); // Implementar si es necesario
    }

    private void calcularYEnviarPromedio() {
        String nombre = editTextNombreEstudiante.getText().toString().trim();
        String strNota1 = editTextNota1.getText().toString().trim();
        String strNota2 = editTextNota2.getText().toString().trim();
        String strNota3 = editTextNota3.getText().toString().trim();

        // Validación: Campos vacíos
        if (nombre.isEmpty() || strNota1.isEmpty() || strNota2.isEmpty() || strNota3.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        double nota1, nota2, nota3;

        // Validación y parseo de notas (aceptando coma o punto)
        try {
            nota1 = parseNota(strNota1);
            nota2 = parseNota(strNota2);
            nota3 = parseNota(strNota3);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error en formato de nota. Use '.' o ',' como decimal.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validación: Rango de notas (1.0 a 7.0)
        if (!esNotaValida(nota1) || !esNotaValida(nota2) || !esNotaValida(nota3)) {
            Toast.makeText(this, "Las notas deben estar entre 1.0 y 7.0", Toast.LENGTH_SHORT).show();
            return;
        }

        // Si todas las validaciones pasan, procedemos a enviar los datos
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("NOMBRE_ESTUDIANTE", nombre);
        intent.putExtra("NOTA1", nota1);
        intent.putExtra("NOTA2", nota2);
        intent.putExtra("NOTA3", nota3);
        startActivity(intent);
    }

    private double parseNota(String strNota) throws NumberFormatException {
        // Reemplazar coma por punto para un parseo consistente con Double.parseDouble
        String parseableStrNota = strNota.replace(',', '.');
        try {
            double nota = Double.parseDouble(parseableStrNota);
            // Redondear a dos decimales para evitar problemas de precisión flotante muy largos
            // Esto es más para la consistencia del dato que para el parseo en sí
            return Math.round(nota * 100.0) / 100.0;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Formato de número inválido: " + strNota);
        }
    }


    private boolean esNotaValida(double nota) {
        // Permitimos una pequeña tolerancia para errores de punto flotante.
        // Es mejor comparar rangos así:
        return nota >= 1.0 - 0.001 && nota <= 7.0 + 0.001;
    }

    private void limpiarCampos() {
        editTextNombreEstudiante.setText("");
        editTextNota1.setText("");
        editTextNota2.setText("");
        editTextNota3.setText("");
        editTextNombreEstudiante.requestFocus(); // Pone el foco en el primer campo
        Toast.makeText(this, "Campos limpiados", Toast.LENGTH_SHORT).show();
    }
}
