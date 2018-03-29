/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

/**
 * Indien een GBA voorwaarde regel niet kan woorden vertaald dan wordt deze exceptie gegooid. De volledige regel wordt
 * meegegen zodat deze gelogd kan worden.
 */
public class GbaVoorwaardeOnvertaalbaarExceptie extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Maakt exceptie aan.
     * @param regel de onvertaalbare voorwaarderegel
     */
    public GbaVoorwaardeOnvertaalbaarExceptie(final String regel) {
        super(regel);
    }

    /**
     * Maakt exceptie aan.
     * @param regel de onvertaalbare voorwaarderegel
     * @param exceptie onderliggende oorzaak van de exceptie
     */
    public GbaVoorwaardeOnvertaalbaarExceptie(final String regel, final Throwable exceptie) {
        super(regel, exceptie);
    }
}
