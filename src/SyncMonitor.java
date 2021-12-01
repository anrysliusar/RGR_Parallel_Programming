public class SyncMonitor {
    private static int
            input = 0,
            calcMC_bc = 0,
            calcMA = 0;

    //синхронізований вивід в консоль
    synchronized void syncSysOut(String message) {
        System.out.println(message);
    }

    //сигнал про введення значення
    synchronized void sigInput() {
        input++;
        notify();
    }

    //чекати сигнал про введення всіх значень
    synchronized void waitForInputAllValues() {
        try {
            while (input != 3)
                wait();
            notify();
        } catch (Exception ignored) {
        }
    }

    //сигнал про обчислення MC, c, b
    synchronized void sigCalcMC_bc() {
        calcMC_bc++;
        notify();
    }

    //чекати сигнал про обчислення c, b, MC
    synchronized void waitCalcMC_bc() {
        try {
            while (calcMC_bc != 4)
                wait();
            notify();
        } catch (Exception ignored) {
        }
    }

    //сигнал про обчислення MAh
    synchronized void sigCalcMAh() {
        calcMA++;
        notify();
    }

    //чекати сигнал про обчислення MAh
    synchronized void waitCalcMAh() {
        try {
            while (calcMA != 3)
                wait();
            notify();
        } catch (Exception ignored) {
        }
    }
}
