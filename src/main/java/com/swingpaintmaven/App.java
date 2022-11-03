package com.swingpaintmaven;

import java.awt.BasicStroke;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.MenuBar;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.coobird.thumbnailator.*;

public class App {
    private JPanel gui;
    private BufferedImage image;
    private BufferedImage canvasImage;
    private JLabel imageLabel;
    private Stroke stroke = new BasicStroke(
            3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.7f);
    private JPanel westMenu;
    private JPanel eastMenu;
    private JButton option1;
    private JButton option2;
    private JButton option3;
    private JButton option4;
    private JButton option5;
    private JButton option6;
    private Color foregroundColor = Color.black;
    private int brushSize = 7;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public JComponent getContentPane() {
        if (gui == null) {

            setImage(new BufferedImage(750, 750, BufferedImage.TYPE_INT_RGB));
            gui = new JPanel(new BorderLayout(20, 4));
            gui.setBorder(new EmptyBorder(5, 3, 5, 3));

            JPanel imageView = new JPanel(new GridBagLayout());
            JScrollPane imageScroll = new JScrollPane(imageView);
            imageView.setPreferredSize(new Dimension(750, 750));
            imageLabel = new JLabel(new ImageIcon(canvasImage));
            imageView.add(imageLabel);

            option1 = new JButton("small brush");
            option2 = new JButton("big brush");
            option3 = new JButton("black");
            option4 = new JButton("orange");
            option5 = new JButton("clear canvas");
            option6 = new JButton("eraser");

            westMenu = new JPanel();
            eastMenu = new JPanel();
            westMenu.setBorder(new LineBorder(Color.black));
            eastMenu.setBorder(new LineBorder(Color.black));

            westMenu.add(option1);
            westMenu.add(option2);
            westMenu.add(option6);
            eastMenu.add(option3);
            eastMenu.add(option4);
            eastMenu.add(option5);

            gui.add(westMenu, BorderLayout.WEST);
            gui.add(eastMenu, BorderLayout.EAST);
            gui.add(imageScroll, BorderLayout.CENTER);

            option1.addActionListener(e -> {
                setBrushSize(7);
            });
            gui.add(imageScroll, BorderLayout.CENTER);
            option2.addActionListener(e -> {
                setBrushSize(15);
            });
            option3.addActionListener(e -> {
                setBrushColor(Color.black);
            });
            option4.addActionListener(e -> {
                setBrushColor(Color.orange);
            });
            option5.addActionListener(e -> {
                clear(canvasImage);
            });
            option6.addActionListener(e -> {
                setBrushColor(Color.white);
            });
            imageLabel.addMouseMotionListener(new ImageMouseMotionListener());
            clear(canvasImage); // initial clear to set the canvas to white
        }
        return gui;
    }

    private static void createAndShowGUI() {
        App app = new App();
        // Create and set up the window.
        JFrame frame = new JFrame("App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.

        frame.setContentPane(app.getContentPane());

        // Display the window.
        frame.pack();

        frame.setMinimumSize(new Dimension(100, 200));
        frame.setVisible(true);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        int w = image.getWidth();
        int h = image.getHeight();
        canvasImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = this.canvasImage.createGraphics();
        // display the canvas
        g.drawImage(image, 0, 0, gui);
        g.dispose();

    }

    public void draw(Point point) {
        Graphics2D g = this.canvasImage.createGraphics();
        g.setColor(foregroundColor);
        g.setStroke(stroke);
        g.fillOval(point.x, point.y, brushSize, brushSize);
        g.dispose();
        this.imageLabel.repaint();
    }

    public void clear(BufferedImage img) {
        Graphics2D g = img.createGraphics();
        Color oldColor = foregroundColor;
        g.setColor(Color.white);
        g.fillRect(0, 0, 2000, 2000);
        g.dispose();
        this.imageLabel.repaint();
        g.setColor(oldColor);
    }

    public void setBrushSize(int size) {
        brushSize = size;
    }

    public void setBrushColor(Color color) {
        foregroundColor = color;
    }

    // will be used to resize the canvas
    public static BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
        return Thumbnails.of(img).size(newW, newH).asBufferedImage();
    }

    class ImageMouseMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            draw(e.getPoint());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // TODO Auto-generated method stub

        }

    }

}
