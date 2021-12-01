public class CriticalRegions {
    //копіювання спільного ресурсу MX
    static synchronized int[][] copyMX() { return Data.MX; }

    //копіювання спільного ресурсу MC
    static synchronized int[][] copyMC() {
        return Data.MC;
    }

    //копіювання спільного ресурсу MZ
    static synchronized int[][] copyMZ() {
        return Data.MZ;
    }

    //копіювання спільного ресурсу b
    static synchronized int copy_b() {
        return Data.b;
    }

    //копіювання спільного ресурсу c
    static synchronized int copy_c() {
        return Data.c;
    }
}
