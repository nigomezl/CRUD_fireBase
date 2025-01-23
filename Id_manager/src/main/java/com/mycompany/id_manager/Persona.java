package com.mycompany.id_manager;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import java.util.*;

public class Persona {
    
    CollectionReference reference;
    
    static Firestore database;
    
    public static boolean guardarPersona(String coleccion, String documento, Map<String, Object> data){
        
        database = FirestoreClient.getFirestore();
        
        try {
            DocumentReference documentoRef = database.collection(coleccion).document(documento);
            ApiFuture<WriteResult> result = documentoRef.set(data);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
