package com.example.project_db;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText tituloInput, textoInput;
    Spinner spinner;
    TextView verComentario;
    Button crearBtn, verBtn, eliminarBtn;
    ArrayAdapter<String> adapter;
    ArrayList<Comentario> comentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        tituloInput = findViewById(R.id.editTextTitulo);
        textoInput = findViewById(R.id.editTextComentario);
        spinner = findViewById(R.id.spinnerComentarios);
        verComentario = findViewById(R.id.textViewVerComentario);
        crearBtn = findViewById(R.id.buttonCrear);
        verBtn = findViewById(R.id.buttonVer);
        eliminarBtn = findViewById(R.id.buttonEliminar);

        actualizarSpinner();

        crearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = tituloInput.getText().toString();
                String texto = textoInput.getText().toString();
                dbHelper.agregarComentario(titulo, texto);
                actualizarSpinner();
            }
        });

        verBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion = spinner.getSelectedItemPosition();
                if (posicion >= 0) {
                    verComentario.setText(comentarios.get(posicion).getTexto());
                }
            }
        });

        eliminarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion = spinner.getSelectedItemPosition();
                if (posicion >= 0) {
                    dbHelper.eliminarComentario(comentarios.get(posicion).getId());
                    actualizarSpinner();
                }
            }
        });
    }

    private void actualizarSpinner() {
        comentarios = dbHelper.obtenerComentarios();
        ArrayList<String> titulos = new ArrayList<>();
        for (Comentario c : comentarios) {
            titulos.add(c.getTitulo());
        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, titulos);
        spinner.setAdapter(adapter);
    }
}