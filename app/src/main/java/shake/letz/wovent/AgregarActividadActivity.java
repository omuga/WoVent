package shake.letz.wovent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import Objetos.Actividad;
import Objetos.myCallBack;

public class AgregarActividadActivity extends Activity {

    EditText nombre_actividad;
    EditText descripcion_actividad;
    EditText horario_actividad;
    String email_creador;
    String nombre_evento;
    private DatabaseReference actividadRef;
    FirebaseDatabase database;
    Button button;
    Button button_volver;
    //variables para subir imagen
    Button btn_subir;
    StorageReference mStorage;
    Uri downloadUri;
    ImageView mImageView;
    static final int GALLERY_INTENT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        actividadRef = database.getReference("Actividad");

        //id_act = Integer.valueOf(actividadRef.child("idauto").toString());

        setContentView(R.layout.activity_agregar_actividad);
        mImageView = findViewById(R.id.fimg);
        nombre_actividad = findViewById(R.id.et_nombre_actividad);
        descripcion_actividad = findViewById(R.id.et_descripcion_actividad);
        horario_actividad = findViewById(R.id.et_fecha_actividad);
        nombre_evento = getIntent().getStringExtra("evento");
        email_creador = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        btn_subir = findViewById(R.id.btn_subir_actividad);
        btn_subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);

            }
        });


        button = findViewById(R.id.btn_agregar_actividad);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readDataActividad(new myCallBack() {
                    @Override
                    public void onCallback(int value) {
                        actividadRef.child("idauto").setValue(value+1);
                        Actividad actividad = new Actividad(nombre_actividad.getText().toString(),
                                descripcion_actividad.getText().toString(),
                                horario_actividad.getText().toString(),"21/12/2018",email_creador,nombre_evento,value+1,downloadUri+"");
                        actividadRef.push().setValue(actividad);
                        Toast.makeText(getApplicationContext(), "Actividad Creada Satisfactoriamente", Toast.LENGTH_SHORT).show();
                        nombre_actividad.setText("");
                        descripcion_actividad.setText("");
                        horario_actividad.setText("");
                        mImageView.setImageResource(R.drawable.ic_menu_camera);
                    }
                });

            }
        });
        button_volver = findViewById(R.id.btn_volver_actividad);
        button_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent(getBaseContext(),UserProfileActivity.class);
             startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mStorage = FirebaseStorage.getInstance().getReference();
        if(requestCode==GALLERY_INTENT && resultCode== Activity.RESULT_OK){
            Uri uri = data.getData();
            final StorageReference filePath = mStorage.child("fotos").child(uri.getLastPathSegment());
            Toast.makeText(getApplicationContext(), "Cargando imágen,por favor espere...", Toast.LENGTH_SHORT).show();

            filePath.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        Log.d("entro mal",downloadUri+"");
                        throw task.getException();
                    }

                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        downloadUri = task.getResult();
                        Log.d("entro bien",downloadUri+"");
                        Glide.with(AgregarActividadActivity.this)
                                .load(downloadUri)
                                .into(mImageView);
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
    }

    public void readDataActividad(final myCallBack myCallback){
        actividadRef.child("idauto").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }
}
