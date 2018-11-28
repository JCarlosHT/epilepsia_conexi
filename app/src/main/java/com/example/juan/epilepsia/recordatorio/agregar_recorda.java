package com.example.juan.epilepsia.recordatorio;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.juan.epilepsia.R;
import com.example.juan.epilepsia.Reporte.reporte;
import com.example.juan.epilepsia.Bitacora.bitacora;
import com.example.juan.epilepsia.datos_contacto;
import com.example.juan.epilepsia.sqlite.ConexionSQLite;
import com.example.juan.epilepsia.ventanas.menu_epi;

import java.util.Calendar;

import static android.widget.Toast.LENGTH_LONG;

public class agregar_recorda extends AppCompatActivity {
    Button []botones=new Button[2];//botones de hora y fecha
    TextView fecha,hora_show;
    EditText medi_to,dosis_de,otros;
    String []datos_reco=new String[5];
    public int[]datos_fecha_hor=new int [5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_recorda);
        botones[0]=(Button)findViewById(R.id.Fecha_IR);
        botones[1]=(Button)findViewById(R.id.Hora);
        fecha=(TextView) findViewById(R.id.Fecha_tex);
        hora_show=(TextView) findViewById(R.id.Hora_tex);
        medi_to=(EditText)findViewById(R.id.medicina);
        dosis_de=(EditText)findViewById(R.id.dosis);
        otros=(EditText)findViewById(R.id.otros_datos);
        botones[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecha();
            }
        });
        botones[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora();
            }
        });
    }

    public void Agregar_recodatorio(View view) {
        if (fecha.getText().toString().equals("Fecha")&&hora_show.getText().toString().equals("Hora")){
            Toast.makeText(getApplicationContext(), "fecha o hora no seleccionada", LENGTH_LONG).show();

        }else{
            if (TextUtils.isEmpty(medi_to.getText())&&TextUtils.isEmpty(dosis_de.getText())&&TextUtils.isEmpty(otros.getText())){
                Toast.makeText(getApplicationContext(), "datos no completos", LENGTH_LONG).show();
            }else{
                datos_contacto.sqLite= new ConexionSQLite(this);
                datos_reco[0]=medi_to.getText().toString();
                datos_reco[1]=dosis_de.getText().toString();
                datos_reco[2]=fecha.getText().toString();
                datos_reco[3]=hora_show.getText().toString();
                datos_reco[4]=otros.getText().toString();
                if(addrecor()){
                    Intent reco = new Intent(this,recordatorio.class);
                    startActivity(reco);
                }
            }
        }
    }

    private boolean addrecor(){
        String sql="insert into Recordatorio(medicina,dosis,fecha,hora,otros)values('"+datos_reco[0]+"','"+datos_reco[1]+"','"+datos_reco[2]+"','"+datos_reco[3]+"','"+datos_reco[4]+"');";
        Log.e("valor","cadena: "+sql);
        return datos_contacto.sqLite.ejecutaSQL(sql);
    }

    public void fecha(){
        final Calendar calen=Calendar.getInstance();
        datos_fecha_hor[0]=calen.get(Calendar.DAY_OF_MONTH);
        datos_fecha_hor[1]=calen.get(Calendar.MONTH)+1;
        datos_fecha_hor[2]=calen.get(Calendar.YEAR);
        DatePickerDialog calenda= new DatePickerDialog(this,R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int mes, int dia) {
                fecha.setText(year+"/"+mes+"/"+dia);
            }
        },datos_fecha_hor[2],datos_fecha_hor[1],datos_fecha_hor[0]);
        calenda.show();
    }

    public  void hora(){
        final Calendar calen=Calendar.getInstance();
        datos_fecha_hor[3]=calen.get(Calendar.HOUR_OF_DAY);
        datos_fecha_hor[4]=calen.get(Calendar.MINUTE);
        TimePickerDialog Hora= new TimePickerDialog(this, R.style.datepicker,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
                hora_show.setText(hora+":"+minuto);
            }
        }, datos_fecha_hor[3], datos_fecha_hor[3],false);
        Hora.show();
    }

    public void inicio_EB(View view) {
        Intent inicio = new Intent(this, menu_epi.class);
        startActivity(inicio);
    }

    public void Bita_EB(View view) {
        Intent bita = new Intent(this, bitacora.class);
        startActivity(bita);
    }

    public void repo_EB(View view) {
        Intent a = new Intent(this,reporte.class);
        startActivity(a);
    }
}
