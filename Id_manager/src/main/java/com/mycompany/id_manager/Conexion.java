
package com.mycompany.id_manager;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import java.util.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Conexion {
    
    public static Firestore database;
    
    public static void conectarFirebase(){
        try {
            FileInputStream accountService = new FileInputStream("Id_manager.json");
            
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(accountService))
                .build();

            FirebaseApp.initializeApp(options);
            database = FirestoreClient.getFirestore();
            System.out.println("Conexion exitosa");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
