/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

/**
 * Het niveau van protocolleren dat van toepassing is.
 *
 * Aan de hand van het protocolleringsniveau wordt bepaald of een bepaalde verstrekking van gegevens aan de burger
 * getoond mag worden (indien de burger daar om vraagt), alsmede of er een beperking is voor gegevensambtenaren voor het
 * inzien van deze protocolgegevens.
 *
 *
 *
 */
public enum Protocolleringsniveau {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY(((short) -1), "Dummy", "Dummy"),
    /**
     * Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens gelegde afnemersindicaties..
     */
    GEEN_BEPERKINGEN(((short) 0), "Geen beperkingen",
            "Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens gelegde afnemersindicaties."),
    /**
     * Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens geregistreerde afnemersindicaties. Deze
     * informatie
     * is beperkt tot daartoe expliciet geautoriseerde gemeenteambtenaren..
     */
    GEVOELIG(
            ((short) 1),
            "Gevoelig",
            "Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens geregistreerde afnemersindicaties. Deze informatie is beperkt tot daartoe expliciet geautoriseerde gemeenteambtenaren."),
    /**
     * Aan een burger wordt GEEN inzage verstrekt over de in kader van deze Autorisatie geleverde gegevens. Toegang tot
     * deze gegevens is beperkt tot gemeenteambtenaren die daartoe expliciet zijn gautoriseerd..
     */
    GEHEIM(
            ((short) 2),
            "Geheim",
            "Aan een burger wordt GEEN inzage verstrekt over de in kader van deze Autorisatie geleverde gegevens. Toegang tot deze gegevens is beperkt tot gemeenteambtenaren die daartoe expliciet zijn gautoriseerd.");

    private final Short  code;
    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor Protocolleringsniveau
     * @param naam Naam voor Protocolleringsniveau
     * @param omschrijving Omschrijving voor Protocolleringsniveau
     */
    private Protocolleringsniveau(final Short code, final String naam, final String omschrijving) {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Code van Protocolleringsniveau.
     *
     * @return Code.
     */
    public Short getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Protocolleringsniveau.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Protocolleringsniveau.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
