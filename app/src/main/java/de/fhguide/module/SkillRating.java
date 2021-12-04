package de.fhguide.module;

import androidx.annotation.NonNull;

public class SkillRating
{
    private final Float[] rating;

    public SkillRating()
    {
        this.rating = new Float[Skill.values().length];
    }

    public float getRating(@NonNull Skill skill)
    {
        return this.rating[skill.ordinal()];
    }

    public float getRating(int skillOrd)
    {
        return this.rating[skillOrd];
    }

    public boolean isRatingSet(int skillOrd)
    {
        return this.rating[skillOrd] != null;
    }

    public boolean isRatingSet(Skill skill)
    {
        return this.rating[skill.ordinal()] != null;
    }

    public void setRating(@NonNull Skill skill, float value)
    {
        this.rating[skill.ordinal()] = value;
    }

    public void setRating(int skillOrd, float value)
    {
        this.rating[skillOrd] = value;
    }
}
