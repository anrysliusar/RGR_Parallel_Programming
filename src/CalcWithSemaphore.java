import java.util.concurrent.Semaphore;

public class CalcWithSemaphore {
    //Використання семафорів

    //для синхронізації по обчисленню с
    static Semaphore semSum = new Semaphore(1);
    //для синхронізації по обчисленню мінімального b
    static Semaphore semMin = new Semaphore(1);

    //обчислення c
    static void findSum_c(int ci) {
        try {
            semSum.acquire();
            if (Data.c == null)
                Data.c = ci;
            else
                Data.c += ci;
            semSum.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //обчислення мінімального b
    static void findMin_b(int b, int bi) {
        try {
            semMin.acquire();
            Data.b = Math.min(b, bi);
            semMin.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
