/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;

/**
 * Hook tijdens de conversie service.
 */
public interface ConversieHook {

    /**
     * De 'lege' ConversieHook; deze doet niets.
     */
    ConversieHook NULL_HOOK = new ConversieHook() {
        @Override
        public void stap(final ConversieStap stap, final Object object) {
            // Niets
        }
    };

    /**
     * Registratie *NA* een stap is uitgevoerd.
     * 
     * @param stap
     *            stap
     * @param object
     *            geconverteerde (tussen)object
     * 
     */
    void stap(ConversieStap stap, Object object);
}
