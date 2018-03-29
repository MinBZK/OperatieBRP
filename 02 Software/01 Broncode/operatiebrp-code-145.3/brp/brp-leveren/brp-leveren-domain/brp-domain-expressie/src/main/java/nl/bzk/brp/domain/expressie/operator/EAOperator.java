/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.Literal;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.Prioriteit;
import nl.bzk.brp.domain.expressie.vergelijker.Vergelijker;
import nl.bzk.brp.domain.expressie.vergelijker.VergelijkerFactory;

/**
 * Implementatie van de operatoren A en E. De operator heeft een linkeroperand van type {@link ExpressieType#LIJST lijst}
 * en een rechteroperand van type {@link Literal literal}.
 * De rechteroperand wordt {@link OperatorType vergeleken} met waarden uit de lijst van de linkeroperand.
 * Afhankelijk van het type collectie operator wordt het resultaat van de expressie bepaald:
 * <ul>
 * <li>{@link TypeCollectieoperator#EN A modus}
 * <br>Alle vergelijkingen moeten {@link BooleanLiteral#WAAR waar} opleveren.
 * In alle andere gevallen in het resultaat {@link BooleanLiteral#ONWAAR onwaar}.
 * <br>Een lege collectie resulteert in {@link BooleanLiteral#ONWAAR onwaar}
 * </li>
 * <li>{@link TypeCollectieoperator#OF E modus}
 * <br>Één vergelijking moet {@link BooleanLiteral#WAAR waar} opleveren, waarna de vergelijking stopt.
 * <br>Een lege collectie resulteert in {@link BooleanLiteral#ONWAAR onwaar}
 * </li>
 * </ul>
 * De operator ondersteunt de volgende syntax:
 * <ul>
 * <li>{@link OperatorType#GELIJK gelijk}
 * <br><pre>{x} A= y</pre>Zijn alle waarden in de collectie X gelijk aan waarde Y?
 * <br><pre>{x} E= y</pre>Bestaat er een waarde in de collectie X gelijk aan waarde Y?
 * </li>
 * <li>{@link OperatorType#KLEINER kleiner}
 * <br><pre>{x} A&lt; y</pre>Zijn alle waarden in de collectie X kleiner dan waarde Y?
 * <br><pre>{x} E&lt; y</pre>Bestaat er een waarde in de collectie X kleiner dan waarde Y?
 * </li>
 * <li>{@link OperatorType#KLEINER_OF_GELIJK kleiner of gelijk}
 * <br><pre>{x} A&lt;= y</pre> Zijn alle waarden in de collectie X kleiner dan of gelijk aan waarde Y?
 * <br><pre>{x} E&lt;= y</pre>Bestaat er een waarde in de collectie X kleiner dan of gelijk aan waarde Y?
 * </li>
 * <li>{@link OperatorType#GROTER groter};
 * <br><pre>{x} A&gt; y</pre>Zijn alle waarden in de collectie X groter dan waarde Y?
 * <br><pre>{x} E&gt; y</pre> Bestaat er een waarde in de collectie X groter dan waarde Y?
 * </li>
 * <li> {@link OperatorType#GROTER_OF_GELIJK groter of gelijk}
 * <br><pre>{x} A&gt;= y</pre>Zijn alle waarden in de collectie X groter dan of gelijk aan waarde Y?
 * <br><pre>{x} E&gt;= y</pre>Bestaat er een waarde in de collectie X groter dan of gelijk aan waarde Y?
 * </li>
 * <li> {@link OperatorType#ONGELIJK ongelijk}
 * <br><pre>{x} A&lt;&gt; y</pre>Zijn alle waarden in de collectie X ongelijk aan waarde Y?
 * <br><pre>{x} E&lt;&gt; y</pre>Bestaat er een waarde in de collectie X ongelijk aan waarde Y?
 * </li>
 * <li>{@link OperatorType#WILDCARD wildcard}
 * <br><pre>{x} A%= y</pre>Zijn alle waarden in de collectie X wildcard gelijk aan waarde Y?
 * <br><pre>{x} E%= y</pre>Bestaat er een waarde in de collectie X wildcard gelijk aan waarde Y?
 * </li>
 * </ul>
 * <p>
 * In onderstaande gevallen worden <b>parse-tijd</b> excepties gegooid:
 * <ul>
 * <li>indien de rechteroperand {@link NullLiteral null} is</li>
 * </ul>
 * In onderstaande gevallen worden <b>evaluatie-tijd</b> excepties gegooid:
 * <ul>
 * <li>indien de vergelijking niet mogelijk is omdat de waarden niet compatibel zijn</li>
 * </ul>
 * <p>
 * De volgende expressietypen worden ondersteund: {@link ExpressieType#STRING String}, {@link ExpressieType#GETAL Getal},
 * {@link ExpressieType#BOOLEAN Boolean}, {@link ExpressieType#DATUM Datum},
 * {@link ExpressieType#DATUMTIJD DatumTijd}.
 */
