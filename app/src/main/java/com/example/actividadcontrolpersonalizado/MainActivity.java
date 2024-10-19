package com.example.actividadcontrolpersonalizado;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // Expresión regular para validar un email
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClick(View view) {
        if (view.getId() == R.id.show_login_button) {
            // Inflar el layout del formulario de login
            LayoutInflater inflater = getLayoutInflater();
            View loginView = inflater.inflate(R.layout.login_form, null);

            // Crear el AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(loginView);  // Configurar la vista inflada en el AlertDialog
            AlertDialog dialog = builder.create();

            dialog.setCancelable(false); // Configurar la vista para que no se cierre

            // Obtener los botones del formulario
            Button loginButton = loginView.findViewById(R.id.login_button);
            Button cancelButton = loginView.findViewById(R.id.cancel_button);

            // Manejar el evento del botón Login
            loginButton.setOnClickListener(v -> {
                // Obtener los campos de texto (email y contraseña)
                EditText emailInput = loginView.findViewById(R.id.login_email);
                EditText passwordInput = loginView.findViewById(R.id.login_password);

                // Obtener los valores ingresados
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                // Lógica para el login
                if (!email.isEmpty() && !password.isEmpty()) {
                    if (isValidEmail(email)){
                        if (email.equals("usuario@gmail.com") && password.equals("1234")) {
                            Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            dialog.setCancelable(true);
                            dialog.dismiss();  // Cerrar el diálogo después del login
                        } else {
                            Toast.makeText(this, "Datos erroneos", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Email no valido", Toast.LENGTH_SHORT).show();
                    }

                } else if (email.isEmpty()){
                    Toast.makeText(MainActivity.this, "Por favor ingrese el email", Toast.LENGTH_SHORT).show();
                    emailInput.requestFocus();
                } else {
                    Toast.makeText(this, "Porfavor ingrese la contraseña", Toast.LENGTH_SHORT).show();
                    passwordInput.requestFocus();
                }
            });

            // Manejar el evento del botón Cancel
            cancelButton.setOnClickListener(v -> {
                dialog.dismiss();  // Cerrar el diálogo si se cancela
            });

            dialog.show();  // Mostrar el AlertDialog
        }
    }

    // Método para verificar si el email tiene un formato válido
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}