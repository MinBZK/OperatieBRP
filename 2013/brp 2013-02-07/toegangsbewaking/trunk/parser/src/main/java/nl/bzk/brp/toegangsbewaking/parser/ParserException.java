/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.parser;

import nl.bzk.brp.toegangsbewaking.parser.tokenizer.Token;


/**
 * Exceptie specifiek voor fouten die optreden bij het parsen. De exceptie bevat, naast het standaard fout bericht,
 * ook een lijn nummer en karakter nummer die aangeven waar de fout is opgetreden in de tekst die wordt geparst.
 */
public class ParserException extends Exception {

    /** Serial version id. */
    private static final long serialVersionUID = -6765406449125374774L;

    private int lijnNr;
    private int karakterNr;
    private boolean lokatieGezet;

    /**
     * Standaard constructor die het fout bericht zet, alsmede lijn en karakter nummer van de positie waar de fout
     * optrad.
     *
     * @param message het fout bericht.
     * @param lijnNr nummer van de lijn waarop de fout optrad.
     * @param karakterNr nummer van het karakter (binnen de lijn) waarop de fout optrad.
     */
    public ParserException(final String message, final int lijnNr, final int karakterNr) {
        super(message);
        this.lijnNr = lijnNr;
        this.karakterNr = karakterNr;
        lokatieGezet = true;
    }

    /**
     * Constructor die het fout bericht zet, maar de lokatie van de fout (nog) niet zet.
     *
     * @param message het fout bericht.
     */
    public ParserException(final String message) {
        this(message, null);
    }

    /**
     * Constructor die het fout bericht zet, alsmede lijn en karakter nummer van de positie waar de fout
     * optrad op basis van de opgegeven token.
     *
     * @param message het fout bericht.
     * @param token de token waarbinnen de fout optrad.
     */
    public ParserException(final String message, final Token token) {
        super(message);
        if (token != null) {
            zetFoutieveToken(token);
        } else {
            lijnNr = 0;
            karakterNr = 1;
            lokatieGezet = false;
        }
    }

    /**
     * Retourneert het nummer van de regel/lijn waarop de fout optrad.
     * @return het nummer van de regel/lijn waarop de fout optrad.
     */
    public int getLijnNr() {
        return lijnNr;
    }

    /**
     * Retourneert het nummer van het karakter (binnen de lijn) waarop de fout optrad.
     * @return het nummer van het karakter (binnen de lijn) waarop de fout optrad.
     */
    public int getKarakterNr() {
        return karakterNr;
    }

    /**
     * Geeft aan of de lokatie waar het probleem optrad is gezet of niet.
     * @return of de lokatie waar het probleem optrad is gezet of niet.
     */
    public boolean isLokatieGezet() {
        return lokatieGezet;
    }

    /**
     * Zet de informatie waar de fout is opgetreden op basis van de token die de fout opleverde.
     * @param token de token die de fout opleverde.
     * @return of de lokatie reeds was gezet (en nu dus is overschreven).
     */
    public boolean zetFoutieveToken(final Token token) {
        boolean wasLokatieGezet = lokatieGezet;
        lijnNr = token.getLine();
        karakterNr = token.getPosition();
        lokatieGezet = true;
        return wasLokatieGezet;
    }

}
