package com.mauge.carritocompras_maeugeniacalle;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Mauge on 16/04/2016.
 */

public class Productos extends AppCompatActivity {

    private EditText producto;
    private EditText precio;
    private Button guardar;
    private Button finalizar;
    private TableLayout tabla;
    private TableRow fila;
    TableRow.LayoutParams layoutFila;
    private String idcliente;
    //private String campo_password;

    private SQLiteDatabase db;
    public static final int VERSION = 1;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context=this;

        Base_datos crearBD = new Base_datos(context,VERSION);
        db = crearBD.getWritableDatabase();

        producto=(EditText)findViewById(R.id.producto);
        precio=(EditText)findViewById(R.id.precio);
        guardar=(Button)findViewById(R.id.enviar);
        tabla=(TableLayout)findViewById(R.id.tabla);
        finalizar=(Button)findViewById(R.id.btnfinalizarprod);

        layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        //Recibimos los datos
        Intent b=getIntent();
        String[] datos_recibidos=new String[1];
        datos_recibidos=b.getStringArrayExtra("datos_usuario");

        idcliente = datos_recibidos[0];
        //campo_password= datos_recibidos[1];

        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            { if(producto.getText().toString().isEmpty()|| precio.getText().toString().isEmpty())
                {
                    Toast.makeText(context, "Nombre y precio del producto son obligatorios.", Toast.LENGTH_SHORT).show();
                }
                    else
                {
                    ContentValues values = new ContentValues();
                    values.put("producto",producto.getText().toString());
                    values.put("precio",precio.getText().toString());
                    db.insert("productos", null, values);
                    db.close();

                    Toast.makeText(getApplicationContext(), "Producto Agregado", Toast.LENGTH_SHORT).show();
                    reiniciarActividad();
                }

            }
        });

        finalizar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick( View v)
            {
                Intent b = new Intent(context, MenuPrincipal.class);
                finish();
                String[] datos = new String[1];
                datos[0] =idcliente.toString();
                //datos[1] =campo_password.toString();
                b.putExtra("datos_usuario", datos);
                startActivity(b);
            }

        });

        agregarFilas("Producto", "Precio","0");
        Cursor productos_existentes=db.rawQuery("SELECT idproducto,producto,precio FROM productos", null);
        if(productos_existentes.moveToFirst())
        {
            do{
                agregarFilas(productos_existentes.getString(1),productos_existentes.getString(2),productos_existentes.getString(0));
            }while(productos_existentes.moveToNext());
        }

    }

    private void reiniciarActividad() {
        Intent a=new Intent(getApplicationContext(),Productos.class);
        finish();
        startActivity(a);
    }

    private void agregarFilas(String prod,String precio1,String id)
    {
        fila=new TableRow(this);
        fila.setLayoutParams(layoutFila);

        TextView nombre_producto=new TextView(this);
        TextView precio_producto=new TextView(this);

        nombre_producto.setText(prod);
        nombre_producto.setBackgroundResource(R.drawable.celda_cuerpo);

        precio_producto.setText(precio1);
        precio_producto.setBackgroundResource(R.drawable.celda_cuerpo);
        precio_producto.setGravity(Gravity.RIGHT);

        nombre_producto.setLayoutParams(new TableRow.LayoutParams(0,
                TableRow.LayoutParams.MATCH_PARENT, 5));
        precio_producto.setLayoutParams(new TableRow.LayoutParams(0,
                TableRow.LayoutParams.MATCH_PARENT, 5));

        fila.addView(nombre_producto);
        fila.addView(precio_producto);


        if(id.compareTo("0")!=0)
        {
            ImageView editar=new ImageView(this);
            ImageView eliminar=new ImageView(this);

            editar.setId(Integer.parseInt(id));
            editar.setAdjustViewBounds(true);
            editar.setBackgroundResource(R.drawable.celda_cuerpo);
            editar.setImageResource(R.drawable.refresh);
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nomproda="";
                    String precioproda="0.00";
                    String nomprodr="";
                    String precioprodr="0.00";

                    String[] argss = new String[] {""+view.getId()};

                    Cursor productosregistrados=db.rawQuery("SELECT producto,precio FROM productos WHERE idproducto=?", argss);
                    if(productosregistrados.moveToFirst())
                    {
                        nomprodr=productosregistrados.getString(0);
                        precioprodr=productosregistrados.getString(1);
                    }
                    if(producto.getText().toString().isEmpty())
                    {
                        nomproda=nomprodr.toString();
                    }
                    else
                    {
                        nomproda=producto.getText().toString();
                    }
                    if( precio.getText().toString().isEmpty())
                    {
                        precioproda=precioprodr.toString();
                    }
                    else
                    {
                        precioproda=precio.getText().toString();
                    }
                    Toast.makeText(context,"Actualizando "+view.getId(),Toast.LENGTH_SHORT).show();
                    ContentValues values = new ContentValues();
                    values.put("producto",nomproda.toString());
                    values.put("precio",precioproda.toString());

                    String[] args=new String[]{""+view.getId()};
                    db.update("productos",values, "idproducto LIKE ?", args);
                    reiniciarActividad();


                }
            });

            eliminar.setId(Integer.parseInt(id));
            eliminar.setAdjustViewBounds(true);
            eliminar.setBackgroundResource(R.drawable.celda_cuerpo);
            eliminar.setImageResource(R.drawable.close);
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Eliminando "+view.getId(),Toast.LENGTH_SHORT).show();
                    String[] args=new String[]{""+view.getId()};
                    db.delete("Productos", "idproducto LIKE ?", args);
                    reiniciarActividad();
                }
            });


            editar.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));
            eliminar.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));

            fila.addView(editar);
            fila.addView(eliminar);
        }
        else
        {
            TextView vacio = new TextView(this);
            vacio.setText("Acción");
            vacio.setBackgroundResource(R.drawable.celda_cuerpo);
            vacio.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 2));
            fila.addView(vacio
            );
        }

        tabla.addView(fila);
    }
}