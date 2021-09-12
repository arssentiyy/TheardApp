public class WaitNotifyClass {
    private final Object mon = new Object();
    private volatile char currentLetterStart = 'A';

    public static void main(String[] args) {
        WaitNotifyClass waitNotifyObj = new WaitNotifyClass();

        Thread thread1 = new Thread(() -> {
            waitNotifyObj.printAbC('A', 'B');
        });
        Thread thread2 = new Thread(() -> {
            waitNotifyObj.printAbC('B','C');
        });
        Thread thread3 = new Thread(() -> {
            waitNotifyObj.printAbC('C','A');
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }

    public void printAbC(char currentLetter, char nextLetter) {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetterStart != currentLetter) {
                        mon.wait();
                    }
                    System.out.print(currentLetter);
                    currentLetterStart = nextLetter;
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}