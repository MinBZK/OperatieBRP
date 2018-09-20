/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie;

/**
 * Wordt gegeven indien een LO3 voorwaarde regel niet te vertalen is.
 *
 */
public final class Lo3VoorwaardeRegelOnvertaalbaarExceptie extends RuntimeException {

    private static final long serialVersionUID = 145233523L;

    private final String geconverteerdDeel;

    /**
     * Maakt een nieuwe exceptie aan op basis van regel en reden van het niet kunnen vertalen.
     *
     * @param regel
     *            de niet te vertalen regel
     * @param geconverteerdDeel
     *            het deel van de te vertalen regel dat wel geconverteerd kon worden
     * @param reden
     *            onderliggende reden van het niet kunnen vertalen
     */
    public Lo3VoorwaardeRegelOnvertaalbaarExceptie(final String regel, final String geconverteerdDeel, final Throwable reden) {
        super(regel, reden);
        this.geconverteerdDeel = geconverteerdDeel;
    }

    /**
     * Geeft het deel terug dat wel geconverteerd kon worden.
     *
     * @return Het geconverteerde deel.
     */
    public String getGeconverteerdDeel() {
        return geconverteerdDeel;
    }
}
