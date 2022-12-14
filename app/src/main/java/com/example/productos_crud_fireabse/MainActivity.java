package com.example.productos_crud_fireabse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /* Definir Objetos, Variables Y Constantes */
    EditText jetCode, jetName, jetPrice, jetQuantity;
    CheckBox jcbAvailable;
    RadioButton jrbAppliance, jrbTechnology, jrbHome, jrbOther;
    Button jbtnAdd, jbtnQuery, jbtnAnnul, jbtnCancel;

    boolean resulDB = false;
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

    private void userMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private Map<String, Object> userProduct(String code, String name, String price, String quantity, String category, String available) {
        Map<String, Object> product = new HashMap<>();

        product.put("Code", code);
        product.put("Name", name);
        product.put("Price", price);
        product.put("Quantity", quantity);
        product.put("Category", category);
        product.put("Available", available);

        return product;
    }

    private String productCategory() {
        if (jrbAppliance.isChecked()) {
            category = "Appliance";
        } else if (jrbTechnology.isChecked()) {
            category = "Technology";
        } else if (jrbHome.isChecked()) {
            category = "Home";
        } else {
            category = "Other";
        }

        return category;
    }

    private void productSaveDB(String nameDB, Map<String, Object> productDocument, String changeInstruction) {
        if (changeInstruction.equalsIgnoreCase("add")) {
            db.collection(nameDB)
                    .add(productDocument)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                            userMessage("Documento Adicionado");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.w("Error adding document", e);
                            userMessage("Documento NO Adicionado");
                        }
                    });
        } else {
            db.collection(nameDB)
                    .document(identDoc)
                    .set(productDocument)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                            userMessage("Documento Anulado Existosamente");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.w("Error adding document", e);
                            userMessage("Documento NO Anulado");
                        }
                    });
        }
    }

    public void productSave(View view) {
        String category;

        code = jetCode.getText().toString();
        name = jetName.getText().toString();
        price = jetPrice.getText().toString();
        quantity = jetQuantity.getText().toString();

        if (code.isEmpty() || name.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
            userMessage("Todos Los Campos Son Requeridos");
            jetCode.requestFocus();
        } else {
            /* Que Categoria Es El Producto */
            category = productCategory();

            // Create A New Product
            Map<String, Object> product = userProduct(code, name, price, quantity, category, "yes");

            // Add a new document with a generated ID
            productSaveDB("Stock", product, "add");
            cleanFields();
        }
    }

    public void productQuery(View view) {
        resulDB = false;
        code = jetCode.getText().toString();

        if (code.isEmpty()) {
            userMessage("El Codigo Es Requerido");
        } else {
            db.collection("Stock")
                    .whereEqualTo("Code", code)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document: task.getResult()) {
                                    resulDB = true;

                                    if (document.getString("Available").equalsIgnoreCase("no")) {
                                        userMessage("El Registro Existe, Pero, Esta Inactivo");
                                    } else {
                                        identDoc = document.getId();

                                        jetName.setText(document.getString("Name"));
                                        jetPrice.setText(document.getString("Price"));
                                        jetQuantity.setText(document.getString("Quantity"));

                                        if (document.getString("Category").equalsIgnoreCase("Appliance")) {
                                            jrbAppliance.setChecked(true);
                                        } else if (document.getString("Category").equalsIgnoreCase("Technology")) {
                                            jrbTechnology.setChecked(true);
                                        } else if (document.getString("Category").equalsIgnoreCase("Home")) {
                                            jrbHome.setChecked(true);
                                        } else {
                                            jrbOther.setChecked(true);
                                        }

                                        if (document.getString("Available").equalsIgnoreCase("yes")) {
                                            jcbAvailable.setChecked(true);
                                        } else {
                                            jcbAvailable.setChecked(false);
                                        }

                                        userMessage("Producto Encontrado Existosamente");
                                    }
                                }

                                if (!resulDB) {
                                    userMessage("Producto NO Encontrado");
                                }
                            } else {
                                userMessage("Tarea No Realizada Correctamente");
                            }
                        }
                    });
        }
    }

    public void productAnnul(View view) {
        if (!resulDB) {
            userMessage("Primero Debe Consultar, Si Existe El Producto");
        } else {
            String category;
            code = jetCode.getText().toString();
            name = jetName.getText().toString();
            price = jetPrice.getText().toString();
            quantity = jetQuantity.getText().toString();

            if (code.isEmpty() || name.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
                userMessage("Todos Los Campos Son Requeridos");
            } else {
                /* Que Categoria Es El Product */
                category = productCategory();

                // Create A Product
                Map<String, Object> product = userProduct(code, name, price, quantity, category, "no");

                // Annul A Product
                productSaveDB("Stock", product, "set");
                cleanFields();
            }
        }
    }

    public void productCancel(View view) {
        cleanFields();
    }

    private void cleanFields() {
        jetCode.setText("");
        jetName.setText("");
        jetPrice.setText("");
        jetQuantity.setText("");

        jrbAppliance.setChecked(true);
        jrbTechnology.setChecked(false);
        jrbHome.setChecked(false);
        jrbOther.setChecked(false);

        jcbAvailable.setChecked(false);
    }
}