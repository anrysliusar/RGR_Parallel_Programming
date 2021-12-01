/*
    Паралельне програмування
    ПКС1СП на мові Java
    Варіант: 19
    MA = min(B) * (MX * MR) * MM + (B * C) * (MZ * MD)
    Слюсаренко Андрій ІО-91
    30.11.21
*/

public class Main {
    public static void main(String[] args) {
        //початок вимірювання часу виконання програми
        long startTime = System.currentTimeMillis();
        SyncMonitor syncMonitor = new SyncMonitor();
        System.out.println("Program started");

        T1 t1 = new T1(syncMonitor);
        T2 t2 = new T2(syncMonitor);
        T3 t3 = new T3(syncMonitor);
        T4 t4 = new T4(syncMonitor);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Program finished");
        //розрахунок часу виконання програми
        System.out.println("Time:" + (double) (System.currentTimeMillis() - startTime) + "ms");
    }
}







