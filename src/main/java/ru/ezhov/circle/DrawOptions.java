package ru.ezhov.circle;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Хранилище настроек рисования для передачи
 * <p>
 * @author ezhov_da
 */
class DrawOptions
{
    Rectangle rectangleFigure;
    Point point;
    Rectangle rectangleInputCircle;
    Rectangle rectangleWithBorderOne;
    Rectangle rectangleWithBorderTwo;
    Point pointGradientStart;
    Point pointGradientEnd;
    Point pointIcon;

    public DrawOptions(
            Rectangle rectangleFigure,
            Point point,
            Rectangle rectangleInputCircle,
            Point pointGradientStart,
            Point pointGradientEnd
    )
    {
        this.rectangleFigure = rectangleFigure;
        this.point = point;
        this.rectangleInputCircle = rectangleInputCircle;
        this.pointGradientStart = pointGradientStart;
        this.pointGradientEnd = pointGradientEnd;
    }

    public Rectangle getRectangleFigure()
    {
        return rectangleFigure;
    }

    public Point getPoint()
    {
        return point;
    }

    public Rectangle getRectangleInputCircle()
    {
        return rectangleInputCircle;
    }

    public Rectangle getRectangleWithBorderOne()
    {
        return rectangleWithBorderOne;
    }

    public Rectangle getRectangleWithBorderTwo()
    {
        return rectangleWithBorderTwo;
    }

    public Point getPointGradientStart()
    {
        return pointGradientStart;
    }

    public Point getPointGradientEnd()
    {
        return pointGradientEnd;
    }

    public Point getPointIcon()
    {
        return pointIcon;
    }

    public void setRectangleWithBorderOne(Rectangle rectangleWithBorderOne)
    {
        this.rectangleWithBorderOne = rectangleWithBorderOne;
    }

    public void setRectangleWithBorderTwo(Rectangle rectangleWithBorderTwo)
    {
        this.rectangleWithBorderTwo = rectangleWithBorderTwo;
    }

    public void setPointIcon(Point pointIcon)
    {
        this.pointIcon = pointIcon;
    }

}
