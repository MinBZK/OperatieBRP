/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieTaalConstanten;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.Keyword;
import nl.bzk.brp.expressietaal.expressies.AttribuutExpressie;
import nl.bzk.brp.expressietaal.expressies.AttribuutReferentieExpressie;
import nl.bzk.brp.expressietaal.expressies.ClosureExpressie;
import nl.bzk.brp.expressietaal.expressies.FoutExpressie;
import nl.bzk.brp.expressietaal.expressies.GroepReferentieExpressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.VariabeleExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.FunctieExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.AttribuutcodeExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.GetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.GrootGetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.expressies.literals.PeriodeLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.StringLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.BevatOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.GelijkheidsoperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.GroterDanOfGelijkAanOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.GroterDanOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.KleinerDanOfGelijkAanOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.KleinerDanOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.LogischeEnExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.LogischeNietExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.LogischeOfExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.MinusOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.NumeriekeInverseOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.OngelijkheidsoperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.PlusOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.WildcardOperatorExpressie;
import nl.bzk.brp.expressietaal.parser.antlr.BRPExpressietaalBaseVisitor;
import nl.bzk.brp.expressietaal.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.expressietaal.symbols.BmrSymbolTable;
import nl.bzk.brp.expressietaal.symbols.ExpressieAttribuut;
import nl.bzk.brp.expressietaal.symbols.ExpressieGroep;
import org.antlr.v4.runtime.misc.NotNull;

/**
 * Visitor voor de parse tree die ANTLR heeft gegenereerd.
 */
public final class BRPExpressieVisitor extends BRPExpressietaalBaseVisitor<Expressie> {

    private static final int MAX_DIGITS_NORMAL_NUMBER = 9;
    private Context context;
    private final BRPExpressietaalErrorListener errorListener;

    /**
     * Constructor.
     *
     * @param aInitialContext De context van gedefinieerde identifier waarmee de visitor begint. In de regel zal
     * hierin de variabele 'persoon' zijn
     *                        gedefineerd.
     * @param aErrorListener  Error listener die de tijdens parsing en vertaling ontdekte fouten opvangt.
     */
    public BRPExpressieVisitor(final Context aInitialContext,
        final BRPExpressietaalErrorListener aErrorListener)
    {
        super();
        this.context = aInitialContext;
        this.errorListener = aErrorListener;
    }

    @Override
    public Expressie visitClosure(final BRPExpressietaalParser.ClosureContext ctx) {
        final Expressie result;
        if (ctx.assignments() == null) {
            result = visit(ctx.booleanExp());
        } else {
            // Verzamel alle assignments en voeg ze toe aan de context van de closure (closureContext).
            final Context closureContext = new Context();
            for (final BRPExpressietaalParser.AssignmentContext assignment : ctx.assignments().assignment()) {
                final Expressie value = visit(assignment.exp());
                closureContext.definieer(assignment.variable().getText(), value);
            }

            // De gedefinieerde variabelen zijn ook bruikbaar in de expressie van de closure en dus moeten ze voor
            // de parser beschikbaar zijn in een context.
            context = new Context(context);
            for (final String identifier : closureContext.identifiers()) {
                final Expressie value = closureContext.zoekWaarde(identifier);
                context.declareer(identifier, value.getType(null));
            }
            final Expressie body = visit(ctx.booleanExp());
            context = context.getOmslotenContext();

            // Het resultaat is een ClosureExpressie met de verzamelde assignments en de vertaalde body.
            result = new ClosureExpressie(body, closureContext);
        }
        return result;
    }

    @Override
    public Expressie visitBracketedExp(final BRPExpressietaalParser.BracketedExpContext ctx) {
        return visit(ctx.exp());
    }

    @Override
    public Expressie visitNumericLiteral(final BRPExpressietaalParser.NumericLiteralContext ctx) {
        final String getal = ctx.getText();

        if (getal.length() > MAX_DIGITS_NORMAL_NUMBER) {
            return new GrootGetalLiteralExpressie(Long.valueOf(getal));
        } else {
            return new GetalLiteralExpressie(Integer.valueOf(getal));
        }
    }

    @Override
    public Expressie visitNullLiteral(final BRPExpressietaalParser.NullLiteralContext ctx) {
        return NullValue.getInstance();
    }

    @Override
    public Expressie visitBooleanLiteral(final BRPExpressietaalParser.BooleanLiteralContext ctx) {
        return BooleanLiteralExpressie.getExpressie(ctx.TRUE_CONSTANT() != null);
    }

