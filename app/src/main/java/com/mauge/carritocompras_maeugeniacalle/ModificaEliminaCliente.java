package com.mauge.carritocompras_maeugeniacalle;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Mauge on 16/04/2016.
 */
public class ModificaEliminaCliente extends AppCompatActivity {

    private EditText nombre;
    private EditText usuario;
    private EditText contraseña;
    private EditText correo;
    private Button btnmodificar;
    private Button btneliminar;

    private String idcliente;

    private SQLiteDatabase db;
    public static final int VERSION = 1;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificaelimina_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Recibimos los datos
        Intent b=getIntent();
         String[] datos_recibidos=new String[1];
         datos_recibidos=b.getStringArrayExtra("datos_usuario");
        idcliente=datos_recibidos[0];

         context=this;

        Base_datos crearBD = new Base_datos(context,VERSION);
        db = crearBD.getWritableDatabase();
        String[] args = new String[] {idcliente};

        Cursor clientesregistrados=db.rawQuery("SELECT idcliente,nombre,usuario,contrasena,correo FROM clientes WHERE idcliente=?", args);
        if(clientesregistrados.moveToFirst())
        {
           // idcliente=clientesregistrados.getString(0);
            nombre=(EditText)findViewById(R.id.nombre);
            usuario=(EditText)findViewById(R.id.usuario);
            contraseña=(EditText)findViewById(R.id.contraseña);
            correo=(EditText)findViewById(R.id.correo );
            btnmodificar=(Button)findViewById(R.id.btnmodificar);
            btneliminar=(Button)findViewById(R.id.btneliminar);

            nombre.setText(clientesregistrados.getString(1));
            usuario.setText(clientesregistrados.getString(2));
            contraseña.setText(clientesregistrados.getString(3));
            correo.setText(clientesregistrados.getString(4));
       }

        btnmodificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(nombre.getText().toString().isEmpty() || usuario.getText().toString().isEmpty()|| contraseña.getText().toString().isEmpty()|| correo.getText().toString().isEmpty() )
                {
                    Toast.makeText(context, "Todos los datos son obligatorios.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ContentValues values = new ContentValues();
                    values.put("nombre",nombre.getText().toString());
                    values.put("usuario",usuario.getText().toString());
                    values.put("contrasena",contraseña.getText().toString());
                    values.put("correo", correo.getText().toString());
                    String[] args=new String[]{idcliente};
                    db.update("clientes", values, "idcliente LIKE ?", args);
                    db.close();

                    Intent a = new Intent(context, MenuPrincipal.class);

                    String[] datos = new String[2];
                    datos[0] =usuario.getText().toString();
                    datos[1] =contraseña.getText().toString();
                    a.putExtra("datos_usuario", datos);
                    finish();
                    startActivity(a);

                }
            }
        });
        btneliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(context, "Su cuenta fue eliminada :(", Toast.LENGTH_SHORT).show();

                String[] args = new String[]{idcliente};
                db.delete("clientes", "idcliente LIKE ?", args);
                db.close();

                Intent a = new Intent(context, MainActivity.class);
                finish();
                startActivity(a);
            }
        });
    }

    private void reiniciarActividad() {
        Intent a=new Intent(context,RegistroCliente.class);
        // finish();
        startActivity(a);
    }
}

