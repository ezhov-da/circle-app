package ru.ezhov.circle;

import java.awt.Color;

/**
 * Цвета для кнопок с градиентом
 * <p>
 * @author ezhov_da
 */
public class Gradient
{
    private Color selectStart;
    private Color selectEnd;
    private Color descelectStart;
    private Color deselectEnd;

    public Gradient(
            Color selectStart,
            Color selectEnd,
            Color descelectStart,
            Color deselectEnd)
    {
        this.selectStart = selectStart;
        this.selectEnd = selectEnd;
        this.descelectStart = descelectStart;
        this.deselectEnd = deselectEnd;
    }

    public Color getSelectStart()
    {
        return selectStart;
    }

    public Color getSelectEnd()
    {
        return selectEnd;
    }

    public Color getDescelectStart()
    {
        return descelectStart;
    }

    public Color getDeselectEnd()
    {
        return deselectEnd;
    }


}
