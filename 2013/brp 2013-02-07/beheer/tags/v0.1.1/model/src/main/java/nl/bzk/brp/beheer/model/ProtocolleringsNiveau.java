/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.model;

/**
 * Het niveau van protocolleren dat van toepassing is.
 *
 * Aan de hand van het protocolleringsniveau wordt bepaald of een bepaalde verstrekking van gegevens aan de burger
 * getoond mag worden (indien de burger daar om vraagt), alsmede of er een beperking is voor gegevensambtenaren
 * voor het inzien van deze protocolgegevens.
 */
public enum ProtocolleringsNiveau {

    /**
     * Deze enumeratie correspondeert met een statische tabel waarvan de id's bij 1 beginnen te tellen. De ordinal van
     * een enum begint echter bij 0.
     */
    DUMMY(0, null, null),
    /**
     * Geen berperking.
     */
    GEEN_BEPERKING(1, "geen beperking", null),
    /**
     * Beperking.
     */
    BEPERKING(2, "beperking", null),
    /**
     * Geheim.
     */
    GEHEIM(3, "geheim", null);

    private final int    code;
    private final String naam;
    private final String omschrijving;

    /**
     * Constructor voor de initialisatie van de enumeratie.
     *
     * @param code code.
     * @param naam naam.
     * @param omschrijving omschrijving.
     */
    private ProtocolleringsNiveau(final int code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert de code.
     * @return de code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Retourneert de naam.
     * @return de naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert de omschrijving.
     * @return de omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
