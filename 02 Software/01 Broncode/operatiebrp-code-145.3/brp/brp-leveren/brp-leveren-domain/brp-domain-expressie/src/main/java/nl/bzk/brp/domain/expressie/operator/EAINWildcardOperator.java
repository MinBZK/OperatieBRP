/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.Prioriteit;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.parser.exception.ExpressieParseException;
import nl.bzk.brp.domain.expressie.validator.OperandValidator;
import nl.bzk.brp.domain.expressie.vergelijker.Vergelijker;
import nl.bzk.brp.domain.expressie.vergelijker.VergelijkerFactory;

/**
 * Implementatie van de operatoren EIN% en AIN%.
 * De operator heeft als operands twee collecties. Waarden uit de linkercollectie worden vergeleken
 * met waarden in de rechtercollectie middels de {@link OperatorType#WILDCARD wildcard} operator.
 * Afhankelijk van de modus (A/E) wordt het {@link BooleanLiteral boolean} resultaat van expressie bepaald:
 * <p>
 * De rechteroperand moet een lijst met wildcard-constanten zijn welke parse-time reeds beschikbaar is.
 * Deze lijst wordt dus NIET gevalueerd. Indien dit niet het geval is, of als de lijst leeg
 * is zal er een {@link ExpressieParseException parse-exceptie}
 * gegooid worden.
 * <p>
 * De operator ondersteunt de volgende modi:
 * <ul>
 * <li>{@link TypeCollectieoperator#EN A modus}
 * <br>Voor elke collectiewaarde van het linkeroperand moet er een match zijn
 * met een wildcard-expressie uit het rechteroperand.
 * <p>
 * Voorbeelden:
 * <table summary="Voorbeelden">
 * <tr><th>Expressie</th><th>Resultaat</th></tr>
 * <tr><td>{"2345*"} AIN% {"2345XD"}</td><td>WAAR</td></tr>
 * <tr><td>{"2345??"} AIN% {"2345XD"}</td><td>WAAR</td></tr>
 * <tr><td>{"2345*} AIN% {"2345AA", "2345BB"}</td><td>WAAR</td></tr>
 * <tr><td>{"2346*"} AIN% {}</td><td>ONWAAR</td></tr>
 * <tr><td>{"2346*"} AIN% {"2345XD"}</td><td>ONWAAR</td></tr>
 * <tr><td>{"2345A*", "2345C*"} AIN% {"2345AA", "2345AB"}</td><td>ONWAAR</td></tr>
 * </table>
 * </li>
 * <li>{@link TypeCollectieoperator#OF E modus}
 * <br>Voor minimaal één collectiewaarde van het linkeroperand moet er een match zijn
 * met een wildcard-expressie uit het rechteroperand. Evaluatie stopt indien dit ene geval
 * gevonden is. Een lege linkeroperand resulteert in {@link BooleanLiteral#ONWAAR onwaar}
 * <p>
 * Voorbeelden:
 * <table summary="Voorbeelden">
 * <tr><th>Expressie</th><th>Resultaat</th></tr>
 * <tr><td>{"2345*"} EIN% {"2345XD"}</td><td>WAAR</td></tr>
 * <tr><td>{"2345A?", "2345B?"} EIN% {"2345AA", "2345BB"}</td><td>WAAR</td></tr>
 * <tr><td>{} EIN% {"2346*"}</td><td>ONWAAR</td></tr>
 * <tr><td>{"2345C*"} EIN% {"2345AA", "2345BB"}</td><td>ONWAAR</td></tr>
 * </table>
 * </li>
 * </ul>
 * <p>
 * In onderstaande gevallen worden <b>parse-tijd</b> excepties gegooid:
 * <ul>
 * <li>indien de linkeroperand géén {@link LijstExpressie lijst} is</li>
 * <li>indien de linkeroperand een lege lijst is</li>
 * <li>indien de linkeroperand niet {@link Expressie#isConstanteWaarde() constant} is</li>
 * <li>indien de linkeroperand niet {@link LijstExpressie#isHomogeen() homogeen} is</li>
 * <li>indien de linkeroperand {@link NullLiteral null} waarden bevat</li>
 * </ul>
 * In onderstaande gevallen worden <b>evaluatie-tijd</b> excepties gegooid:
 * <ul>
 * <li>indien de rechteroperand niet tot een {@link LijstExpressie lijst} evalueert</li>
 * <li>indien de vergelijking niet mogelijk is omdat de waarden niet compatibel zijn</li>
 * </ul>
 * Wildcard vergelijkingen hebben alleen betekenis voor het expressietype {@link ExpressieType#STRING String}.
 * Voor andere expressiettypen werkt de operator wel, maar de werking is dan gelijk aan de {@link EAINOperator}
 */
public final class EAINWildcardOperator extends AbstractBinaireOperator {

