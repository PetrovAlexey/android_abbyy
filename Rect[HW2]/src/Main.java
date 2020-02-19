import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Main {

    static void ShowArray(Integer[][] arr) {
        for(int i=0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println("\n");
        }
    }

    public static void OrderArray(Integer[][] arr) {
        int a = arr.length;
        for (int i=0; i < a; i++) {
            Arrays.sort(arr[i]);
            Collections.reverse(Arrays.asList(arr[i]));
        }
    }

    public static void main(String[] args) {
        Integer[][] ints = new Integer[6][7];
        Random rnd = new Random();
        for(int i=0; i < 6; i++) {
            for (int j=0; j < 7; j++) {
                ints[i][j] = rnd.nextInt(10);
            }
        }
        ShowArray(ints);
        OrderArray(ints);
        System.out.println("Sorted:");
        ShowArray(ints);

    }
}
