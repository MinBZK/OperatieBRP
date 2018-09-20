/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.parser.ParserUtils;
import nl.bzk.brp.expressietaal.symbols.Characters;
import nl.bzk.brp.expressietaal.symbols.DefaultKeywordMapping;
import nl.bzk.brp.expressietaal.symbols.Keywords;
import org.joda.time.DateTime;

/**
 * Representeert constante datumexpressies.
 */
public class DateLiteralExpressie extends AbstractLiteralExpressie {

    /**
     * Code voor onbekende maand in een datum.
     */
    public static final int MAAND_ONBEKEND = 0;
    /**
     * Code voor een onbekende dag in een datum.
     */
    public static final int DAG_ONBEKEND = 0;

    private static final int JAARFACTOR = 10000;
    private static final int MAANDFACTOR = 100;

    private final int jaar;
    private final int maand;
    private final int dag;

    /**
     * Constructor.
     *
     * @param jaar  Jaartal.
     * @param maand Maandnummer.
     * @param dag   Dagnummer.
     */
    public DateLiteralExpressie(final int jaar, final int maand, final int dag) {
        this.jaar = jaar;
        this.maand = maand;
        this.dag = dag;
    }

    /**
     * Constructor.
     *
     * @param datum Datum als integer, zoals gebruikt wordt in de Java mapping van het BMR.
     */
    public DateLiteralExpressie(final int datum) {
        this.jaar = datum / JAARFACTOR;
        this.maand = (datum - jaar * JAARFACTOR) / MAANDFACTOR;
        this.dag = (datum - jaar * JAARFACTOR - maand * MAANDFACTOR);
    }

    /**
     * Constructor.
     *
     * @param dt DateTime-waarde voor datum literal.
     */
    public DateLiteralExpressie(final DateTime dt) {
        this(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
    }

    /**
     * Geef jaartal van de datum.
     *
     * @return Jaartal.
     */
    public final int getJaar() {
        return jaar;
    }

    /**
     * Geef maandnummer van de datum.
     *
     * @return Maandnummer.
     */
    public final int getMaand() {
        return maand;
    }

    /**
     * Geef dagnummer van de datum.
     *
     * @return Dagnummer.
     */
    public final int getDag() {
        return dag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return ExpressieType.DATE;
    }

    @Override
    public final boolean isRootObject() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsLeesbareString() {
        String result;
        if (dag != DAG_ONBEKEND) {
            result = String.format("%04d-%02d-%02d", getJaar(), getMaand(), getDag());
        } else if (maand != MAAND_ONBEKEND) {
            result = String.format("%04d-%02d", getJaar(), getMaand());
        } else {
            result = String.format("%04d", getJaar());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsFormeleString() {
        Keywords maandKeyword = ParserUtils.maandNummerNaarVerkorteNaam(getMaand());
        String maandNaam = DefaultKeywordMapping.getSyntax(maandKeyword);
        if (maandNaam != null) {
            return String.format("%c%04d-%s-%02d%c", Characters.DATUM_START, getJaar(), maandNaam, getDag(),
                    Characters.DATUM_EIND);
        } else {
            return String.format("%c%04d-???-%02d%c", Characters.DATUM_START, getJaar(), getDag(),
                    Characters.DATUM_EIND);
        }
    }
}

