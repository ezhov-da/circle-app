package ru.ezhov.circle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT;
import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

/**
 * @author ezhov_da
 */
public class CircleAppTest {

    public static void main(String[] args) {
        // Determine what the GraphicsDevice can support.
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        final boolean isTranslucencySupported =
                gd.isWindowTranslucencySupported(TRANSLUCENT);

        //If shaped windows aren't supported, exit.
        if (!gd.isWindowTranslucencySupported(PERPIXEL_TRANSPARENT)) {
            System.err.println("Shaped windows are not supported");
            System.exit(0);
        }
        //If translucent windows aren't supported,
        //create an opaque window.
        if (!isTranslucencySupported) {
            System.out.println(
                    "Translucency is not supported, creating an opaque window");
        }

        SwingUtilities.invokeLater(() ->
        {
            try {
                //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Throwable ex) {
                //
            }
            CircleApp frame = new CircleApp(80, 80);

            CircleButton lt = frame.getCircleButtonLT();
            lt.setGradient(new Gradient(Color.magenta, Color.black, Color.CYAN, Color.green));
            //lt.setBorder(border);
            CircleButton rt = frame.getCircleButtonRT();
            rt.setGradient(new Gradient(Color.magenta, Color.black, Color.CYAN, Color.green));
            //rt.setBorder(border);
            CircleButton lb = frame.getCircleButtonLB();
            lb.setGradient(new Gradient(Color.magenta, Color.black, Color.CYAN, Color.green));
            //lb.setBorder(border);
            CircleButton rb = frame.getCircleButtonRB();
            rb.setGradient(new Gradient(Color.magenta, Color.black, Color.CYAN, Color.green));
            //rb.setBorder(border);
            MouseMoveWindowListener mouseMoveWindowListener = new MouseMoveWindowListener(frame);
            rb.addMouseListener(mouseMoveWindowListener);
            rb.addMouseMotionListener(mouseMoveWindowListener);

            lt.setIcon(new ImageIcon(CircleAppTest.class.getResource("/add_task.png")));
            rt.setIcon(new ImageIcon(CircleAppTest.class.getResource("/delete_16x16.png")));
            lb.setIcon(new ImageIcon(CircleAppTest.class.getResource("/folder-tree_16x16.png")));
            rb.setIcon(new ImageIcon(CircleAppTest.class.getResource("/initiator_16x16.png")));

            lt.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    JOptionPane.showMessageDialog(null, "GOOD");
                }

            });

            frame.setLocationByPlatform(true);
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);
        });
    }

}
