/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor.helper;

/**
 * Todo Tijdelijk is er voor gekozen om ThreadLocal te gebruiken om het bericht ID later te kunnen achterhalen.
 */
public final class BerichtIDsThreadLocal {


    /**
     * Constructor is private want een instantie is nooit nodig.
     */
    private BerichtIDsThreadLocal() {
    }

    /**
     * De ThreadLocal variabele om het bericht id op te kunnen slaan.
     */
    private static final ThreadLocal<BerichtenIds> GEBRUIKERS_THREAD = new ThreadLocal<BerichtenIds>();

    /**
     * Zet het bericht id in een ThreadLocal variabele.
     *
     * @param ingaandBerichtId foo
     * @param uitgaandBerichtiD bar
     */
    public static void setBerichtIDs(final Long ingaandBerichtId, final Long uitgaandBerichtiD) {
        GEBRUIKERS_THREAD.set(new BerichtenIds(ingaandBerichtId, uitgaandBerichtiD));
    }

    public static BerichtenIds getBerichtIDs() {
        return GEBRUIKERS_THREAD.get();
    }

    /**
     * Verwijder ThreadLocal variabele en zorg ervoor dat ThreadLocal wordt opgeruimd.
     */
    public static void verwijder() {
        GEBRUIKERS_THREAD.remove();
    }

    /**
     * Een wrapper class voor het ingaande en uitgaande berichtId.
     */
    public static final class BerichtenIds {
        private Long ingaandBerichtId;
        private Long uitgaandBerichtId;

        /**
         * Creeer een wrapper voor het inkomende en uitgaande bericht ID.
         * @param ingaandBerichtId Het id van het inkomende bericht.
         * @param uitgaandBerichtId Het id van het uitgaande bericht.
         */
        BerichtenIds(final Long ingaandBerichtId, final Long uitgaandBerichtId) {
            this.ingaandBerichtId = ingaandBerichtId;
            this.uitgaandBerichtId = uitgaandBerichtId;
        }

        public Long getIngaandBerichtId() {
            return ingaandBerichtId;
        }

        public Long getUitgaandBerichtId() {
            return uitgaandBerichtId;
        }
    }
}
