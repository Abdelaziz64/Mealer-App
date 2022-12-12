package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Client extends AppCompatActivity {
    DatabaseReference databaseProducts = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mealerapp-a6e86-default-rtdb.firebaseio.com/");
    //        DatabaseReference databaseProducts;
    EditText editTextName;

    ListView listViewProducts;

    List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        //databaseProducts = FirebaseDatabase.getInstance().getReference("products");

       SearchView searchview= (SearchView) findViewById(R.id.searchview);
       searchview.clearFocus();
       searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String s) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String s) {
               fileList(s);
               return true;
           }

           private void fileList(String text) {
               List<Product> filtredlist=new ArrayList<>();
               for(Product item:products ){
                   if(item.getProductName().toLowerCase().contains(text.toLowerCase())){
                       filtredlist.add(item);
                   }
               }
               if(filtredlist.isEmpty()){
                   Toast.makeText(Client.this, "no data found", Toast.LENGTH_SHORT).show();
               }
               else{
                   ProductList productsAdapter = new ProductList(Client.this, filtredlist);
                   listViewProducts.setAdapter(productsAdapter);
               }
           }
       });
        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        products = new ArrayList<>();


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

    public void OnSet4(View view){
        Intent intent = new Intent(getApplicationContext(), Welcome.class);
        startActivityForResult(intent, 0);
    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseProducts.child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot postSnapShot : snapshot.getChildren()) {
                    String check= postSnapShot.child("isChecked").getValue(String.class);
                    if(check.equals("yes")){
                        Product product = new Product(postSnapShot.child("id").getValue(String.class),postSnapShot.child("name").getValue(String.class),postSnapShot.child("price").getValue(Integer.class));
                        products.add(product);

                    }

                    //Product product = postSnapShot.getValue();
                }
                ProductList productsAdapter = new ProductList(Client.this, products);
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
        final View dialogView = inflater.inflate(R.layout.buyproduct, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);


        dialogBuilder.setTitle(productName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }


    public void ichrak(View view){
        Intent intent = new Intent(getApplicationContext(), Plaintes.class);
        startActivityForResult(intent, 0);}
    public void ichrak2(View view){
        Intent intent = new Intent(getApplicationContext(), paymentform.class);
        startActivityForResult(intent, 0);}
    public void ichrak3(View view){
        Intent intent = new Intent(getApplicationContext(), Welcome.class);
        startActivityForResult(intent, 0);}
    public void anas4(View view){
        Intent intent = new Intent(getApplicationContext(), rating.class);
        startActivityForResult(intent, 0);
    }
}