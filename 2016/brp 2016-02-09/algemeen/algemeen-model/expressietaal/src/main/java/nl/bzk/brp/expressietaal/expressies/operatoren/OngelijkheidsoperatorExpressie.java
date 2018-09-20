/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.operatoren;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;

/**
 * Representeert een ongelijkheidsexpressie.
 */
public class OngelijkheidsoperatorExpressie extends AbstractVergelijkingsoperatorExpressie {

    /**
     * Constructor.
     *
     * @param termLinks  Linkerterm van de operator.
     * @param termRechts Rechterterm van de operator.
     */
    public OngelijkheidsoperatorExpressie(final Expressie termLinks, final Expressie termRechts) {
        super(termLinks, termRechts);
    }

    @Override
    protected final String operatorAlsString() {
        return "<>";
    }

    @Override
    public final int getPrioriteit() {
        return PRIORITEIT_GELIJKHEIDSOPERATOR;
    }

    @Override
    protected final Expressie pasToeOpGetallen(final Expressie waardeLinks, final Expressie waardeRechts) {
        return BooleanLiteralExpressie.getExpressie(waardeLinks.alsInteger() != waardeRechts.alsInteger());
    }

    @Override
    protected final Expressie pasToeOpGroteGetallen(final Expressie waardeLinks, final Expressie waardeRechts) {
        return BooleanLiteralExpressie.getExpressie(waardeLinks.alsLong() != waardeRechts.alsLong());
    }

    @Override
    protected final Expressie pasToeOpBooleans(final Expressie waardeLinks, final Expressie waardeRechts) {
        return BooleanLiteralExpressie.getExpressie(waardeLinks.alsBoolean() != waardeRechts.alsBoolean());
    }

    @Override
    protected final Expressie pasToeOpStrings(final Expressie waardeLinks, final Expressie waardeRechts) {
        return BooleanLiteralExpressie.getExpressie(!waardeLinks.alsString().equals(waardeRechts.alsString()));
    }

    @Override
    protected final Expressie pasToeOpDatums(final DatumLiteralExpressie waardeLinks,
                                             final DatumLiteralExpressie waardeRechts)
    {
        Boolean resultaat = null;
        if (waardeLinks.isVolledigBekend() && waardeRechts.isVolledigBekend()) {
            // bekende datums
            resultaat = waardeLinks.compareTo(waardeRechts) != 0;
        } else if (waardeLinks.isVolledigOnbekend() || waardeRechts.isVolledigOnbekend()) {
            // volledig onbekende datums
            resultaat = null;
        } else {
            if (waardeLinks.getJaar().equals(waardeRechts.getJaar())) {
                if (!(waardeLinks.isMaandDagOnbekend() || waardeRechts.isMaandDagOnbekend())) {
                    if (waardeLinks.getMaand().equals(waardeRechts.getMaand())) {
                        if (!(waardeLinks.isDagOnbekend() || waardeRechts.isDagOnbekend())) {
                            resultaat = !waardeLinks.getDag().equals(waardeRechts.getDag());
                        }
                    } else if (!waardeLinks.isMaandDagOnbekend() && !waardeRechts.isMaandDagOnbekend()) {
                        resultaat = true;
                    }
                }
            } else {
                resultaat = true;
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
            != LijstExpressie.Vergelijkingsresultaat.GELIJK);
    }

    @Override
    protected final Expressie pasToeOpBrpObjecten(final BrpObjectExpressie waardeLinks,
                                                  final BrpObjectExpressie waardeRechts)
    {
        return new LogischeNietExpressie(BrpObjectExpressieUtil.objectenGelijk(waardeLinks, waardeRechts)).evalueer(null);
    }
}
