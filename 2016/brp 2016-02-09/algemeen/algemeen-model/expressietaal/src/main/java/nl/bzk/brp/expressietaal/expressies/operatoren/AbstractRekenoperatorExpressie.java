/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.operatoren;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.FoutExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.GetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.expressies.literals.PeriodeLiteralExpressie;

/**
 * Representeert expressies van numerieke operatoren.
 */
public abstract class AbstractRekenoperatorExpressie extends AbstractBinaireOperatorExpressie {

    /**
     * Constructor.
     *
     * @param operandLinks  Linkeroperand van de operator.
     * @param operandRechts Rechteroperand van de operator.
     */
    AbstractRekenoperatorExpressie(final Expressie operandLinks, final Expressie operandRechts)
    {
        super(operandLinks, operandRechts);
    }

    @Override
    public final ExpressieType getType(final Context context) {
        final ExpressieType result;

        final ExpressieType typeLinks = getOperandLinks().getType(context);
        final ExpressieType typeRechts = getOperandRechts().getType(context);

        if (isTypeLinksGetalEnTypeRechtsGetal(typeLinks, typeRechts)) {
            result = ExpressieType.GETAL;
        } else if (isTypeLinksDatumEnTypeRechtsGetal(typeLinks, typeRechts)) {
            result = ExpressieType.DATUM;
        } else if (isTypeLinksDatumEnTypeRechtsPeriode(typeLinks, typeRechts)) {
            result = ExpressieType.DATUM;
        } else if (isTypeLinksPeriodeEnTypeRechtsPeriode(typeLinks, typeRechts)) {
            result = ExpressieType.PERIODE;
        } else if (isTypeLinksGrootGetalEnTypeRechtsGrootGetal(typeLinks, typeRechts)) {
            result = ExpressieType.GROOT_GETAL;
        } else {
            result = ExpressieType.ONBEKEND_TYPE;
        }
        return result;
    }

    @Override
    protected final Expressie pasOperatorToe(final Expressie berekendeOperandLinks,
                                             final Expressie berekendeOperandRechts,
                                             final Context context)
    {
        final Expressie resultaat;

        final ExpressieType typeOperandLinks = berekendeOperandLinks.getType(context);
        final ExpressieType typeOperandRechts = berekendeOperandRechts.getType(context);

        if (isOperandNull(context, berekendeOperandLinks, berekendeOperandRechts)) {
            resultaat = NullValue.getInstance();
        } else if (!berekendeOperandLinks.isConstanteWaarde() || !berekendeOperandRechts.isConstanteWaarde()) {
            resultaat = NullValue.getInstance();
        } else if (isTypeLinksGetalEnTypeRechtsGetal(typeOperandLinks, typeOperandRechts)) {
            resultaat = pasToeOpGetallen(berekendeOperandLinks, berekendeOperandRechts);
        } else if (isTypeLinksDatumEnTypeRechtsGetal(typeOperandLinks, typeOperandRechts)) {
            resultaat = pasToeOpDatumEnGetal((DatumLiteralExpressie) berekendeOperandLinks, (GetalLiteralExpressie) berekendeOperandRechts);
        } else if (isTypeLinksDatumEnTypeRechtsPeriode(typeOperandLinks, typeOperandRechts)) {
            resultaat = pasToeOpDatumEnPeriode((DatumLiteralExpressie) berekendeOperandLinks, (PeriodeLiteralExpressie) berekendeOperandRechts);
        } else if (isTypeLinksPeriodeEnTypeRechtsPeriode(typeOperandLinks, typeOperandRechts)) {
            resultaat = pasToeOpPerioden((PeriodeLiteralExpressie) berekendeOperandLinks, (PeriodeLiteralExpressie) berekendeOperandRechts);
        } else if (isTypeLinksLijstEnTypeRechtsLijst(typeOperandLinks, typeOperandRechts)) {
            resultaat = pasToeOpLijsten(berekendeOperandLinks, berekendeOperandRechts);
        } else if (isTypeLinksGrootGetalEnTypeRechtsGrootGetal(typeOperandLinks, typeOperandRechts)) {
            resultaat = pasToeOpGroteGetallen(berekendeOperandLinks, berekendeOperandRechts);
        } else {
            resultaat = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        }
        return resultaat;
    }

    private boolean isTypeLinksGrootGetalEnTypeRechtsGrootGetal(final ExpressieType typeLinks, final ExpressieType typeRechts) {
        return ExpressieType.GROOT_GETAL.isCompatibel(typeLinks) && ExpressieType.GROOT_GETAL.isCompatibel(typeRechts);
    }

    private boolean isTypeLinksPeriodeEnTypeRechtsPeriode(final ExpressieType typeLinks, final ExpressieType typeRechts) {
        return typeLinks == ExpressieType.PERIODE && typeRechts == ExpressieType.PERIODE;
    }

    private boolean isTypeLinksDatumEnTypeRechtsPeriode(final ExpressieType typeLinks, final ExpressieType typeRechts) {
        return typeLinks == ExpressieType.DATUM && typeRechts == ExpressieType.PERIODE;
    }

    private boolean isTypeLinksDatumEnTypeRechtsGetal(final ExpressieType typeLinks, final ExpressieType typeRechts) {
        return typeLinks == ExpressieType.DATUM && typeRechts == ExpressieType.GETAL;
    }

    private boolean isTypeLinksGetalEnTypeRechtsGetal(final ExpressieType typeLinks, final ExpressieType typeRechts) {
        return typeLinks == ExpressieType.GETAL && typeRechts == ExpressieType.GETAL;
    }

    private boolean isTypeLinksLijstEnTypeRechtsLijst(final ExpressieType typeLinks, final ExpressieType typeRechts) {
        return typeLinks == ExpressieType.LIJST && typeRechts == ExpressieType.LIJST;
    }

    private boolean isOperandNull(final Context context, final Expressie berekendeOperandLinks, final Expressie berekendeOperandRechts) {
        return berekendeOperandLinks.isNull(context) || berekendeOperandRechts.isNull(context);
    }

    /**
     * Past de operator toe op twee getallen.
     *
     * @param operandLinks  Linkeroperand.
     * @param operandRechts Rechteroperand.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpGetallen(Expressie operandLinks,
        Expressie operandRechts);

    /**
     * Past de operator toe op twee grote getallen.
     *
     * @param operandLinks  Linkeroperand.
     * @param operandRechts Rechteroperand.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpGroteGetallen(Expressie operandLinks,
        Expressie operandRechts);

    /**
     * Past de operator toe op een datum en een getal.
     *
     * @param operandLinks  Linkeroperand.
     * @param operandRechts Rechteroperand.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpDatumEnGetal(DatumLiteralExpressie operandLinks,
        GetalLiteralExpressie operandRechts);

    /**
     * Past de operator toe op een datum en een periode.
     *
     * @param operandLinks  Linkeroperand.
     * @param operandRechts Rechteroperand.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpDatumEnPeriode(DatumLiteralExpressie operandLinks,
        PeriodeLiteralExpressie operandRechts);

    /**
     * Past de operator toe op twee perioden.
     *
     * @param operandLinks  Linkeroperand.
     * @param operandRechts Rechteroperand.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpPerioden(PeriodeLiteralExpressie operandLinks,
        PeriodeLiteralExpressie operandRechts);

    /**
     * Past de operator toe op twee lijsten.
     *
     * @param operandLinks  Linkeroperand.
     * @param operandRechts Rechteroperand.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpLijsten(Expressie operandLinks,
        Expressie operandRechts);

}
