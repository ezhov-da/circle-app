package ru.ezhov.circle;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Кнопка
 * <p>
 *
 * @author ezhov_da
 */
public class CircleButton extends JPanel implements MouseListener {

    private static final Color SELECT_START = Color.BLUE;
    private static final Color SELECT_END = Color.WHITE;
    private static final Color DESELECT_START = Color.BLACK;
    private static final Color DESELECT_END = Color.WHITE;

    private Gradient gradient;
    private Color colorNowStart = DESELECT_START;
    private Color colorNowEnd = DESELECT_END;
    private Part part;
    private Component parent;
    private Icon icon;
    private Area areaInputCircle;
    private DrawButtons drawButtons;

    public CircleButton(Part part, Component parent) {
        setLayout(null);
        this.part = part;
        this.parent = parent;
        drawButtons = new DrawButtons();
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHints(rh);

        drawButtons.draw(graphics2D);

    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        initSelectColor();
    }

    private void initSelectColor() {
        if (gradient != null) {
            colorNowStart = gradient.getSelectStart();
            colorNowEnd = gradient.getSelectEnd();
        } else {
            colorNowStart = SELECT_START;
            colorNowEnd = SELECT_END;
        }

        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        initDescelectColor();
    }

    private void initDescelectColor() {
        if (gradient != null) {
            colorNowStart = gradient.getDescelectStart();
            colorNowEnd = gradient.getDeselectEnd();
        } else {
            colorNowStart = DESELECT_START;
            colorNowEnd = DESELECT_END;
        }

        repaint();
    }

    public Gradient getGradient() {
        return gradient;
    }

    public void setGradient(Gradient gradient) {
        this.gradient = gradient;
        initDescelectColor();
    }

    //Помощник в рисовании кнопки
    private class DrawButtons {
        private Area a1;
        private int cX;
        private int cY;
        private Border border;
        private int w;
        private int h;
        private int x;
        private int y;
        private int wHalf;
        private int hHalf;
        private int wC;
        private int hC;

        private Point2D.Float p1;
        private Point2D.Float p2;
        private GradientPaint g2;
        private Graphics2D graphics2D;

        public void draw(Graphics2D graphics2D) {
            this.graphics2D = graphics2D;
            w = parent.getWidth();
            h = parent.getHeight();
            x = getX();
            y = getY();
            wHalf = w / 2;
            hHalf = h / 2;
            border = getBorder();
            wC = (int) (w * 20 / 100);
            hC = (int) (h * 20 / 100);

            switch (part) {
                case LEFT_TOP:
                    drawLT();
                    break;
                case RIGHT_TOP:
                    drawRT();
                    break;
                case LEFT_BUTTON:
                    drawLB();
                    break;
                case RIGHT_BUTTON:
                    drawRB();
                    break;
            }
        }

        private void drawLT() {
            DrawOptions drawOptions =
                    new DrawOptions(
                            new Rectangle(x, y, w, h),
                            new Point(wHalf, hHalf),
                            new Rectangle(cX - wC / 2, cY - hC / 2, wC, hC),
                            new Point(0, 0),
                            new Point(w, h));
            if (border != null) {
                Insets inserts = border.getBorderInsets(CircleButton.this);
                drawOptions.setRectangleWithBorderOne(
                        new Rectangle(wHalf - inserts.left - inserts.right, 0, wHalf + inserts.left + inserts.right, h));
                drawOptions.setRectangleWithBorderOne(
                        new Rectangle(0, hHalf - inserts.bottom - inserts.top, w, hHalf + inserts.bottom + inserts.top));
            }
            if (icon != null) {
                int iw = icon.getIconWidth();
                int ih = icon.getIconHeight();
                drawOptions.setPointIcon(new Point((w / 2) - iw - iw / 2, (h / 2) - ih - ih / 2));
            }
            drawButton(drawOptions);
        }

        private void drawButton(DrawOptions drawOptions) {
            Rectangle rectangleFigure = drawOptions.getRectangleFigure();
            Point point = drawOptions.getPoint();
            Rectangle rectangleInputCircle = drawOptions.getRectangleInputCircle();
            Rectangle rectangleWithBorderOne = drawOptions.getRectangleWithBorderOne();
            Rectangle rectangleWithBorderTwo = drawOptions.getRectangleWithBorderTwo();
            Point pointGradientStart = drawOptions.getPointGradientStart();
            Point pointGradientEnd = drawOptions.getPointGradientEnd();
            Point pointIcon = drawOptions.getPointIcon();

            drawFigure(
                    rectangleFigure.x,
                    rectangleFigure.y,
                    rectangleFigure.width,
                    rectangleFigure.height);
            setCenterXY(point.x, point.y);
            createInputCircleAndSubtract(
                    rectangleInputCircle.x,
                    rectangleInputCircle.y,
                    rectangleInputCircle.width,
                    rectangleInputCircle.height);

            if (rectangleWithBorderOne != null && rectangleWithBorderTwo != null) {
                executeWithBorder(
                        rectangleWithBorderOne.x, rectangleWithBorderOne.y, rectangleWithBorderOne.width, rectangleWithBorderOne.height,
                        rectangleWithBorderTwo.x, rectangleWithBorderTwo.y, rectangleWithBorderTwo.width, rectangleWithBorderTwo.height);
            }
            paintGradientStart(pointGradientStart.x, pointGradientStart.y);
            paintGradientEnd(pointGradientEnd.x, pointGradientEnd.y);
            drawGradient();
            if (pointIcon != null) {
                drawIcon(pointIcon.x, pointIcon.y);
            }

        }

