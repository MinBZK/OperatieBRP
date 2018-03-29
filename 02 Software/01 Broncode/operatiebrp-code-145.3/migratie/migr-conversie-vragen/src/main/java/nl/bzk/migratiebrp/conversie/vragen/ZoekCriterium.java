/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.vragen;

/**
 * Zoek criterium.
 */
public class ZoekCriterium {

    private final String element;
    private final String waarde;
    private ZoekCriterium of;

    /**
     * Constructor.
     * @param element element waarop gezocht wordt
     * @param waarde waarde van het gezochte element
     */
    public ZoekCriterium(final String element, final String waarde) {
        this.element = element;
        if (waarde != null && !"".equals(waarde)) {
            this.waarde = waarde;
        } else {
            this.waarde = null;
        }
    }

    /**
     * @return element
     */
    public String getElement() {
        return element;
    }

    /**
     * @return waarde
     */
    public String getWaarde() {
        return waarde;
    }

    /**
     * set het of zoekcriterium.
     * @param zoekCriterium of zoekcriterium.
     */
    public void setOf(final ZoekCriterium zoekCriterium) {
        this.of = zoekCriterium;
    }

    /**
     * @return of zoekcriterium
     */
    public ZoekCriterium getOf() {
        return of;
    }
}
