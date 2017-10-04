
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GUIclient {
    private static int info;
    private static int CountSort = 0;
    private static int N;
    private final JTextField jtpX0 = new JTextField(5);
    private String[] ArrayStr = new String[100];
    private String[] a = new String[100];
    private final JLabel TextStatus = new JLabel();


    public static class ImagePanel extends JPanel {

        private BufferedImage image;

        public ImagePanel() {
            try {
                image = ImageIO.read(new File("C:\\Users\\acer\\IdeaProjects\\ClientBubbleGUI\\src\\resurs\\backGround.jpg"));
            } catch (IOException ex) {

            }
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(image, 0, 0, null);
        }
    }


    GUIclient() throws IOException {
        final Socket s = new Socket("127.0.0.1", 127);
        JFrame frame = new JFrame(" Programm <BubbleSort>");
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        frame.setSize(800, 400);
        JButton b1 = new JButton("Accept");
        JButton b2 = new JButton("Status");
        JButton b3 = new JButton("Open folder outputFile");

        frame.setContentPane(new ImagePanel());
        final Container cont = frame.getContentPane();
        frame.setContentPane(cont);

        GridBagLayout gbl1 = new GridBagLayout();
        cont.setLayout(gbl1);

        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0;
        c.weighty = 0;


        final JTextArea TextStatus1 = new JTextArea();
        final JScrollPane scrollInf1 = new JScrollPane(TextStatus1,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        final JTextArea TextStatus2 = new JTextArea();
        final JScrollPane scrollInf2 = new JScrollPane(TextStatus2,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        c.insets = new Insets(0, 0, 85, 0);
        c.ipadx = 200;
        c.ipady = 200;
        gbl1.setConstraints(scrollInf1, c);
        cont.add(scrollInf1);


        c.insets = new Insets(0, 500, 85, 0);
        gbl1.setConstraints(scrollInf2, c);
        scrollInf2.getViewportBorder();
        cont.add(scrollInf2);


        JLabel LabelN = new JLabel("Number sort");
        LabelN.setForeground(Color.GREEN);
        c.insets = new Insets(0, 0, 400, 500);
        gbl1.setConstraints(LabelN, c);
        cont.add(LabelN);

        c.insets = new Insets(0, 0, 260, 490);
        c.ipadx = 100;
        c.ipady = 10;
        gbl1.setConstraints(jtpX0, c);
        cont.add(this.jtpX0);

        c.insets = new Insets(0, 0, 150, 636);
        c.ipadx = 0;
        c.ipady = 0;
        gbl1.setConstraints(b1, c);
        cont.add(b1);

        c.insets = new Insets(0, 0, 150, 458);
        gbl1.setConstraints(b2, c);
        cont.add(b2);

        c.insets = new Insets(0, 0, 60, 550);
        c.ipadx = 40;
        gbl1.setConstraints(b3, c);
        cont.add(b3);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ++GUIclient.CountSort;
                GUIclient.this.a[0] = GUIclient.this.jtpX0.getText();
                InputStream input = null;

                try {
                    input = s.getInputStream();
                } catch (IOException var9) {
                    var9.printStackTrace();
                }

                assert input != null;

                DataInputStream in = new DataInputStream(input);
                GUIclient.N = Integer.parseInt(GUIclient.this.a[0]);
                System.out.println("Send N = " + GUIclient.N);
                OutputStream out = null;

                try {
                    out = s.getOutputStream();
                } catch (IOException var8) {
                    var8.printStackTrace();
                }

                DataOutputStream dos = new DataOutputStream(out);

                try {
                    assert out != null;

                    dos.writeInt(GUIclient.N);
                    dos.writeInt(0);
                    GUIclient.info = in.readInt();
                } catch (IOException var7) {
                    var7.printStackTrace();
                }

                String L2 = "Start thread sort with " + GUIclient.info + " number.\n";
                TextStatus1.append(L2);
                scrollInf1.repaint();
            }
        });
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InputStream input = null;

                try {
                    input = s.getInputStream();
                } catch (IOException var11) {
                    var11.printStackTrace();
                }

                DataInputStream in = new DataInputStream(input);
                OutputStream out = null;

                try {
                    out = s.getOutputStream();
                } catch (IOException var10) {
                    var10.printStackTrace();
                }

                DataOutputStream dos = new DataOutputStream(out);

                try {
                    dos.writeInt(0);
                    dos.writeInt(66);
                    GUIclient.info = in.readInt();
                } catch (IOException var9) {
                    var9.printStackTrace();
                }

                GUIclient.this.TextStatus.setText("");

                for (int i = 0; i < GUIclient.CountSort; ++i) {
                    try {
                        GUIclient.this.ArrayStr[i] = in.readUTF();
                    } catch (IOException var8) {
                        var8.printStackTrace();
                    }

                    System.out.println(GUIclient.this.ArrayStr[i] + "\n");
                    TextStatus2.append(ArrayStr[i] + "\n");
                    scrollInf2.repaint();
                }

            }
        });

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("explorer C:\\Users\\acer\\IdeaProjects\\ClientBubbleGUI\\src\\outputFile");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] agrs) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GUIclient();
                } catch (IOException var2) {
                    var2.printStackTrace();
                }

            }
        });
    }
}
