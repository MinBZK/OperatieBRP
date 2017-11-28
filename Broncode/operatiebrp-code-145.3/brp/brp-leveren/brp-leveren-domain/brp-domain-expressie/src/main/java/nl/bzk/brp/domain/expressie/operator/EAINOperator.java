/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.operator;

import com.google.common.collect.Sets;
import java.util.Set;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.Prioriteit;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.validator.OperandValidator;
import nl.bzk.brp.domain.expressie.vergelijker.Vergelijker;
import nl.bzk.brp.domain.expressie.vergelijker.VergelijkerFactory;

/**
 * Implementatie van de operatoren (EIN en AIN). De operator heeft
 * als operands twee collecties. Waarden uit de linkercollectie worden vergeleken
 * met waarden in de rechtercollectie middels de {@link OperatorType#GELIJK gelijk} operator.
 * Afhankelijk van de modus (A/E) wordt het {@link BooleanLiteral boolean} resultaat van expressie bepaald:
 * <ul>
 * <li>{@link TypeCollectieoperator#EN A modus}
 * <br>Elke waarde uit de linkercollectie moet voorkomen in de rechtercollectie.
 * <br>Voorbeelden:
 * <table summary="Voorbeelden">
 * <tr><th>Expressie</th><th>Resultaat</th></tr>
 * <tr><td>{10} AIN {10}</td><td>WAAR</td></tr>
 * <tr><td>{10} AIN {10, 20}</td><td>WAAR</td></tr>
 * <tr><td>{10, 20} AIN {10, 20}</td><td>WAAR</td></tr>
 * <tr><td>{10} AIN {20}</td><td>ONWAAR</td></tr>
 * <tr><td>{10,20} AIN {10}</td><td>ONWAAR</td></tr>
 * <tr><td>{} AIN {10}</td><td>ONWAAR</td></tr>
 * </table>
 * </li>
 * <li>{@link TypeCollectieoperator#OF E modus}
 * <br>Er moet minimaal één waarde uit de linkercollectie moet voorkomen in de rechtercollectie. Evaluatie
 * stopt indien zodra dit waar is.
 * <br>Voorbeelden:
 * <table summary="Voorbeelden">
 * <tr><th>Expressie</th><th>Resultaat</th></tr>
 * <tr><td>{10} EIN {10}</td><td>WAAR</td></tr>
 * <tr><td>{10,20} EIN {10}</td><td>WAAR</td></tr>
 * <tr><td>{10} EIN {10, 20}</td><td>WAAR</td></tr>
 * <tr><td>{10} EIN {20}</td><td>ONWAAR</td></tr>
 * <tr><td>{} EIN {10}</td><td>ONWAAR</td></tr>
 * </table>
 * </li>
 * </ul>
 * <p>
 * In onderstaande gevallen worden <b>evaluatie-tijd</b> excepties gegooid:
 * <ul>
 * <li>indien de linker-/rechteroperand niet tot een {@link LijstExpressie lijst} evalueert</li>
 * <li>indien de linker-/rechteroperand géén {@link LijstExpressie lijst} is</li>
 * <li>indien de linker-/rechteroperand niet {@link Expressie#isConstanteWaarde() constante} waarden bevat</li>
 * <li>indien de linker-/rechteroperand niet {@link LijstExpressie#isHomogeen() homogeen} is</li>
 * <li>indien de linker-/rechteroperand {@link NullLiteral null} waarden bevat</li>
 * <li>indien de vergelijking niet mogelijk is omdat de waarden niet compatibel zijn</li>
 * </ul>
 *
 * De volgende expressietypen worden ondersteund:
 * {@link ExpressieType#STRING String}, {@link ExpressieType#GETAL Getal}, {@link ExpressieType#BOOLEAN Boolean},
 * {@link ExpressieType#DATUM Datum}, {@link ExpressieType#DATUMTIJD DatumTijd}.
 */
public final class EAINOperator extends AbstractBinaireOperator {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final Vergelijker<Expressie, Expressie> GETAL_GELIJK = VergelijkerFactory.get(ExpressieType.GETAL, OperatorType.GELIJK);
    private static final Vergelijker<Expressie, Expressie> BOOLEAN_GELIJK = VergelijkerFactory.get(ExpressieType.BOOLEAN, OperatorType.GELIJK);
    private static final Vergelijker<Expressie, Expressie> STRING_GELIJK = VergelijkerFactory.get(ExpressieType.STRING, OperatorType.GELIJK);
    private static final Vergelijker<Expressie, Expressie> DATUM_GELIJK = VergelijkerFactory.get(ExpressieType.DATUM, OperatorType.GELIJK);
    private static final Vergelijker<Expressie, Expressie> OBJECT_GELIJK = VergelijkerFactory.get(ExpressieType.BRP_METAOBJECT, OperatorType.GELIJK);
    private static final Vergelijker<Expressie, Expressie> NULL_GELIJK = VergelijkerFactory.get(ExpressieType.NULL, OperatorType.GELIJK);

    private static final OperandValidator COMPOSITIE_VALIDATOR = new OperandValidator(
            OperandValidator.IS_COMPOSITIE,
            OperandValidator.IS_CONSTANT,
            OperandValidator.IS_HOMOGENE_COMPOSITIE
    );

