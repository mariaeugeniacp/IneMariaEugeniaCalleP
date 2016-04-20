package com.mauge.carritocompras_maeugeniacalle;

/**
 * Created by Mauge on 16/04/2016.
 */
import android.app.ListActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class RegistroPedido extends AppCompatActivity{
//public class EjemploActivity1 extends Activity {
    private ListView listado;
    private Button btnconfirmar;
    private Button btnlimpiar;
    private Button btncancelar;

    private String idcliente;
    private Integer idpedido;

    private Context context;
    String[] nombres=new String[]{};

    private SQLiteDatabase db;
    public static final int VERSION = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_registro_pedido);

        btnconfirmar=(Button)findViewById(R.id.btnconfirmar);
        btnlimpiar=(Button)findViewById(R.id.btnlimpiar);
        btncancelar=(Button)findViewById(R.id.btncancelar);

        //Recibimos los datos
        Intent b=getIntent();
        String[] datos_recibidos=new String[1];
        datos_recibidos=b.getStringArrayExtra("datos_usuario");

        idcliente = datos_recibidos[0];

        //Verifica el numero de pedido

        context=this;

       Base_datos crearBD = new Base_datos(context,VERSION);
       db = crearBD.getWritableDatabase();

        listado = (ListView)findViewById(R.id.lista);
        //poner mi lista en modo de selección múltiple
        //con esto, al hacer click en los elementos se seleccionarán automáticamente
        listado.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        //Carga datos de productos

        Cursor cursor=db.rawQuery("SELECT idproducto as _id,producto,precio FROM productos order by producto", null);

        String[] desdeEstasColumnas = {"_id", "producto", "precio"};
        int[] aEstasViews = {R.id.id, R.id.producto, R.id.precio};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.fila_producto, cursor, desdeEstasColumnas, aEstasViews, 0);

        listado.setAdapter(adapter);

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                               // Hacer algo cuando un elemento de la lista es seleccionado
                                               TextView textoTitulo = (TextView) view.findViewById(R.id.producto);

                                               CharSequence texto = "Seleccionado: " + textoTitulo.getText();
                                               Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG).show();
                                           }
                                       }

        );

        btnconfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Obtiene los elementos seleccionados de mi lista
                SparseBooleanArray seleccionados = listado.getCheckedItemPositions();

                if(seleccionados==null || seleccionados.size()==0){
                    //Si no había elementos seleccionados...
                    Toast.makeText(context,"No hay elementos seleccionados",Toast.LENGTH_SHORT).show();
                }else{
                    //si los había, miro sus valores

                    //Esto es para ir creando un mensaje largo que mostraré al final
                    StringBuilder resultado=new StringBuilder();
                    resultado.append("Se eliminarán los siguientes elementos:\n");

                    //Recorro my "array" de elementos seleccionados
                    final int size=seleccionados.size();
                    for (int i=0; i<size; i++) {
                        //Si valueAt(i) es true, es que estaba seleccionado
                        if (seleccionados.valueAt(i)) {
                            //en keyAt(i) obtengo su posición
                            resultado.append("El elemento "+seleccionados.keyAt(i)+" estaba seleccionado\n");
                        }
                    }
                    Toast.makeText(context,resultado.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void deleteSelected(View view) {
        //Obtiene los elementos seleccionados de mi lista
        SparseBooleanArray seleccionados = listado.getCheckedItemPositions();

        if(seleccionados==null || seleccionados.size()==0){
            //Si no había elementos seleccionados...
            Toast.makeText(this,"No hay elementos seleccionados",Toast.LENGTH_SHORT).show();
        }else{
            //si los había, miro sus valores

            //Esto es para ir creando un mensaje largo que mostraré al final
            StringBuilder resultado=new StringBuilder();
            resultado.append("Se eliminarán los siguientes elementos:\n");

            //Recorro my "array" de elementos seleccionados
            final int size=seleccionados.size();
            for (int i=0; i<size; i++) {
                //Si valueAt(i) es true, es que estaba seleccionado
                if (seleccionados.valueAt(i)) {
                    //en keyAt(i) obtengo su posición
                    resultado.append("El elemento "+seleccionados.keyAt(i)+" estaba seleccionado\n");
                }
            }
            Toast.makeText(this,resultado.toString(),Toast.LENGTH_LONG).show();
        }
    }
}