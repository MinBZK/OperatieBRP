/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;


/**
 * NullHook implementatie
 */
public class NullHook implements ConversieHook {
    /**
     * In dit geval doen we niks.
     * @param stap stap
     * @param object geconverteerde (tussen)object
     */
    @Override
    public void stap(final ConversieStap stap, final Object object) {
          // Niets
    }
}
