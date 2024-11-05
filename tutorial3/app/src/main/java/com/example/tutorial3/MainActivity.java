package com.example.tutorial3;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Gérer les insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiser la référence Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        // Écrire une valeur dans la base de données
        myRef.setValue("Bonjour, World!").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("FirebaseDatabase", "Données écrites avec succès.");
            } else {
                Log.e("FirebaseDatabase", "Échec de l'écriture des données.", task.getException());
            }
        });

        // Lire la valeur de la base de données en temps réel
        myRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value != null) {
                    Toast.makeText(MainActivity.this, "Valeur : " + value, Toast.LENGTH_SHORT).show();
                } else {
                    Log.w("FirebaseDatabase", "Aucune valeur trouvée.");
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("FirebaseDatabase", "Erreur de lecture.", error.toException());
            }
        });
    }
}
