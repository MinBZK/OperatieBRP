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

/**
 * Representeert constante datumtijd-expressies.
 */
public class DateTimeLiteralExpressie extends AbstractLiteralExpressie {

    /**
     * Code voor onbekend urental in een tijdstip.
     */
    public static final int UUR_ONBEKEND = -1;
    /**
     * Code voor onbekende minuten in een tijdstip.
     */
    public static final int MINUTEN_ONBEKEND = -1;

    private final DateLiteralExpressie datum;
    private final int uur;
    private final int minuten;

    /**
     * Constructor.
     *
     * @param jaar    Jaartal.
     * @param maand   Maandnummer.
     * @param dag     Dagnummer.
     * @param uur     Uren.
     * @param minuten Minuten.
     */
    public DateTimeLiteralExpressie(final int jaar, final int maand, final int dag, final int uur, final int minuten) {
        this.datum = new DateLiteralExpressie(jaar, maand, dag);
        this.uur = uur;
        this.minuten = minuten;
    }

    /**
     * Geeft jaartal van de datum.
     *
     * @return Jaartal.
     */
    public final int getJaar() {
        return datum.getJaar();
    }

    /**
     * Geeft maandnummer van de datum.
     *
     * @return Maandnummer.
     */
    public final int getMaand() {
        return datum.getMaand();
    }

    /**
     * Geeft dagnummer van de datum.
     *
     * @return Dagnummer.
     */
    final int getDag() {
        return datum.getDag();
    }

    /**
     * Geeft de datum.
     *
     * @return Datum.
     */
    final DateLiteralExpressie getDatum() {
        return datum;
    }

    final int getUur() {
        return uur;
    }

    final int getMinuten() {
        return minuten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return ExpressieType.DATETIME;
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
        if (minuten != MINUTEN_ONBEKEND) {
            result = String.format("%s:%02d:%02d", datum.alsLeesbareString(), getUur(), getMinuten());
        } else if (uur != UUR_ONBEKEND) {
            result = String.format("%s:%02d", datum.alsLeesbareString(), getUur());
        } else {
            result = datum.alsLeesbareString();
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
            return String.format("%c%04d-%s-%02d:%02d:%02d%c", Characters.DATUM_START, getJaar(), maandNaam, getDag(),
                    getUur(), getMinuten(), Characters.DATUM_EIND);
        } else {
            return String.format("%c%04d-???-%02d:%02d:%02d%c", Characters.DATUM_START, getJaar(), getDag(), getUur(),
                    getMinuten(), Characters.DATUM_EIND);
        }
    }
}
