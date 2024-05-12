package com.example.dapaactividad11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText item_id, item_description, item_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item_id = findViewById(R.id.item_id);
        item_description = findViewById(R.id.item_description);
        item_price = findViewById(R.id.item_price);
    }

    public void create(View view) {
        SQLTest admin = new SQLTest(this, "admin", null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String id = item_id.getText().toString();
        String description = item_description.getText().toString();
        String price = item_price.getText().toString();

        if (!id.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
            ContentValues item = new ContentValues();
            item.put("id",id);
            item.put("description",description);
            item.put("price",price);

            db.insert("products", null, item);

            db.close();

            item_id.setText("");
            item_description.setText("");
            item_price.setText("");

            Toast.makeText(this, "Registro Exitoso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Llena todos los campos para crear el producto", Toast.LENGTH_SHORT).show();
        }
    }

    public void search(View view) {
        SQLTest admin = new SQLTest(this, "admin", null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String id = item_id.getText().toString();
        String descrption = item_description.getText().toString();

        if (!id.isEmpty()) {
            Cursor row = db.rawQuery("select description, price from products where id = " + id, null);

            if (row.moveToFirst()) {
                item_description.setText(row.getString(0));
                item_price.setText((row.getString(1)));

            } else {
                Toast.makeText(this, "No se encontro producto con el codigo: " + id, Toast.LENGTH_SHORT).show();

            }

            db.close();

        } else if (!descrption.isEmpty()) {
            Cursor row = db.rawQuery("select id, price from products where description = \"" + descrption + "\"" , null);

            if (row.moveToFirst()) {
                item_id.setText(row.getString(0));
                item_price.setText((row.getString(1)));

            } else {
                Toast.makeText(this, "No se encontro producto con la descripcion: " + descrption, Toast.LENGTH_SHORT).show();

            }

            db.close();
        } else {
            Toast.makeText(this, "Debes escribir el codgio o descripcion para buscar el producto correspondiente ", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View view) {
        SQLTest admin = new SQLTest(this, "admin", null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String id = item_id.getText().toString();

        if (!id.isEmpty()) {
            int row = db.delete("products", "id = " + id, null);
            db.close();

            item_id.setText("");
            item_description.setText("");
            item_price.setText("");

            if (row == 1) {
                Toast.makeText(this, "Producto Eliminado Correctamente! ", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "No se encontro producto con el codigo: " + id, Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(this, "Debes escribir el codgio para eliminar el producto correspondiente ", Toast.LENGTH_SHORT).show();
        }
    }

    public void edit(View view) {
        SQLTest admin = new SQLTest(this, "admin", null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String id = item_id.getText().toString();
        String description = item_description.getText().toString();
        String price = item_price.getText().toString();

        Log.d("test","here");

        if (!id.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
            ContentValues item = new ContentValues();
            item.put("id",id);
            item.put("description",description);
            item.put("price",price);

            int row = db.update("products", item, "id = " + id, null);

            db.close();

            if (row == 1) {
                Toast.makeText(this, "Producto Editado Correctamente! ", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "No se encontro producto con el codigo: " + id, Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(this, "Llena todos los campos para modificar el producto correspondiente ", Toast.LENGTH_SHORT).show();
        }
    }
}