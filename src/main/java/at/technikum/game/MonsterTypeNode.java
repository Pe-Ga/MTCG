package at.technikum.game;

import at.technikum.application.model.card.ElementType;
import at.technikum.application.model.card.MonsterType;

import java.util.HashMap;
import java.util.Map;

public class MonsterTypeNode extends Node
{
    MonsterType monsterType;

    MonsterTypeNode(MonsterType type, SpecialCaseResult result)
    {
        super(result);
        this.monsterType = type;
    }

    public MonsterType getMonsterType()
    {
        return this.monsterType;
    }
}