    private final TypeCollectieoperator conditieType;
    private Vergelijker<Expressie, Expressie> vergelijker;

    /**
     * Constructor.
     *
     * @param linkerOperand         de linkeroperand, een expressie welke moet evalueren tot een {@link LijstExpressie}
     * @param rechterOperand        de rechteroperand, een contante lijst welke NIET gevalueerd wordt.
     * @param typeCollectieoperator het type collectie operator
     */
    public EAINOperator(final Expressie linkerOperand, final Expressie rechterOperand,
                        final TypeCollectieoperator typeCollectieoperator) {
        super(linkerOperand, rechterOperand);
        this.conditieType = typeCollectieoperator;
    }

    @Override
    protected Expressie pasOperatorToe(final Expressie berekendeOperandLinks, final Expressie berekendeOperandRechts, final Context context) {
        final LijstExpressie linkerCompositie = LijstExpressie.ensureList(berekendeOperandLinks);
        COMPOSITIE_VALIDATOR.valideer(linkerCompositie, () -> String.format("Ongeldig linkeroperand in expressie %s", operatorAlsString()));

        final LijstExpressie rechterCompositie = LijstExpressie.ensureList(berekendeOperandRechts);
        COMPOSITIE_VALIDATOR.valideer(rechterCompositie, () -> String.format("Ongeldig rechteroperand in expressie %s", operatorAlsString()));

        boolean resultaat = false;
        if (linkerCompositie.isEmpty() && rechterCompositie.isEmpty()) {
            resultaat = true;
        } else if (!linkerCompositie.isEmpty()) {
            resultaat = pasOperatorToeOpLegeLinkerOperand(linkerCompositie, rechterCompositie);
        }
        final BooleanLiteral booleanLiteral = BooleanLiteral.valueOf(resultaat);
        LOGGER.debug("resultaat: {}", booleanLiteral);
        return booleanLiteral;
    }

    private boolean pasOperatorToeOpLegeLinkerOperand(final LijstExpressie linkerCompositie, final LijstExpressie rechterCompositie) {
        vergelijker = bepaalVergelijker(linkerCompositie.alsLijst());
        final Set<VergelijkbaarExpressie> linkerVergelijkSet = maakComparableSet(linkerCompositie);
        final Set<VergelijkbaarExpressie> rechterVergelijkSet = maakComparableSet(rechterCompositie);
        boolean resultaat = false;
        if (conditieType == TypeCollectieoperator.EN) {
            resultaat = rechterVergelijkSet.containsAll(linkerVergelijkSet);
        } else {
            for (VergelijkbaarExpressie vergelijkbaarExpressie : linkerVergelijkSet) {
                if (rechterVergelijkSet.contains(vergelijkbaarExpressie)) {
                    resultaat = true;
                    break;
                }
            }
        }
        return resultaat;
    }

    @Override
    public String operatorAlsString() {
        return conditieType == TypeCollectieoperator.EN ? "AIN" : "EIN";
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public Prioriteit getPrioriteit() {
        return Prioriteit.PRIORITEIT_BEVAT;
    }

    private Set<VergelijkbaarExpressie> maakComparableSet(final Expressie expressie) {
        COMPOSITIE_VALIDATOR.valideer(expressie, () -> String.format("Ongeldig operand in expressie %s", operatorAlsString()));
        final Set<VergelijkbaarExpressie> set = Sets.newHashSet();
        for (Expressie e : expressie.alsLijst()) {
            set.add(new VergelijkbaarExpressie(e));
        }
        return set;
    }

    private static Vergelijker<Expressie, Expressie> bepaalVergelijker(final LijstExpressie rh) {
        final Vergelijker<Expressie, Expressie> vergelijker;
        final Expressie next = rh.iterator().next();
        switch (next.getType(null)) {
            case GETAL:
                vergelijker = GETAL_GELIJK;
                break;
            case STRING:
                vergelijker = STRING_GELIJK;
                break;
            case DATUM:
            case DATUMTIJD:
                vergelijker = DATUM_GELIJK;
                break;
            case BOOLEAN:
                vergelijker = BOOLEAN_GELIJK;
                break;
            case BRP_METAOBJECT:
                vergelijker = OBJECT_GELIJK;
                break;
            case NULL:
                vergelijker = NULL_GELIJK;
                break;
            default:
                throw new ExpressieRuntimeException("ExpressieType wordt niet ondersteund: " + next.getType(null));
        }

        return vergelijker;
    }

    /**
     * Hulpklasse voor het vergelijken van expressies. Expressies hebben namelijk zelf geen
     * equals of hashcode implementatie. Vergelijkingen bijv voor equals moeten via de {@link Vergelijker}
     * objecten gebeuren. Hoe een literal vergeleken wordt staat los van de literal of operator.
     */
    private final class VergelijkbaarExpressie {

        private final Expressie expressie;
        private final int hashcode;

        private VergelijkbaarExpressie(final Expressie expressie) {
            this.expressie = expressie;
            hashcode = expressie.hashCode();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final VergelijkbaarExpressie that = (VergelijkbaarExpressie) o;
            final Expressie vergelijk = vergelijker.apply(expressie, that.expressie);
            return vergelijk == BooleanLiteral.WAAR;
        }

        @Override
        public int hashCode() {
            return hashcode;
        }
    }
}
