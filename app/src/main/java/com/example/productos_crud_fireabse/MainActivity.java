package com.example.productos_crud_fireabse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    /* Definir Objetos, Variables Y Constantes */
    EditText jetCode, jetName, jetPrice, jetQuantity;
    CheckBox jcbAvailable;
    RadioButton jrbAppliance, jrbTechnology, jrbHome, jrbOther;
    Button jbtnAdd, jbtnQuery, jbtnAnnul, jbtnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Asociar Los Objetos XML Con Las Variables Definidas Anteriormente */
        jetCode = findViewById(R.id.etCode);
        jetName = findViewById(R.id.etName);
        jetPrice = findViewById(R.id.etPrice);
        jetQuantity = findViewById(R.id.etQuantity);

        jcbAvailable = findViewById(R.id.cbAvailable);

        jrbAppliance = findViewById(R.id.rbAppliance);
        jrbTechnology = findViewById(R.id.rbTechnology);
        jrbHome = findViewById(R.id.rbHome);
        jrbOther = findViewById(R.id.rbOther);

        jbtnAdd = findViewById(R.id.btnAdd);
        jbtnQuery = findViewById(R.id.btnQuery);
        jbtnAnnul = findViewById(R.id.btnAnnul);
        jbtnCancel = findViewById(R.id.btnCancel);
    }
}