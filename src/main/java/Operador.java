public class Operador {

    public static <T extends Comparable<T>> T getMin(T[] a) {
        T min = a[0];
        for (int i = 1; i < a.length; i++) {
            if(a[i] == null) continue;
            if (min.compareTo(a[i]) < 0) {
                min = a[i];
            }
        }
        return min;
    }

    public static <T extends Comparable<T>> T getMax(T[] a) {
        T max = a[0];
        for (int i = 1; i < a.length; i++) {
            if(a[i] == null) continue;
            if (max.compareTo(a[i]) > 0) {
                max = a[i];
            }
        }
        return max;
    }

    public static <T extends Comparable<T>> int getIndexOF(T[] a, T objetoBuscado) {
        int index = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i].equals(objetoBuscado)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
