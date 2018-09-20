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
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;

/**
 * Representeert expressies van vergelijkingsoperatoren.
 */
public abstract class AbstractVergelijkingsoperatorExpressie extends AbstractBinaireOperatorExpressie {

    /**
     * Constructor.
     *
     * @param operandLinks  Linkerterm van de operator.
     * @param operandRechts Rechterterm van de operator.
     */
    AbstractVergelijkingsoperatorExpressie(final Expressie operandLinks, final Expressie operandRechts)
    {
        super(operandLinks, operandRechts);
    }

    @Override
    public final ExpressieType getType(final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public final Expressie optimaliseer(final Context context) {
        final Expressie geoptimaliseerdeOperandLinks = getOperandLinks().optimaliseer(context);
        final Expressie geoptimaliseerdeOperandRechts = getOperandRechts().optimaliseer(context);
        final Expressie resultaat;
        if (geoptimaliseerdeOperandLinks.isConstanteWaarde() && geoptimaliseerdeOperandRechts.isConstanteWaarde()) {
            resultaat = pasOperatorToe(geoptimaliseerdeOperandLinks, geoptimaliseerdeOperandRechts, context);
        } else {
            resultaat = this;
        }
        return resultaat;
    }

    /**
     * Pas de operator toe op de twee numeriek waarden.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpGetallen(final Expressie waardeLinks, final Expressie waardeRechts);

    /**
     * Pas de operator toe op de twee grote numeriek waarden.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpGroteGetallen(final Expressie waardeLinks, final Expressie waardeRechts);

    /**
     * Pas de operator toe op de twee boolean waarden.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpBooleans(final Expressie waardeLinks, final Expressie waardeRechts);

    /**
     * Pas de operator toe op de twee string-waarden.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpStrings(final Expressie waardeLinks, final Expressie waardeRechts);

    /**
     * Pas de operator toe op de twee datumwaarden.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpDatums(final DatumLiteralExpressie waardeLinks,
        final DatumLiteralExpressie waardeRechts);

    /**
     * Pas de operator toe op de twee lijsten.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpLijsten(final Expressie waardeLinks, final Expressie waardeRechts);

    /**
     * Pas de operator toe op de twee BRP-objecten.
     *
     * @param waardeLinks  De linkerwaarde.
     * @param waardeRechts De rechterwaarde.
     * @return Resultaat van de berekening.
     */
    protected abstract Expressie pasToeOpBrpObjecten(final BrpObjectExpressie waardeLinks,
        final BrpObjectExpressie waardeRechts);

    @Override
    protected final Expressie pasOperatorToe(final Expressie berekendeOperandLinks,
        final Expressie berekendeOperandRechts,
        final Context context)
    {
        final Expressie resultaat;

        if (berekendeOperandLinks.isNull(context) || berekendeOperandRechts.isNull(context)) {
            resultaat = NullValue.getInstance();
        } else if (!berekendeOperandLinks.isConstanteWaarde() || !berekendeOperandRechts.isConstanteWaarde()) {
            resultaat = NullValue.getInstance();
        } else {
            final ExpressieType typeLinks = berekendeOperandLinks.getType(context);
            final ExpressieType typeRechts = berekendeOperandRechts.getType(context);

            if (typeLinks != typeRechts && !typeLinks.isCompatibel(typeRechts)) {
                resultaat = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            } else if (typeLinks == ExpressieType.GROOT_GETAL || typeRechts == ExpressieType.GROOT_GETAL) {
                resultaat = pasToeOpGroteGetallen(berekendeOperandLinks, berekendeOperandRechts);
            } else if (typeLinks == ExpressieType.GETAL) {
                resultaat = pasToeOpGetallen(berekendeOperandLinks, berekendeOperandRechts);
            } else if (typeLinks == ExpressieType.BOOLEAN) {
                resultaat = pasToeOpBooleans(berekendeOperandLinks, berekendeOperandRechts);
            } else if (typeLinks == ExpressieType.STRING) {
                resultaat = pasToeOpStrings(berekendeOperandLinks, berekendeOperandRechts);
            } else if (typeLinks == ExpressieType.DATUM || typeLinks == ExpressieType.DATUMTIJD) {
                final DatumLiteralExpressie v1 = (DatumLiteralExpressie) berekendeOperandLinks;
                final DatumLiteralExpressie v2 = (DatumLiteralExpressie) berekendeOperandRechts;
                resultaat = pasToeOpDatums(v1, v2);
            } else if (typeLinks == ExpressieType.LIJST) {
                resultaat = pasToeOpLijsten(berekendeOperandLinks, berekendeOperandRechts);
            } else if (ExpressieType.isBRPObjectType(typeLinks)) {
                final BrpObjectExpressie v1 = (BrpObjectExpressie) berekendeOperandLinks;
                final BrpObjectExpressie v2 = (BrpObjectExpressie) berekendeOperandRechts;
                resultaat = pasToeOpBrpObjecten(v1, v2);
            } else {
                resultaat = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            }
        }
        return resultaat;
    }
}
