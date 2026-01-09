import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ReadersWriters {
public static void main(String[] args) {
Scanner input = new Scanner(System.in);

    System.out.print("Enter number of readers: ");
    int readers = input.nextInt();

    System.out.print("Enter number of writers: ");
    int writers = input.nextInt();

    SharedData data = new SharedData();

for (int i = 1; i <= writers; i++) {
    int id = i;
    new Thread(() -> data.write(id)).start();
}

for (int i = 1; i <= readers; i++) {
    int id = i;
    new Thread(() -> data.read(id)).start();
}
    input.close();
  }
}

class SharedData {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private int data = 0;

public void read(int readerId) {
    while (true) {
    lock.readLock().lock();
        try {
            System.out.println("Reader " + readerId +" reads data: " + data);
        } finally {
            lock.readLock().unlock();
        }
        sleep();
    }
}

public void write(int writerId) {
    while (true) {
        lock.writeLock().lock();
        try {
            data++;
            System.out.println("Writer " + writerId +" writes data: " + data);
            } finally {
                lock.writeLock().unlock();
            }
            sleep();
        }
    }
    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
