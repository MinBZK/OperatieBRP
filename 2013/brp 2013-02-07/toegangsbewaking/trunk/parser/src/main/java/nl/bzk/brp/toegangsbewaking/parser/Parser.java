/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.toegangsbewaking.parser.grammar.Grammar;
import nl.bzk.brp.toegangsbewaking.parser.node.ArgumentSeparatorNode;
import nl.bzk.brp.toegangsbewaking.parser.node.BinaireOperatorNode;
import nl.bzk.brp.toegangsbewaking.parser.node.FunctieNode;
import nl.bzk.brp.toegangsbewaking.parser.node.IdentifierNode;
import nl.bzk.brp.toegangsbewaking.parser.node.IntegerNode;
import nl.bzk.brp.toegangsbewaking.parser.node.ListNode;
import nl.bzk.brp.toegangsbewaking.parser.node.Node;
import nl.bzk.brp.toegangsbewaking.parser.node.StringNode;
import nl.bzk.brp.toegangsbewaking.parser.node.UnaireOperatorNode;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.Token;
import nl.bzk.brp.toegangsbewaking.parser.tokenizer.TokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Specifieke parser voor BRP filter/toegangsbewakings expressies. Deze parser zet, op basis van een specifieke
 * {@link Grammar}, een lijst van {@link Token} instanties om naar een "syntax tree", ook wel {@link ParseTree} genoemd.
 * Deze parse tree kan daarna door een doel-specifieke {@link nl.bzk.brp.toegangsbewaking.parser.processer.Processer}
 * worden verwerkt tot een doel-specifieke filter, zoals bijvoorbeeld een SQL of JPQL WHERE-Clause snippet, die dan
 * gebruikt kan worden voor het filteren van te retourneren objecten op basis van de toegangsbewakings expressies.
 */
public class Parser {

    private static final Logger LOGGER             = LoggerFactory.getLogger(Parser.class);

    private static final String ARGUMENT_SEPARATOR = ",";

    private final Grammar       grammar;

    /**
     * Constructor methode die een nieuwe instantie van een parser creeert op basis van de meegeleverde specifieke
     * {@link Grammar}. Deze parser kan dan gebruikt worden voor het parsen van een lijst van tokens.
     *
     * @param grammar de specifieke grammer conform welke de parser lijsten van tokens kan parsen.
     */
    public Parser(final Grammar grammar) {
        this.grammar = grammar;
    }

    /**
     * Zet de meegeleverde lijst van tokens om naar een {@link ParseTree}. Het doet dit door eerst bepaalde tokens
     * (conform de gedefinieerde {@link Grammar}) te vervangen, dan de tokens om te zetten naar nodes en uiteindelijk
     * de nodes om te zetten naar een parse tree.
     *
     * @param tokens de lijst van tokens in de tree.
     * @return de opgebouwde parse tree.
     * @throws ParserException indien er fouten optreden tijdens de parsing of het opbouwen van de parse tree.
     */
    public ParseTree bouwParseTree(final List<Token> tokens) throws ParserException {
        ParseTree result;
        if (tokens.isEmpty()) {
            LOGGER.warn("Geen tokens gevonden voor parsing; lege parse tree geretourneerd.");
            result = new ParseTree();
        } else {
            List<Token> tokensNaVervanging = kopierOfVervangTokens(tokens);
            ListNode listNode = zetTokensOmInNodes(tokensNaVervanging);

            result = zetListNodeOmInParseTree(listNode);
        }

        return result;
    }

