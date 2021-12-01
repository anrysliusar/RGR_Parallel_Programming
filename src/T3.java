public class T3 extends Thread {
    private final SyncMonitor syncMonitor;

    public T3(SyncMonitor syncMonitor) {
        this.syncMonitor = syncMonitor;
    }

    public void run() {
        syncMonitor.syncSysOut("T3 started");

        //чекати сигнал про введення всіх значень
        syncMonitor.waitForInputAllValues();

        //визначення діапазону обрахунку матриць та векторів
        int start = 2 * Data.H;
        int end = 3 * Data.H;

        //обрахунок матриць та векторів
        Data.evaluate(start, end);

        //сигнал про обчислення MAh
        syncMonitor.sigCalcMAh();

        syncMonitor.syncSysOut("T3 finished");
    }
}
