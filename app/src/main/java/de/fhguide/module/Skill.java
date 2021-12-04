package de.fhguide.module;

import android.graphics.Color;

public enum Skill
{
    JAVA("skill.java.name", Color.argb(255, 200, 50, 50)),
    LOGIC("skill.logic.name", Color.argb(255, 50, 50, 200)),
    MATH("skill.math.name", Color.argb(255, 255, 50, 50));

    private final String name;
    private final int color;

    private Skill(String name, int color)
    {
        this.name = name;
        this.color = color;
    }

    public String getName()
    {
        return this.name;
    }

    public int getColor()
    {
        return this.color;
    }
}
