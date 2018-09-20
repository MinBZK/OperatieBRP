/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.expressietaal.lexical.LexicalAnalyzer;
import nl.bzk.brp.expressietaal.lexical.tokens.EndOfLineToken;
import nl.bzk.brp.expressietaal.lexical.tokens.Token;
import nl.bzk.brp.expressietaal.lexical.tokens.TokenStack;
import nl.bzk.brp.expressietaal.lexical.tokens.TokenSubtype;
import nl.bzk.brp.expressietaal.lexical.tokens.TokenType;
import nl.bzk.brp.expressietaal.parser.syntaxtree.AttribuutExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.BooleanANDOperatorExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.BooleanNegateExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.BooleanOROperatorExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;
import nl.bzk.brp.expressietaal.parser.syntaxtree.IndexedAttribuutExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ListElementExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.ListExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.NumericNegateExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.OperatorExpressieFactory;
import nl.bzk.brp.expressietaal.parser.syntaxtree.UnqualifiedIndexedAttribuutExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.VariableExpressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.functies.FunctieALLE;
import nl.bzk.brp.expressietaal.parser.syntaxtree.functies.FunctieERIS;
import nl.bzk.brp.expressietaal.parser.syntaxtree.functies.FunctieFactory;
import nl.bzk.brp.expressietaal.symbols.Attributes;
import nl.bzk.brp.expressietaal.symbols.BmrSymbolTableFactory;
import nl.bzk.brp.expressietaal.symbols.Keywords;
import nl.bzk.brp.expressietaal.symbols.SymbolTable;

/**
 * Implementeert interface Parser.
 * Vertaalt een string of lijst tokens (TokenStack) naar een Expressie, een abstracte syntax tree van de tokens.
 */
public class ParserImpl implements Parser {
    private TokenStack stack;
    private SymbolTable symbolTable;

