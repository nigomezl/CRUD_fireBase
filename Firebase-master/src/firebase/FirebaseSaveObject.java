package firebase;

import java.util.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;


public class FirebaseSaveObject {

    public static void main(String[] args) throws FileNotFoundException {
        Item item = new Item();
        item.setId(150L);
        item.setName("PruebaNetbeans");
        item.setPrice(100.0);

        //save item objec to firebase.
        //new FirebaseSaveObject().save(item);
        new FirebaseSaveObject().recover();
    }

    public FirebaseDatabase firebaseDatabase;

    /**
         * inicialización de firebase.
*/
    public void initFirebase() {
        
        try {
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()   
                    .setDatabaseUrl("https://crudnigomezl-e1a0d-default-rtdb.firebaseio.com/")
                    //.setServiceAccount(new FileInputStream(new File("C:\\Users\\Lenovo\\Documents\\pc\\NetBeansProjects\\firebase\\prueba-esp-a7c2a-firebase-adminsdk-yd7qe-1bdb100458.json")))
                    .setServiceAccount(new FileInputStream(new File("C:\\Users\\jgome\\Documents\\NetBeansProjects\\crudnigomezl-e1a0d-firebase-adminsdk-fbsvc-5d0193b4cd.json")))

                   // .setDatabaseUrl("https://prueba-esp-a7c2a.firebaseio.com")
                    //.setServiceAccount(new FileInputStream(new File("C:\\Users\\Lenovo\\Documents\\pc\\NetBeansProjects\\firebase\\prueba-esp-a7c2a-firebase-adminsdk-yd7qe-1bdb100458.json")))
                    .build();

            FirebaseApp.initializeApp(firebaseOptions);
            firebaseDatabase = FirebaseDatabase.getInstance();
            System.out.println("Conexión exitosa....");
        }catch (RuntimeException ex) {
            System.out.println("Problema ejecucion ");
        }catch (FileNotFoundException ex) {
            System.out.println("Problema archivo");
        }

         
    }

    /**
     * Save item object in Firebase.
     * @param item 
     */
    public void save(Item item) throws FileNotFoundException {
        if (item != null) {
            initFirebase();
            
            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");
            
            /* Get existing child or will be created new child. */
            DatabaseReference childReference = databaseReference.child("item");

            /**
             * The Firebase Java client uses daemon threads, meaning it will not prevent a process from exiting.
             * So we'll wait(countDownLatch.await()) until firebase saves record. Then decrement `countDownLatch` value
             * using `countDownLatch.countDown()` and application will continues its execution.
             */
            CountDownLatch countDownLatch = new CountDownLatch(1);
            childReference.setValue(item, new DatabaseReference.CompletionListener() {

                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    System.out.println("Registro guardado!");
                    // decrement countDownLatch value and application will be continues its execution.
                    countDownLatch.countDown();
                }
            });
            try {
                //wait for firebase to saves record.
                countDownLatch.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void recover() {
            
            initFirebase();

            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("item");

            /* Get existing child or will be created new child. */
            DatabaseReference childReference = databaseReference.child("item");

            /**
             * The Firebase Java client uses daemon threads, meaning it will not
             * prevent a process from exiting. So we'll
             *wait(countDownLatch.await()) until firebase saves record. Then
             * decrement `countDownLatch` value using
             * `countDownLatch.countDown()` and application will continues its
             * execution.
             */
            
            CountDownLatch countDownLatch = new CountDownLatch(1);
            databaseReference.addValueEventListener(new ValueEventListener() {
                    
                    @Override
                    
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called onataSnapshotce with the initial value and again
                        // whenever data at this location is updated.
                        Item value = dataSnapshot.getValue(Item.class);
                        System.out.println("valor: "+ value.getId());
                        System.out.println("valor: "+ value);

                        System.out.println("valor: "+ value.getPrice());
                        System.out.println("valor: "+ value.getName());
                        countDownLatch.countDown();

                        //Log.d(TAG, "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        //Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
        try {
            //wait for firebase to saves record.
            countDownLatch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        
            }
}
//Realizar la recuperacion de informacion
//perfeccionar el metodo en que se realiza una operacion, suprimir el conteo regresivo