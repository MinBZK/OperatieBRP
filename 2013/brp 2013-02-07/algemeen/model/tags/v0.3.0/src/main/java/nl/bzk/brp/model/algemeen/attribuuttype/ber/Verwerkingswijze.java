/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.ber;

import nl.bzk.brp.model.basis.VasteWaardeAttribuutType;


/**
 *
 *
 */
public enum Verwerkingswijze implements VasteWaardeAttribuutType<String> {

    /**
     * Bijhouden.
     */
    B("B", "Bijhouden"),
    /**
     * Prevalideren.
     */
    P("P", "Prevalideren");

    private final String waarde;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param waarde Waarde voor Verwerkingswijze
     * @param omschrijving Omschrijving voor Verwerkingswijze
     */
    private Verwerkingswijze(final String waarde, final String omschrijving) {
        this.waarde = waarde;
        this.omschrijving = omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWaarde() {
        return waarde;
    }

    /**
     * Retourneert omschrijving. voor Verwerkingswijze
     *
     * @return omschrijving voor Verwerkingswijze
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Tekstuele representatie van de enumeratie waarde.
     *
     * @return Tekstuele representatie van Verwerkingswijze.
     */
    @Override
    public String toString() {
        return String.format("%s - %s", waarde, omschrijving);
    }
}
