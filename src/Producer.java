import java.util.Queue;
import java.util.Random;

class Producer extends Thread {
    private Queue<String> queue;
    private int maxSize;

    private String packets[] = {
            "First packet",
            "Second packet",
            "Third packet",
            "Fourth packet",
            "Fifth packet",
            "Sixth packet",
            "End"
    };

    public Producer(Queue<String> queue, int maxSize, String name) {
        super(name);
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        for (String packet : packets) {
            synchronized (queue) {
                while (queue.size() == maxSize) {
                    try {
                        System.out.println("Queue is full, producer is waiting for consumer to take something from queue");
                        queue.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Producer added: " + packet);
                queue.add(packet);
                queue.notifyAll();
            }
        }
    }
}
