import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class LazyBarberSleeps{
    public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    System.out.print("Enter the number of chairs in the waiting room: ");
    int waitingChairs = input.nextInt();

    System.out.print("Enter the number of customers: ");
    int customerCount = input.nextInt();

    BarberShop shop = new BarberShop(waitingChairs);

    new Thread(() -> shop.barberWork()).start();

    for (int i = 1; i <= customerCount; i++) {
        int customerId = i;
        new Thread(() -> shop.customerArrives(customerId)).start();
    }
    input.close();
    }
}

class BarberShop {
    private final Semaphore barberReady;
    private final Semaphore customerReady;
    private final Semaphore chairAccess;
    private int availableChairs;
    public BarberShop(int chairs) {
        this.barberReady = new Semaphore(0);
        this.customerReady = new Semaphore(0);
        this.chairAccess = new Semaphore(1);
        this.availableChairs = chairs;
    }

public void barberWork() {
    while (true) {
    try {
    customerReady.acquire();  
    chairAccess.acquire();
    availableChairs++;       
    barberReady.release();   
    chairAccess.release();
    System.out.println("Barber is cutting hair.");
    Thread.sleep((int) (Math.random() * 1000));
    System.out.println("Barber finished cutting hair.");
    } catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    }
  }
}
public void customerArrives(int customerId) {
    try {
    chairAccess.acquire();
    if (availableChairs > 0) {
    availableChairs--;
    System.out.println("Customer " + customerId + " is waiting.");

    customerReady.release();  
    chairAccess.release();

    barberReady.acquire();    
    System.out.println("Customer " + customerId + " is getting a haircut.");

    Thread.sleep((int) (Math.random() * 1000));
    System.out.println("Customer " + customerId + " is done.");

    } else {
    chairAccess.release();
    System.out.println("Customer " + customerId + " left (no seats available).");
    }

    } catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    }
 }
}