public final class EAOperator extends AbstractBinaireOperator {

    private final OperatorType operator;
    private final TypeCollectieoperator conditieType;

    /**
     * Constructor.
     * @param operandLinks de linkeroperand van de operator.
     * @param aOperandRechts de rechteroperand van de operator.
     * @param operatorType het type opertator
     * @param conditieType het type collectie operator
     */
    public EAOperator(final Expressie operandLinks, final Expressie aOperandRechts, final OperatorType operatorType,
                      final TypeCollectieoperator conditieType) {
        super(operandLinks, aOperandRechts);
        this.operator = operatorType;
        this.conditieType = conditieType;
    }

    @Override
    public String operatorAlsString() {
        return (conditieType == TypeCollectieoperator.EN ? "A" : "E") + operator.toString();
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
        Expressie literalRechts = berekendeOperandRechts;
        if (berekendeOperandRechts instanceof LijstExpressie) {
            final LijstExpressie lijstExpressie = berekendeOperandRechts.alsLijst();
            if (lijstExpressie.isEmpty()) {
                literalRechts = NullLiteral.INSTANCE;
            } else {
                literalRechts = lijstExpressie.geefEnkeleWaarde();
            }
        }
        return conditieType == TypeCollectieoperator.EN
                ? evalueerEn(LijstExpressie.ensureList(berekendeOperandLinks), literalRechts)
                : evalueerOf(LijstExpressie.ensureList(berekendeOperandLinks), literalRechts);
    }

    private Expressie evalueerEn(final LijstExpressie berekendeOperandLinks, final Expressie berekendeOperandRechts) {
        BooleanLiteral resultaat = BooleanLiteral.WAAR;
        if (berekendeOperandLinks.isEmpty()) {
            resultaat = BooleanLiteral.ONWAAR;
        }

        for (final Expressie expressie : berekendeOperandLinks) {
            final Vergelijker<Expressie, Expressie> vergelijker = VergelijkerFactory.get(expressie.getType(null),
                    berekendeOperandRechts.getType(null), operator);
            final Expressie vergelijking = vergelijker.apply(expressie, berekendeOperandRechts);
            if (vergelijking == BooleanLiteral.ONWAAR) {
                resultaat = BooleanLiteral.ONWAAR;
                break;
            }
        }

        return resultaat;
    }

    private Expressie evalueerOf(final LijstExpressie linkerLijst, final Expressie berekendeOperandRechts) {
        for (Expressie linkerLijstWaarde : linkerLijst) {
            final Vergelijker<Expressie, Expressie> vergelijker = VergelijkerFactory.get(linkerLijstWaarde.getType(null),
                    berekendeOperandRechts.getType(null), operator);
            final Expressie vergelijking = vergelijker.apply(linkerLijstWaarde, berekendeOperandRechts);
            if (vergelijking == BooleanLiteral.WAAR) {
                return BooleanLiteral.WAAR;
            }
        }
        return BooleanLiteral.ONWAAR;
    }
}
