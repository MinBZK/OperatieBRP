/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.toegangsbewaking.parser.grammar.DefaultGrammar;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.AbstractTokenizer;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.DefaultTokenizer;
import org.junit.Test;


public class BRPParserTest {

    @Test
    public void test() throws ParserException {
        List<String> lines = new ArrayList<String>();
        lines.add("( Geslacht= 'M') & (Leeftijd >=55) & ( (Provincie = 'U''trecht')");
        lines.add("of (Provincie = 'Noord Holland' ) ) & niet (Woonplaats = 'Utrecht')");
        
        long start = System.currentTimeMillis();
        for (int i =  1; i <= 1; i++) {
            AbstractTokenizer tokenizer = new DefaultTokenizer(lines);
            tokenizer.execute();
    
            Parser parser = new Parser(new DefaultGrammar());
            
            System.out.println("PARSE TREE: " + parser.bouwParseTree(tokenizer.getTokens()));
            tokenizer = null;
            parser = null;
        }
        long end = System.currentTimeMillis();
        
        System.out.println("Time in millis: " + (end - start));
    }

}

