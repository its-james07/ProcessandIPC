class Buffer {
    private final int[] buffer = new int[5]; 
    private int count = 0, in = 0, out = 0;
    public synchronized void produce(int item) throws InterruptedException {
        while (count == buffer.length) { 
            System.out.println("Buffer full! Producer waiting...");
            wait();
        }
        buffer[in] = item;
        in = (in + 1) % buffer.length;
        count++;

        System.out.println("Produced: " + item);
        printBuffer();
        notifyAll(); 
    }
    public synchronized int consume() throws InterruptedException {
        while (count == 0) { 
            System.out.println("Buffer empty! Consumer waiting...");
            wait();
        }
        int item = buffer[out];
        out = (out + 1) % buffer.length;
        count--;
        System.out.println("Consumed: " + item);
        printBuffer();
        notifyAll(); 
        return item;
    }

    private void printBuffer() {
        System.out.print("Buffer state: [");
        for (int i = 0; i < buffer.length; i++) {
            System.out.print(buffer[i] + " ");
        }
        System.out.println("]");
    }
}

class Producer extends Thread {
    private final Buffer buffer;
    Producer(Buffer buffer) {
        this.buffer = buffer;  }
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) { 
                buffer.produce(i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer extends Thread {
    private final Buffer buffer;

    Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                buffer.consume();
                Thread.sleep(800); 
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class WaitNotifyProducerConsumerDemo {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        producer.start();
        consumer.start();
    }
}
