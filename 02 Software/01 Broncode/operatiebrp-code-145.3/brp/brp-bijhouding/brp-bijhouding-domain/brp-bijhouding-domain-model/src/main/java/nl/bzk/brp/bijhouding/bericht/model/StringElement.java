/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

/**
 * Dit element wordt gebruikt voor alle BMR attributen die string waardes bevatten.
 */
public final class StringElement extends AbstractBmrAttribuut<String> {

    /**
     * Maakt een nieuw StringElement object.
     *
     * @param waarde waarde, mag niet null zijn
     */
    public StringElement(final String waarde) {
        super(waarde);
    }

    @Override
    public String toString() {
        return getWaarde();
    }

    /**
     * Maakt een instantie van {@link StringElement}, null als {@link #waarde} null is.
     *
     * @param waarde
     *            de string die omgezet moet worden
     * @return de {@link StringElement} of null als {@link #waarde} null is
     */
    static StringElement getInstance(final String waarde) {
        if (waarde == null) {
            return null;
        } else {
            return new StringElement(waarde);
        }
    }

    /**
     * Test of het element niet null is en dat de lengte van de ingevulde String groter als 1 is.
     *
     * @param element the element
     * @return the boolean
     */
    public static boolean heeftElementWaarde(final StringElement element) {
        return element != null && element.getWaarde().length() > 0;
    }
}
