package at.technikum.game;

import at.technikum.application.model.card.Card;
import at.technikum.application.model.card.ElementType;
import at.technikum.application.model.card.MonsterType;

import java.util.HashMap;
import java.util.Map;

public class SpecialCaseSet
{
    Node rootNode = new Node(SpecialCaseResult.NO_SPECIAL_CASE);

    public void addSpecialCase(MonsterType card1_mt, ElementType card1_et, MonsterType card2_mt, ElementType card2_et, SpecialCaseResult specialCaseResult)
    {
        Node firstLevelNode = this.rootNode.getMonsterChild(card1_mt);
        Node secondLevelNode = null;
        Node thirdLevelNode = null;
        Node fourthLevelNode = null;

        // create the first level node if not already existing
        if(firstLevelNode == null)
            firstLevelNode = new MonsterTypeNode(card1_mt, SpecialCaseResult.NO_SPECIAL_CASE);

        // create the second level node if present
        if(card1_et != null)
        {
            secondLevelNode = firstLevelNode.getElementChild(card1_et);
            if (secondLevelNode == null)
                secondLevelNode = new ElementTypeNode(card1_et, SpecialCaseResult.NO_SPECIAL_CASE);
        }

        // create the third level node
        if (secondLevelNode != null) {
            thirdLevelNode = secondLevelNode.getMonsterChild(card2_mt);
        }
        if (thirdLevelNode == null)
            thirdLevelNode = new MonsterTypeNode(card2_mt, card2_et == null ? specialCaseResult : SpecialCaseResult.NO_SPECIAL_CASE);

        // create the fourth level node if present
        if(card2_et != null)
            fourthLevelNode = new ElementTypeNode(card2_et, specialCaseResult);

        // assemble full sub-tree

        rootNode.setChild(firstLevelNode);

        if(secondLevelNode != null)
        {
            firstLevelNode.setChild(secondLevelNode);
            secondLevelNode.setChild(thirdLevelNode);
        }
        else
            firstLevelNode.setChild(thirdLevelNode);

        if(fourthLevelNode != null)
            thirdLevelNode.setChild(fourthLevelNode);
    }

    public SpecialCaseResult evaluate(Card card1, Card card2)
    {
        // iterate over all 4 levels of special case result decision tree
        MonsterType card1_mt = card1.getMonsterType();
        MonsterType card2_mt = card2.getMonsterType();
        ElementType card1_et = card1.getElementType();
        ElementType card2_et = card2.getElementType();

        // System.out.println("Evaluating special cases in: " + card1.onlyNameToString() + " <--> " + card2.onlyNameToString());

        Node firstLevelNode;
        Node secondLevelNode;
        Node thirdLevelNode;
        Node fourthLevelNode;


        if (rootNode != null)
        {
            firstLevelNode = rootNode.getMonsterChild(card1_mt);

        }
        else
            return SpecialCaseResult.NO_SPECIAL_CASE;


        if(firstLevelNode != null)
        {
            // System.out.println("Found first level node: " + ((MonsterTypeNode)firstLevelNode).getMonsterType() + " with value " + firstLevelNode.getValue());
            secondLevelNode = firstLevelNode.getElementChild(card1_et);

        }
        else
            return SpecialCaseResult.NO_SPECIAL_CASE;


        if (secondLevelNode != null)
        {
            // System.out.println("Found second level node: " + ((ElementTypeNode)secondLevelNode).getElementType() + " with value " + secondLevelNode.getValue());
            thirdLevelNode = secondLevelNode.getMonsterChild(card2_mt);

        }
        else
        {
            thirdLevelNode = firstLevelNode.getMonsterChild(card2_mt);

        }


        if (thirdLevelNode != null)
        {
            //System.out.println("Found third level node: " + ((MonsterTypeNode)thirdLevelNode).getMonsterType() + " with value " + thirdLevelNode.getValue());
            fourthLevelNode = thirdLevelNode.getElementChild(card2_et);

        }
        else
        {
            //System.out.println("Couldnt find a third level node. Returning value of either 1st or 2nd level.");
            return secondLevelNode == null ? firstLevelNode.getValue() : secondLevelNode.getValue();
        }


        if(fourthLevelNode != null)
        {
            //System.out.println("Found fourth level node: " + ((ElementTypeNode)fourthLevelNode).getElementType() + " with value " + fourthLevelNode.getValue());
            return fourthLevelNode.getValue();
        }
        else
            return thirdLevelNode.getValue();
    }
}
