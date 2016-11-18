package com.example.martin.proyectorezero;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NuevoActivity extends Activity {

    TextView lbl1;
    EditText edt1, edt2;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_2);

        lbl1 = (TextView) findViewById(R.id.lblEditable);

        edt1 = (EditText) findViewById(R.id.editText);
        edt2 = (EditText) findViewById(R.id.editText2);

        String mensajeResivido = "modif";

        if (mensajeResivido.equals("modificar")) {

            lbl1.setText("ESTA PANTALLA SERA PARA MODIFICAR");

        } else {

            lbl1.setText("ESTA PANTALLA ES PARA GUARDAR");

        }

    }
}
