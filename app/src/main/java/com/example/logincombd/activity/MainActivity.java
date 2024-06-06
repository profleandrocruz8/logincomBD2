package com.example.logincombd.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.logincombd.R;
import com.example.logincombd.Util.ConfiguraBd;
import com.example.logincombd.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Usuario usuario; // Objeto Usuario que armazena as informações do usuário
    FirebaseAuth autenticacao; // Objeto FirebaseAuth para autenticação com Firebase
    EditText campoNome, campoEmail, camposSnha; // Campos de entrada para nome, email e senha
    Button botaoCadastrar; // Botão para acionar o cadastro


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Habilita layout de borda a borda
        setContentView(R.layout.activity_main); // Define o layout da atividade
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.leo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // Ajusta o padding para suportar áreas de recorte da tela
            inicializar(); // Inicializa os elementos da interface

            return insets;
        });
    }

    // Inicializa os elementos da interface com seus respectivos IDs
    public void inicializar() {
        campoNome = findViewById(R.id.editTextNome);
        campoEmail = findViewById(R.id.TextLogin);
        camposSnha = findViewById(R.id.textSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);
    }

    // Valida os campos de entrada e, se válidos, chama o método para cadastrar o usuário
    public void validarCampos(View view) {
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String pass = camposSnha.getText().toString();

        if (!nome.isEmpty()) { // Verifica se o campo nome não está vazio
            if (!email.isEmpty()) { // Verifica se o campo email não está vazio
                if (!pass.isEmpty()) { // Verifica se o campo senha não está vazio

                    usuario = new Usuario(); // Cria um novo objeto Usuario
                    usuario.setNome(nome); // Define o nome do usuário
                    usuario.setEmail(email); // Define o email do usuário
                    usuario.setPass(pass); // Define a senha do usuário

                    // Chama o método para cadastrar o usuário
                    cadastrarUsuario();

                } else {
                    Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show(); // Exibe mensagem se o campo senha estiver vazio
                }
            } else {
                Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show(); // Exibe mensagem se o campo email estiver vazio
            }
        } else {
            Toast.makeText(this, "Preencha o nome", Toast.LENGTH_SHORT).show(); // Exibe mensagem se o campo nome estiver vazio
        }
    }

    // Método para cadastrar o usuário utilizando Firebase Authentication
    private void cadastrarUsuario() {
        autenticacao = ConfiguraBd.Fireautenticacao(); // Obtém instância de autenticação do Firebase

        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getPass()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Sucesso ao Cadastrar o usuario", Toast.LENGTH_SHORT).show(); // Exibe mensagem de sucesso
                }else{
                    Toast.makeText(MainActivity.this, "Deu ruim!", Toast.LENGTH_SHORT).show(); // Exibe mensagem de erro
                }
            }
        });
    }
}