package at.technikum.application.model.card;

public class Card
{

    private int id;
    private MonsterType monsterType;
    private ElementType elementType;

    private int ownerId;
    private int baseDamage;

    public Card(){};

    public Card(int id, MonsterType monsterType, ElementType elementType, int ownerId, int baseDamage)
    {
        this.id = id;
        this.monsterType = monsterType;
        this.elementType = elementType;
        this.ownerId = ownerId;
        this.baseDamage  =  baseDamage;
    }

    public Card(MonsterType monsterType, ElementType elementType, int baseDamage) {
        this.monsterType = monsterType;
        this.elementType = elementType;
        this.baseDamage = baseDamage;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    boolean isSpecialCase(Card card)
    {
        if(card.getMonsterType() == MonsterType.Goblin && this.monsterType == MonsterType.Dragon)
        {
            return true;
        }

        if (card.getMonsterType() == MonsterType.Wizzard && this.monsterType == MonsterType.Orc)
        {
            return true;
        }

        if (card.getMonsterType() == MonsterType.Knight && (this.monsterType == MonsterType.Spell && this.elementType == ElementType.Water))
        {
            return true;
        }

        if (card.getMonsterType() == MonsterType.Kraken && this.monsterType == MonsterType.Spell)
        {
            return true;
        }

        if ((card.getMonsterType() == MonsterType.Elve && card.getElementType() == ElementType.Fire) && this.monsterType == MonsterType.Dragon)
        {
            return true;
        }

    return false;
    }

    boolean specialCaseWon(Card card)
    {
        if(card.getMonsterType() == MonsterType.Goblin && this.monsterType == MonsterType.Dragon)
        {
            return true;
        }

        if (card.getMonsterType() == MonsterType.Wizzard && this.monsterType == MonsterType.Orc)
        {
            return false;
        }

        if (card.getMonsterType() == MonsterType.Knight && (this.monsterType == MonsterType.Spell && this.elementType == ElementType.Water))
        {
            return true;
        }

        if (card.getMonsterType() == MonsterType.Kraken && this.monsterType == MonsterType.Spell)
        {
            return false;
        }

        if ((card.getMonsterType() == MonsterType.Elve && card.getElementType() == ElementType.Fire) && this.monsterType == MonsterType.Dragon)
        {
            return false;
        }

        return false;
    }

    @Override
    public String toString() {
        String elementTypeString = (this.elementType != null) ? this.elementType.name() : "null";
        String monsterTypeString = (this.monsterType != null) ? this.monsterType.name() : "null";
        String baseDamageString = (this.baseDamage != 0) ? String.valueOf((this.baseDamage)) : "null";
        return elementTypeString + monsterTypeString + " (" + baseDamageString + " Damage)";
    }

    public String onlyNameToString()
    {
        String elementTypeString = (this.elementType != null) ? this.elementType.name() : "null";
        String monsterTypeString = (this.monsterType != null) ? this.monsterType.name() : "null";
        return elementTypeString+monsterTypeString;
    }

}
