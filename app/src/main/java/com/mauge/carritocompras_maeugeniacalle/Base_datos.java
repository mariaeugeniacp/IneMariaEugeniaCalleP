package com.mauge.carritocompras_maeugeniacalle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by andresvasquez on 11/4/16.
 */
public class Base_datos extends SQLiteOpenHelper
{
    public static final String NOMBREBD = "carrito_compras.sqlite";
    //Versión de la base de datos
    public Base_datos(Context context, int VERSION)
    {
        super(context, NOMBREBD, null, VERSION);
    }

   // String sqlCreate = "CREATE TABLE pedidos (idpedido integer autoincrement not null,idcliente integer,idproducto integer, precio double,cantidad integer";
           // ,primary key(idpedido,idcliente,idproducto));";

    //Método utilizado cuando se crea la base de datos.
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table clientes (idcliente integer primary key autoincrement not null, nombre varchar,usuario varchar,contrasena varchar,correo varchar );");
        db.execSQL("create table productos (idproducto integer primary key autoincrement not null, producto varchar, precio double);");
        //db.execSQL("create table pedidos (idpedido integer primary key autoincrement not null,idcliente integer,idproducto integer, precio double,cantidad integer);");
        //db.execSQL(sqlCreate);
        db.execSQL("CREATE TABLE pedidos (idpedido integer not null,idcliente integer,idproducto integer, precio double,cantidad integer,primary key(idpedido,idcliente,idproducto));");
        Log.d("Todas las tablas: ", "Se crearon las tablas");
    }

    //Método utilizado cuando se actualiza la base de datos
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Se elimina la versión anterior de la tabla
       // db.execSQL("DROP TABLE IF EXISTS pedidos");
        //Se crea la nueva versión de la tabla
        //db.execSQL(sqlCreate);

    }

}