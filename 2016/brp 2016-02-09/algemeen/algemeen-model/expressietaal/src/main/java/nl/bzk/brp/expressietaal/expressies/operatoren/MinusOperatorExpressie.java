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
import nl.bzk.brp.expressietaal.expressies.literals.GrootGetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.expressies.literals.PeriodeLiteralExpressie;
import org.joda.time.DateTime;

/**
 * Representeert een numerieke aftrekexpressie.
 */
public class MinusOperatorExpressie extends AbstractRekenoperatorExpressie {

    /**
     * Constructor.
     *
     * @param termLinks  Linkerterm van de operator.
     * @param termRechts Rechterterm van de operator.
     */
    public MinusOperatorExpressie(final Expressie termLinks, final Expressie termRechts)
    {
        super(termLinks, termRechts);
    }

    @Override
    public final int getPrioriteit() {
        return PRIORITEIT_MINUS;
    }

    @Override
    public final Expressie optimaliseer(final Context context) {
        final Expressie resultaat;
        final Expressie geoptimaliseerdeOperandLinks = getOperandLinks().optimaliseer(context);
        final Expressie geoptimaliseerdeOperandRechts = getOperandRechts().optimaliseer(context);

        if (geoptimaliseerdeOperandLinks.isNull(context) || geoptimaliseerdeOperandRechts.isNull(context)) {
            resultaat = NullValue.getInstance();
        } else if (geoptimaliseerdeOperandLinks.isConstanteWaarde(ExpressieType.GETAL)
            && geoptimaliseerdeOperandLinks.alsInteger() == 0)
        {
            resultaat =
                new NumeriekeInverseOperatorExpressie(geoptimaliseerdeOperandRechts).optimaliseer(context);

        } else if (geoptimaliseerdeOperandRechts.isConstanteWaarde(ExpressieType.GETAL)
            && geoptimaliseerdeOperandRechts.alsInteger() == 0)
        {
            resultaat = geoptimaliseerdeOperandLinks;
        } else if (geoptimaliseerdeOperandLinks.isConstanteWaarde(ExpressieType.GETAL)
            && geoptimaliseerdeOperandRechts.isConstanteWaarde(ExpressieType.GETAL))
        {
            resultaat = pasOperatorToe(geoptimaliseerdeOperandLinks, geoptimaliseerdeOperandRechts, context);
        } else {
            resultaat = new MinusOperatorExpressie(geoptimaliseerdeOperandLinks, geoptimaliseerdeOperandRechts);
        }
        return resultaat;
    }

    @Override
    protected final String operatorAlsString() {
        return "-";
    }

    @Override
    protected final Expressie pasToeOpGetallen(final Expressie operandLinks,
        final Expressie operandRechts)
    {
        return new GetalLiteralExpressie(operandLinks.alsInteger() - operandRechts.alsInteger());
    }

    @Override
    protected final Expressie pasToeOpGroteGetallen(final Expressie operandLinks,
        final Expressie operandRechts)
    {
        return new GrootGetalLiteralExpressie(operandLinks.alsLong() - operandRechts.alsLong());
    }

    @Override
    protected final Expressie pasToeOpDatumEnGetal(final DatumLiteralExpressie operandLinks,
        final GetalLiteralExpressie operandRechts)
    {
        Expressie resultaat = NullValue.getInstance();
        if (operandLinks.isVolledigBekend()) {
            final DateTime dt = operandLinks.alsDateTime();
            resultaat = new DatumLiteralExpressie(dt.minusDays(operandRechts.alsInteger()));
        }
        return resultaat;
    }

    @Override
    protected final Expressie pasToeOpDatumEnPeriode(final DatumLiteralExpressie operandLinks,
        final PeriodeLiteralExpressie operandRechts)
    {
        Expressie resultaat = NullValue.getInstance();
        if (operandLinks.isVolledigBekend()) {
            DateTime dt = operandLinks.alsDateTime();
            dt = dt.minusYears(operandRechts.getJaar());
            dt = dt.minusMonths(operandRechts.getMaand());
            resultaat = new DatumLiteralExpressie(dt.minusDays(operandRechts.getDag()));
        }
        return resultaat;
    }

    @Override
    protected final Expressie pasToeOpPerioden(final PeriodeLiteralExpressie operandLinks,
        final PeriodeLiteralExpressie operandRechts)
    {
        return new PeriodeLiteralExpressie(
            operandLinks.getJaar() - operandRechts.getJaar(),
            operandLinks.getMaand() - operandRechts.getMaand(),
            operandLinks.getDag() - operandRechts.getDag());
    }

    @Override
    protected final Expressie pasToeOpLijsten(final Expressie operandLinks,
        final Expressie operandRechts)
    {
        return new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE,
            "Operator '-' kan niet toegepast worden op lijsten.");
    }
}
