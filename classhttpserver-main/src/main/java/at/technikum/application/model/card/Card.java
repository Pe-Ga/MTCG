package at.technikum.application.model.card;

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

    public boolean isMonster()
    {
        return this.monsterType != MonsterType.Spell && this.monsterType != null;
    }

    public float calculatedDamage(Card card)
    {
        float factor;

        if(card == null )
            factor = 0;
        else
            if(this.isMonster() && card.isMonster())
                factor = 1;
            else
            {
                factor = switch (this.getElementType())
                {
                    case Fire -> switch (card.getElementType())
                    {
                        case Water -> 0.5f;
                        case Fire -> 1.0f;
                        case Normal -> 2.0f;
                    };
                    case Water -> switch (card.getElementType())
                    {
                        case Water -> 1.0f;
                        case Fire -> 2.0f;
                        case Normal -> 0.5f;
                    };
                    case Normal -> switch (card.getElementType())
                    {
                        case Water -> 2.0f;
                        case Fire -> 0.5f;
                        case Normal -> 1.0f;
                    };
                };
            }
            return this.baseDamage * factor;
    }

    @Override
    public String toString()
    {
        return this.elementType.name() + " "+ this.monsterType.name() + " ( " + this.baseDamage + " Damage )";
    }
}
