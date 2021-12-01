public class T1 extends Thread {
    private final SyncMonitor syncMonitor;

    public T1(SyncMonitor syncMonitor) {
        this.syncMonitor = syncMonitor;
    }

    @Override
    public void run() {
        syncMonitor.syncSysOut("T1 started");

        //введення MD
        Data.MD = Data.getMatrixFilledWithValue(1);
        //введення C
        Data.C = Data.getVectorFilledWithValue(1);

        //сигнал про введення значень
        syncMonitor.sigInput();

        //чекати сигнал про введення всіх значень
        syncMonitor.waitForInputAllValues();

        //визначення діапазону обрахунку матриць та векторів
        int start = 0;
        int end = Data.H;

        //обрахунок матриць та векторів
        Data.evaluate(start, end);

        //сигнал про обчислення MAh
        syncMonitor.sigCalcMAh();

        syncMonitor.syncSysOut("T1 finished");
    }
}
