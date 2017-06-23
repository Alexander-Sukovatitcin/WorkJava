import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;


public class clientGUI {

    private Socket s;
    public static int info;
    public static int CountSort = 0;
    public static int N;
    final JTextField jtpX0 = new JTextField(5);
    final String[] a = new String[100000000];
    final JTextArea TextInfo = new JTextArea();
        String[] ArrayStr = new String[100];
    final JTextArea TextStatus = new JTextArea();
    clientGUI() throws IOException {


        final Socket s = new Socket("127.0.0.1", 127);

        final JFrame frame = new JFrame(" Programm <BubbleSort>");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(250, 250);
        final JFrame frameInfo = new JFrame("Info");
        frameInfo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameInfo.setVisible(true);
        frameInfo.setSize(500, 500);

        JButton b1 = new JButton("Accept");
        JButton b2 = new JButton("Status");

        final JPanel Backpanel = new JPanel();
        Backpanel.setLayout(new GridLayout(1, 2));
        JPanel Panelbutton = new JPanel();
        final JPanel PanelInfo = new JPanel();
        frame.add(Backpanel);
        Backpanel.add(Panelbutton);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;


        PanelInfo.setBackground(Color.WHITE);
        frameInfo.add(PanelInfo);
        PanelInfo.setLayout(new GridLayout(1, 2));


        PanelInfo.add(TextStatus);
        PanelInfo.add(TextInfo);

        JPanel PanelbuttonSecond = new JPanel();
        Panelbutton.add(PanelbuttonSecond);
        PanelbuttonSecond.setLayout(new GridLayout(3, 1));
        JLabel LabelN = new JLabel("Number sort");
        PanelbuttonSecond.add(LabelN);
        PanelbuttonSecond.add(jtpX0);
        PanelbuttonSecond.add(b1);
        PanelbuttonSecond.add(b2);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CountSort++;
                a[0] = jtpX0.getText();
                InputStream input = null;
                try {
                    input = s.getInputStream();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                assert input != null;
                DataInputStream in = new DataInputStream(input);
                N = Integer.parseInt(a[0]);
                System.out.println("Send N = " + N);
                OutputStream out = null;
                try {
                    out = s.getOutputStream();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                DataOutputStream dos = new DataOutputStream(out);

                try {
                    assert out != null;
                    dos.writeInt(N);
                    dos.writeInt(0);
                    info = in.readInt();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                String L2 = ("Start thread sort with " + info + " number.\n");
                TextInfo.append(L2);
                PanelInfo.add(TextInfo);
                PanelInfo.repaint();


            }

        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                InputStream input = null;
                try {
                    input = s.getInputStream();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                DataInputStream in = new DataInputStream(input);
                OutputStream out = null;
                try {
                    out = s.getOutputStream();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                DataOutputStream dos = new DataOutputStream(out);
                try {
                    dos.writeInt(0);
                    dos.writeInt(66);
                    info = in.readInt();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                TextStatus.setText("");
                for(int i=0;i<CountSort;i++)
                {
                    try {

                        ArrayStr[i]= in.readUTF();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println(ArrayStr[i]+"\n");
                    TextStatus.append(ArrayStr[i]+"\n");
                    PanelInfo.add(TextStatus);
                    PanelInfo.repaint();
                }

            }

        });


    }


    public static void main(String[] agrs) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new clientGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
