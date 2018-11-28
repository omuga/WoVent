package shake.letz.wovent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import Objetos.Evento;


public class AgregarEventoFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //variables para subir imagen
    Button btn_subir;
    StorageReference mStorage;
    Uri downloadUri;
    ImageView mImageView;
    static final int GALLERY_INTENT = 1;


    private String mParam1;
    private String mParam2;
    private DatabaseReference eventoRef;
    FirebaseDatabase database;
    EditText et_nombre;
    EditText et_descripcion;
    EditText et_fechaS;
    EditText et_fechaE;
    Button btn_agregar_evento;

    private OnFragmentInteractionListener mListener;

    public AgregarEventoFragment() {
        // Required empty public constructor
    }

    public static AgregarEventoFragment newInstance(String param1, String param2) {
        AgregarEventoFragment fragment = new AgregarEventoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStorage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        eventoRef = database.getReference("Evento");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_agregar_evento, container, false);
        btn_subir = v.findViewById(R.id.btn_subir);
        mImageView = v.findViewById(R.id.fimg);
        et_nombre = v.findViewById(R.id.et_nombre);
        et_descripcion = v.findViewById(R.id.et_descripcion);
        et_fechaS = v.findViewById(R.id.et_fechaS);
        et_fechaE = v.findViewById(R.id.et_fechaE);
        btn_agregar_evento = v.findViewById(R.id.btn_agregar_evento);
        btn_agregar_evento.setOnClickListener(this);
        btn_subir.setOnClickListener(this);
        return v;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        switch (v.getId()){
            case R.id.btn_subir:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
                break;
            case R.id.btn_agregar_evento:
                Evento evento = new Evento(et_nombre.getText().toString(),et_descripcion.getText().toString(),
                        et_fechaS.getText().toString(),et_fechaE.getText().toString(), user.getEmail(),downloadUri+"");
                eventoRef.push().setValue(evento);
                Toast.makeText(getContext(), "Evento Creado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                et_nombre.setText("");
                et_descripcion.setText("");
                et_fechaS.setText("");
                et_fechaE.setText("");

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode== Activity.RESULT_OK){
            Uri uri = data.getData();
            final StorageReference filePath = mStorage.child("fotos").child(uri.getLastPathSegment());
            Toast.makeText(getContext(), "Cargando imágen,por favor espere...", Toast.LENGTH_SHORT).show();

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
                        Glide.with(AgregarEventoFragment.this)
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
