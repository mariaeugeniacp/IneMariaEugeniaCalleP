package com.mauge.carritocompras_maeugeniacalle;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsuario;
    private EditText txtPassword;
    private Button btnEnviar;
    private Button btnRegistrarse;


    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Donde se ejecuta la App
        context = this;

        //Comentado porque no necesitamos el action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Vinculamos las variables con los IDs de la interfaz
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);


        //Evento de click en el botón
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 SQLiteDatabase db;
                 int VERSION = 1;

                Base_datos crearBD = new Base_datos(context,VERSION);
                db = crearBD.getWritableDatabase();

                String campo_usuario = txtUsuario.getText().toString();
                String campo_password = txtPassword.getText().toString();

                if(campo_usuario.isEmpty() || campo_password.isEmpty() )
                {
                    Toast.makeText(context, "Por favor ingrese el usuario y contraseña.", Toast.LENGTH_SHORT).show();
                }
                else{
                    String[] args = new String[] {campo_usuario,campo_password};

                    Cursor clientesregistrados=db.rawQuery("SELECT idcliente,nombre FROM clientes WHERE usuario=? and contrasena=?", args);
                    if(clientesregistrados.moveToFirst())
                    {
                        Intent a = new Intent(context, MenuPrincipal.class);

                        String[] datos = new String[1];
                        datos[0] = clientesregistrados.getString(0);
                       // datos[1] = txtPassword.getText().toString();
                        a.putExtra("datos_usuario", datos);
                        Toast.makeText(context, "Bienvenido "+clientesregistrados.getString(1).toString(), Toast.LENGTH_SHORT).show();
                        startActivity(a);
                    }
                    else {
                        Toast.makeText(context, "Usuario no registrado. ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        //Evento de click en el botón
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent b = new Intent(context, RegistroCliente.class);
                startActivity(b);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