        private void drawFigure(int x, int y, int w, int h) {
            a1 = new Area(new Ellipse2D.Double(x, y, w, h));
        }

        private void setCenterXY(int x, int y) {
            cX = x;
            cY = y;
        }

        private void createInputCircleAndSubtract(int x, int y, int w, int h) {
            areaInputCircle = new Area(new Ellipse2D.Double(x, y, w, h));
            a1.subtract(areaInputCircle);
        }

        private void executeWithBorder(
                int x1, int y1, int w1, int h1,
                int x2, int y2, int w2, int h2) {
            Area r1 = new Area(new Rectangle2D.Double(x1, y1, w1, h1));
            Area r2 = new Area(new Rectangle2D.Double(x2, y2, w2, h2));
            a1.subtract(r1);
            a1.subtract(r2);
        }

        private void paintGradientStart(int x, int y) {
            p1 = new Point2D.Float(x, y);
        }

        private void paintGradientEnd(int x, int y) {
            p2 = new Point2D.Float(x, y);
        }

        private void drawGradient() {
            g2 = new GradientPaint(p1, colorNowStart, p2, colorNowEnd, false);
            graphics2D.setPaint(g2);
            graphics2D.fill(a1);
        }

        private void drawIcon(int x, int y) {
            icon.paintIcon(CircleButton.this, graphics2D, x, y);
        }

        private void drawRT() {
            DrawOptions drawOptions =
                    new DrawOptions(
                            new Rectangle(-wHalf, y, w, h),
                            new Point(0, h / 2),
                            new Rectangle(cX - wC / 2, cY - hC / 2, wC, hC),
                            new Point(w, 0),
                            new Point(0, h));
            if (border != null) {
                Insets inserts = border.getBorderInsets(CircleButton.this);
                drawOptions.setRectangleWithBorderOne(
                        new Rectangle(0, 0, inserts.right + inserts.left, h));
                drawOptions.setRectangleWithBorderOne(
                        new Rectangle(0, hHalf - inserts.bottom - inserts.top, w, hHalf + inserts.bottom + inserts.top));
            }
            if (icon != null) {
                int iw = icon.getIconWidth();
                int ih = icon.getIconHeight();
                drawOptions.setPointIcon(new Point(iw / 2, (h / 2) - ih - ih / 2));
            }
            drawButton(drawOptions);
        }

        private void drawLB() {
            DrawOptions drawOptions =
                    new DrawOptions(
                            new Rectangle(x, -hHalf, w, h),
                            new Point(w / 2, 0),
                            new Rectangle(cX - wC / 2, cY - hC / 2, wC, hC),
                            new Point(0, h),
                            new Point(w, 0));
            if (border != null) {
                Insets inserts = border.getBorderInsets(CircleButton.this);
                drawOptions.setRectangleWithBorderOne(
                        new Rectangle(wHalf - inserts.left - inserts.right, 0, wHalf + inserts.left + inserts.right, h));
                drawOptions.setRectangleWithBorderOne(
                        new Rectangle(0, 0, w, inserts.top + inserts.bottom));
            }
            if (icon != null) {
                int iw = icon.getIconWidth();
                int ih = icon.getIconHeight();
                drawOptions.setPointIcon(new Point((w / 2) - iw - iw / 2, ih / 2));
            }
            drawButton(drawOptions);
        }

        private void drawRB() {

            DrawOptions drawOptions =
                    new DrawOptions(
                            new Rectangle(-wHalf, -hHalf, w, h),
                            new Point(0, 0),
                            new Rectangle(cX - wC / 2, cY - hC / 2, wC, hC),
                            new Point(w, h),
                            new Point(0, 0));
            if (border != null) {
                Insets inserts = border.getBorderInsets(CircleButton.this);
                drawOptions.setRectangleWithBorderOne(
                        new Rectangle(0, 0, inserts.right + inserts.left, h));
                drawOptions.setRectangleWithBorderOne(
                        new Rectangle(0, 0, w, inserts.top + inserts.bottom));
            }
            if (icon != null) {
                int iw = icon.getIconWidth();
                int ih = icon.getIconHeight();
                drawOptions.setPointIcon(new Point(iw / 2, ih / 2));
            }
            drawButton(drawOptions);
        }
    }

}
