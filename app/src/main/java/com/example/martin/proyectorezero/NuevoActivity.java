package com.example.martin.proyectorezero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NuevoActivity extends Activity {

    TextView lbl1;
    EditText edt1, edt2;
    Button btn1, btn2;

    String contenido = "";
    String codigo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_2);

        lbl1 = (TextView) findViewById(R.id.lblEditable);

        edt1 = (EditText) findViewById(R.id.editText);
        edt2 = (EditText) findViewById(R.id.editText2);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        Intent intentrecibido = getIntent();

        String mensajeResivido = intentrecibido.getStringExtra("TipoAccion");


        ValidarAccion(mensajeResivido, intentrecibido);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Salir();

            }
        });


    }

    public void ValidarAccion(String mensajeResivido, Intent intent) {

        if (mensajeResivido.equals("modificar")) {

            lbl1.setText("ESTA PANTALLA SERA PARA MODIFICAR");

            edt1.setHint(intent.getStringExtra("Codigo") + "-> :V");
            edt2.setText(" ");
            edt2.setFocusable(true);


            codigo = intent.getStringExtra("Codigo");


            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //aun no puedo capturar el valor del campo edt2 , solucionar ....
                    metodo2(codigo, "");
                }
            });


        } else {

            lbl1.setText("ESTA PANTALLA ES PARA GUARDAR");
            edt1.setHint("Campo inhabilitado....");
            edt2.setFocusable(true);


            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    metodo1();
                }
            });

        }
    }

    public void Salir() {

        Intent intent = new Intent(NuevoActivity.this, MainActivity.class);
        startActivity(intent);

    }

    public void metodo1() {
        Toast.makeText(getApplicationContext(), "HOLA GUARDAR", Toast.LENGTH_SHORT).show();
    }

    public void metodo2(String codigo, String contenidocampo) {
        Toast.makeText(getApplicationContext(), "LOS DATOS SON :" + codigo + " y " + contenidocampo, Toast.LENGTH_SHORT).show();

    }

}
