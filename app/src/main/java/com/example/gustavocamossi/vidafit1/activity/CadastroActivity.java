package com.example.gustavocamossi.vidafit1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gustavocamossi.vidafit1.R;
import com.example.gustavocamossi.vidafit1.firebase.AutenticadorFirebase;
import com.example.gustavocamossi.vidafit1.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoTelefone, campoSenha;
    private Button btnCadastrar, btnLogin;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editEmpresaNome);
        campoEmail = findViewById(R.id.editEmail);
        campoTelefone = findViewById(R.id.editTelefone);
        campoSenha = findViewById(R.id.editSenha);
        btnCadastrar = findViewById(R.id.btnEntrar);
        btnLogin = findViewById(R.id.btnLogin);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textNome = campoNome.getText().toString();
                String textEmail = campoEmail.getText().toString();
                String textTeleone = campoTelefone.getText().toString();
                String textSenha = campoSenha.getText().toString();

                //Validar se os campos foram preenchidos

                if (!textNome.isEmpty()) {
                    if (!textEmail.isEmpty()) {
                        if (!textTeleone.isEmpty()) {
                            if (!textSenha.isEmpty()) {
                                usuario = new Usuario();
                                usuario.setNome(textNome);
                                usuario.setEmail(textEmail);
                                usuario.setTelefone(textTeleone);
                                usuario.setSenha(textSenha);
                                cadastrarUsuario();

                            } else {
                                Toast.makeText(CadastroActivity.this,
                                        "Preencha a senha!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CadastroActivity.this,"Preencha o email!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CadastroActivity.this,"Preencha o Telefone!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CadastroActivity.this,"Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    public void cadastrarUsuario() {
        autenticacao = AutenticadorFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                        } else {
                            String execao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                execao = "Digite uma senha mais Forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                execao = "Por favor, digite um e-mail válido!";
                            } catch (FirebaseAuthUserCollisionException e) {
                                execao = "Esta conta já foi cadastrada!";
                            } catch (Exception e) {
                                execao = "Erro ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(CadastroActivity.this,
                                    execao,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

        );
    }


    public void btnLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
