/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser.processer;

import nl.bzk.brp.toegangsbewaking.parser.OperatorType;
import nl.bzk.brp.toegangsbewaking.parser.ParseTree;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import nl.bzk.brp.toegangsbewaking.parser.node.BinaireOperatorNode;
import nl.bzk.brp.toegangsbewaking.parser.node.IdentifierNode;
import nl.bzk.brp.toegangsbewaking.parser.node.ListNode;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.Token;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.TokenType;
import org.junit.Test;


public class BaseProcesserTest {

    @Test
    public void test() throws ParserException {
        ListNode listNode = new ListNode(null, new Token(null, 0, 0));
        listNode.add(new IdentifierNode(listNode, new Token(TokenType.IDENTIFIER, 0, 1), "TEST"));
        listNode.add(new BinaireOperatorNode(listNode, new Token(TokenType.SYMBOL, 0, 2), OperatorType.GELIJK));
        listNode.add(new IdentifierNode(listNode, new Token(TokenType.IDENTIFIER, 0, 3), "TEST"));
        listNode.add(new BinaireOperatorNode(listNode, new Token(TokenType.SYMBOL, 0, 3), OperatorType.EN));
        listNode.add(new IdentifierNode(listNode, new Token(TokenType.IDENTIFIER, 0, 1), "TEST"));
        listNode.add(new BinaireOperatorNode(listNode, new Token(TokenType.SYMBOL, 0, 2), OperatorType.GELIJK));
        listNode.add(new IdentifierNode(listNode, new Token(TokenType.IDENTIFIER, 0, 3), "TEST"));

        ParseTree parseTree = new ParseTree(listNode);

        JPQLFilterProcesser proc = new JPQLFilterProcesser();
        System.out.println(proc.process(parseTree));
    }

}
