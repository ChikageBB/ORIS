package ru.itis.dis403.ui.client;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

public class GraphDemoClient extends JFrame {

    public GraphDemoClient() throws IOException {
        super("game");

        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //createBufferStrategy(2);
        setLayout(new BorderLayout());

        add(new GComponentClient());
        System.out.println("============================");
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> {
                    try {
                        new GraphDemoClient();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
