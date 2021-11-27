package de.fhguide.module;

public class SkillRating
{
    private final float[] rating;

    public SkillRating()
    {
        this.rating = new float[Skill.values().length];
    }

    public float getRating(Skill skill)
    {
        return this.rating[skill.ordinal()];
    }

    public float getRating(int skillOrd)
    {
        return this.rating[skillOrd];
    }

    public void setRating(Skill skill, float value)
    {
        this.rating[skill.ordinal()] = value;
    }

    public void setRating(int skillOrd, float value)
    {
        this.rating[skillOrd] = value;
    }
}
