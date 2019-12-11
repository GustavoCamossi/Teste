package com.example.gustavocamossi.vidafit1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gustavocamossi.vidafit1.R;
import com.example.gustavocamossi.vidafit1.firebase.AutenticadorFirebase;
import com.example.gustavocamossi.vidafit1.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.gustavocamossi.vidafit1.model.Constantes.USUARIO_ADMIN;

public class AutenticacaoActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoTelefone, campoSenha;
    private Button btnCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);
        verificarUsarioLogado();
    }

    protected void onStart() {
        super.onStart();
        verificarUsarioLogado();
    }

    public void btnFacaPedido(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void btnCadastrar(View view) {
        startActivity(new Intent(this, CadastroActivity.class));
    }



    public void verificarUsarioLogado() {
        autenticacao = AutenticadorFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null) {
            if (autenticacao.getCurrentUser().getEmail().equals(USUARIO_ADMIN))
                abrirTelaAdministrativa();
            else
                abrirTelaPrincipal();
        }

    }

    public void abrirTelaPrincipal() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    public void abrirTelaAdministrativa() {
        startActivity(new Intent(this, EmpresaActivity.class));
        finish();
    }

}