    @Override
    public Expressie visitStringLiteral(final BRPExpressietaalParser.StringLiteralContext ctx) {
        return new StringLiteralExpressie(ctx.getText().substring(1, ctx.getText().length() - 1));
    }

    @Override
    public Expressie visitDateLiteral(final BRPExpressietaalParser.DateLiteralContext ctx) {
        return BRPExpressieVisitorUtils.maakDatumExpressie(ctx.year(), ctx.month(), ctx.day(), errorListener);
    }

    @Override
    public Expressie visitDateTimeLiteral(@NotNull final BRPExpressietaalParser.DateTimeLiteralContext ctx) {
        return BRPExpressieVisitorUtils.maakDatumTijdExpressie(ctx, errorListener);
    }

    @Override
    public Expressie visitPeriodLiteral(final BRPExpressietaalParser.PeriodLiteralContext ctx) {
        int jaar = 0;
        int maand = 0;
        int dag = 0;
        if (ctx.relativeYear() != null) {
            jaar = Integer.parseInt(ctx.relativeYear().getText());
            if (ctx.relativeMonth() != null) {
                maand = Integer.parseInt(ctx.relativeMonth().getText());
                if (ctx.relativeDay() != null) {
                    dag = Integer.parseInt(ctx.relativeDay().getText());
                }
            }
        }
        return new PeriodeLiteralExpressie(jaar, maand, dag);
    }

    @Override
    public Expressie visitEmptyList(final BRPExpressietaalParser.EmptyListContext ctx) {
        return new LijstExpressie();
    }

    @Override
    public Expressie visitNonEmptyList(final BRPExpressietaalParser.NonEmptyListContext ctx) {
        final List<BRPExpressietaalParser.ExpContext> elements = ctx.exp();
        final List<Expressie> lijst = new ArrayList<>();
        if (elements != null) {
            for (final BRPExpressietaalParser.ExpContext e : elements) {
                lijst.add(visit(e));
            }
        }
        return new LijstExpressie(lijst);
    }

    @Override
    public Expressie visitNegatableExpression(final BRPExpressietaalParser.NegatableExpressionContext ctx) {
        final Expressie result;
        final Expressie exp = visit(ctx.unaryExpression());

        if (ctx.negationOperator() != null) {
            if (ctx.negationOperator().OP_MINUS() != null) {
                // Een expressie die begint met een minteken(operator) vereist een getal als operand.
                final ParserFoutCode foutCode = ParserUtils.checkTypes(exp, ExpressieType.GETAL, ExpressieType.GROOT_GETAL);
                if (foutCode == ParserFoutCode.GEEN_FOUT) {
                    if (exp.isConstanteWaarde(ExpressieType.GETAL)) {
                        // Als de expressie een constante waarde is, kan het resultaat ook een constante waarde zijn
                        // (de negatieve waarde van de constante).
                        result = new GetalLiteralExpressie(-exp.alsInteger());
                    } else if (exp.isConstanteWaarde(ExpressieType.GROOT_GETAL)) {
                        result = new GrootGetalLiteralExpressie(-exp.alsLong());
                    } else {
                        result = new NumeriekeInverseOperatorExpressie(exp);
                    }
                } else {
                    errorListener.voegFoutToe(new ParserFout(foutCode, ctx.unaryExpression().getText(),
                        ctx.unaryExpression().getStart().getStartIndex()));
                    result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
                }
            } else if (ctx.negationOperator().OP_NOT() != null) {
                // Een expressie die begint met de logische ontkenning vereist een boolean expressie als operand.
                final ParserFoutCode foutCode = ParserUtils.checkType(exp, ExpressieType.BOOLEAN);
                if (foutCode == ParserFoutCode.GEEN_FOUT) {
                    result = new LogischeNietExpressie(exp);
                } else {
                    errorListener.voegFoutToe(new ParserFout(foutCode, ctx.unaryExpression().getText(),
                        ctx.unaryExpression().getStart().getStartIndex()));
                    result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
                }
            } else {
                result = NullValue.getInstance();
            }
        } else {
            result = exp;
        }
        return result;
    }

