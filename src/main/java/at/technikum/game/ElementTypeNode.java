package at.technikum.game;

import at.technikum.application.model.card.ElementType;
import at.technikum.application.model.card.MonsterType;

import java.util.HashMap;
import java.util.Map;

public class ElementTypeNode extends Node
{
    ElementType elementType;

    ElementTypeNode(ElementType type, SpecialCaseResult result)
    {
        super(result);
        this.elementType = type;
    }

    public ElementType getElementType()
    {
        return this.elementType;
    }
}
