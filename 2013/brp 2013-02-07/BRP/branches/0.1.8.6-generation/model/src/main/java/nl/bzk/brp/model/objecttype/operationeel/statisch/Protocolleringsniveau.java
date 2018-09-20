/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie 1.0.18.
 *
 */
package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * Het niveau van protocolleren dat van toepassing is.
 * @version 1.0.18.
 */
public enum Protocolleringsniveau {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", "", ""),
    /** Geen beperkingen. */
    GEEN_BEPERKINGEN("0", "Geen beperkingen", "Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens gelegde afnemersindicaties."),
    /** Gevoelig. */
    GEVOELIG("1", "Gevoelig", "Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens geregistreerde afnemersindicaties. Deze informatie is beperkt tot daartoe expliciet geautoriseerde gemeenteambtenaren."),
    /** Geheim. */
    GEHEIM("2", "Geheim", "Aan een burger wordt GEEN inzage verstrekt over de in kader van deze Autorisatie geleverde gegevens. Toegang tot deze gegevens is beperkt tot gemeenteambtenaren die daartoe expliciet zijn gautoriseerd.");

    /** De functionele code voor het protocolleringsniveau. */
    private final String code;
    /** De naam van het protocolleringsniveau. */
    private final String naam;
    /** De omschrijving van het protocolleringsniveau. */
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param code De functionele code voor het protocolleringsniveau.
     * @param naam De naam van het protocolleringsniveau.
     * @param omschrijving De omschrijving van het protocolleringsniveau.
     *
     */
    private Protocolleringsniveau(final String code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * @return De functionele code voor het protocolleringsniveau.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return De naam van het protocolleringsniveau.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return De omschrijving van het protocolleringsniveau.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