    @Override
    public Expressie visitOrdinalExpression(final BRPExpressietaalParser.OrdinalExpressionContext ctx) {
        final Expressie result;
        final Expressie left = visit(ctx.negatableExpression());
        if (!left.isFout() && ctx.ordinalOp() != null) {
            final Expressie right = visit(ctx.ordinalExpression());

            ParserFoutCode foutCode = ParserUtils.checkOrdinalType(left);
            if (foutCode != ParserFoutCode.GEEN_FOUT && left.getType(null) == ExpressieType.LIJST) {
                foutCode = ParserFoutCode.GEEN_FOUT;
            }
            if (foutCode == ParserFoutCode.GEEN_FOUT) {
                foutCode = ParserUtils.checkOrdinalType(right);
                if (foutCode != ParserFoutCode.GEEN_FOUT && right.getType(null) == ExpressieType.LIJST) {
                    foutCode = ParserFoutCode.GEEN_FOUT;
                }

                if (foutCode == ParserFoutCode.GEEN_FOUT) {
                    if (!right.isFout()) {
                        if (ctx.ordinalOp().OP_PLUS() != null) {
                            result = new PlusOperatorExpressie(left, right);
                        } else if (ctx.ordinalOp().OP_MINUS() != null) {
                            result = new MinusOperatorExpressie(left, right);
                        } else {
                            result = NullValue.getInstance();
                        }
                    } else {
                        result = right;
                    }
                } else {
                    errorListener.voegFoutToe(new ParserFout(foutCode, ctx.ordinalExpression().getText(),
                        ctx.ordinalExpression().getStart().getStartIndex()));
                    result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
                }
            } else {
                errorListener.voegFoutToe(new ParserFout(foutCode, ctx.negatableExpression().getText(),
                    ctx.negatableExpression().getStart().getStartIndex()));
                result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            }
        } else {
            result = left;
        }

        return result;
    }

    @Override
    public Expressie visitRelationalExpression(final BRPExpressietaalParser.RelationalExpressionContext ctx) {
        final Expressie result;
        final Expressie left = visit(ctx.ordinalExpression(0));
        if (ctx.relationalOp() != null) {
            final Expressie right = visit(ctx.ordinalExpression(1));

            if (ctx.relationalOp().OP_IN() != null || ctx.relationalOp().OP_IN_WILDCARD() != null) {
                final boolean wildcard = ctx.relationalOp().OP_IN_WILDCARD() != null;
                if (ParserUtils.checkType(right, ExpressieType.LIJST) == ParserFoutCode.GEEN_FOUT) {
                    result = new BevatOperatorExpressie(left, right, wildcard);
                } else {
                    errorListener.voegFoutToe(
                        new ParserFout(ParserFoutCode.LIJST_EXPRESSIE_VERWACHT, ctx.ordinalExpression(1).getText(),
                            ctx.ordinalExpression(1).getStart().getStartIndex()));
                    result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
                }
            } else {
                final ParserFoutCode foutCode = ParserUtils.checkComparedTypes(left, right);
                if (foutCode == ParserFoutCode.GEEN_FOUT) {
                    if (ctx.relationalOp().OP_LESS() != null) {
                        result = new KleinerDanOperatorExpressie(left, right);
                    } else if (ctx.relationalOp().OP_LESS_EQUAL() != null) {
                        result = new KleinerDanOfGelijkAanOperatorExpressie(left, right);
                    } else if (ctx.relationalOp().OP_GREATER() != null) {
                        result = new GroterDanOperatorExpressie(left, right);
                    } else if (ctx.relationalOp().OP_GREATER_EQUAL() != null) {
                        result = new GroterDanOfGelijkAanOperatorExpressie(left, right);
                    } else {
                        result = NullValue.getInstance();
                    }
                } else {
                    errorListener.voegFoutToe(new ParserFout(foutCode, ctx.ordinalExpression(1).getText(),
                        ctx.ordinalExpression(1).getStart().getStartIndex()));
                    result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
                }
            }
        } else {
            result = left;
        }
        return result;
    }

    @Override
    public Expressie visitEqualityExpression(final BRPExpressietaalParser.EqualityExpressionContext ctx) {
        final Expressie result;
        final Expressie left = visit(ctx.relationalExpression(0));
        if (ctx.equalityOp() != null) {
            final Expressie right = visit(ctx.relationalExpression(1));

            final ParserFoutCode foutCode = ParserUtils.checkComparedTypes(left, right);
            if (foutCode == ParserFoutCode.GEEN_FOUT) {
                if (ctx.equalityOp().OP_EQUAL() != null) {
                    result = new GelijkheidsoperatorExpressie(left, right);
                } else if (ctx.equalityOp().OP_NOT_EQUAL() != null) {
                    result = new OngelijkheidsoperatorExpressie(left, right);
                } else if (ctx.equalityOp().OP_LIKE() != null) {
                    result = new WildcardOperatorExpressie(left, right);
                } else {
                    result = NullValue.getInstance();
                }
            } else {
                errorListener.voegFoutToe(new ParserFout(foutCode, ctx.relationalExpression(1).getText(),
                    ctx.relationalExpression(1).getStart().getStartIndex()));
                result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            }
        } else {
            result = left;
        }
        return result;
    }

