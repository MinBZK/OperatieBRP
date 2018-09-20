/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import nl.bzk.brp.model.basis.VasteAttribuutWaarde;


/**
 *
 *
 */
public enum StatusHistorie implements VasteAttribuutWaarde<String> {

    /**
     * Actueel voorkomend.
     */
    A("A", "Actueel voorkomend"),
    /**
     * Toekomstig voorkomend, maar niet Actueel.
     */
    T("T", "Toekomstig voorkomend, maar niet Actueel"),
    /**
     * Materieel voorkomend, maar niet Actueel (en niet Toekomstig).
     */
    M("M", "Materieel voorkomend, maar niet Actueel (en niet Toekomstig)"),
    /**
     * (Alleen) Formeel historisch voorkomend.
     */
    F("F", "(Alleen) Formeel historisch voorkomend"),
    /**
     * Geen records aanwezig (ook geen vervallen records).
     */
    X("X", "Geen records aanwezig (ook geen vervallen records)");

    private final String vasteWaarde;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param vasteWaarde  VasteWaarde voor StatusHistorie
     * @param omschrijving Omschrijving voor StatusHistorie
     */
    private StatusHistorie(final String vasteWaarde, final String omschrijving) {
        this.vasteWaarde = vasteWaarde;
        this.omschrijving = omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVasteWaarde() {
        return vasteWaarde;
    }

    /**
     * Retourneert omschrijving. voor StatusHistorie
     *
     * @return omschrijving voor StatusHistorie
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s - %s", vasteWaarde, omschrijving);
    }

}
