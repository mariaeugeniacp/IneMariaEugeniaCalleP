package com.mauge.carritocompras_maeugeniacalle;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuPrincipal extends AppCompatActivity {

    private Button btncarrito;
    private Button btncuenta;
    private Button btnorden;
    private Button btnreporte;
    private Button btnproducto;
    private Button btnsalir;
    private String idcliente;

    private SQLiteDatabase db;
    public static final int VERSION = 1;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context=this;

        Base_datos crearBD = new Base_datos(context,VERSION);
        db = crearBD.getWritableDatabase();

        btncarrito=(Button)findViewById(R.id.btncarrito);
        btncuenta=(Button)findViewById(R.id.btncuenta);
        btnorden=(Button)findViewById(R.id.btnorden);
        btnreporte=(Button)findViewById(R.id.btnreporte);
        btnproducto=(Button)findViewById(R.id.btnproducto);
        btnsalir=(Button)findViewById(R.id.btnsalir);

        //Recibimos los datos
        Intent b=getIntent();
        String[] datos_recibidos=new String[1];
        datos_recibidos=b.getStringArrayExtra("datos_usuario");

        idcliente = datos_recibidos[0];

        btncarrito.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent b = new Intent(context, RegistroPedido.class);
                String[] datos = new String[1];
                datos[0] = idcliente.toString();
                b.putExtra("datos_usuario", datos);
                startActivity(b);
            }
        });
        btncuenta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent b = new Intent(context, ModificaEliminaCliente.class);
                String[] datos = new String[1];
                datos[0] = idcliente.toString();
                b.putExtra("datos_usuario", datos);
                startActivity(b);
            }
        });
        btnorden.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent b = new Intent(context, PagoOrden.class);
                //startActivity(b);
            }
        });
        btnreporte.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Intent b = new Intent(context, ListaVentas.class);
               // startActivity(b);
            }
        });
        btnproducto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent b = new Intent(context, Productos.class);
                String[] datos = new String[1];
                datos[0] = idcliente.toString();
                b.putExtra("datos_usuario", datos);
                startActivity(b);
            }
        });
        btnsalir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


    }

}
