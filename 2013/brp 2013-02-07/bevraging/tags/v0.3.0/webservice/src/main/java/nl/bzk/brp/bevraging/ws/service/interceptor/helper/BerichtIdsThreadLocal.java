/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.interceptor.helper;

import nl.bzk.brp.bevraging.business.dto.BerichtenIds;


/**
 * ThreadLocal wordt gebruikt om het bericht ID later te kunnen achterhalen. Dit daar deze ids niet aan de
 * messagecontext kunnen worden toegevoegd en in de interceptors worden bepaald en/of nodig zijn, maar ook
 * in de werkelijke implementatie van de service. Vandaar de ThreadLocal gebruik.
 */
public final class BerichtIdsThreadLocal {

    /**
     * Constructor is private want een instantie is nooit nodig.
     */
    private BerichtIdsThreadLocal() {
    }

    /**
     * De ThreadLocal variabele om het bericht id op te kunnen slaan.
     */
    private static final ThreadLocal<BerichtenIds> GEBRUIKERS_THREAD = new ThreadLocal<BerichtenIds>();

    /**
     * Zet de bericht id's in een ThreadLocal variabele.
     *
     * @param berichtenIds berichtenIds
     */
    public static void setBerichtenIds(final BerichtenIds berichtenIds) {
        GEBRUIKERS_THREAD.set(berichtenIds);
    }

    /**
     * @return ingaandBerichtId
     */
    public static Long getIngaandBerichtId() {
        Long id;
        BerichtenIds berichtenIds = GEBRUIKERS_THREAD.get();
        if (berichtenIds == null) {
            id = null;
        } else {
            id = berichtenIds.getIngaandBerichtId();
        }
        return id;
    }

    /**
     * @return uitgaandBerichtId
     */
    public static Long getUitgaandBerichtId() {
        Long id;
        BerichtenIds berichtenIds = GEBRUIKERS_THREAD.get();
        if (berichtenIds == null) {
            id = null;
        } else {
            id = berichtenIds.getUitgaandBerichtId();
        }
        return id;
    }

    /**
     * Verwijder ThreadLocal variabele en zorg ervoor dat ThreadLocal wordt opgeruimd.
     */
    public static void verwijder() {
        GEBRUIKERS_THREAD.remove();
    }

}
