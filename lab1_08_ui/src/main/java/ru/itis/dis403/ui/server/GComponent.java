package ru.itis.dis403.ui.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GComponent extends JComponent {
    private int x, y, bounds = 64;
    private boolean moved_up, moved_left;
    private int speed = 3;

    private int bYS = 300, bYC = 300;

    private Image image;

    private Socket clientSocket;


    public GComponent(Socket clientSocket) {
        this.clientSocket = clientSocket;

        image = new ImageIcon("ball.png").getImage();

        setDoubleBuffered(true);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                bYS = e.getY();
            }
        });

        Timer timer = new Timer(10, e -> {
            if (x < 0) {
                moved_left = false;
            }

            if (x > getWidth() - bounds) {
                moved_left = true;
            }

            if (y > getHeight() - bounds) {
                moved_up = true;
            }

            if (moved_left) {
                x -= speed;
            } else {
                x += speed;
            }

            if (moved_up) {
                y -= speed;
            } else {
                y += speed;
            }

            try {
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                dos.write(bYS);
                dos.write(x);
                dos.write(y);

                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                bYS = dis.readInt();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image,x,y,null);

        g2d.fillRect(10, bYS, 10, 200);
        g2d.fillRect(this.getWidth() - 20, bYC, 10, 200);

        g2d.dispose();

        Toolkit.getDefaultToolkit().sync();
    }

}