    /**
     * Kopieert alle tokens uit de opgegeven lijst van tokens naar een nieuwe lijst van tokens en vervangt de in de
     * {@link Grammar} gedefinieerde identifiers/tokens voor de tevens in de {@link Grammar} geconfigureerde
     * vervangings identifiers. De positie en lijnnummer van de vervangings tokens worden tevens aangepast naar
     * dezelfde waarde als de token die ze vervangen in verband met traceerbaarheid van problemen.
     *
     * @param tokens lijst van tokens die moet worden gekopieerd of vervangen.
     * @return nieuwe lijst van tokens.
     */
    private List<Token> kopierOfVervangTokens(final List<Token> tokens) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Tokens voor vervanging: %s", tokens));
        }

        List<Token> nieuweTokens = new ArrayList<Token>();
        for (Token token : tokens) {
            if (token.getType() == TokenType.IDENTIFIER && grammar.isTeVervangenIdentifier(token.getText())) {
                List<Token> vervangingsTokens = grammar.getVervangingVoorIdentifier(token.getText());
                vervangingsTokens = pasTokenPositieAanVoorVervangingsTokens(vervangingsTokens, token);

                nieuweTokens.addAll(vervangingsTokens);
            } else {
                nieuweTokens.add(token.clone());
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Tokens na vervanging: %s", nieuweTokens));
        }
        return nieuweTokens;
    }

    /**
     * Retourneert een lijst van tokens gelijk aan de meegeleverde lijst van {@code vervangingsTokens}, maar waarbij
     * de positie en lijn nummer van de vervangings tokens zijn gezet naar dezelfde waarde als die van het token dat
     * ze vervangen. Dit in verband met de traceerbaarheid van mogelijk optredende fouten.
     *
     * @param vervangingsTokens de nieuwe tokens die de oude token vervangen.
     * @param token de token die is vervangen.
     * @return lijst van vervangingstokens, maar waarbij de positie en lijnnummer is gezet naar die van het vervangen
     *         token.
     */
    private List<Token> pasTokenPositieAanVoorVervangingsTokens(final List<Token> vervangingsTokens, final Token token)
    {
        List<Token> result = new ArrayList<Token>(vervangingsTokens.size());

        for (Token vervangToken : vervangingsTokens) {
            Token tokenKloon = vervangToken.clone();

            // Maak positie gelijk aan oorspronkelijke token
            tokenKloon.setLine(token.getLine());
            tokenKloon.setPosition(token.getPosition());

            result.add(tokenKloon);
        }
        return result;
    }

    /**
     * Zet een lijst van nodes om naar een parse tree en retourneert die.
     *
     * @param listNode de {@link ListNode} instantie die alle onderliggende nodes bevat.
     * @return de parse tree.
     * @throws ParserException indien er fouten optreden tijdens de parsing of omzetting.
     */
    private ParseTree zetListNodeOmInParseTree(final ListNode listNode) throws ParserException {
        ParseTree parseTree = new ParseTree(listNode);

        if (LOGGER.isDebugEnabled()) {
            List<String> res = new ArrayList<String>();
            parseTree.getRootNode().debugDump(res, 0);
            LOGGER.debug(String.format("Nodes na binding: %s", res));
        }

        return parseTree;
    }

    /**
     * Zet de lijst van tokens om in een lijst van nodes.
     *
     * @param tokens een lijst van tokens die moet worden omgezet.
     * @return lijst van nodes.
     * @throws ParserException indien er fouten optreden bij het omzetten van de tokens.
     */
    private ListNode zetTokensOmInNodes(final List<Token> tokens) throws ParserException {
        ListNode listNode = new ListNode(null, null);

        int laatsteTokenPositie = voegTokensToeAanNodeList(tokens, listNode, 0);

        // Niet alle tokens omgezet?
        if (laatsteTokenPositie < tokens.size()) {
            Token token = tokens.get(laatsteTokenPositie);
            throw new ParserException(String.format("Syntax error: %s", token.getText()), token);
        }

        if (LOGGER.isDebugEnabled()) {
            List<String> res = new ArrayList<String>();
            listNode.debugDump(res, 0);
            LOGGER.debug(String.format("Nodes na omzetting van tokens: %s", res));
        }
        return listNode;
    }

    /**
     * Zet een lijst van tokens om naar de betreffende node, op basis van het token type, en voegt deze toe aan de
     * lijst van nodes. De lijst van tokens wordt afgelopen tot het eind, of totdat er een 'einde groepering' token
     * wordt tegengekomen.
     *
     * @param tokens de lijst van tokens die moet worden toegevoegd.
     * @param listNode de {@link ListNode} waaraan de in nodes omgezette tokens moeten worden toegevoegd.
     * @param recursieNiveau het recursieniveau waarin de parser zich bevindt.
     * @return de positie van de laatst verwerkte token.
     * @throws ParserException indien er fouten optreden bij het omzetten van de tokens.
     */
    private int voegTokensToeAanNodeList(final List<Token> tokens, final ListNode listNode, final int recursieNiveau)
            throws ParserException
    {
        int positie = 0;
        int niveau = recursieNiveau;

        boolean continueOuterLoop = true;

        while (positie < tokens.size() && continueOuterLoop) {
            Token token = tokens.get(positie);
            Node node = null;

            switch (token.getType()) {
                case IDENTIFIER:
                    node = zetIdentifierTokenOmInNode(listNode, token);
                    break;
                case SYMBOL:
                    node = zetSymboolTokenOmInNode(listNode, token);
                    break;
                case STRING:
                    node = new StringNode(listNode, token, token.getText());
                    break;
                case GROEPERING:
                    if (token.getText().equals("(")) {
                        node = new ListNode(listNode, token);
                        positie +=
                            voegTokensToeAanNodeList(tokens.subList(positie + 1, tokens.size()), (ListNode) node,
                                    recursieNiveau + 1);
                    } else {
                        niveau--;
                        continueOuterLoop = false;
                    }
                    break;
                case INTEGER:
                    node = new IntegerNode(listNode, token, token.getI());
                    break;
                case COMMENT:
                    // Comments zouden verwijderd moeten zijn door de tokenizer
                    throw new ParserException(String.format("Interne fout. Onbekend soort token: %s", token.getText()),
                            token);
                case FLOAT:
                    throw new ParserException(String.format("Interne fout. Onbekend soort token: %s", token.getText()),
                            token);
                default:
                    throw new ParserException(String.format("Interne fout. Onbekend soort token: %s", token.getText()),
                            token);
            }

            if (node != null) {
                listNode.add(node);
            }
            positie++;
        }

        // Als je er hier uitknalt met een recursieniveau > 0 en <= niveau, dan heb je een haakje gemist
        if (recursieNiveau > 0 && recursieNiveau <= niveau) {
            throw new ParserException("Er ontbreekt een haakje.", tokens.get(tokens.size() - 1));
        }
        return positie;
    }

    /**
     * Zet specifiek een identifier token om in een node. Hierbij wordt gekeken of de identifier een operator is, een
     * functie of een 'echte' identifier.
     *
     * @param listNode de {@link ListNode} waartoe de nieuwe node behoort. De 'parent' node dus.
     * @param token de token die omgezet dient te worden.
     * @return de uit het token omgezette node.
     * @throws ParserException indien er fouten optreden bij het omzetten.
     */
    private Node zetIdentifierTokenOmInNode(final ListNode listNode, final Token token) throws ParserException {
        Node node = null;

        // Controleer of er sprake is van een Operator
        if (grammar.isOperatorIdentifier(token.getText())) {
            OperatorType type = grammar.getOperatorTypeVoorOperatorIdentifier(token.getText());
            // Functies zitten in een andere lijst!
            assert type != OperatorType.FUNCTIE;

            if (type.isUnaireOperator()) {
                node = new UnaireOperatorNode(listNode, token, type);
            } else {
                node = new BinaireOperatorNode(listNode, token, type);
            }
        } else if (grammar.isFunctie(token.getText())) {
            // Controleer of er sprake is van een Functie
            int aantalArgumenten = grammar.getAantalArgumentenVoorFunctie(token.getText());
            node = new FunctieNode(listNode, token, OperatorType.FUNCTIE, token.getText(), aantalArgumenten);
        } else if (grammar.isIdentifier(token.getText())) {
            // Controleer of er sprake is van een Identifier
            node = new IdentifierNode(listNode, token, token.getText());
        }

        if (node == null) {
            throw new ParserException(String.format("Onbekende identifier: %s", token.getText()), token);
        }
        return node;
    }

    /**
     * Zet specifiek een symbool token om in een node. Hierbij wordt gekeken of het symbool een operator is of een
     * argument afscheidingsteken.
     *
     * @param listNode de {@link ListNode} waartoe de nieuwe node behoort. De 'parent' node dus.
     * @param token de token die omgezet dient te worden.
     * @return de uit het token omgezette node.
     * @throws ParserException indien er fouten optreden bij het omzetten.
     */
    private Node zetSymboolTokenOmInNode(final ListNode listNode, final Token token) throws ParserException {
        Node node = null;

        // Controleer of er sprake is van een Operator
        if (grammar.isOperatorSymbool(token.getText())) {
            OperatorType type = grammar.getOperatorTypeVoorOperatorSymbool(token.getText());
            // Functies zitten in een andere lijst!
            assert type != OperatorType.FUNCTIE;

            if (type.isUnaireOperator()) {
                node = new UnaireOperatorNode(listNode, token, type);
            } else {
                node = new BinaireOperatorNode(listNode, token, type);
            }
        } else if (token.getText().equals(ARGUMENT_SEPARATOR)) {
            // Controle op komma (argument separator)
            node = new ArgumentSeparatorNode(listNode, token);
        }

        if (node == null) {
            throw new ParserException(String.format("Onbekend symbool: %s", token.getText()), token);
        }
        return node;
    }

}
