public class PCDriver {
    private static class Data {
        private int count;
        private boolean isPut;

        public Data() {
            this.count = 0;
            this.isPut = false;
        }

        public synchronized void put() throws InterruptedException {
            if (this.isPut)
                wait();

            System.out.println("Put: " + ++this.count);
            this.isPut = true;
            notify();
        }

        public synchronized void get() throws InterruptedException {
            if (!this.isPut)
                wait();

            System.out.println("Get: " + this.count);
            this.isPut = false;
            notify();
        }
    }

    private static class Producer implements Runnable {
        Data data;

        public Producer(Data data) {
            this.data = data;
            new Thread(this).start();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    data.put();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Consumer implements Runnable {
        Data data;

        public Consumer(Data data) {
            this.data = data;
            new Thread(this).start();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    data.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        Data d = new Data();
        Producer p = new Producer(d);
        Consumer c = new Consumer(d);
    }
}