    @Override
    public Expressie visitBooleanTerm(final BRPExpressietaalParser.BooleanTermContext ctx) {
        final Expressie result;
        final Expressie left = visit(ctx.equalityExpression());
        if (ctx.booleanTerm() != null) {
            final Expressie right = visit(ctx.booleanTerm());
            ParserFoutCode foutCode = ParserUtils.checkType(left, ExpressieType.BOOLEAN);
            if (foutCode == ParserFoutCode.GEEN_FOUT) {
                foutCode = ParserUtils.checkType(right, ExpressieType.BOOLEAN);
                if (foutCode == ParserFoutCode.GEEN_FOUT) {
                    result = new LogischeEnExpressie(left, right);
                } else {
                    errorListener.voegFoutToe(new ParserFout(foutCode, ctx.booleanTerm().getText(),
                        ctx.booleanTerm().getStart().getStartIndex()));
                    result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
                }
            } else {
                errorListener.voegFoutToe(new ParserFout(foutCode, ctx.equalityExpression().getText(),
                    ctx.equalityExpression().getStart().getStartIndex()));
                result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            }
        } else {
            result = left;
        }
        return result;
    }

    @Override
    public Expressie visitBooleanExp(final BRPExpressietaalParser.BooleanExpContext ctx) {
        final Expressie result;
        final Expressie left = visit(ctx.booleanTerm());
        if (ctx.booleanExp() != null) {
            final Expressie right = visit(ctx.booleanExp());

            ParserFoutCode foutCode = ParserUtils.checkType(left, ExpressieType.BOOLEAN);
            if (foutCode == ParserFoutCode.GEEN_FOUT) {
                foutCode = ParserUtils.checkType(right, ExpressieType.BOOLEAN);
                if (foutCode == ParserFoutCode.GEEN_FOUT) {
                    result = new LogischeOfExpressie(left, right);
                } else {
                    errorListener.voegFoutToe(new ParserFout(foutCode, ctx.booleanExp().getText(),
                        ctx.booleanExp().getStart().getStartIndex()));
                    result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
                }
            } else {
                errorListener.voegFoutToe(new ParserFout(foutCode, ctx.booleanTerm().getText(),
                    ctx.booleanTerm().getStart().getStartIndex()));
                result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            }
        } else {
            result = left;
        }
        return result;
    }

    @Override
    public Expressie visitFunction(final BRPExpressietaalParser.FunctionContext ctx) {
        final String functionName = ctx.functionName().getText();
        final Keyword functionKeyword = DefaultKeywordMapping.zoekKeyword(functionName);
        Expressie result = null;

        final List<Expressie> argumenten = new ArrayList<>();

        final List<BRPExpressietaalParser.ExpContext> expList = ctx.exp();
        for (final BRPExpressietaalParser.ExpContext e : expList) {
            final Expressie argument = visit(e);
            if (argument.isFout()) {
                result = argument;
                break;
            }
            argumenten.add(argument);
        }

        if (result == null) {
            final Expressie functie = FunctieExpressie.maakFunctieaanroep(functionKeyword, argumenten);

            if (functie.isFout()) {
                errorListener.voegFoutToe(new ParserFout(ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, functionName,
                    ctx.getStart().getStartIndex()));
            }
            result = functie;
        }
        return result;
    }

