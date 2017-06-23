package BubbleSort;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;


public class BubbleSort {
    public static void run(int n){
        int N ;
        N=n;
        System.out.print(" Start solving ");
        int a[] = new int[N];
        for (int i = 0; i < a.length; i++) {

            a[i] = (int) (Math.random() * (N * 10));
        }

        System.out.print("\n");
        bubbleSort(a);
        if(Sorted(a)==false)
            System.out.print("Not Sorted");
        else System.out.println("Sorted "+N+" number");
OutSortInFile(a,n);

    }

    public static void OutSortInFile(int[] a,int n){
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileOutputStream("Sorted_"+n+".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.println("Sorted massive");
        for (int i = 0; i < a.length; i++) {
            out.print(a[i] + "  ");
        }


        out.close();
    }

    public static void bubbleSort(int[] a) {
        for (int i = a.length - 1; i > 0; i--)
            for (int j = 0; j < i; j++) {
                if (a[j] > a[j + 1]) {
                    int b = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = b;
                }
            }
    }
    public static boolean Sorted(int[] a)
    {
        for(int i = a.length - 1; i > 0; i--)
            if(a[i]<a[i-1])
                return false;
        return true;
    }
}
