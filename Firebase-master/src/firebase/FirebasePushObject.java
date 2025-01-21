
package firebase;

import java.util.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.concurrent.CountDownLatch;


public class FirebasePushObject {
    
    public static void main(String[] args) {
        Item item = new Item();
        item.setId(150L);
        item.setName("iphone");
        item.setPrice(100.156);
        //test de prueba
        // You can use List<Item> also.
        new FirebasePushObject().saveUsingPush(item);
    }
    
    public FirebaseDatabase firebaseDatabase;

    /**
     * inicialización de firebase.
     */
    public void initFirebase() {
        
        try {
            
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    //.setDatabaseUrl("https://prueba-esp-a7c2a.firebaseio.com") // 
                    .setDatabaseUrl("https://crudnigomezl-e1a0d-default-rtdb.firebaseio.com/")
                    //.setServiceAccount(new FileInputStream(new File("C:\\Users\\Lenovo\\Documents\\pc\\NetBeansProjects\\firebase\\prueba-esp-a7c2a-firebase-adminsdk-yd7qe-1bdb100458.json")))
                    .setServiceAccount(new FileInputStream(new File("C:\\Users\\jgome\\Documents\\NetBeansProjects\\crudnigomezl-e1a0d-firebase-adminsdk-fbsvc-5d0193b4cd.json")))

                    .build();

            FirebaseApp.initializeApp(firebaseOptions);
            firebaseDatabase = FirebaseDatabase.getInstance();
            System.out.println("La conexión se realizo exitosamente...");
            
        }catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (RuntimeException ex) {   
            ex.printStackTrace();
        }
        
        
    }

   
    public void saveUsingPush(Item item) {
        if (item != null) {
            initFirebase();
            
            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");
            
            /* Get existing child or will be created new child. */
            DatabaseReference childReference = databaseReference.child("CarpetaPrueba");

            /**
             * The Firebase Java client uses daemon threads, meaning it will not prevent a process from exiting.
             * So we'll wait(countDownLatch.await()) until firebase saves record. Then decrement `countDownLatch` value
             * using `countDownLatch.countDown()` and application will continues its execution.
             */
            CountDownLatch countDownLatch = new CountDownLatch(1);
            
            /**
             * push()
             * Add to a list of data in the database. Every time you push a new node onto a list, 
             * your database generates a unique key, like items/unique-item-id/data
             */
            childReference.push().setValue(item, new DatabaseReference.CompletionListener() {

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
}