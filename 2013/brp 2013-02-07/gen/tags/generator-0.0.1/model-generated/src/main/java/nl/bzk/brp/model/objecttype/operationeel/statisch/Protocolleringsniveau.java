/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Het niveau van protocolleren dat van toepassing is..
 */
public enum Protocolleringsniveau {

    /** DUMMY. */
    DUMMY("", "", ""),
    /** Geen beperkingen. */
    GEEN_BEPERKINGEN("0", "Geen beperkingen", "Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens gelegde afnemersindicaties."),
    /** Gevoelig. */
    GEVOELIG("1", "Gevoelig", "Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens geregistreerde afnemersindicaties. Deze informatie is beperkt tot daartoe expliciet geautoriseerde gemeenteambtenaren."),
    /** Geheim. */
    GEHEIM("2", "Geheim", "Aan een burger wordt GEEN inzage verstrekt over de in kader van deze Autorisatie geleverde gegevens. Toegang tot deze gegevens is beperkt tot gemeenteambtenaren die daartoe expliciet zijn gautoriseerd.");

    /** code. */
    private String code;
    /** naam. */
    private String naam;
    /** omschrijving. */
    private String omschrijving;

    /**
     * Constructor.
     *
     * @param code de code
     * @param naam de naam
     * @param omschrijving de omschrijving
     *
     */
    private Protocolleringsniveau(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * De functionele code voor het protocolleringsniveau..
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * De naam van het protocolleringsniveau..
     * @return String
     */
    public String getNaam() {
        return naam;
    }

    /**
     * De omschrijving van het protocolleringsniveau..
     * @return String
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
