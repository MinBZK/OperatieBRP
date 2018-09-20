/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import nl.bzk.brp.model.basis.VasteWaardeAttribuutType;


/**
 * Uit migratieoogpunt kiezen we NIET voor een stamtabel.
 * RvdP 5 september 2011.
 *
 *
 *
 */
public enum AanduidingBijHuisnummer implements VasteWaardeAttribuutType<String> {

    /**
     * bij.
     */
    BY("by", "bij"),
    /**
     * tegen over.
     */
    TO("to", "tegen over");

    private final String waarde;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param waarde Waarde voor AanduidingBijHuisnummer
     * @param omschrijving Omschrijving voor AanduidingBijHuisnummer
     */
    private AanduidingBijHuisnummer(final String waarde, final String omschrijving) {
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
     * Retourneert omschrijving. voor AanduidingBijHuisnummer
     *
     * @return omschrijving voor AanduidingBijHuisnummer
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Tekstuele representatie van de enumeratie waarde.
     *
     * @return Tekstuele representatie van AanduidingBijHuisnummer.
     */
    @Override
    public String toString() {
        return String.format("%s - %s", waarde, omschrijving);
    }
}
