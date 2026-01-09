import java.util.Scanner;
class DiningPhilosophers{
public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter the number of Philosophers: ");
    int n = sc.nextInt();
    Philosopher[] philosophers = new Philosopher[n];
    Fork[] forks = new Fork[n];
    for(int i = 0; i<n; i++){
        forks[i] = new Fork(i);
    }
    for(int i = 0; i<n; i++){
        philosophers[i] = new Philosopher(i, forks[i], forks[(i+1)%n]);
        new Thread(philosophers[i]).start();
    }
    System.out.println("Philosophers are dining. Press CTRL + C to stop.");
    sc.close();
  }
}
class Fork{
    private boolean isUsed; 
    private final int forkId;
    Fork(int id){
        this.forkId = id;
        this.isUsed = false;
    }
    public int getForkId(){
    return forkId;
    }
    synchronized void pickUp(int philosopherId) throws InterruptedException{
    while(isUsed){
        wait();
    }
    isUsed = true;
    System.out.println("Philosopher "+ philosopherId +" picked up fork "+forkId);
    }
    synchronized void putDown(int philosopherId){
    isUsed = false;
    System.out.println("Philosopher "+ philosopherId +" put down fork "+forkId);
    notify();
    }
}
class Philosopher implements Runnable{
    private final int philosopherId;
    private final Fork leftFork; 
    private final Fork rightFork;
    Philosopher(int id, Fork left, Fork right){
        this.philosopherId = id;
        this.leftFork = left; 
        this.rightFork = right;
    }
    @Override
    public void run() {
        try {
            for(int i = 0; i<3; i++){
                think();
                eat();
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    public void think() throws InterruptedException{
        System.out.println("Philosopher "+philosopherId+" is thinking.");
        Thread.sleep((long)Math.random()*1000);
    }

    public void eat() throws InterruptedException{
        Fork firstFork = leftFork.getForkId() < rightFork.getForkId() ? leftFork : rightFork;
        Fork secondFork = leftFork.getForkId() < rightFork.getForkId() ? rightFork : leftFork;
        
        synchronized(firstFork) {
            firstFork.pickUp(philosopherId);
            synchronized(secondFork) {
                secondFork.pickUp(philosopherId);
                System.out.println("Philosopher "+philosopherId+" is eating.");
                Thread.sleep((long)Math.random()* 1000);
                secondFork.putDown(philosopherId);
            }
            firstFork.putDown(philosopherId);
        }
    }
}

