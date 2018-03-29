/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

/**
 * Een element binnen een bijhoudingsbericht.
 */
public interface Element {

    /**
     * Geef het verzoekbericht waar dit element bij hoort.
     * @return het verzoekbericht, of null als dit element niet bij een verzoekbericht hoort
     */
    BijhoudingVerzoekBericht getVerzoekBericht();

    /**
     * Zet het verzoekbericht waar dit element bij hoort.
     * @param verzoekBericht het verzoekbericht
     */
    void setVerzoekBericht(BijhoudingVerzoekBericht verzoekBericht);

    /**
     * Deze methode kan worden overridden om functionaliteit uit te voeren nadat alle
     * elementen van een bijhoudingsbericht zijn gemaakt en gecontroleerd zijn op geldige objectsleutels en referenties.
     */
    default void postConstruct() {
    }
}
