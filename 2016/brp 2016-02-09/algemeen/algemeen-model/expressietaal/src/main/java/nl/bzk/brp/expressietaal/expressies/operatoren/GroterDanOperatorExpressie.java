/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.operatoren;

import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.FoutExpressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;

/**
 * Representeert een groter-dan-expressie.
 */
public class GroterDanOperatorExpressie extends AbstractVergelijkingsoperatorExpressie {

    /**
     * Constructor.
     *
     * @param termLinks  Linkerterm van de operator.
     * @param termRechts Rechterterm van de operator.
     */
    public GroterDanOperatorExpressie(final Expressie termLinks, final Expressie termRechts) {
        super(termLinks, termRechts);
    }

    @Override
    protected final String operatorAlsString() {
        return ">";
    }

    @Override
    public final int getPrioriteit() {
        return PRIORITEIT_VERGELIJKINGSOPERATOR;
    }

    @Override
    protected final Expressie pasToeOpGetallen(final Expressie waardeLinks, final Expressie waardeRechts) {
        return BooleanLiteralExpressie.getExpressie(waardeLinks.alsInteger() > waardeRechts.alsInteger());
    }

    @Override
    protected final Expressie pasToeOpGroteGetallen(final Expressie waardeLinks, final Expressie waardeRechts) {
        return BooleanLiteralExpressie.getExpressie(waardeLinks.alsLong() > waardeRechts.alsLong());
    }

    @Override
    protected final Expressie pasToeOpBooleans(final Expressie waardeLinks, final Expressie waardeRechts) {
        return BooleanLiteralExpressie.getExpressie(waardeLinks.alsBoolean() && !waardeRechts.alsBoolean());
    }

    @Override
    protected final Expressie pasToeOpStrings(final Expressie waardeLinks, final Expressie waardeRechts) {
        return BooleanLiteralExpressie.getExpressie(waardeLinks.alsString().compareTo(waardeRechts.alsString()) > 0);
    }

    @Override
    protected final Expressie pasToeOpDatums(final DatumLiteralExpressie waardeLinks,
                                             final DatumLiteralExpressie waardeRechts)
    {
        Boolean resultaat = null;
        if (waardeLinks.isVolledigBekend() && waardeRechts.isVolledigBekend()) {
            // bekende datums
            resultaat = waardeLinks.compareTo(waardeRechts) == 1;
        } else if (waardeLinks.isVolledigOnbekend() || waardeRechts.isVolledigOnbekend()) {
            // volledig onbekende datums
            resultaat = null;
        } else {
            final int jaarCompare = waardeLinks.getJaar().compareTo(waardeRechts.getJaar());

            if (jaarCompare != 0) {
                resultaat = jaarCompare == 1;
            } else {
                if (!(waardeLinks.isMaandDagOnbekend() || waardeRechts.isMaandDagOnbekend())) {
                    // jaren gelijk, en iig maanden bekend
                    final int maandCompare = waardeLinks.getMaand().compareTo(waardeRechts.getMaand());

                    if (maandCompare != 0 && (waardeLinks.isDagOnbekend() || waardeRechts.isDagOnbekend())) {
                        resultaat = maandCompare == 1;
                    } else if (waardeLinks.getDag().getWaarde() == 1) {
                        resultaat = false;
                    }
                } else if (waardeLinks.getMaand().getWaarde() == 1 && waardeLinks.getDag().getWaarde() == 1) {
                    resultaat = false;
                }
            }
        }

        if (null != resultaat) {
            return BooleanLiteralExpressie.getExpressie(resultaat);
        } else {
            return NullValue.getInstance();
        }
    }

    @Override
    protected final Expressie pasToeOpLijsten(final Expressie waardeLinks, final Expressie waardeRechts) {
        return BooleanLiteralExpressie.getExpressie(LijstExpressie.vergelijkLijsten(waardeLinks, waardeRechts, null)
            == LijstExpressie.Vergelijkingsresultaat.GROTER);
    }

    @Override
    protected final Expressie pasToeOpBrpObjecten(final BrpObjectExpressie waardeLinks,
                                                  final BrpObjectExpressie waardeRechts)
    {
        return new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE,
            "Operator niet gedefinieerd voor BRP-objecten");
    }
}