    /**
     * {@inheritDoc}
     */
    @Override
    public ParserResultaat parse(final String expressie, final LexicalAnalyzer lexicalAnalyzer) {
        ParserResultaat result;
        TokenStack tokens = lexicalAnalyzer.tokenize(expressie);
        if (tokens != null) {
            if (tokens.succes()) {
                result = parse(tokens);
            } else {
                result = new ParserResultaat(new ParserFout(tokens.getFout(),
                        new EndOfLineToken(tokens.getFoutPositie())));
            }
        } else {
            result = new ParserResultaat(new ParserFout(ParserFoutCode.SYNTAX_ERROR, new EndOfLineToken(0)));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ParserResultaat parse(final TokenStack tokenStack) {
        ParserResultaat result;
        ParserFout fout;
        symbolTable = BmrSymbolTableFactory.createSymbolTable();

        if (tokenStack == null || tokenStack.size() == 0) {
            fout = new ParserFout(ParserFoutCode.EXPRESSIE_VERWACHT, new EndOfLineToken(0));
            result = new ParserResultaat(fout);
        } else {
            stack = tokenStack;
            ParserContext context = new ParserContext();
            context.addIdentifier(new Identifier("persoon", ExpressieType.PERSOON));
            ParserResultaat exp = parseExpression(context);
            fout = exp.getFout();

            // check end of line
            if (fout == null && !stack.finished()
                    && stack.currentToken().getTokenType() != TokenType.END_OF_LINE)
            {
                if (exp.getExpressie() == null) {
                    fout = new ParserFout(ParserFoutCode.SYNTAX_ERROR, stack.currentToken());
                } else {
                    fout = new ParserFout(ParserFoutCode.EINDE_EXPRESSIE_VERWACHT, stack.currentToken());
                }
                result = new ParserResultaat(fout);
            } else {
                result = new ParserResultaat(exp.getExpressie(), fout);
            }
        }
        return result;
    }

    /**
     * Parse een boolean expressie volgens afleidingsregel:
     * <booleanExpression> ::= <booleanTerm> [OF <booleanExpression>].
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Resultaatexpressie of null, indien fout.
     */
    private ParserResultaat parseExpression(final ParserContext context) {
        Token startToken = stack.currentToken();
        ParserResultaat termLinks = parseBooleanTerm(context);
        ParserFout fout = termLinks.getFout();
        Expressie exp = termLinks.getExpressie();
        if (termLinks.succes() && stack.matchKeyword(Keywords.BOOLEAN_OR, true)) {
            ParserFoutCode typeFout = ParserUtils.checkType(exp, ExpressieType.BOOLEAN);
            if (typeFout == ParserFoutCode.GEEN_FOUT) {
                startToken = stack.currentToken();
                ParserResultaat termRechts = parseExpression(context);
                typeFout = ParserUtils.checkType(termRechts, ExpressieType.BOOLEAN);
                if (typeFout == ParserFoutCode.GEEN_FOUT) {
                    exp = new BooleanOROperatorExpressie(termLinks.getExpressie(),
                            termRechts.getExpressie());
                } else {
                    fout = new ParserFout(typeFout, startToken);
                }
            } else {
                fout = new ParserFout(typeFout, startToken);
            }

        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Parse een boolean term volgens afleidingsregel: <booleanTerm> ::= <equalityExpression> [EN <booleanTerm>].
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Resultaatexpressie of null, indien fout.
     */
    private ParserResultaat parseBooleanTerm(final ParserContext context) {
        Token startToken = stack.currentToken();
        ParserResultaat termLinks = parseEqualityExpression(context);
        ParserFout fout = termLinks.getFout();
        Expressie exp = termLinks.getExpressie();
        if (termLinks.succes() && stack.matchKeyword(Keywords.BOOLEAN_AND, true)) {
            ParserFoutCode typeFout = ParserUtils.checkType(termLinks, ExpressieType.BOOLEAN);
            if (typeFout == ParserFoutCode.GEEN_FOUT) {
                startToken = stack.currentToken();
                ParserResultaat termRechts = parseBooleanTerm(context);
                typeFout = ParserUtils.checkType(termRechts, ExpressieType.BOOLEAN);
                if (typeFout == ParserFoutCode.GEEN_FOUT) {
                    exp = new BooleanANDOperatorExpressie(termLinks.getExpressie(),
                            termRechts.getExpressie());
                } else {
                    fout = new ParserFout(typeFout, startToken);
                }
            } else {
                fout = new ParserFout(typeFout, startToken);
            }
        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Parse een gelijkheidstestexpressie volgens afleidingsregel:
     * <equalityExpression> ::= <relationalExpression> [<equalityOperator> <relationExpression>].
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Resultaatexpressie of null, indien fout.
     */
    private ParserResultaat parseEqualityExpression(final ParserContext context) {
        ParserResultaat termLinks = parseRelationalExpression(context);
        ParserFout fout = termLinks.getFout();
        Expressie exp = termLinks.getExpressie();
        if (termLinks.succes() && stack.matchEqualityOperator(false)) {
            Token operator = stack.shift();
            Token startToken = stack.currentToken();
            ParserResultaat termRechts = parseRelationalExpression(context);
            if (termRechts.succes()) {
                ParserFoutCode typeFout = ParserUtils.checkComparedTypes(
                        termLinks.getExpressie(), termRechts.getExpressie());
                if (typeFout == ParserFoutCode.GEEN_FOUT) {
                    exp = OperatorExpressieFactory.getOperatorExpressie(operator.getTokenSubtype(),
                            termLinks.getExpressie(), termRechts.getExpressie());
                } else {
                    fout = new ParserFout(typeFout, startToken);
                }
            }
        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Parse een vergelijkingsexpressie volgens afleidingsregel:
     * <relationalExpression> ::= <expression> [<relationOperator> <expression>].
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Resultaatexpressie of null, indien fout.
     */
    private ParserResultaat parseRelationalExpression(final ParserContext context) {
        ParserResultaat termLinks = parseOrdinalExpression(context);
        ParserFout fout = termLinks.getFout();
        Expressie exp = termLinks.getExpressie();
        if (termLinks.succes() && stack.matchRelationalOperator(false)) {
            if (stack.matchNextToken(TokenType.OPERATOR, false)) {
                Token operator = stack.shift();
                Token startToken = stack.currentToken();
                ParserResultaat termRechts = parseOrdinalExpression(context);
                ParserFoutCode typeFout = ParserUtils.checkType(termRechts, termLinks.getType());
                if (typeFout == ParserFoutCode.GEEN_FOUT) {
                    exp = OperatorExpressieFactory.getOperatorExpressie(operator
                            .getTokenSubtype(),
                            termLinks.getExpressie(), termRechts.getExpressie());
                } else {
                    fout = new ParserFout(typeFout, startToken);
                }
            } else if (stack.matchKeyword(Keywords.IN, true)) {
                Token startToken = stack.currentToken();
                ParserResultaat termRechts = parseExpressionList(context);
                ParserFoutCode typeFout = ParserUtils.checkType(termRechts, ExpressieType.LIST);
                if (typeFout == ParserFoutCode.GEEN_FOUT) {
                    ParserResultaat lijstExpressie = creeerElementInLijstExpressie(termLinks.getExpressie(),
                            termRechts.getExpressie(), startToken);
                    fout = lijstExpressie.getFout();
                    exp = lijstExpressie.getExpressie();
                } else {
                    fout = new ParserFout(typeFout, startToken);
                }
            }
        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Maak een ListElementExpressie-object (volgens regel "elementExpressie IN lijstExpressie").
     * Controleer de types van de expressies. Geef null als de types incompatible zijn.
     *
     * @param elementExpressie Te zoeken element.
     * @param lijstExpressie   Lijst waarin gezocht moet worden.
     * @param startToken       Token waarmee de lijst begint.
     * @return Het Expressie-object of null, indien een fout is opgetreden.
     */
    private ParserResultaat creeerElementInLijstExpressie(final Expressie elementExpressie,
                                                          final Expressie lijstExpressie, final Token startToken)
    {
        Expressie exp = null;
        ParserFout fout = null;
        if (lijstExpressie.getType() == ExpressieType.LIST) {
            ListExpressie list = (ListExpressie) lijstExpressie;
            for (Expressie e : list.getElements()) {
                ParserFoutCode typeFout = ParserUtils.checkType(e, elementExpressie.getType());
                if (typeFout != ParserFoutCode.GEEN_FOUT) {
                    fout = new ParserFout(typeFout, startToken);
                    break;
                }
            }
            if (fout == null) {
                exp = new ListElementExpressie(elementExpressie, list);
            }
        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Parse een expressie volgens afleidingsregel: <expression> ::= <factor> [<additiveOperator> <expression>].
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Het Expressie-object of null, indien een fout is opgetreden.
     */
    private ParserResultaat parseOrdinalExpression(final ParserContext context) {
        ParserResultaat termLinks = parseFactor(context);
        ParserFout fout = termLinks.getFout();
        Expressie exp = termLinks.getExpressie();
        if (termLinks.succes() && stack.matchAdditiveOperator(false)) {
            TokenSubtype operator = stack.shift().getTokenSubtype();
            Token startToken = stack.currentToken();

            ParserFoutCode typeFout = ParserUtils.checkOrdinalType(termLinks.getExpressie());

            if (typeFout == ParserFoutCode.GEEN_FOUT) {
                ParserResultaat termRechts = parseOrdinalExpression(context);

                typeFout = ParserUtils.checkType(termRechts, termLinks.getType());

                if (typeFout == ParserFoutCode.GEEN_FOUT) {
                    exp = OperatorExpressieFactory.getOperatorExpressie(operator,
                            termLinks.getExpressie(), termRechts.getExpressie());
                } else {
                    fout = new ParserFout(typeFout, startToken);
                }
            } else {
                fout = new ParserFout(typeFout, startToken);
            }

        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Parse een factor volgens afleidingsregel:
     * <factor> ::= <negatableExpression> [<multiplicativeOperator> <factor>].
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Het Expressie-object of null, indien een fout is opgetreden.
     */
    private ParserResultaat parseFactor(final ParserContext context) {
        ParserResultaat termLinks = parseNegatableExpression(context);
        ParserFout fout = termLinks.getFout();
        Expressie exp = termLinks.getExpressie();
        if (termLinks.succes() && stack.matchMultiplicativeOperator(false)) {
            TokenSubtype operator = stack.shift().getTokenSubtype();
            Token startToken = stack.currentToken();
            ParserFoutCode typeFout = ParserUtils.checkType(termLinks, ExpressieType.NUMBER);
            if (typeFout == ParserFoutCode.GEEN_FOUT) {
                ParserResultaat termRechts = parseFactor(context);

                typeFout = ParserUtils.checkType(termRechts, termLinks.getType());

                if (typeFout == ParserFoutCode.GEEN_FOUT) {
                    exp = OperatorExpressieFactory.getOperatorExpressie(operator,
                            termLinks.getExpressie(), termRechts.getExpressie());
                } else {
                    fout = new ParserFout(typeFout, startToken);
                }
            } else {
                fout = new ParserFout(typeFout, startToken);
            }
        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Parse een omkeerbare expressie volgens afleidingsregel:
     * <negatableExpression> ::= [<negationOperator>] <unaryExpression>.
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Het Expressie-object of null, indien een fout is opgetreden.
     */
    private ParserResultaat parseNegatableExpression(final ParserContext context) {
        Expressie exp;
        ParserFout fout;
        if (stack.matchNextToken(TokenType.OPERATOR, TokenSubtype.MINUS, true)) {
            Token startToken = stack.currentToken();
            ParserResultaat term = parseUnaryExpression(context);
            fout = term.getFout();
            exp = term.getExpressie();
            if (fout == null) {
                if (exp != null) {
                    ParserFoutCode typeFout = ParserUtils.checkType(term, ExpressieType.NUMBER);
                    if (typeFout == ParserFoutCode.GEEN_FOUT) {
                        exp = new NumericNegateExpressie(term.getExpressie());
                    } else {
                        fout = new ParserFout(typeFout, startToken);
                    }
                } else {
                    fout = new ParserFout(ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, startToken);
                }
            }
        } else if (stack.matchKeyword(Keywords.BOOLEAN_NOT, true)) {
            Token startToken = stack.currentToken();
            ParserResultaat term = parseUnaryExpression(context);
            fout = term.getFout();
            exp = term.getExpressie();
            if (fout == null) {
                if (exp != null) {
                    ParserFoutCode typeFout = ParserUtils.checkType(term, ExpressieType.BOOLEAN);
                    if (typeFout == ParserFoutCode.GEEN_FOUT) {
                        exp = new BooleanNegateExpressie(term.getExpressie());
                    } else {
                        fout = new ParserFout(typeFout, startToken);
                    }
                } else {
                    fout = new ParserFout(ParserFoutCode.BOOLEAN_EXPRESSIE_VERWACHT, startToken);
                }
            }
        } else {
            ParserResultaat unary = parseUnaryExpression(context);
            exp = unary.getExpressie();
            fout = unary.getFout();
        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Parse een expressie tussen haakjes volgens afleidingsregel: <unaryExpression> ::= ( <booleanExpression> ).
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Het Expressie-object of null, indien een fout is opgetreden.
     */
    private ParserResultaat parseBracketedExpression(final ParserContext context) {
        Expressie exp = null;
        ParserFout fout = null;
        if (stack.matchNextToken(TokenType.BRACKET, TokenSubtype.LEFT_BRACKET, true)) {
            ParserResultaat subExp = parseExpression(context);
            exp = subExp.getExpressie();
            fout = subExp.getFout();
            if (subExp.succes() && !stack.matchNextToken(TokenType.BRACKET, TokenSubtype.RIGHT_BRACKET, true)) {
                fout = new ParserFout(ParserFoutCode.HAAKJE_ONTBREEKT, stack.currentToken());
            }
        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Parse een lijst van expressies volgens afleidingsregels:
     * <expressionList> ::= '[' [ <elements> ] ']'
     * <elements> ::= <booleanExpression> [ ',' <elements> ].
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return ListExpressie-object of null, indien een fout is opgetreden.
     */
    private ParserResultaat parseExpressionList(final ParserContext context) {
        ListExpressie exp = null;
        ParserFout fout = null;
        if (stack.matchNextToken(TokenType.BRACKET, TokenSubtype.LIST_START, true)) {
            if (stack.matchNextToken(TokenType.BRACKET, TokenSubtype.LIST_END, true)) {
                exp = new ListExpressie();
            } else {
                List<Expressie> values = new ArrayList<Expressie>();
                boolean listFinished = false;

                while (!stack.finished() && fout == null && !listFinished) {
                    //Token startToken = stack.currentToken();
                    ParserResultaat element = parseExpression(context);
                    if (!element.succes()) {
                        fout = element.getFout();
                    } else {
                        values.add(element.getExpressie());

                        if (stack.matchNextToken(TokenType.END_OF_LINE, false)) {
                            fout = new ParserFout(ParserFoutCode.LIJST_NIET_AFGESLOTEN, stack.currentToken());
                        } else if (stack.matchNextToken(TokenType.BRACKET, TokenSubtype.LIST_END, true)) {
                            listFinished = true;
                        } else if (!stack.matchNextToken(TokenType.SEPARATOR, true)) {
                            fout = new ParserFout(ParserFoutCode.FOUT_IN_LIJST, stack.currentToken());
                        }
                    }
                }

                if (fout == null) {
                    exp = new ListExpressie(values);
                }
            }
        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Parse een enkelvoudige expressie tussen haakjes volgens afleidingsregel:
     * <unaryExpression> ::= '(' <booleanExpression> ')' | <expressionList> | <field> | <function> | <literal>.
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Het Expressie-object of null, indien een fout is opgetreden.
     */
    private ParserResultaat parseUnaryExpression(final ParserContext context) {
        ParserResultaat exp = parseBracketedExpression(context);
        if (exp.getExpressie() == null && exp.getFout() == null) {
            exp = parseExpressionList(context);
            if (exp.getExpressie() == null && exp.getFout() == null) {
                exp = LiteralParser.parseLiteral(stack);
                if (exp.getExpressie() == null && exp.getFout() == null) {
                    exp = parseIdentifier(context);
                }
            }
        }
        return exp;
    }

    /**
     * Parse een attribuut, variabele of functie.
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Resultaatexpressie of null, indien fout.
     */
    private ParserResultaat parseIdentifier(final ParserContext context) {
        ParserResultaat exp;
        if (stack.matchNextToken(TokenType.KEYWORD, false)) {
            exp = parseFunctie(context);
        } else {
            exp = parseAttribuut(context);
        }
        return exp;
    }

    /**
     * Parse een attribuut.
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Resultaatexpressie of null, indien fout.
     */
    private ParserResultaat parseAttribuut(final ParserContext context) {
        Expressie exp = null;
        ParserFout fout = null;
        String object = "";
        Attributes attr = null;
        Token start = stack.currentToken();
        ExpressieType objectType = null;
        if (stack.matchNextToken(TokenType.ATTRIBUTE, false)) {
            Token ct = stack.shift();
            object = ParserUtils.DEFAULT_OBJECT;
            objectType = context.lookupType(object);
            if (objectType != null) {
                attr = symbolTable.lookupSymbol(ct.getValueAsString(), objectType, null);
                if (attr == null) {
                    fout = new ParserFout(ParserFoutCode.ATTRIBUUT_ONBEKEND, ct);
                }
            } else {
                fout = new ParserFout(ParserFoutCode.IDENTIFIER_ONBEKEND, ct);
            }
        } else if (stack.matchNextToken(TokenType.IDENTIFIER, false)) {
            Token ct = stack.shift();
            object = ct.getValueAsString();
            objectType = context.lookupType(object);
            if (objectType != null) {
                if (stack.matchNextToken(TokenType.QUALIFIER, false)) {
                    ct = stack.currentToken();
                    if (stack.matchNextToken(TokenType.QUALIFIER, true)) {
                        if (stack.matchNextToken(TokenType.ATTRIBUTE, false)) {
                            ct = stack.shift();
                            attr = symbolTable.lookupSymbol(ct.getValueAsString(), objectType, null);
                        } else {
                            fout = new ParserFout(ParserFoutCode.INCOMPLETE_ATTRIBUUTVERWIJZING, ct);
                        }
                    }
                    if (fout == null && attr == null) {
                        fout = new ParserFout(ParserFoutCode.ATTRIBUUT_ONBEKEND, ct);
                    }
                }
            } else {
                fout = new ParserFout(ParserFoutCode.IDENTIFIER_ONBEKEND, ct);
            }
        }

        if (fout != null) {
            return new ParserResultaat(fout);
        }

        if (attr != null && objectType != null) {
            if (stack.matchNextToken(TokenType.BRACKET, TokenSubtype.INDEX_START, true)) {
                if (attr.getType() == ExpressieType.INDEXED) {

                    if (stack.matchNextToken(TokenType.BRACKET, TokenSubtype.INDEX_EIND, true)) {
                        exp = new UnqualifiedIndexedAttribuutExpressie(object, attr);
                    } else {
                        Token startToken = stack.currentToken();
                        ParserResultaat index = parseOrdinalExpression(context);
                        if (index.succes() && index.getType() == ExpressieType.NUMBER) {
                            if (stack.matchNextToken(TokenType.BRACKET, TokenSubtype.INDEX_EIND, true)) {
                                if (stack.matchNextToken(TokenType.QUALIFIER, true)) {
                                    if (stack.matchNextToken(TokenType.ATTRIBUTE, false)) {
                                        Token ct = stack.shift();
                                        Attributes subattribuut = symbolTable.lookupSymbol(
                                                ct.getValueAsString(), objectType, attr);
                                        if (subattribuut != null && subattribuut.belongsToIndexedAttribute(attr)) {
                                            exp = new IndexedAttribuutExpressie(object, attr, index.getExpressie(),
                                                    subattribuut);
                                        } else {
                                            fout = new ParserFout(ParserFoutCode.ATTRIBUUT_ONBEKEND, ct);
                                        }
                                    } else {
                                        fout = new ParserFout(ParserFoutCode.ATTRIBUUT_VERWACHT, stack.currentToken());
                                    }
                                } else {
                                    fout = new ParserFout(ParserFoutCode.INCOMPLETE_ATTRIBUUTVERWIJZING,
                                            stack.currentToken());
                                }
                            } else {
                                fout = new ParserFout(ParserFoutCode.HAAKJE_ONTBREEKT, stack.currentToken());
                            }
                        } else {
                            fout = new ParserFout(ParserFoutCode.NUMERIEKE_EXPRESSIE_VERWACHT, startToken);
                        }
                    }
                } else {
                    fout = new ParserFout(ParserFoutCode.ATTRIBUUT_IS_NIET_GEINDEXEERD, stack.currentToken());
                }
            } else {
                if (attr.getType() != ExpressieType.INDEXED) {
                    exp = new AttribuutExpressie(object, attr);
                } else {
                    fout = new ParserFout(ParserFoutCode.INDEX_VERWACHT, stack.currentToken());
                }
            }
        } else if (object.length() > 0) {
            objectType = context.lookupType(object);
            if (objectType != null) {
                exp = new VariableExpressie(object, objectType);
            } else {
                fout = new ParserFout(ParserFoutCode.IDENTIFIER_ONBEKEND, start);
            }
        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Parse een functieaanroep met argumenten.
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Expressie voor functieaanroep, of null, indien fout.
     */

    private ParserResultaat parseFunctie(final ParserContext context) {
        Expressie exp = null;
        ParserFout fout = null;

        if (stack.matchNextToken(TokenType.KEYWORD, false)) {
            Token ct = stack.shift();
            Keywords functie = ct.getValueAsKeyword();

            if (stack.matchNextToken(TokenType.BRACKET, TokenSubtype.LEFT_BRACKET, true)) {
                Token startToken = stack.currentToken();

                if (functie == Keywords.ER_IS || functie == Keywords.ALLE) {
                    /* Deze functies vereisen speciale afhandeling omdat ze de context van de argumenten veranderen:
                     * ze introduceren een variabele die achtereenvolgens waarden uit een collectie aannemen. Deze
                     * variabele moet bruikbaar zijn in het derde argument.
                     */
                    ParserResultaat exOp = parseExistentieleOperator(functie, context);
                    exp = exOp.getExpressie();
                    fout = exOp.getFout();
                } else {
                    exp = FunctieFactory.creeerFunctieaanroep(functie, parseArgumenten(context));
                    if (exp == null) {
                        fout = new ParserFout(ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, startToken);
                    }
                }
            } else {
                fout = new ParserFout(ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, stack.currentToken());
            }
        }
        return new ParserResultaat(exp, fout);
    }

    /**
     * Vertaalt de argumenten van de functie ER_IS of ALLE.
     *
     * @param functie Betreffende functie.
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Expressie voor de functieaanroep, of NULL indien fout.
     */
    private ParserResultaat parseExistentieleOperator(final Keywords functie, final ParserContext context) {
        Expressie exp = null;
        ParserFout fout = null;
        Token ct = stack.currentToken();

        ParserResultaat lijstArgument = parseExpression(context);
        fout = lijstArgument.getFout();

        if (fout == null) {
            ParserFoutCode typeFout = ParserUtils.checkType(lijstArgument, ExpressieType.LIST);
            if (typeFout != ParserFoutCode.GEEN_FOUT) {
                fout = new ParserFout(typeFout, ct);
            }
        }
        if (fout == null && lijstArgument.getExpressie().getTypeElementen() == ExpressieType.UNKNOWN) {
            fout = new ParserFout(ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, ct);
        }

        if (fout == null && stack.matchNextToken(TokenType.SEPARATOR, true)
                && stack.matchNextToken(TokenType.IDENTIFIER, false))
        {
            ct = stack.shift();
            String identifier = ct.getValueAsString();
            if (stack.matchNextToken(TokenType.SEPARATOR, true)) {
                ParserContext argumentContext = new ParserContext(context);
                argumentContext.addIdentifier(new Identifier(identifier,
                        lijstArgument.getExpressie().getTypeElementen()));

                ct = stack.currentToken();
                ParserResultaat condition = parseExpression(argumentContext);
                fout = condition.getFout();

                if (fout == null) {
                    ParserFoutCode typeFout = ParserUtils.checkType(condition, ExpressieType.BOOLEAN);
                    if (typeFout != ParserFoutCode.GEEN_FOUT) {
                        fout = new ParserFout(typeFout, ct);
                    }
                }
                if (fout == null && stack.matchNextToken(TokenType.BRACKET, TokenSubtype.RIGHT_BRACKET, true)) {
                    List<Expressie> argumenten = new ArrayList<Expressie>();
                    argumenten.add(lijstArgument.getExpressie());
                    argumenten.add(new VariableExpressie(identifier,
                            lijstArgument.getExpressie().getTypeElementen()));
                    argumenten.add(condition.getExpressie());

                    if (functie == Keywords.ER_IS) {
                        exp = new FunctieERIS(argumenten);
                    } else {
                        exp = new FunctieALLE(argumenten);
                    }

                }
            }
        }

        return new ParserResultaat(exp, fout);
    }

    /**
     * Vertaalt een lijst van argumenten voor een functieaanroep.
     *
     * @param context Context voor de parser: alle bekende identifiers en hun type.
     * @return Lijst met argumenten of NULL indien een fout is gevonden.
     */
    private List<Expressie> parseArgumenten(final ParserContext context) {
        ParserFout fout = null;
        List<Expressie> argumenten = new ArrayList<Expressie>();
        boolean finished = stack.matchNextToken(TokenType.BRACKET, TokenSubtype.RIGHT_BRACKET, true);

        while (!finished && fout == null) {
            ParserResultaat argument = parseExpression(context);
            fout = argument.getFout();

            if (fout == null) {
                argumenten.add(argument.getExpressie());

                if (stack.matchNextToken(TokenType.BRACKET, TokenSubtype.RIGHT_BRACKET, true)) {
                    finished = true;
                } else if (!stack.matchNextToken(TokenType.SEPARATOR, true)) {
                    new ParserFout(ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, stack.currentToken());
                }
            }
        }

        if (fout == null) {
            return argumenten;
        } else {
            return null;
        }
    }
}