    @Override
    public Expressie visitExistFunction(final BRPExpressietaalParser.ExistFunctionContext ctx) {
        final Expressie result;
        final String functionName = ctx.existFunctionName().getText();
        final Keyword functionKeyword = DefaultKeywordMapping.zoekKeyword(functionName);

        final List<Expressie> argumenten = new ArrayList<>();
        final Expressie lijst = visit(ctx.exp(0));

        if (!lijst.isFout()) {
            argumenten.add(lijst);
            if (ctx.variable() != null) {
                final String variabele = ctx.variable().getText();
                argumenten.add(new VariabeleExpressie(variabele));

                context = new Context(context);
                context.declareer(variabele, lijst.bepaalTypeVanElementen(null));
                final Expressie body = visit(ctx.exp(1));
                argumenten.add(body);
                context = context.getOmslotenContext();

                if (!body.isFout()) {
                    result = FunctieExpressie.maakFunctieaanroep(functionKeyword, argumenten);
                } else {
                    result = body;
                }
            } else {
                errorListener.voegFoutToe(
                    new ParserFout(ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, ctx.variable().getText(),
                        ctx.variable().getStart().getStartIndex()));
                result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            }
        } else {
            errorListener.voegFoutToe(new ParserFout(ParserFoutCode.FOUT_IN_FUNCTIEAANROEP, ctx.exp(0).getText(),
                ctx.exp(0).getStart().getStartIndex()));
            if (lijst.isFout()) {
                result = lijst;
            } else {
                result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            }
        }

        return result;
    }

    @Override
    public Expressie visitAttribute(final BRPExpressietaalParser.AttributeContext ctx) {
        final Expressie result;
        final String object;
        final ExpressieType objectType;
        final String attribuutNaam;
        final ExpressieAttribuut attr;

        if (ctx.objectIdentifier() != null) {
            object = ctx.objectIdentifier().getText();
        } else {
            object = ExpressieTaalConstanten.DEFAULT_OBJECT;
        }

        objectType = context.zoekType(object);
        if (objectType != null) {
            attribuutNaam = ctx.attribute_path().getText();
            attr = BmrSymbolTable.getInstance().zoekSymbool(attribuutNaam, objectType);
            if (attr != null) {
                result = new AttribuutExpressie(object, attr);
            } else {
                errorListener.voegFoutToe(
                    new ParserFout(ParserFoutCode.ATTRIBUUT_ONBEKEND, attribuutNaam,
                        ctx.getStart().getStartIndex()));
                result = new FoutExpressie(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, attribuutNaam);
            }
        } else {
            errorListener.voegFoutToe(
                new ParserFout(ParserFoutCode.IDENTIFIER_ONBEKEND, object, ctx.getStart().getStartIndex()));
            result = new FoutExpressie(EvaluatieFoutCode.VARIABELE_NIET_GEVONDEN, object);
        }
        return result;
    }

    @Override
    public Expressie visitAttributeReference(final BRPExpressietaalParser.AttributeReferenceContext ctx) {
        final Expressie expressie = visit(ctx.attribute());
        if (!expressie.isFout()) {
            final AttribuutExpressie attr = (AttribuutExpressie) expressie;
            return new AttribuutReferentieExpressie(attr.getObject(), attr.getExpressieAttribuut());
        } else {
            return expressie;
        }
    }

    @Override
    public Expressie visitGroepReference(final BRPExpressietaalParser.GroepReferenceContext ctx) {
        String groepNaam = ctx.groep().getText();
        // Wanneer persoon wordt toegevoegd aan de expressie (bijv. $persoon.geboorte),
        // dan dient alleen de groepnaam doorgegeven te worden.
        if (groepNaam.startsWith("persoon.")) {
            groepNaam = groepNaam.replace("persoon.", "");
        }

        final ExpressieType expressieType = ExpressieType.GROEP;
        final ExpressieGroep expressieGroep = BmrSymbolTable.getInstance().zoekGroepSymbool(groepNaam, expressieType);

        final GroepReferentieExpressie expressie = new GroepReferentieExpressie("persoon", expressieGroep);

        if (!expressie.isFout()) {
            return expressie;
        } else {
            return null;
        }
    }

    @Override
    public Expressie visitVariable(final BRPExpressietaalParser.VariableContext ctx) {
        return new VariabeleExpressie(ctx.getText());
    }

    @Override
    public Expressie visitAttributeCodeLiteral(final BRPExpressietaalParser.AttributeCodeLiteralContext ctx) {
        final Expressie result;
        final String attribuutNaam = ctx.attribute_path().getText();
        final ExpressieAttribuut attr = BmrSymbolTable.getInstance().zoekSymbool(attribuutNaam, ExpressieType.ONBEKEND_TYPE);
        if (attr != null) {
            result = new AttribuutcodeExpressie(attribuutNaam);
        } else {
            errorListener.voegFoutToe(
                new ParserFout(ParserFoutCode.ATTRIBUUT_ONBEKEND, attribuutNaam,
                    ctx.getStart().getStartIndex()));
            result = new FoutExpressie(EvaluatieFoutCode.ATTRIBUUT_NIET_GEVONDEN, attribuutNaam);
        }
        return result;
    }


}
