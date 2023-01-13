package at.technikum.card;

import java.lang.annotation.ElementType;

public class Card
{

    private MonsterType monsterType;
    private ElementType elementType;

    private int baseDamage;

    public Card(MonsterType monsterType, ElementType elementType, int baseDamage)
    {
        this.monsterType = monsterType;
        this.elementType = elementType;
        this.baseDamage  =  baseDamage;
    }

    public MonsterType getMonsterType()
    {
        return monsterType;
    }

    public void setMonsterType(MonsterType monsterType)
    {
        this.monsterType = monsterType;
    }

    public ElementType getElementType()
    {
        return elementType;
    }

    public void setElementType(ElementType elementType)
    {
        this.elementType = elementType;
    }

    public int getBaseDamage()
    {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage)
    {
        this.baseDamage = baseDamage;
    }


    @Override
    public String toString() {
        return this.elementType.name() + this.monsterType.name() + " (" + this.baseDamage + " Damage)";
    }
}
