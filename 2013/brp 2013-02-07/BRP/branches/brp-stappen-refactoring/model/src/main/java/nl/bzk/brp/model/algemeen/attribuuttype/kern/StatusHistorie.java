/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import nl.bzk.brp.model.basis.VasteWaardeAttribuutType;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.AttribuutTypenGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:49 CET 2013.
 */
public enum StatusHistorie implements VasteWaardeAttribuutType<String> {

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

    private final String waarde;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param waarde Waarde voor StatusHistorie
     * @param omschrijving Omschrijving voor StatusHistorie
     */
    private StatusHistorie(final String waarde, final String omschrijving) {
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
     * Retourneert omschrijving voor StatusHistorie
     *
     * @return omschrijving voor StatusHistorie
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Tekstuele representatie van de enumeratie waarde.
     *
     * @return Tekstuele representatie van StatusHistorie.
     */
    @Override
    public String toString() {
        return String.format("%s - %s", waarde, omschrijving);
    }
}
