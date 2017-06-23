import BubbleSort.BubbleSort;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by acer on 05.12.2016.
 */
public class serverSort {
    public static int n;
    public static int CountServerSort = 0;
    public static int signal = 0;
    static String[] Arrayinfo = new String[100];

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(127);
        Socket s = ss.accept();

        for (int i = 0; i < 2; i++) {
            System.out.println(Arrayinfo[i]);
        }
        OutputStream outfile = new FileOutputStream("log.txt", true);
        InputStream input = s.getInputStream();
        DataInputStream in = new DataInputStream(input);
        OutputStream out = s.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);

        while (true) {

            n = in.readInt();
            signal = in.readInt();
            dos.writeInt(n);
            if (n != 0 || signal == 0) {
                Arrayinfo[CountServerSort] = ("Sorted " + n + " number the PROGRESS");
                ThreadRun T1 = new ThreadRun(n, CountServerSort);
                T1.SetEndAction(new EndAction() {
                    @Override
                    public void ActionPerformed(ThreadRun t) {
                        Arrayinfo[t.visibleC()]=("Sorted "+t.GetN()+" number the END");
                    }
                });

                CountServerSort = CountServerSort + 1;
            }


            if (n == 0 || signal != 0) {
                for (int i = 0; i < CountServerSort; i++) {
                    dos.writeUTF(Arrayinfo[i]);
                }
            } else if (n == 123450) {
                s.close();
            }


        }

    }


    public static class ThreadRun implements Runnable {
        EndAction ea;
        private int C;
        private int m;
        public  int GetN(){
            return m;
        }


        ThreadRun(int N, int CountS) throws IOException {
            C = CountS;
            ea = null;
            m=N;
            Thread t = new Thread(this, "Worked thread with N= " + N);
            System.out.println("create " + t);

            t.start();

        }

        public void SetEndAction(EndAction e) {
            ea = e;
        }

        public int visibleC() {
            return C;
        }

        public void run() {
            {
                BubbleSort Sort = new BubbleSort();
                Sort.run(n);
                if (ea != null) {
                    ea.ActionPerformed(this);
                }

            }

        }
    }

}