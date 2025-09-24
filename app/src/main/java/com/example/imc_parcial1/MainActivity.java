package com.example.imc_parcial1;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etPeso, etAltura;
    private TextView tvResultado, tvImcGigante;
    private LinearLayout resultContainer;
    private Button btnCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        tvResultado = findViewById(R.id.tvResultado);
        tvImcGigante = findViewById(R.id.tvImcGigante);
        resultContainer = findViewById(R.id.resultContainer);
        btnCalcular = findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(v -> calcularIMC());
    }

    private void calcularIMC() {
        String sPeso = etPeso.getText().toString().trim();
        String sAltura = etAltura.getText().toString().trim();

        if (sPeso.isEmpty() || sAltura.isEmpty()) {
            Toast.makeText(this, "Ingresá peso y altura", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float peso = Float.parseFloat(sPeso.replace(",", "."));
            float alturaCm = Float.parseFloat(sAltura.replace(",", "."));
            if (peso <= 0 || alturaCm <= 0) {
                Toast.makeText(this, "Valores inválidos", Toast.LENGTH_SHORT).show();
                return;
            }
            float alturaM = alturaCm / 100f;
            float imc = peso / (alturaM * alturaM);


            String categoria;
            boolean esNormal;
            if (imc < 18.5f) {
                categoria = "Bajo peso";
                esNormal = false;
            } else if (imc < 25f) {
                categoria = "Peso normal";
                esNormal = true;
            } else if (imc < 30f) {
                categoria = "Sobrepeso";
                esNormal = false;
            } else {
                categoria = "Obesidad";
                esNormal = false;
            }


            tvImcGigante.setText(String.format(Locale.getDefault(), "%.1f", imc));
            tvImcGigante.setVisibility(View.VISIBLE);
            resultContainer.setVisibility(View.VISIBLE);


            tvResultado.setText(categoria);


            @ColorInt int color = ContextCompat.getColor(this,
                    esNormal ? R.color.imc_good : R.color.imc_bad);
            Drawable bg = resultContainer.getBackground();
            if (bg instanceof GradientDrawable) {
                ((GradientDrawable) bg.mutate()).setColor(color);
            } else {
                resultContainer.setBackgroundColor(color);
            }
            


        } catch (NumberFormatException e) {
            Toast.makeText(this, "Formato numérico inválido", Toast.LENGTH_SHORT).show();
        }
    }
}
