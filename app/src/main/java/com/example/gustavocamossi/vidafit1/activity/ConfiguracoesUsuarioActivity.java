package com.example.gustavocamossi.vidafit1.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.gustavocamossi.vidafit1.R;
import com.example.gustavocamossi.vidafit1.firebase.AutenticadorFirebase;
import com.example.gustavocamossi.vidafit1.firebase.UsuarioFirebase;
import com.example.gustavocamossi.vidafit1.model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class ConfiguracoesUsuarioActivity extends AppCompatActivity {

    private static final int SELECAO_GALERIA = 200;
    private EditText editUsuarioNome, editUsuarioRua, editUsuarioCidade, editUsuarioNumero, editUsuarioEstado, editUsuarioBairro, editusuarioCep, editUsuarioComplemento;
    private String idUsuario;
    private DatabaseReference firebaseRef;
    private ImageView imagePerfilUsuario;
    private StorageReference storageReference;
    private String urlImagemSelecionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_usuario);

        //Configurações iniciais
        inicializarComponentes();
        storageReference = AutenticadorFirebase.getFirebaseStorage();
        firebaseRef = AutenticadorFirebase.getFirebase();
        idUsuario = UsuarioFirebase.getIdUsuario();

        //Configurações Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações usuário");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagePerfilUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });

        //Recupera dados do usuário
        recuperarDadosUsuario();

    }

    private void recuperarDadosUsuario() {

        DatabaseReference usuarioRef = firebaseRef
                .child("usuarios")
                .child(idUsuario);

        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    editUsuarioNome.setText(usuario.getNome());
                    editUsuarioRua.setText(usuario.getRua());
                    editUsuarioCidade.setText(usuario.getCidade());
                    editUsuarioNumero.setText(usuario.getNumeroResidencial());
                    editUsuarioEstado.setText(usuario.getEstado());
                    editUsuarioBairro.setText(usuario.getBairro());
                    editusuarioCep.setText(usuario.getCep());
                    editUsuarioComplemento.setText(usuario.getComplemento());

                    urlImagemSelecionada = usuario.getUrlImagem();
                    if( urlImagemSelecionada != "" ){
                        Picasso.get()
                                .load(urlImagemSelecionada)
                                .into(imagePerfilUsuario);
                    }

                }

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void validarDadosUsuario(View view) {

        //Valida se os campos foram preenchidos
        String nome = editUsuarioNome.getText().toString();
        String rua = editUsuarioRua.getText().toString();
        String cidade = editUsuarioCidade.getText().toString();
        String numero = editUsuarioNumero.getText().toString();
        String estado = editUsuarioEstado.getText().toString();
        String bairro = editUsuarioBairro.getText().toString();
        String cep = editusuarioCep.getText().toString();
        String complemento = editUsuarioComplemento.getText().toString();


        if (!nome.isEmpty()) {
            if (!rua.isEmpty()) {
                if (!cidade.isEmpty()) {
                    if (!numero.isEmpty()) {
                        if (!estado.isEmpty()) {
                            if (!bairro.isEmpty()) {
                                if (!cep.isEmpty()) {
                                    if (!complemento.isEmpty()) {

                                        Usuario usuario = new Usuario();
                                        usuario.setIdUsuario(idUsuario);
                                        usuario.setNome(nome);
                                        usuario.setRua(rua);
                                        usuario.setCidade(cidade);
                                        usuario.setNumeroResidencial(numero);
                                        usuario.setEstado(estado);
                                        usuario.setBairro(bairro);
                                        usuario.setCep(cep);
                                        usuario.setComplemento(complemento);
                                        usuario.salvar();
                                        exibirMensagem("Dados atualizados com sucesso!");
                                        finish();
                                    } else {
                                        exibirMensagem("Digite o Complemento");

                                    }
                                } else {
                                    exibirMensagem("Digite o Cep");
                                }
                            } else {
                                exibirMensagem("Digite o Bairro");
                            }
                        } else {
                            exibirMensagem("Digite o Estado");
                        }
                    } else {
                        exibirMensagem("Digite o Número de sua Residência");
                    }
                } else {
                    exibirMensagem("Digite a Cidade");
                }
            } else exibirMensagem("Digite a Rua");

        }
    }

    private void exibirMensagem(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap imagem = null;

            try {

                switch (requestCode) {
                    case SELECAO_GALERIA:
                        Uri localImagem = data.getData();
                        imagem = MediaStore.Images
                                .Media
                                .getBitmap(
                                        getContentResolver(),
                                        localImagem
                                );
                        break;
                }

                if (imagem != null) {

                    imagePerfilUsuario.setImageBitmap(imagem);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    StorageReference imagemRef = storageReference
                            .child("imagens")
                            .child("usuarios")
                            .child(idUsuario + "jpeg");

                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ConfiguracoesUsuarioActivity.this,
                                    "Erro ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            urlImagemSelecionada = taskSnapshot.getDownloadUrl().toString();
                            Toast.makeText(ConfiguracoesUsuarioActivity.this,
                                    "Sucesso ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void inicializarComponentes() {
        editUsuarioNome = findViewById(R.id.editEmpresaNome);
        editUsuarioRua = findViewById(R.id.editEmpresaCategoria);
        editUsuarioCidade = findViewById(R.id.editEmpresaTaxa);
        editUsuarioNumero = findViewById(R.id.editUsuarioNumero);
        editUsuarioEstado = findViewById(R.id.editEmpresaTempo);
        editUsuarioBairro = findViewById(R.id.editUsuarioBairro);
        editusuarioCep = findViewById(R.id.editUsuarioCep);
        editUsuarioComplemento = findViewById(R.id.editUsuarioComplemento);
        imagePerfilUsuario = findViewById(R.id.imagePerfilEmpresa);

    }


}
