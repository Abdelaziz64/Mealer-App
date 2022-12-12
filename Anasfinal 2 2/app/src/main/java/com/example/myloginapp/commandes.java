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

public class commandes extends AppCompatActivity {
    DatabaseReference databaseProducts = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mealerapp-a6e86-default-rtdb.firebaseio.com/");
    //        DatabaseReference databaseProducts;
    EditText editTextName;

    ListView listViewProducts;

    List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commandes);
        final Button logout = findViewById(R.id.logout1);
        //databaseProducts = FirebaseDatabase.getInstance().getReference("products");
        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        products = new ArrayList<>();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), cook.class);
                startActivityForResult(intent, 0);
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
        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                products.clear();
               Long commandes=snapshot.child("commande").getChildrenCount();
               int commande1=commandes.intValue();
               int c=0;
                for (DataSnapshot postSnapShot : snapshot.child("products").getChildren()){
                    if (c==commande1){
                        break;
                    }
                    Product product = new Product(postSnapShot.child("id").getValue(String.class),postSnapShot.child("name").getValue(String.class),postSnapShot.child("price").getValue(Integer.class));
                    products.add(product);
                    c+=1;

                }

                ProductList productsAdapter = new ProductList(commandes.this, products);
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
        final View dialogView = inflater.inflate(R.layout.acceptrejectorder, null);
        dialogBuilder.setView(dialogView);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);
        final Button cancel = (Button) dialogView.findViewById(R.id.button);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseProducts.child("commande").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String futureUID = "";
                            for(DataSnapshot futureUIDdatasnapshot:snapshot.getChildren() ){
                                futureUID = futureUIDdatasnapshot.getKey();
                                break;}
                            databaseProducts.child("commande").child(futureUID).removeValue();
                            Intent intent = new Intent(getApplicationContext(), commandes.class);
                            startActivityForResult(intent, 0);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

       buttonDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               databaseProducts.child("commande").addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if (snapshot.exists()) {
                           String futureUID = "";
                           for(DataSnapshot futureUIDdatasnapshot:snapshot.getChildren() ){
                               futureUID = futureUIDdatasnapshot.getKey();
                               break;}
                           databaseProducts.child("commande").child(futureUID).removeValue();
                           Intent intent = new Intent(getApplicationContext(), commandes.class);
                           startActivityForResult(intent, 0);

                       }


                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
           }
       });
       cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(), commandes.class);
               startActivityForResult(intent, 0);
           }
       });

        dialogBuilder.setTitle(productName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


    }


    public void ichrak4(View view){
        Intent intent = new Intent(getApplicationContext(), content1.class);
        startActivityForResult(intent, 0);}
    public void ichrak8(View view){
        Intent intent = new Intent(getApplicationContext(), cook.class);
        startActivityForResult(intent, 0);}

}

