/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import nl.bzk.brp.model.basis.VasteWaardeAttribuutType;


/**
 * Waarde van dit veld is ALTIJD gelijk aan JA.
 *
 * Het Ja datatype heeft, uitzonderlijk, in Postgres GEEN waardebeperking: daar is het namelijk geen char(1). Om die
 * reden is bij de regel (handmatig) de waarde "IN_OGM" op N gezet. De code houdt hier rekening mee, zodat er geen
 * waarderegel wordt ge�mplementeerd in Postgres. RvdP 9 december 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.AttribuutTypenGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:49 CET 2013.
 */
public enum Ja implements VasteWaardeAttribuutType<Boolean> {

    /**
     * Ja.
     */
    J(true, "Ja");

    private final Boolean waarde;
    private final String  omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param waarde Waarde voor Ja
     * @param omschrijving Omschrijving voor Ja
     */
    private Ja(final Boolean waarde, final String omschrijving) {
        this.waarde = waarde;
        this.omschrijving = omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getWaarde() {
        return waarde;
    }

    /**
     * Retourneert omschrijving voor Ja
     *
     * @return omschrijving voor Ja
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Tekstuele representatie van de enumeratie waarde.
     *
     * @return Tekstuele representatie van Ja.
     */
    @Override
    public String toString() {
        return String.format("%s - %s", waarde, omschrijving);
    }
}
