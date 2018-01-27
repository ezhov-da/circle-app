package ru.ezhov.circle;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * Окно приложения
 * <p>
 * @author ezhov_da
 */
public class CircleApp extends JDialog
{
    private JPanel basicPanel;
    private CircleButton circleButtonLT;
    private CircleButton circleButtonRT;
    private CircleButton circleButtonLB;
    private CircleButton circleButtonRB;
    private int w;
    private int h;

    public CircleApp(int w, int h)
    {
        this.w = w;
        this.h = h;
        shape();
        setSize(w, h);
        init();
    }

    private void init()
    {
        setLayout(null);
        initBasicPanel();
        add(basicPanel);
    }

    private void initBasicPanel()
    {
        basicPanel = new JPanel(new GridLayout(2, 2));
        basicPanel.setSize(w - 2, h - 2);
        circleButtonLT = createCircleButton(Part.LEFT_TOP);
        circleButtonRT = createCircleButton(Part.RIGHT_TOP);
        circleButtonLB = createCircleButton(Part.LEFT_BUTTON);
        circleButtonRB = createCircleButton(Part.RIGHT_BUTTON);
        basicPanel.setLocation(1, 1);
    }

    private CircleButton createCircleButton(Part part)
    {
        CircleButton circleButton = new CircleButton(part, basicPanel);
        basicPanel.add(circleButton);
        return circleButton;
    }

    private void shape()
    {
        setUndecorated(true);
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                Area a1 = new Area(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
                setShape(a1);
            }
        });
    }

    @Override
    public void paint(Graphics g)
    {
        //Убираем шероховатости:
        //https://community.oracle.com/blogs/campbell/2006/07/19/java-2d-trickery-soft-clipping

        //Clear the background to black
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Create a translucent intermediate image in which we can perform
        // the soft clipping
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();

        GraphicsDevice graphicsDevice = ge.getDefaultScreenDevice();

        GraphicsConfiguration gc = graphicsDevice.getDefaultConfiguration();
        BufferedImage img =
                gc.createCompatibleImage(getWidth(), getHeight(),
                        Transparency.TRANSLUCENT);
        Graphics2D g2 = img.createGraphics();

        // Clear the image so all pixels have zero alpha
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Render our clip shape into the image.  Note that we enable
        // antialiasing to achieve the soft clipping effect.  Try
        // commenting out the line that enables antialiasing, and
        // you will see that you end up with the usual hard clipping.
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        // Here's the trick... We use SrcAtop, which effectively uses the
        // alpha value as a coverage value for each pixel stored in the
        // destination.  For the areas outside our clip shape, the destination
        // alpha will be zero, so nothing is rendered in those areas.  For
        // the areas inside our clip shape, the destination alpha will be fully
        // opaque, so the full color is rendered.  At the edges, the original
        // antialiasing is carried over to give us the desired soft clipping
        // effect.
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.setPaint(new GradientPaint(0, 0, Color.RED, 0, getHeight(), Color.YELLOW));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        // Copy our intermediate image to the screen
        g.drawImage(img, 0, 0, null);
        super.paint(g);
    }

    public CircleButton getCircleButtonLT()
    {
        return circleButtonLT;
    }

    public CircleButton getCircleButtonRT()
    {
        return circleButtonRT;
    }

    public CircleButton getCircleButtonLB()
    {
        return circleButtonLB;
    }

    public CircleButton getCircleButtonRB()
    {
        return circleButtonRB;
    }
}
