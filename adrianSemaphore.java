import java.util.concurrent.Semaphore;
/*
Adrian Miller
Prof Tarquinio
CMSC 412
9-11-2020
HW3_ Purpose of Program:
create three new threads (besides the already existing main thread) and synchronizes them in such a way that each thread displays it's thread id in turn for 5 iterations. The output of the program should look like this:


Thread 1 - iteration no. 1

Thread 2 - iteration no. 1

Thread 3 - iteration no. 1

Thread 1 - iteration no. 2

Thread 2 - iteration no. 2

Thread 3 - iteration no. 2

Thread 1 - iteration no. 3

Thread 2 - iteration no. 3

Thread 3 - iteration no. 3

Thread 1 - iteration no. 4

Thread 2 - iteration no. 4

Thread 3 - iteration no. 4

Thread 1 - iteration no. 5

Thread 2 - iteration no. 5

Thread 3 - iteration no. 5
*/
public class MergeThreads implements Runnable {
private final Semaphore prev;
private final Semaphore nxt;
public MergeThreads (Semaphore prev, Semaphore nxt) {
this.prev = prev;
this.nxt = nxt;
}


//runs and prints out threads and respective iterations
public void run() {
for (int i = 0; i < 5; i++) {
waitForPrevLock();
System.out.println(Thread.currentThread().getName()+ " Iteration no. "+ (i+1));
releaseForNxt();
}
}
private void releaseForNxt() {
nxt.release();
}
private void waitForPrevLock() {
try {
prev.acquire();
} catch (InterruptedException e) {
e.printStackTrace();
throw new RuntimeException(e);
}
}
static public void main(String argv[]) throws InterruptedException {
Semaphore lock1 = new Semaphore(1);
Semaphore lock2 = new Semaphore(1);
Semaphore lock3 = new Semaphore(1);
lock1.acquire();
lock2.acquire();
lock3.acquire();
Thread t1 = new Thread(new MergeThreads (lock3, lock1),"Thread 1");
Thread t2 = new Thread(new MergeThreads (lock1, lock2),"Thread 2");
Thread t3 = new Thread(new MergeThreads (lock2, lock3),"Thread 3");
t1.start();
t2.start();
t3.start();
lock3.release();
t1.join();
t2.join();
t3.join();
}
}
