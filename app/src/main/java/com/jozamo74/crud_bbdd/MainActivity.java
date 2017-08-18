package com.jozamo74.crud_bbdd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.jozamo74.crud_bbdd.R.id.apellidos;
import static com.jozamo74.crud_bbdd.R.id.nombre;


public class MainActivity extends AppCompatActivity {

    Button botonInsertar, botonActualizar, botonBorrar, botonBuscar;
    EditText textoID, textoNombre, textoApellidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonInsertar = (Button) findViewById(R.id.insertar);
        botonActualizar = (Button) findViewById(R.id.actualizar);
        botonBorrar = (Button) findViewById(R.id.borrar);
        botonBuscar = (Button) findViewById(R.id.buscar);

        textoID = (EditText) findViewById(R.id.id);
        textoNombre = (EditText) findViewById(nombre);
        textoApellidos = (EditText) findViewById(apellidos);

        final BBDD_Helper helper = new BBDD_Helper(this);


        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gets the data repository in write mode
                SQLiteDatabase db = helper.getWritableDatabase();


                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.CAMPO1, textoID.getText().toString());
                values.put(Estructura_BBDD.CAMPO2, textoNombre.getText().toString());
                values.put(Estructura_BBDD.CAMPO3, textoApellidos.getText().toString());

// Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(Estructura_BBDD.TABLE_NAME, null, values);

                Toast.makeText(getApplicationContext(), "Se guardo el regitro con clave: " +
                        newRowId, Toast.LENGTH_LONG).show();
                textoID.setText("");
                textoNombre.setText("");
                textoApellidos.setText("");


            }
        });

        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = helper.getReadableDatabase();
                String id = textoID.getText().toString();
                String nombre = textoNombre.getText().toString();
                String apellidos = textoApellidos.getText().toString();
                // if(!id.isEmpty() && (!nombre.isEmpty() || !apellidos.isEmpty()) ){
                // New value for one column
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.CAMPO2, textoNombre.getText().toString());
                values.put(Estructura_BBDD.CAMPO3, textoApellidos.getText().toString());

                // Which row to update, based on the title
                String selection = Estructura_BBDD.CAMPO1 + " LIKE ?";
                String[] selectionArgs = {textoID.getText().toString()};

                int count = db.update(
                        Estructura_BBDD.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                if (count > 0)

                    Toast.makeText(getApplicationContext(), "Registro Actualizado", Toast.LENGTH_LONG).show();

                else
                    Toast.makeText(getApplicationContext(), "Error al actualizar registro", Toast.LENGTH_LONG).show();

            }
        });


        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gets the data repository in write mode
                SQLiteDatabase db = helper.getWritableDatabase();



                // Define 'where' part of query.
                String selection = Estructura_BBDD.CAMPO1 + " LIKE ?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = {textoID.getText().toString()};

               // Issue SQL statement.
                if (db.delete(Estructura_BBDD.TABLE_NAME, selection, selectionArgs) > 0) {
                    Toast.makeText(getApplicationContext(), "Registro borrado", Toast.LENGTH_SHORT).show();
                    textoID.setText("");
                    textoNombre.setText("");
                    textoApellidos.setText("");

                }else
                    Toast.makeText(getApplicationContext(), "Registro no existe", Toast.LENGTH_SHORT).show();
            }


        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = helper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
                String[] projection = {
                        Estructura_BBDD.CAMPO2,
                        Estructura_BBDD.CAMPO3
                };

// Filter results WHERE "title" = 'My Title'
                String selection = Estructura_BBDD.CAMPO1 + " = ?";
                String[] selectionArgs = {textoID.getText().toString()};

// How you want the results sorted in the resulting Cursor
              /*  String sortOrder =
                        Estructura_BBDD.COLUMN_NAME_SUBTITLE + " DESC"; */

              try {

                  Cursor cursor = db.query(
                          Estructura_BBDD.TABLE_NAME,                     // The table to query
                          projection,                               // The columns to return
                          selection,                                // The columns for the WHERE clause
                          selectionArgs,                            // The values for the WHERE clause
                          null,                                     // don't group the rows
                          null,                                     // don't filter by row groups
                          null                                     // The sort order
                  );

                  cursor.moveToFirst();

                  textoNombre.setText(cursor.getString(0));
                  textoApellidos.setText(cursor.getString(1));
              }catch (Exception e){
                  Toast.makeText(getApplicationContext(),"Registro no encontrado", Toast.LENGTH_LONG).show();
              }


            }
        });
    }
}
