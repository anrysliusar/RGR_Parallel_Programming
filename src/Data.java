import java.util.Arrays;
import java.util.Random;


public class Data {
    static int
            N = 600,
            P = 4,
            H = N / P;

    static int[][]
            MA = new int[N][N],
            MX = new int[N][N],
            MR = new int[N][N],
            MM = new int[N][N],
            MZ = new int[N][N],
            MC = new int[N][N],
            MD = new int[N][N];
    static int[]
            B = new int[N],
            C = new int[N];
    static Integer b, c;

    //Монітор для синхронізації використання спільних ресурсів
    static SyncMonitor syncMonitor = new SyncMonitor();

    //приймає значення для діапазону обрахунку матриць та векторів і виконує підрахунки.
    public static void evaluate(int startIndex, int endIndex) {
        //чекати сигнал про введення всіх значень
        syncMonitor.waitForInputAllValues();

        // обчислення локальної змінної b1
        int bi = Data.minVector(Arrays.copyOfRange(Data.B, startIndex, endIndex));
        // критична ділянка для обчислення Спільного Ресурсу b
        CalcWithSemaphore.findMin_b(Data.b, bi);

        // обчислення локальної змінної с1
        int ci = Data.mulVectors(Arrays.copyOfRange(Data.B, startIndex, endIndex), Arrays.copyOfRange(Data.C, startIndex, endIndex));
        // критична ділянка для обчислення Спільного Ресурсу с
        CalcWithSemaphore.findSum_c(ci);

        // критична ділянка для обчислення Спільного Ресурсу MX
        int[][] MXi = CriticalRegions.copyMX();
        // критична ділянка для обчислення локальної змінної MCh
        int[][] MCh = Data.multiplyMatrices(Arrays.copyOfRange(Data.MR, startIndex, endIndex), MXi);

        // критична ділянка для обчислення Спільного Ресурсу MC
        if (Data.H >= 0) System.arraycopy(MCh, 0, Data.MC, startIndex, Data.H);

        //сигнал про обчислення MC, c, b
        syncMonitor.sigCalcMC_bc();

        //чекати сигнал про обчислення c, b, MC
        syncMonitor.waitCalcMC_bc();

        //критична ділянка для обчислення CP
        bi = CriticalRegions.copy_b();
        ci = CriticalRegions.copy_c();
        int[][] MCi = CriticalRegions.copyMC();
        int[][] MZi = CriticalRegions.copyMZ();

        // обчислення MAh
        int[][] MAh = Data.sumMatrix(Data.multiplyMatrixByValue(Data.multiplyMatrices(Arrays.copyOfRange(Data.MM, startIndex, endIndex), MCi), bi),
                Data.multiplyMatrixByValue(Data.multiplyMatrices(Arrays.copyOfRange(Data.MD, startIndex, endIndex), MZi), ci));
        if (Data.H >= 0) System.arraycopy(MAh, 0, Data.MA, startIndex, Data.H);
    }

    //повертає вектор заповнеий значеням
    public static int[] getVectorFilledWithValue(int value) {
        int[] vector = new int[N];
        Arrays.fill(vector, value);
        return vector;
    }

    //повертає вектор заповнеий випадковими значенями
    public static int[] getVectorUsingRandom(int upperBound) {
        int[] vector = new int[N];
        Random random = new Random();
        for (int i = 0; i < vector.length; i++) {
            vector[i] = random.nextInt(upperBound);
        }
        return vector;
    }

    //повертає матрицю заповнену значеням
    public static int[][] getMatrixFilledWithValue(int value) {
        int[][] matrix = new int[N][N];
        for (int[] ints : matrix) {
            Arrays.fill(ints, value);
        }
        return matrix;
    }

    //повертає матрицю заповнену випадковими значенями
    public static int[][] getMatrixUsingRandom() {
        int[][] matrix = new int[N][N];
        Random random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = random.nextInt(60);
            }
        }
        return matrix;
    }

    //виконує операцію множення векторів
    public static int mulVectors(int[] vector1, int[] vector2) {
        int result = 0;
        for (int i = 0; i < vector1.length; i++) {
            result += vector1[i] * vector2[i];
        }
        return result;
    }

    //виконує операцію множення матриць
    public static int[][] multiplyMatrices(int[][] firstMatrix, int[][] secondMatrix) {
        int row1 = firstMatrix.length;
        int col1 = firstMatrix[0].length;
        int col2 = secondMatrix[0].length;
        int[][] result = new int[N][N];

        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col2; j++) {
                for (int k = 0; k < col1; k++) {
                    result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        return result;
    }

    //виконує операцію додавання матриць
    public static int[][] sumMatrix(int[][] matrix1, int[][] matrix2) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix1[i][j] += matrix2[i][j];
            }
        }
        return matrix1;
    }

    //виконує операцію множення матриці на число
    public static int[][] multiplyMatrixByValue(int[][] matrix, int value) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] *= value;
            }
        }
        return matrix;
    }

    //виконує операцію пошуку мінімуму вектору
    public static int minVector(int[] vector) {
        int minimum = vector[0];
        for (int num : vector) {
            if (minimum > num) minimum = num;
        }
        return minimum;
    }
}
