/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common;

/**
 * Exceptie die gegooid wordt in het geval het resultaat niet met zekerheid is vastgesteld.
 * De testclient kan in dat geval een nieuwe poging doen op een later moment.
 */
public class OnzekerResultaatExceptie extends TestclientExceptie {

    /**
     * Constructor.
     * @param s message
     */
    public OnzekerResultaatExceptie(final String s) {
        super(s);
    }
}
