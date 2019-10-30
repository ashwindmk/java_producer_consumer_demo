import java.util.Queue;

public class Consumer extends Thread {
    private Queue<String> queue;
    private int maxSize;

    public Consumer(Queue<String> queue, int maxSize, String name) {
        super(name);
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    System.out.println("Queue is empty, consumer is waiting for producer to put something in queue");
                    try {
                        queue.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                String packet = queue.remove();
                System.out.println("Consumer removed: " + packet);
                queue.notifyAll();

                // To stop consumer thread, comment this block to keep the consumer listening
                if (packet.equals("End")) {
                    break;
                }
            }
        }
    }
}
