/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message;

/**
 * Berichtfactory.
 */
public interface BerichtFactory {

    /**
     * Parse een 'String' bericht naar een 'Object' bericht.
     * 
     * Indien het String bericht onbekend of ongeldig is word een OngeldigBericht of OnbekendBericht geretourneerd
     * 
     * @param bericht
     *            bericht
     * @return bericht
     */
    // CHECKSTYLE:OFF - Throws count
    Bericht getBericht(final String bericht);
    // CHECKSTYLE:ON
}
