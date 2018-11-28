package shake.letz.wovent;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Objetos.Actividad;

public class AgregarActividadActivity extends Activity {

    EditText nombre_actividad;
    EditText descripcion_actividad;
    EditText horario_actividad;
    String email_creador;
    String nombre_evento;
    private DatabaseReference actividadRef;
    FirebaseDatabase database;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        actividadRef = database.getReference("Actividad");
        setContentView(R.layout.activity_agregar_actividad);
        nombre_actividad = findViewById(R.id.et_nombre_actividad);
        descripcion_actividad = findViewById(R.id.et_descripcion_actividad);
        horario_actividad = findViewById(R.id.et_fecha_actividad);
        nombre_evento = getIntent().getStringExtra("evento");
        email_creador = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        button = findViewById(R.id.btn_agregar_actividad);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actividad actividad = new Actividad(nombre_actividad.getText().toString(),
                        descripcion_actividad.getText().toString(),horario_actividad.getText().toString(),email_creador,nombre_evento);
                actividadRef.push().setValue(actividad);
                Toast.makeText(getApplicationContext(), "Actividad Creada Satisfactoriamente", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
