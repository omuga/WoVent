package shake.letz.wovent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.TimeUnit;

import Objetos.Evento;

public class EditarEventoActivity extends Activity {
    String nombre_ant;
    EditText nombre;
    String descripcion_ant;
    EditText descripcion;
    String fechaS_ant;
    EditText fechaS;
    String fechaE_ant;
    EditText fechaE;
    Button button;
    private DatabaseReference eventoRef;
    FirebaseDatabase database;
    Button btn_subir;
    StorageReference mStorage;
    Uri downloadUri;
    ImageView mImageView;
    Button btn_cancelar;
    static final int GALLERY_INTENT = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode== Activity.RESULT_OK){
            Uri uri = data.getData();
            final StorageReference filePath = mStorage.child("fotos").child(uri.getLastPathSegment());
            Toast.makeText(getApplicationContext(), "Cargando imágen,por favor espere...", Toast.LENGTH_SHORT).show();

            filePath.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        downloadUri = task.getResult();
                        Log.d("EventoDelFragment",downloadUri+"");
                        Glide.with(EditarEventoActivity.this)
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mStorage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        eventoRef = database.getReference("Evento");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);
        btn_subir = findViewById(R.id.btn_subir_ed);
        mImageView = findViewById(R.id.fimg_ed);
        nombre_ant = getIntent().getStringExtra("nombre");
        nombre = findViewById(R.id.et_nombre_ed);
        descripcion_ant = getIntent().getStringExtra("descripcion");
        descripcion = findViewById(R.id.et_descripcion_ed);
        fechaS_ant = getIntent().getStringExtra("fechaS");
        fechaS = findViewById(R.id.et_fechaS_ed);
        fechaE_ant = getIntent().getStringExtra("fechaE");
        fechaE = findViewById(R.id.et_fechaE_ed);
        button = findViewById(R.id.btn_editar_evento);
        nombre.setText(nombre_ant);
        descripcion.setText(descripcion_ant);
        fechaS.setText(fechaS_ant);
        fechaE.setText(fechaE_ant);
        btn_cancelar = findViewById(R.id.btn_cancelar_edicion);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),UserProfileActivity.class);
                startActivity(intent);
            }
        });
        btn_subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            @Override
            public void onClick(View v) {
                DatabaseReference database1 = FirebaseDatabase.getInstance().getReference("Evento");
                Query query = database1.orderByChild("nombre").equalTo(nombre_ant);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot datos: dataSnapshot.getChildren()){
                            datos.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                //
                Evento evento = new Evento(nombre.getText().toString(),descripcion.getText().toString(),
                        fechaS.getText().toString(),fechaE.getText().toString(), user.getEmail(),downloadUri+"");
                eventoRef.push().setValue(evento);
                Toast.makeText(getApplicationContext(), "Evento Editado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(),UserProfileActivity.class);
                startActivity(intent);

            }
        });
    }

}
