package com.example.tutoriel3;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    EditText email2, motDePasse2;
    Button btnSeconnecter;

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

        email2 = findViewById(R.id.emailconnexion);
        motDePasse2 = findViewById(R.id.motDePasseconnexion);
        btnSeconnecter = findViewById(R.id.btnSeConnecter);


        btnSeconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {

                } else {
                    checkUser();
                }
            }
        });






    }

    public void register(View view){
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

    public Boolean validateUsername() {
        String val = email2.getText().toString();
        if (val.isEmpty()) {
            email2.setError("Username cannot be empty");
            return false;
        } else {
            email2.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = motDePasse2.getText().toString();
        if (val.isEmpty()) {
            motDePasse2.setError("Password cannot be empty");
            return false;
        } else {
            motDePasse2.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userEmail = email2.getText().toString().trim();
        String userPassword = motDePasse2.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(userEmail);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    email2.setError(null);

                    // Parcours les enfants retournés par la requête
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String passwordFromDB = userSnapshot.child("password").getValue(String.class);
                        if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                            // Si le mot de passe est correct
                            email2.setError(null);

                            // Récupère les informations de l'utilisateur
                            String nameFromDB = userSnapshot.child("username").getValue(String.class);
                            String emailFromDB = userSnapshot.child("email").getValue(String.class);
                            String idFromDB = userSnapshot.child("id").getValue(String.class);

                            // Passe les données à l'écran de profil
                            Intent intent = new Intent(MainActivity.this, ProfileUser.class);
                            intent.putExtra("email", emailFromDB);
                            intent.putExtra("username", nameFromDB);
                            intent.putExtra("id", idFromDB);
                            startActivity(intent);
                            return; // Quitte la boucle et la méthode
                        }
                    }

                    // Si le mot de passe ne correspond pas
                    motDePasse2.setError("Invalid Credentials");
                    motDePasse2.requestFocus();
                } else {
                    // Si l'utilisateur n'existe pas
                    email2.setError("User does not exist");
                    email2.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Gérer l'annulation de la requête si nécessaire
            }
        });
    }


}