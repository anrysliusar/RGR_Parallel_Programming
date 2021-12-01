public class T4 extends Thread {
    private final SyncMonitor syncMonitor;

    public T4(SyncMonitor syncMonitor) {
        this.syncMonitor = syncMonitor;
    }

    public void run() {
        syncMonitor.syncSysOut("T4 started");
        Data.B = Data.getVectorFilledWithValue(1);
        Data.MR = Data.getMatrixFilledWithValue(1);
        Data.MM = Data.getMatrixFilledWithValue(1);
        Data.b = Data.B[0];

        //сигнал про введення значення
        syncMonitor.sigInput();

        //чекати сигнал про введення всіх значень
        syncMonitor.waitForInputAllValues();

        //визначення діапазону обрахунку матриць та векторів
        int start = 3 * Data.H;
        int end = 4 * Data.H;

        //обрахунок матриць та векторів
        Data.evaluate(start, end);

        //сигнал про обчислення MAh
        syncMonitor.sigCalcMAh();

        syncMonitor.syncSysOut("T4 finished");
    }
}
