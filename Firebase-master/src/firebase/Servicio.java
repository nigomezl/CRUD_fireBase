
package firebase;

import java.util.*;

public class Servicio {
    public static void main(String[] args) {
        System.out.println("1: crear persona"); 
        System.out.println("2: leer persona"); 
        Scanner s = new Scanner(System.in);
        int num = s.nextInt();
        switch(num) {
            case 1:
                Item item = new Item();
                // Crea el objeto
                System.out.println("nombre del objeto");
                String name = s.next();
                item.setName(name);
                
                System.out.println("id del objeto");
                long number = s.nextInt();
                item.setId(number);
                
                System.out.println("precio del objeto");
                double decimals = s.nextDouble();
                item.setPrice(decimals);
                
                new FirebasePushObject().saveUsingPush(item);
                
                break;
            case 2:
                
                break;
        }
    }
}
