package at.technikum.game;

import at.technikum.application.model.card.ElementType;
import at.technikum.application.model.card.MonsterType;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

    SpecialCaseResult specialCaseResult;
    Map<MonsterType, Node> monsterChildren = new HashMap<>();
    Map<ElementType, Node> elementChildren = new HashMap<>();

    public Node(SpecialCaseResult result)
    {
        this.specialCaseResult = result;
    }

    public SpecialCaseResult getValue()
    {
        return this.specialCaseResult;
    }

    public void setValue(SpecialCaseResult result)
    {
        this.specialCaseResult = result;
    }

    public Node getElementChild(ElementType type)
    {
        return this.elementChildren.get(type);
    }

    public Node getMonsterChild(MonsterType type)
    {
        return this.monsterChildren.get(type);
    }

    public void setChild(Node child)
    {
        if (child != null)
        {
            if(child instanceof MonsterTypeNode) {
                this.monsterChildren.put(((MonsterTypeNode) child).getMonsterType(), child);
            }
            if(child instanceof ElementTypeNode)
                this.elementChildren.put(((ElementTypeNode) child).getElementType(), child);
        }
    }
}
