import java.util.Arrays;

public class T2 extends Thread {
    private final SyncMonitor syncMonitor;

    public T2(SyncMonitor syncMonitor) {
        this.syncMonitor = syncMonitor;
    }

    public void run() {
        syncMonitor.syncSysOut("T2 started");

        //введення MX
        Data.MX = Data.getMatrixFilledWithValue(1);
        //введення MZ
        Data.MZ = Data.getMatrixFilledWithValue(1);

        //сигнал про введення значення
        syncMonitor.sigInput();

        //чекати сигнал про введення всіх значень
        syncMonitor.waitForInputAllValues();

        //визначення діапазону обрахунку матриць та векторів
        int start = Data.H;
        int end = 2 * Data.H;

        //обрахунок матриць та векторів
        Data.evaluate(start, end);

        //очікування сигналу про обчислення MAh
        syncMonitor.waitCalcMAh();

        //вивід MA
        syncMonitor.syncSysOut(Arrays.deepToString(Data.MA));

        syncMonitor.syncSysOut("T2 finished");
    }
}
