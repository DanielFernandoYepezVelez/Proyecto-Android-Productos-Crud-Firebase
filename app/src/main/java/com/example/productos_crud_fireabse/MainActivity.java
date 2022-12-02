package com.example.productos_crud_fireabse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /* Definir Objetos, Variables Y Constantes */
    EditText jetCode, jetName, jetPrice, jetQuantity;
    CheckBox jcbAvailable;
    RadioButton jrbAppliance, jrbTechnology, jrbHome, jrbOther;
    Button jbtnAdd, jbtnQuery, jbtnAnnul, jbtnCancel;

    String code, name, price, quantity, category, available, identDoc;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    public void productSave(View view) {
        code = jetCode.getText().toString();
        name = jetName.getText().toString();
        price = jetPrice.getText().toString();
        quantity = jetQuantity.getText().toString();

        if (code.isEmpty() || name.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
            Toast.makeText(MainActivity.this, "Todos Los Campos Son Requeridos", Toast.LENGTH_LONG).show();
        } else {
            /* Que Categoria Es El Producto */
            if (jrbAppliance.isChecked()) {
                category = "Appliance";
            } else if (jrbTechnology.isChecked()) {
                category = "Technology";
            } else if (jrbHome.isChecked()) {
                category = "Home";
            } else {
                category = "Other";
            }

            // Create A New Product
            Map<String, Object> product = new HashMap<>();
            product.put("Code", code);
            product.put("name", name);
            product.put("price", price);
            product.put("Quantity", quantity);
            product.put("Category", category);
            product.put("Available", "yes");

            // Add a new document with a generated ID
            /*db.collection("Stock")
                            .add()

            // Add a new document with a generated ID
            db.collection("Campeonato")
                    .add(equipo)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(MainActivity.this, "Documento Adicionado", Toast.LENGTH_LONG).show();
                            limpiarCampos();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.w("Error adding document", e);
                            Toast.makeText(MainActivity.this, "Documento NO Adicionado", Toast.LENGTH_LONG).show();
                        }
                    }); */

        }
    }
}