package com.example.tutoriel3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    EditText nomUtilisateur;
    EditText email;
    EditText motDePasse;
    Button btnInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("user");
        nomUtilisateur = findViewById(R.id.nomUtilisateur);
        email = findViewById(R.id.emailinscription);
        motDePasse = findViewById(R.id.motDePasseinscription);
        btnInscription = findViewById(R.id.btnInscription);

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNewUser();
                Toast.makeText(RegisterActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void login(View view){
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
    }

    public void writeNewUser() {

        String name = nomUtilisateur.getText().toString();
        String email1 = email.getText().toString();
        String password = motDePasse.getText().toString();


        String id = mDatabase.push().getKey();

        assert id != null;
        User user = new User(name,email1,password ,id);
        mDatabase.child(id).setValue(user);


    }
}