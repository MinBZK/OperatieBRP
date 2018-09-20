/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.aut;

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
     * een enum begint echter bij 0. Deze waarde is dan ook puur een dummy waarde en dient dan ook niet gebruikt te
     * worden.
     */
    DUMMY(0, null, null),
    /**
     * Geen berperking geeft aan dat er voor deze protocollering geen beperking is en dus altijd getoond mag worden.
     */
    GEEN_BEPERKING(1, "geen beperking", null),
    /**
     * Beperking geeft aan dat de protocollering maar beperkt getoond mag worden en standaard niet door de
     * gegevensambtenaar kan worden ingezien.
     */
    BEPERKING(2, "beperking", null),
    /**
     * Geheim geeft aan dat de protocollering geheim is en in principe door niemand en dus ook de burger ingezien
     * mag worden.
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
     * Retourneert de protocolleringscode welke door het systeem en de database gebruikt wordt voor het
     * protocolleringsniveau.
     * @return de code van het protocolleringsniveau.
     */
    public int getCode() {
        return code;
    }

    /**
     * Retourneert de naam van het protocolleringsniveau.
     * @return de naam van het protocolleringsniveau.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert de omschrijving van het protocolleringsniveau.
     * @return de omschrijving van het protocolleringsniveau.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