    private static final Vergelijker<Expressie, Expressie> DATUM_WILDCARD = VergelijkerFactory.get(ExpressieType.DATUM, OperatorType.WILDCARD);
    private static final Vergelijker<Expressie, Expressie> STRING_WILDCARD = VergelijkerFactory.get(ExpressieType.STRING, OperatorType.WILDCARD);
    private static final Vergelijker<Expressie, Expressie> GETAL_WILDCARD = VergelijkerFactory.get(ExpressieType.GETAL, OperatorType.WILDCARD);
    private static final Vergelijker<Expressie, Expressie> BOOLEAN_WILDCARD = VergelijkerFactory.get(ExpressieType.BOOLEAN, OperatorType.WILDCARD);
    private static final OperandValidator LINKEROPERAND_VALIDATOR
            = new OperandValidator(OperandValidator.IS_COMPOSITIE);
    private static final OperandValidator WILDCARD_OP_VALIDATOR = new OperandValidator(
            OperandValidator.IS_COMPOSITIE,
            OperandValidator.IS_NIET_LEGE_COMPOSITIE,
            OperandValidator.IS_CONSTANT,
            OperandValidator.IS_HOMOGENE_COMPOSITIE,
            OperandValidator.IS_NON_NULL_COMPOSITIE
    );

    private final TypeCollectieoperator conditieType;

    /**
     * Constructor.
     *
     * @param linkerOperand         de linkeroperand, een expressie welke moet evalueren tot een {@link LijstExpressie}
     * @param rechterOperand        de rechteroperand, een contante lijst welke NIET gevalueerd wordt.
     * @param typeCollectieoperator het type collectie operator
     */
    public EAINWildcardOperator(final Expressie linkerOperand,
                                final Expressie rechterOperand, final TypeCollectieoperator typeCollectieoperator) {
        super(linkerOperand, rechterOperand);
        this.conditieType = typeCollectieoperator;
    }

    @Override
    public String operatorAlsString() {
        return conditieType == TypeCollectieoperator.EN ? "AIN%" : "EIN%";
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public Prioriteit getPrioriteit() {
        return Prioriteit.PRIORITEIT_BEVAT;
    }

    @Override
    protected Expressie pasOperatorToe(final Expressie berekendeOperandLinks, final Expressie berekendeOperandRechts, final Context context) {

        LINKEROPERAND_VALIDATOR.valideer(berekendeOperandLinks,
                () -> String.format("Ongeldig linkeroperand in expressie %s", operatorAlsString()));
        final LijstExpressie linkerLijst = berekendeOperandLinks.alsLijst();
        if (linkerLijst.isEmpty()) {
            return BooleanLiteral.ONWAAR;
        }

        WILDCARD_OP_VALIDATOR.valideer(berekendeOperandRechts,
                () -> String.format("Ongeldig rechteroperand in expressie %s", operatorAlsString()));
        final LijstExpressie wildcardCompositie = berekendeOperandRechts.alsLijst();
        final Vergelijker<Expressie, Expressie> vergelijker = bepaalVergelijker(wildcardCompositie);
        int matches = 0;
        for (final Expressie expressie : linkerLijst) {
            boolean match = heeftWildcardMatch(expressie, wildcardCompositie, vergelijker);
            if (match) {
                matches++;
                if (conditieType == TypeCollectieoperator.OF) {
                    //short-circuit
                    break;
                }
            }

        }
        return BooleanLiteral.valueOf(conditieType == TypeCollectieoperator.EN
                ? linkerLijst.size() == matches : matches > 0);
    }


    private boolean heeftWildcardMatch(final Expressie expressie,
                                           final LijstExpressie wildcardExpressies,
                                           final Vergelijker<Expressie, Expressie> vergelijker) {
        for (final Expressie wildcardExpressie : wildcardExpressies) {
            final Expressie vergelijk = vergelijker.apply(expressie, wildcardExpressie);
            if (vergelijk.alsBoolean()) {
                return true;
            }
        }
        return false;

    }

    private Vergelijker<Expressie, Expressie> bepaalVergelijker(final LijstExpressie wildcardLijst) {
        final Vergelijker<Expressie, Expressie> tempVergelijk;
        final Expressie next = wildcardLijst.iterator().next();
        switch (next.getType(null)) {
            case GETAL:
                tempVergelijk = GETAL_WILDCARD;
                break;
            case STRING:
                tempVergelijk = STRING_WILDCARD;
                break;
            case DATUM:
            case DATUMTIJD:
                tempVergelijk = DATUM_WILDCARD;
                break;
            case BOOLEAN:
                tempVergelijk = BOOLEAN_WILDCARD;
                break;
            default:
                throw new ExpressieRuntimeException("ExpressieType wordt niet ondersteund: " + next.getType(null));
        }

        return tempVergelijk;
    }
}
