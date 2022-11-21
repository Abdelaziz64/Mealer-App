package com.example.myloginapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class content1 extends AppCompatActivity {
    DatabaseReference databaseProducts = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mealerapp-a6e86-default-rtdb.firebaseio.com/");
//        DatabaseReference databaseProducts;
        EditText editTextName;
        EditText editTextPrice;
        Button buttonAddProduct;
        ListView listViewProducts;

        List<Product> products;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_content1);

            //databaseProducts = FirebaseDatabase.getInstance().getReference("products");

            editTextName = (EditText) findViewById(R.id.editTextName);
            editTextPrice = (EditText) findViewById(R.id.editTextPrice);
            listViewProducts = (ListView) findViewById(R.id.listViewProducts);
            buttonAddProduct = (Button) findViewById(R.id.addButton);

            products = new ArrayList<>();

            //adding an onclicklistener to button
            buttonAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addProduct();
                }
            });

            listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println(i);
                    Product product = products.get(i);
                    System.out.println(product);
                    System.out.println(product.getId());

                    showUpdateDeleteDialog(product.getId(), product.getProductName());
                    return true;
                }
            });

        }


        @Override
        protected void onStart() {
            super.onStart();
            databaseProducts.child("products").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    products.clear();
                    for (DataSnapshot postSnapShot : snapshot.getChildren()) {

                        if(postSnapShot.child("price").getValue(Integer.class) != null){
                            Product product = new Product(postSnapShot.child("id").getValue(String.class),postSnapShot.child("name").getValue(String.class),postSnapShot.child("price").getValue(Integer.class));
                            products.add(product);

                        }

                       //Product product = postSnapShot.getValue();
                    }
                    ProductList productsAdapter = new ProductList(content1.this, products);
                    listViewProducts.setAdapter(productsAdapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        private void showUpdateDeleteDialog( String productId, String productName) {
            System.out.println(productId);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.update_dialogue, null);
            dialogBuilder.setView(dialogView);

            final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
            final EditText editTextPrice  = (EditText) dialogView.findViewById(R.id.editTextPrice);
            final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
            final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

            dialogBuilder.setTitle(productName);
            final AlertDialog b = dialogBuilder.create();
            b.show();

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = editTextName.getText().toString().trim();
                    int price = Integer.parseInt(String.valueOf(editTextPrice.getText().toString()));
                    if(name.isEmpty()){
                        Toast.makeText(content1.this, "Please enter the name of the product", Toast.LENGTH_SHORT).show();
                    }
                    if(String.valueOf(editTextPrice.getText().toString()).isEmpty()){
                        Toast.makeText(content1.this, "Please enter the price of the product", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        updateProduct(productId, name, price);
                        b.dismiss();

                    }
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteProduct(productId);
                    b.dismiss();
                }
            });
        }

        private void updateProduct(String id, String name, int price) {
            databaseProducts.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    databaseProducts.child("products").child(id).child("name").setValue(name);
                    databaseProducts.child("products").child(id).child("price").setValue(price);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_LONG).show();
        }

        private void deleteProduct(String id) {
           databaseProducts.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   databaseProducts.child("products").child(id).removeValue();
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });

            Toast.makeText(getApplicationContext(), "Product deleted", Toast.LENGTH_LONG).show();


        }

        private void addProduct() {
            String name = editTextName.getText().toString().trim();
            int price = Integer.parseInt(editTextPrice.getText().toString());


            if (!TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();

                String id = databaseProducts.push().getKey();

                Product product = new Product(id, name, price);

                products.add(product);
                databaseProducts.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseProducts.child("products").child(id).child("name").setValue(product.getProductName());
                        databaseProducts.child("products").child(id).child("price").setValue(product.getPrice());
                        databaseProducts.child("products").child(id).child("id").setValue(product.getId());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                editTextName.setText("");
                editTextPrice.setText("");

            } else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            }

        }
    }

